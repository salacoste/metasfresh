package org.adempiere.ad.persistence.modelgen;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Collection;

import org.adempiere.ad.security.TableAccessLevel;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * AD_Table/AD_Column related meta data.
 * 
 * @author tsa
 */
@Value
public class TableInfo
{
	int adTableId;
	String tableName;
	TableAccessLevel accessLevel;
	String entityType;
	ImmutableList<ColumnInfo> columnInfos;

	@Builder
	private TableInfo(
			final int adTableId,
			final String tableName,
			@NonNull final TableAccessLevel accessLevel,
			final String entityType,
			final Collection<ColumnInfo> columnInfos)
	{
		Check.assume(adTableId > 0, "AD_Table_ID > 0");
		Check.assumeNotEmpty(tableName, "tableName not empty");

		this.adTableId = adTableId;
		this.tableName = tableName;
		this.accessLevel = accessLevel;
		this.entityType = entityType;
		this.columnInfos = ImmutableList.copyOf(columnInfos);
	}
}
