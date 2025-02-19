package de.metas.handlingunits.inventory.tabcallout;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.Adempiere;

import de.metas.handlingunits.inventory.InventoryLineRecordService;
import de.metas.handlingunits.model.I_M_InventoryLine;
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


public class M_InventoryLineTabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(@NonNull final ICalloutRecord calloutRecord)
	{
		final I_M_InventoryLine inventoryLineRecord = calloutRecord.getModel(I_M_InventoryLine.class);

		final InventoryLineRecordService inventoryLineRecordService = Adempiere.getBean(InventoryLineRecordService.class);
		inventoryLineRecordService.updateHUAggregationTypeIfAllowed(inventoryLineRecord);
	}
}
