package org.adempiere.ad.persistence.modelgen;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class ModelGeneratorUtils
{
	private static final transient Logger logger = LogManager.getLogger(ModelGeneratorUtils.class);

	public static void writeJavaClassToDirectory(
			@NonNull final JavaClass javaClass,
			@NonNull final File directory)
	{
		writeJavaClassToFile(javaClass.getContent(), directory, javaClass.getClassname());
	}

	public static void writeJavaClassToFile(
			@NonNull final CharSequence content,
			@NonNull final File directory,
			@NonNull final String classname)
	{
		final File file = new File(directory, classname + ".java");

		try
		{
			file.getParentFile().mkdirs(); // make sure directory exists

			Files.write(content, file, Charsets.UTF_8);

			logger.info("Wrote {} ({} bytes)", file.getAbsolutePath(), file.length());
		}
		catch (final IOException ex)
		{
			throw new RuntimeException("Failed writing " + file, ex);
		}
	}
}
