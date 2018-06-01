package de.metas.purchasecandidate.availability;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

import de.metas.order.OrderAndLineId;
import de.metas.purchasecandidate.PurchaseCandidateGroup;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestException;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import de.metas.vendor.gateway.api.availability.TrackingId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

class AvailabilityCheckCommand
{
	// services
	private final VendorGatewayRegistry vendorGatewayRegistry;

	private final ImmutableListMultimap<BPartnerId, PurchaseCandidateGroupWithTrackingId> purchaseCandidatesByVendorId;

	@Builder
	private AvailabilityCheckCommand(
			@NonNull final PurchaseCandidatesAvailabilityRequest request,
			@NonNull final VendorGatewayRegistry vendorGatewayRegistry)
	{
		this.vendorGatewayRegistry = vendorGatewayRegistry;
		this.purchaseCandidatesByVendorId = request.getPurchaseCandidateGroups()
				.entrySet()
				.stream()
				.map(PurchaseCandidateGroupWithTrackingId::ofMapEntry)
				.collect(GuavaCollectors.toImmutableListMultimap(PurchaseCandidateGroupWithTrackingId::getVendorId));
	}

	public AvailabilityMultiResult checkAvailability()
	{
		final Set<BPartnerId> vendorIds = purchaseCandidatesByVendorId.keySet();

		return vendorIds.stream()
				.map(this::checkAvailabilityAndConvertThrowable)
				.reduce(AvailabilityMultiResult::merge)
				.orElse(AvailabilityMultiResult.EMPTY);
	}

	public void checkAvailabilityAsync(@NonNull final AvailabilityCheckCallback callback)
	{
		final Properties localCtx = Env.copyCtx(Env.getCtx());

		for (final BPartnerId vendorId : purchaseCandidatesByVendorId.keySet())
		{
			CompletableFuture
					.supplyAsync(() -> {
						try (final IAutoCloseable ctxWithinAsyncThread = Env.switchContext(localCtx))
						{
							return checkAvailabilityAndConvertThrowable(vendorId);
						}
					})
					.whenComplete((result, throwable) -> {

						final boolean resultWasFound = result != null && !result.isEmpty();
						final boolean errorOccured = throwable != null;
						if (resultWasFound || errorOccured)
						{
							callback.onResult(result, throwable);
						}
					});
		}
	}

	private AvailabilityMultiResult checkAvailabilityAndConvertThrowable(final BPartnerId vendorId)
	{
		if (!vendorProvidesAvailabilityCheck(vendorId))
		{
			return AvailabilityMultiResult.EMPTY;
		}

		final Map<AvailabilityRequestItem, PurchaseCandidateGroupWithTrackingId> requestItem2purchaseCandidate = createRequestItems(vendorId);
		if (requestItem2purchaseCandidate.isEmpty())
		{
			return AvailabilityMultiResult.EMPTY;
		}

		try
		{
			return checkAvailability0(vendorId, requestItem2purchaseCandidate);
		}
		catch (final Throwable ex)
		{
			throw convertThrowable(ex);
		}
	}

	private AvailabilityMultiResult checkAvailability0(
			@NonNull final BPartnerId vendorId,
			@NonNull final Map<AvailabilityRequestItem, PurchaseCandidateGroupWithTrackingId> requestItem2purchaseCandidate)
	{
		final VendorGatewayService vendorGatewayService = vendorGatewayRegistry
				.getSingleVendorGatewayService(vendorId.getRepoId())
				.orElse(null);
		if (vendorGatewayService == null)
		{
			return AvailabilityMultiResult.EMPTY;
		}

		final AvailabilityResponse availabilityResponse = vendorGatewayService.retrieveAvailability(AvailabilityRequest.builder()
				.vendorId(vendorId.getRepoId())
				.availabilityRequestItems(requestItem2purchaseCandidate.keySet())
				.build());

		final ImmutableList.Builder<AvailabilityResult> result = ImmutableList.builder();
		for (final AvailabilityResponseItem responseItem : availabilityResponse.getAvailabilityResponseItems())
		{
			final AvailabilityResult availabilityResult = AvailabilityResult
					.prepareBuilderFor(responseItem)
					.build();

			result.add(availabilityResult);
		}

		return AvailabilityMultiResult.of(result.build());
	}

	private RuntimeException convertThrowable(@NonNull final Throwable throwable)
	{
		final boolean isAvailabilityRequestException = throwable instanceof AvailabilityRequestException;
		if (!isAvailabilityRequestException)
		{
			return AdempiereException.wrapIfNeeded(throwable);
		}

		final AvailabilityRequestException availabilityRequestException = AvailabilityRequestException.cast(throwable);

		final ImmutableList<AvailabilityException.ErrorItem> errorItems = availabilityRequestException
				.getRequestItem2Exception()
				.entrySet()
				.stream()
				.map(entry -> AvailabilityException.ErrorItem.builder()
						.trackingId(entry.getKey().getTrackingId())
						.error(entry.getValue())
						.build())
				.collect(ImmutableList.toImmutableList());

		return new AvailabilityException(errorItems);
	}

	private Map<AvailabilityRequestItem, PurchaseCandidateGroupWithTrackingId> createRequestItems(final BPartnerId vendorId)
	{
		return purchaseCandidatesByVendorId.get(vendorId)
				.stream()
				.filter(group -> !group.isProcessed())
				.collect(GuavaCollectors.toImmutableMapByKey(PurchaseCandidateGroupWithTrackingId::createAvailabilityRequestItem));
	}

	private boolean vendorProvidesAvailabilityCheck(@NonNull final BPartnerId vendorBPartnerId)
	{
		return vendorGatewayRegistry.getSingleVendorGatewayService(vendorBPartnerId.getRepoId()).isPresent();
	}

	@lombok.Value
	@lombok.Builder
	private static final class PurchaseCandidateGroupWithTrackingId
	{
		public static PurchaseCandidateGroupWithTrackingId ofMapEntry(Map.Entry<TrackingId, PurchaseCandidateGroup> entry)
		{
			return builder().trackingId(entry.getKey()).purchaseCandidateGroup(entry.getValue()).build();
		}

		@lombok.NonNull
		TrackingId trackingId;
		@lombok.NonNull
		@lombok.Getter(AccessLevel.NONE)
		PurchaseCandidateGroup purchaseCandidateGroup;

		public BPartnerId getVendorId()
		{
			return purchaseCandidateGroup.getVendorId();
		}

		public boolean isProcessed()
		{
			return purchaseCandidateGroup.isProcessed();
		}

		public AvailabilityRequestItem createAvailabilityRequestItem()
		{
			final ProductAndQuantity productAndQuantity = purchaseCandidateGroup.createProductAndQuantity();

			final PurchaseCandidateId purchaseCandidateId = purchaseCandidateGroup.getSinglePurchaseCandidateIdOrNull();
			OrderAndLineId salesOrderAndLineId = purchaseCandidateGroup.getSingleOrderAndLineIdOrNull();

			return AvailabilityRequestItem.builder()
					.trackingId(trackingId)
					.productAndQuantity(productAndQuantity)
					.purchaseCandidateId(PurchaseCandidateId.getRepoIdOr(purchaseCandidateId, -1))
					.salesOrderLineId(OrderAndLineId.getOrderLineRepoIdOr(salesOrderAndLineId, -1))
					.build();
		}

	}
}
