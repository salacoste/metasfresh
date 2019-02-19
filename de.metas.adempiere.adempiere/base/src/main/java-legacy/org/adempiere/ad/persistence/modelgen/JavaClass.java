package org.adempiere.ad.persistence.modelgen;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Data
class JavaClass
{
	private final String packageName;
	private final String classname;
	private final CharSequence content;

	@Builder
	private JavaClass(
			@NonNull final String packageName,
			@NonNull final String classname,
			@NonNull final String content)
	{
		this.packageName = packageName;
		this.classname = classname;
		this.content = content;
	}
}
