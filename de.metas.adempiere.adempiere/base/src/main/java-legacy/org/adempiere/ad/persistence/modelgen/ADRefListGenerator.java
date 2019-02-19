package org.adempiere.ad.persistence.modelgen;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;

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

/**
 * Generates Java code for AD_Ref_List items.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ADRefListGenerator
{
	private final ListInfo listInfo;

	private final String classColumnName;

	private final String enumPackageName;
	private final String enumClassname;

	@Builder
	private ADRefListGenerator(
			@NonNull final ListInfo listInfo,
			//
			@NonNull final String classColumnName,
			//
			@NonNull final String enumPackageName,
			@NonNull final String enumClassname)
	{
		this.listInfo = listInfo;
		//
		this.classColumnName = classColumnName;
		//
		this.enumPackageName = enumPackageName;
		this.enumClassname = enumClassname;
	}

	public String generateEnum()
	{
		final StringBuilder javaCode = new StringBuilder();
		javaCode.append("package ").append(enumPackageName).append(";");

		//
		// Imports
		javaCode.append("\n\n");
		javaCode.append("\n").append("import java.util.Arrays;");
		javaCode.append("\n").append("import org.adempiere.exceptions.AdempiereException;");
		javaCode.append("\n").append("import com.google.common.collect.ImmutableMap;");
		javaCode.append("\n").append("import com.google.common.collect.Maps;");
		javaCode.append("\n").append("import lombok.NonNull;");
		javaCode.append("\n").append("import de.metas.util.lang.ReferenceListAwareEnum;");

		//
		// Enum start
		javaCode.append("\n\n");
		javaCode.append("\n").append("public enum ").append(enumClassname).append("implements ReferenceListAwareEnum");
		javaCode.append("\n{");

		//
		// Enum names
		for (int i = 0, lastIndex = listInfo.getItems().size() - 1; i <= lastIndex; i++)
		{
			final ListItemInfo item = listInfo.getItems().get(i);

			String name = createJavaName(item);
			javaCode.append("\n\t").append(name).append("(\"").append(item.getValue()).append("\")");
			if (i != lastIndex)
			{
				javaCode.append(", //");
			}
			else
			{
				javaCode.append("; //");
			}
		}

		//
		// AD_REFERENCE_ID constant
		javaCode.append("\n\n");
		javaCode.append("\n\t").append("public static final int AD_REFERENCE_ID=").append(listInfo.getAdReferenceId()).append(";");

		//
		// Fields
		javaCode.append("\n\n");
		javaCode.append("\n\t").append("private final String code");

		//
		// Constructor
		javaCode.append("\n\n");
		javaCode.append("\n\t").append("DeliveryRule(final String code)");
		javaCode.append("\n\t").append("{");
		javaCode.append("\n\t\t").append("this.code = code;");
		javaCode.append("\n\t").append("}");

		//
		// Getter
		javaCode.append("\n\n");
		javaCode.append("\n\t").append("@Override");
		javaCode.append("\n\t").append("public String getCode()");
		javaCode.append("\n\t").append("{");
		javaCode.append("\n\t\t").append("return code;");
		javaCode.append("\n\t").append("}");

		//
		// Static factories
		javaCode.append("\n");
		javaCode.append("\n\t").append("public static DeliveryRule ofNullableCode(final String code)");
		javaCode.append("\n\t").append("{");
		javaCode.append("\n\t").append("\treturn code != null ? ofCode(code) : null;");
		javaCode.append("\n\t").append("}");
		//
		javaCode.append("\n");
		javaCode.append("\n\t").append("public static DeliveryRule ofCode(@NonNull final String code)");
		javaCode.append("\n\t").append("{");
		javaCode.append("\n\t\t").append("DeliveryRule type = typesByCode.get(code);");
		javaCode.append("\n\t\t").append("if (type == null)");
		javaCode.append("\n\t\t").append("{");
		javaCode.append("\n\t\t\t").append("throw new AdempiereException(\"No \" + " + enumClassname + ".class.getName() + \" found for code: \" + code);");
		javaCode.append("\n\t\t").append("}");
		javaCode.append("\n\t\t").append("return type;");
		javaCode.append("\n\t").append("}");
		//
		javaCode.append("\n");
		javaCode.append("\n\t").append("private static final ImmutableMap<String, " + enumClassname + "> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), " + enumClassname + "::getCode);");

		//
		// Enum end
		javaCode.append("\n}");

		//
		return javaCode.toString();
	}

	/**
	 * Generates java constants.
	 *
	 * @return java constants. Example:
	 *
	 *         <pre>
	 *         public static final int NEXTACTION_AD_Reference_ID = 219;
	 *         public static final String NEXTACTION_None = &quot;N&quot;;
	 *         public static final String NEXTACTION_FollowUp = &quot;F&quot;;
	 *         </pre>
	 */
	public String generateConstants()
	{
		final String classColumnNameUC = classColumnName.toUpperCase();
		final String enumClassnameFQ = enumPackageName + "." + enumClassname;

		final String referenceName = listInfo.getName();

		final StringBuilder javaCode = new StringBuilder();
		javaCode.append("\n\t/** ")
				.append("\n\t * Reference name: ").append(referenceName)
				.append("\n\t */")
				.append("\n\tpublic static final int ").append(classColumnNameUC).append("_AD_Reference_ID=").append(enumClassnameFQ).append(".AD_REFERENCE_ID").append(";");

		//
		for (final ListItemInfo item : listInfo.getItems())
		{
			final String name = createJavaName(item);
			javaCode.append("\n\tpublic static final String ")
					.append(classColumnNameUC).append("_").append(name)
					.append(" = ")
					// .append("\"").append(value).append("\"")
					.append(enumClassnameFQ).append(".").append(name)
					.append(";");
		}

		return javaCode.toString();
	}

	private static final String createJavaName(final ListItemInfo item)
	{
		String name = item.getName();
		final String valueName = item.getValueName();
		if (!Check.isEmpty(valueName, true))
		{
			name = valueName;
		}

		return createJavaName(name);
	}

	private static final String createJavaName(final String name)
	{
		final char[] nameArray = name.toCharArray();
		final StringBuilder nameClean = new StringBuilder();
		boolean initCap = true;
		for (int i = 0; i < nameArray.length; i++)
		{
			final char c = nameArray[i];
			// metas: teo_sarca: begin
			// replacing german umlauts with equivalent ascii
			if (c == '\u00c4')
			{
				nameClean.append("Ae");
				initCap = false;
			}
			else if (c == '\u00dc')
			{
				nameClean.append("Ue");
				initCap = false;
			}
			else if (c == '\u00d6')
			{
				nameClean.append("Oe");
				initCap = false;
			}
			else if (c == '\u00e4')
			{
				nameClean.append("ae");
				initCap = false;
			}
			else if (c == '\u00fc')
			{
				nameClean.append("ue");
				initCap = false;
			}
			else if (c == '\u00f6')
			{
				nameClean.append("oe");
				initCap = false;
			}
			else if (c == '\u00df')
			{
				nameClean.append("ss");
				initCap = false;
			}
			else
			// metas: teo_sarca: end
			if (Character.isJavaIdentifierPart(c))
			{
				if (initCap)
				{
					nameClean.append(Character.toUpperCase(c));
				}
				else
				{
					nameClean.append(c);
				}
				initCap = false;
			}
			else
			{
				if (c == '+')
				{
					nameClean.append("Plus");
				}
				else if (c == '-')
				{
					nameClean.append("_");
				}
				else if (c == '>')
				{
					if (name.indexOf('<') == -1)
					{
						nameClean.append("Gt");
					}
				}
				else if (c == '<')
				{
					if (name.indexOf('>') == -1)
					{
						nameClean.append("Le");
					}
				}
				else if (c == '!')
				{
					nameClean.append("Not");
				}
				else if (c == '=')
				{
					nameClean.append("Eq");
				}
				else if (c == '~')
				{
					nameClean.append("Like");
				}
				initCap = true;
			}
		}

		return nameClean.toString();
	}
}
