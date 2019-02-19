package org.adempiere.ad.persistence.modelgen;

import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/**
 * AD_Reference/AD_Ref_List related meta data.
 * 
 * @author tsa
 *
 */
@Value
public class ListInfo
{
	int adReferenceId;
	String name;
	String entityType;
	ImmutableList<ListItemInfo> items;

	@Builder
	private ListInfo(
			final int adReferenceId,
			@NonNull final String name,
			@NonNull final String entityType,
			@NonNull @Singular final List<ListItemInfo> items)
	{
		// Check.assumeNotEmpty(items, "items not empty"); // allow empty list

		this.adReferenceId = adReferenceId;
		this.name = name;
		this.entityType = entityType;
		this.items = ImmutableList.copyOf(items);
	}
}
