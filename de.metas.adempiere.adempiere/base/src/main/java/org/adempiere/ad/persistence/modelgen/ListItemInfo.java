package org.adempiere.ad.persistence.modelgen;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * AD_Ref_List related meta data.
 * 
 * @author tsa
 *
 */
@Value
class ListItemInfo
{
	private final String value;
	private final String name;
	private final String valueName;
	private final String entityType;

	@Builder
	private ListItemInfo(
			@NonNull final String value,
			@NonNull final String name,
			@Nullable final String valueName,
			@NonNull final String entityType)
	{
		this.value = value;
		this.name = name;
		this.valueName = valueName;
		this.entityType = entityType;
	}
}
