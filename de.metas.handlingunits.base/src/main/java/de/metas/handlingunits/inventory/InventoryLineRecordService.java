package de.metas.handlingunits.inventory;

import static de.metas.inventory.InventoryConstants.HUAggregationType_SINGLE_HU;
import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Inventory;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.document.DocBaseAndSubType;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inventory.AggregationType;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class InventoryLineRecordService
{

	private static final String MSG_EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE = "de.metas.handlingunits.inventory.ExistingLinesWithDifferentHUAggregationType";

	private InventoryLineRepository inventoryLineRepository;

	private InventoryLineRecordService(@NonNull final InventoryLineRepository inventoryLineRepository)
	{
		this.inventoryLineRepository = inventoryLineRepository;
	}

	public void syncToHUs(@NonNull final I_M_Inventory inventoryRecord)
	{
		final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

		final InventoryId inventoryId = createInventoryId(inventoryRecord);
		final ImmutableList<InventoryLine> inventoryLines = inventoryLineRepository.getByInventoryId(inventoryId);

		for (final InventoryLine inventoryLine : inventoryLines)
		{
			final I_M_InventoryLine inventoryLineRecord = inventoryLineRepository.getInventoryLineRecordFor(inventoryLine);

			// 'inventoryLine' might or might not have HUs..but inventoryLineWithHUs will have
			final InventoryLine inventoryLineWithHUs;

			final Quantity qtyDiff = inventoryBL.getMovementQty(inventoryLineRecord);
			if (qtyDiff.signum() == 0)
			{
				continue;
			}
			else if (qtyDiff.signum() > 0)
			{
				inventoryLineWithHUs = addQtyDiffToHU(inventoryLine);
			}
			else // qtyDiff < 0
			{
				subtractQtyDiffFromHU(inventoryLine);
				inventoryLineWithHUs = inventoryLine;
			}

			for (final InventoryLineHU inventoryLineHU : inventoryLineWithHUs.getInventoryLineHUs())
			{
				final HuId huId = Check.assumeNotNull(inventoryLineHU.getHuId(), "Every inventoryLineHU instance needs to have an HuId; inventoryLineHU={}", inventoryLineHU);

				final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
				transferAttributesToHU(inventoryLineRecord, hu);
				InterfaceWrapperHelper.save(hu);
			}
		}
	}

	private final void transferAttributesToHU(
			@NonNull final I_M_InventoryLine inventoryLine,
			@NonNull final I_M_HU hu)
	{
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor();

		executor.run((IHUContextProcessor)huContext -> {

			final IHUTransactionAttributeBuilder trxAttributesBuilder = executor.getTrxAttributesBuilder();
			final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();

			//
			// Transfer ASI attributes from inventory line to our HU
			final IAttributeStorage asiAttributeStorageFrom = attributeStorageFactory.getAttributeStorageIfHandled(inventoryLine);
			if (asiAttributeStorageFrom == null)
			{
				return IHUContextProcessor.NULL_RESULT; // can't transfer from nothing
			}
			final IAttributeStorage huAttributeStorageTo = attributeStorageFactory.getAttributeStorage(hu);

			final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
			final IHUStorage huStorageFrom = storageFactory.getStorage(hu);

			final IHUAttributeTransferRequest request = new HUAttributeTransferRequestBuilder(huContext)
					.setProductId(ProductId.ofRepoId(inventoryLine.getM_Product_ID()))
					.setQty(Services.get(IInventoryBL.class).getMovementQty(inventoryLine).getAsBigDecimal())
					.setUOM(inventoryLine.getC_UOM())
					.setAttributeStorageFrom(asiAttributeStorageFrom)
					.setAttributeStorageTo(huAttributeStorageTo)
					.setHUStorageFrom(huStorageFrom)
					.create();

			trxAttributesBuilder.transferAttributes(request);

			return IHUContextProcessor.NULL_RESULT; // we don't care
		});
	}

	private InventoryLine addQtyDiffToHU(@NonNull final InventoryLine inventoryLine)
	{
		final InventoryLineBuilder result = inventoryLine
				.toBuilder()
				.clearInventoryLineHUs();

		boolean needToSaveInventoryLine = false;

		if (inventoryLine.isSingleHUAggregation())
		{
			final I_M_InventoryLine inventoryLineRecord = inventoryLineRepository.getInventoryLineRecordFor(inventoryLine);

			final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
			final Quantity qtyDiff = inventoryBL.getMovementQty(inventoryLineRecord);

			final IAllocationSource source = createInventoryLineAllocationSourceOrDestination(inventoryLineRecord);
			final IAllocationDestination huDestination = createHUAllocationDestination(inventoryLineRecord);

			final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
					.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext())
					.setDateAsToday()
					.setProduct(inventoryLine.getProductId())
					.setQuantity(qtyDiff)
					.setFromReferencedModel(inventoryLineRecord)
					.setForceQtyAllocation(true)
					.create();

			HULoader.of(source, huDestination)
					.load(request);

			if (inventoryLine.getSingleHU().getHuId() == null)
			{
				final InventoryLineHU resultInventoryLineHU = inventoryLine
						.getSingleHU()
						.toBuilder()
						.huId(HuId.ofRepoId(extractSingleCreatedHUId(huDestination)))
						.build();
				result.inventoryLineHU(resultInventoryLineHU);
				needToSaveInventoryLine = true;
			}
		}
		else
		{
			for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
			{
				final IAllocationDestination huDestination = syncQtyToInventoryLine(inventoryLine, inventoryLineHU);

				if (inventoryLineHU.getHuId() == null)
				{
					final InventoryLineHU resultInventoryLineHU = inventoryLine
							.getSingleHU()
							.toBuilder()
							.huId(HuId.ofRepoId(extractSingleCreatedHUId(huDestination)))
							.build();
					result.inventoryLineHU(resultInventoryLineHU);
					needToSaveInventoryLine = true;
				}
				else
				{
					result.inventoryLineHU(inventoryLineHU);

				}
			}
		}

		final InventoryLine resultInventoryLine = result.build();
		if (needToSaveInventoryLine)
		{
			inventoryLineRepository.save(resultInventoryLine);
		}
		return resultInventoryLine;
	}

	private IAllocationDestination syncQtyToInventoryLine(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final InventoryLineHU inventoryLineHU)
	{
		final Quantity qtyDiff = inventoryLineHU.getQtyCount().subtract(inventoryLineHU.getQtyBook());
		final ProductId productId = inventoryLine.getProductId();
		final PlainProductStorage productStorage = new PlainProductStorage(productId, qtyDiff.getUOM(), qtyDiff.getAsBigDecimal());

		final I_M_InventoryLine inventoryLineRecord = inventoryLineRepository.getInventoryLineRecordFor(inventoryLine);
		final IAllocationSource source = new GenericAllocationSourceDestination(productStorage, inventoryLineRecord);

		final IAllocationDestination huDestination;
		if (inventoryLineHU.getHuId() == null)
		{
			huDestination = HUProducerDestination
					.ofVirtualPI()
					.setHUStatus(X_M_HU.HUSTATUS_Active)
					.setLocatorId(inventoryLine.getLocatorId());
		}
		else
		{
			huDestination = HUListAllocationSourceDestination
					.ofHUId(inventoryLineHU.getHuId());
		}

		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext())
				.setDateAsToday()
				.setProduct(inventoryLine.getProductId())
				.setQuantity(qtyDiff)
				.setFromReferencedModel(inventoryLineRecord)
				.setForceQtyAllocation(true)
				.create();

		HULoader.of(source, huDestination)
				.load(request);
		return huDestination;
	}

	private void subtractQtyDiffFromHU(final InventoryLine inventoryLine)
	{
		if (inventoryLine.isSingleHUAggregation())
		{
			final I_M_InventoryLine inventoryLineRecord = inventoryLineRepository.getInventoryLineRecordFor(inventoryLine);

			final HuId singleHuId = inventoryLine.getSingleHU().getHuId();
			if (singleHuId == null)
			{
				throw new FillMandatoryException(I_M_InventoryLine.COLUMNNAME_M_HU_ID)
						.setParameter(I_M_InventoryLine.COLUMNNAME_Line, inventoryLineRecord.getLine())
						.appendParametersToMessage();
			}

			final Quantity qtyDiff = Services.get(IInventoryBL.class).getMovementQty(inventoryLineRecord).negate();

			final IAllocationSource source = HUListAllocationSourceDestination.ofHUId(singleHuId);
			final IAllocationDestination destination = createInventoryLineAllocationSourceOrDestination(inventoryLineRecord);

			final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
					.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext())
					.setDateAsToday()
					.setProduct(inventoryLine.getProductId())
					.setQuantity(qtyDiff)
					.setFromReferencedModel(inventoryLineRecord)
					.setForceQtyAllocation(true)
					.create();

			HULoader.of(source, destination)
					.load(request);
		}
		else
		{
			for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
			{
				syncQtyToInventoryLine(inventoryLine, inventoryLineHU);
			}
		}
	}

	private GenericAllocationSourceDestination createInventoryLineAllocationSourceOrDestination(final I_M_InventoryLine inventoryLine)
	{
		final ProductId productId = ProductId.ofRepoId(inventoryLine.getM_Product_ID());
		final Quantity qtyDiff = Services.get(IInventoryBL.class).getMovementQty(inventoryLine);
		final PlainProductStorage productStorage = new PlainProductStorage(productId, qtyDiff.getUOM(), qtyDiff.getAsBigDecimal());
		return new GenericAllocationSourceDestination(productStorage, inventoryLine);
	}

	private IAllocationDestination createHUAllocationDestination(final I_M_InventoryLine inventoryLine)
	{
		if (inventoryLine.getM_HU_ID() > 0)
		{
			return HUListAllocationSourceDestination.ofHUId(inventoryLine.getM_HU_ID());
		}
		// TODO handle: else if(inventoryLine.getM_HU_PI_Item_Product_ID() > 0)
		else
		{
			final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
			final LocatorId locatorId = warehousesRepo.getLocatorIdByRepoIdOrNull(inventoryLine.getM_Locator_ID());

			return HUProducerDestination.ofVirtualPI()
					.setHUStatus(X_M_HU.HUSTATUS_Active)
					.setLocatorId(locatorId);
		}
	}

	private int extractSingleCreatedHUId(@NonNull final IAllocationDestination huDestination)
	{
		if (huDestination instanceof IHUProducerAllocationDestination)
		{
			final List<I_M_HU> createdHUs = ((IHUProducerAllocationDestination)huDestination).getCreatedHUs();
			if (createdHUs.isEmpty())
			{
				throw new HUException("No HU was created by " + huDestination);
			}
			else if (createdHUs.size() > 1)
			{
				throw new HUException("Only one HU expected to be created by " + huDestination);
			}
			else
			{
				return createdHUs.get(0).getM_HU_ID();
			}
		}
		else
		{
			throw new HUException("No HU was created by " + huDestination);
		}
	}

	public void updateHUAggregationTypeIfAllowed(@NonNull final I_M_Inventory inventoryRecord)
	{
		final DocBaseAndSubType docBaseAndSubType = extractDocBaseAndSubType(inventoryRecord);

		// note that the loaded lines' M_Inventory's docType is still the old one
		final ImmutableList<InventoryLine> inventoryLines = inventoryLineRepository.getByInventoryId(createInventoryId(inventoryRecord));
		for (final InventoryLine inventoryLine : inventoryLines)
		{
			final I_M_InventoryLine inventoryLineRecord = inventoryLineRepository.getInventoryLineRecordFor(inventoryLine);
			updateHUAggregationTypeIfAllowed(inventoryLineRecord, docBaseAndSubType);
			saveRecord(inventoryLineRecord);
		}
	}

	private InventoryId createInventoryId(final I_M_Inventory inventoryRecord)
	{
		return InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());
	}

	public void updateHUAggregationTypeIfAllowed(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final DocBaseAndSubType baseAndSubType = extractDocBaseAndSubType(inventoryLineRecord.getM_Inventory());

		updateHUAggregationTypeIfAllowed(inventoryLineRecord, baseAndSubType);
	}

	private DocBaseAndSubType extractDocBaseAndSubType(@Nullable final I_M_Inventory inventoryRecord)
	{
		if (inventoryRecord == null || inventoryRecord.getC_DocType_ID() <= 0)
		{
			return null; // nothing to extract
		}

		final I_C_DocType docTypeRecord = inventoryRecord.getC_DocType();
		final DocBaseAndSubType baseAndSubType = DocBaseAndSubType.of(docTypeRecord.getDocBaseType(), docTypeRecord.getDocSubType());
		return baseAndSubType;
	}

	private void updateHUAggregationTypeIfAllowed(
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			@Nullable final DocBaseAndSubType baseAndSubType)
	{
		if (baseAndSubType == null)
		{
			return; // nothing to do
		}

		final String refListValueToUse = Optional
				.ofNullable(AggregationType.getByDocType(baseAndSubType))
				.map(AggregationType::getRefListValue)
				.orElse(HUAggregationType_SINGLE_HU); // the default

		if (Objects.equals(refListValueToUse, inventoryLineRecord.getHUAggregationType()))
		{
			return; // nothing to do
		}

		if (!isNew(inventoryLineRecord) && !Check.isEmpty(inventoryLineRecord.getHUAggregationType(), true))
		{
			// this line already has a different aggregation type
			final ITranslatableString message = Services.get(IMsgBL.class)
					.getTranslatableMsgText(MSG_EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE);
			throw new AdempiereException(message).markAsUserValidationError();
		}

		inventoryLineRecord.setHUAggregationType(refListValueToUse);
	}
}
