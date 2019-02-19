/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 * Contributor(s): Carlos Ruiz - globalqss *
 * Teo Sarca - www.arhipac.ro *
 * Trifon Trifonov *
 *****************************************************************************/
package org.adempiere.ad.persistence.modelgen;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.NonNull;

/**
 * Generate Model Classes extending PO.
 * Base class for CMP interface - will be extended to create byte code directly
 *
 * @author Jorg Janke
 * @version $Id: GenerateModel.java,v 1.42 2005/05/08 15:16:56 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1781629 ] Don't use Env.NL in model class/interface generators
 *         <li>FR [ 1781630 ] Generated class/interfaces have a lot of unused imports
 *         <li>BF [
 *         1781632 ] Generated class/interfaces should be UTF-8
 *         <li>FR [ xxxxxxx ] better formating of generated source
 *         <li>FR [ 1787876 ] ModelClassGenerator: list constants should be ordered
 *         <li>FR
 *         [ 1803309 ] Model generator: generate get method for Search cols
 *         <li>FR [ 1990848 ] Generated Models: remove hardcoded field length
 *         <li>FR [ 2343096 ] Model Generator: Improve Reference
 *         Class Detection
 *         <li>BF [ 2780468 ] ModelClassGenerator: not generating methods for Created*
 *         <li>--
 *         <li>FR [ 2848449 ] ModelClassGenerator: Implement model getters
 *         https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2848449&group_id=176962
 * @author Victor Perez, e-Evolution
 *         <li>FR [ 1785001 ] Using ModelPackage of EntityType to Generate Model Class
 */
public class ModelClassGenerator
{
	private static final String NL = "\n";
	private static final Set<String> COLUMNNAMES_STANDARD = ImmutableSet.of("AD_Client_ID", "AD_Org_ID", "IsActive", "Created", "CreatedBy", "Updated", "UpdatedBy");
	private static final String PLACEHOLDER_serialVersionUID = "[*serialVersionUID*]";

	private final String packageName;
	private final TableInfo tableInfo;
	private final Set<String> classesToImport = new TreeSet<String>();

	public ModelClassGenerator(
			@NonNull final TableInfo tableInfo,
			@NonNull File directory,
			@NonNull String packageName)
	{
		this.packageName = packageName;
		this.tableInfo = tableInfo;

		final JavaClass javaClass = generateJavaCode();
		ModelGeneratorUtils.writeJavaClassToDirectory(javaClass, directory);
	}

	private JavaClass generateJavaCode()
	{
		final String tableName = tableInfo.getTableName();
		final String keyColumn = tableName + "_ID";
		final String className = "X_" + tableName;

		final StringBuilder javaCode_gettersAndSetters = createColumns(tableInfo);

		//
		final StringBuilder javaCode = new StringBuilder()
				.append("/** Generated Model - DO NOT CHANGE */").append(NL)
				.append("package " + packageName + ";").append(NL)
				.append(NL);

		addImportClass(java.util.Properties.class);
		addImportClass(java.sql.ResultSet.class);
		createImports(javaCode);

		//
		// Class start
		javaCode.append("/**").append(NL)
				.append(" * TableName: ").append(tableName).append(NL)
				.append(" */").append(NL)
				.append("@SuppressWarnings(\"javadoc\")").append(NL) // metas
				.append("public class ").append(className)
				.append(" extends org.compiere.model.PO")
				.append(" implements I_").append(tableName)
				.append(", org.compiere.model.I_Persistent ")
				.append(NL)
				.append("{").append(NL);

		//
		// serialVersionUID: will be generated before save
		javaCode.append(NL)
				.append("\tprivate static final long serialVersionUID = ").append(PLACEHOLDER_serialVersionUID).append("L;").append(NL);

		//
		// Standard Constructor
		javaCode.append(NL)
				.append("\t/** Standard Constructor */").append(NL)
				.append("\tpublic ").append(className).append(" (final Properties ctx, final int ").append(keyColumn).append(", final String trxName)").append(NL)
				.append("\t{").append(NL)
				.append("\t\tsuper (ctx, ").append(keyColumn).append(", trxName);").append(NL)
				.append("\t}").append(NL);

		//
		// Load Constructor
		javaCode.append(NL)
				.append("\t/** Load Constructor */").append(NL)
				.append("\tpublic ").append(className).append(" (final Properties ctx, final ResultSet rs, final String trxName)").append(NL)
				.append("\t{").append(NL)
				.append("\t\tsuper (ctx, rs, trxName);").append(NL)
				.append("\t}").append(NL);

		// initPO
		javaCode.append(NL)
				.append("\t/** Load Meta Data */").append(NL)
				.append("\t@Override").append(NL)
				// metas
				.append("\tprotected org.compiere.model.POInfo initPO (final Properties ctx)").append(NL)
				.append("\t{").append(NL)
				.append("\t\treturn org.compiere.model.POInfo.getPOInfo (Table_Name);").append(NL)
				.append("\t}").append(NL);

		//
		// Getters/Setters
		javaCode.append(NL)
				.append(javaCode_gettersAndSetters);

		//
		// Class end
		javaCode.append(NL)
				.append("}")
				.append(NL);

		String javaCodeFinal = javaCode.toString();

		//
		// Generate serial number
		{
			final int serialVersionUID = javaCodeFinal.hashCode();
			javaCodeFinal = javaCodeFinal.replace(PLACEHOLDER_serialVersionUID, String.valueOf(serialVersionUID));
		}

		return JavaClass.builder()
				.classname(className)
				.packageName(packageName)
				.content(javaCodeFinal)
				.build();
	}

	/**
	 * Create Column access methods
	 */
	private StringBuilder createColumns(final TableInfo tableInfo)
	{
		final StringBuilder sb = new StringBuilder();

		boolean isKeyNamePairCreated = false; // true if the method "getKeyNamePair" is already generated
		final List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
		for (final ColumnInfo columnInfo : columnInfos)
		{
			// Skip standard columns because for those we already have methods in org.compiere.model.PO
			if (COLUMNNAMES_STANDARD.contains(columnInfo.getColumnName()))
			{
				continue;
			}

			// Create record info KeyNamePair
			if (columnInfo.getSeqNo() == 1 && columnInfo.isIdentifier())
			{
				if (!isKeyNamePairCreated)
				{
					sb.append(createKeyNamePair(columnInfo.getColumnName(), columnInfo.getDisplayType()));
					isKeyNamePairCreated = true;
				}
				else
				{
					throw new RuntimeException("More than one primary identifier found: " + columnInfo);
				}
			}

			sb.append(createColumnMethods(columnInfo));
		}

		return sb;
	}	// createColumns

	/**
	 * Create set/get methods for column
	 * 
	 * @param mandatory init call for mandatory columns
	 * @param columnInfo
	 * @return set/get methods (java code)
	 */
	private String createColumnMethods(final ColumnInfo columnInfo)
	{
		final Class<?> clazz = ModelInterfaceGenerator.getClass(columnInfo);
		final int displayType = columnInfo.getDisplayType();
		final String dataType = ModelInterfaceGenerator.getDataTypeName(clazz, displayType);
		final String columnName = columnInfo.getColumnName();

		String defaultValue = columnInfo.getDefaultValue();
		if (defaultValue == null)
			defaultValue = "";

		//
		// Set
		String setValue = "\t\tset_Value";
		if (columnInfo.isEncrypted())
			setValue = "\t\tset_ValueE";
		// Handle isUpdateable
		if (!columnInfo.isUpdateable())
		{
			setValue = "\t\tset_ValueNoCheck";
			if (columnInfo.isEncrypted())
				setValue = "\t\tset_ValueNoCheckE";
		}

		final StringBuilder sb = new StringBuilder();

		// TODO - New functionality
		// 1) Must understand which class to reference
		if (DisplayType.isID(displayType) && !columnInfo.isKey())
		{
			String fieldName = ModelInterfaceGenerator.getFieldName(columnName);
			String referenceClassName = ModelInterfaceGenerator.getReferenceClassName(columnInfo);
			//
			if (fieldName != null && referenceClassName != null)
			{
				sb.append(NL)
						.append("\t@Override").append(NL) // metas
						.append("\tpublic " + referenceClassName + " get").append(fieldName).append("() throws RuntimeException").append(NL)
						.append("\t{").append(NL)
						// .append("\t\treturn ("+referenceClassName+")MTable.get(getCtx(), "+referenceClassName+".Table_Name)").append(NL)
						// .append("\t\t\t.getPO(get"+columnName+"(), get_TrxName());")
						.append("\t\treturn get_ValueAsPO(COLUMNNAME_" + columnName + ", " + referenceClassName + ".class);").append(NL) // metas: new model getter
						/**/
						.append("\t}").append(NL);

				// metas: begin: model setter
				sb.append(NL)
						.append("\t@Override").append(NL) // metas
						.append("\tpublic void set" + fieldName + "(" + referenceClassName + " " + fieldName + ")").append(NL)
						.append("\t{").append(NL)
						.append("\t\tset_ValueFromPO(COLUMNNAME_" + columnName + ", " + referenceClassName + ".class, " + fieldName + ");").append(NL)
						.append("\t}").append(NL);
				// metas: end
				// Add imports:
				// addImportClass(clazz);
			}
		}

		//
		// Handle AD_Table_ID/Record_ID generic model reference
		if (!Check.isEmpty(columnInfo.getTableIdColumnName(), true))
		{
			final String fieldName = ModelInterfaceGenerator.getFieldName(columnName);
			Check.assume("Record".equals(fieldName), "Generic reference field name shall be 'Record'");

			sb.append(NL)
					.append("\t@Override").append(NL)
					.append("\tpublic <RecordType> RecordType get").append(fieldName).append("(final Class<RecordType> recordType)").append(NL)
					.append("\t{").append(NL)
					.append("\t\t return getReferencedRecord(recordType);").append(NL)
					.append("\t}").append(NL);

			sb.append(NL)
					.append("\t@Override").append(NL)
					.append("\tpublic <RecordType> void set").append(fieldName).append("(final RecordType record)").append(NL)
					.append("\t{").append(NL)
					.append("\t\t setReferencedRecord(record);").append(NL)
					.append("\t}").append(NL);

			sb.append(NL);
		}

		// Create Java Comment
		generateJavaSetComment(columnName, columnInfo.getName(), columnInfo.getDescription(), sb);

		// public void setColumn (xxx variable)
		sb.append("\t@Override").append(NL); // metas
		sb.append("\tpublic void set").append(columnName).append(" (").append(dataType).append(" ").append(columnName).append(")").append(NL)
				.append("\t{").append(NL);
		// List Validation
		if (columnInfo.getAD_Reference_ID() > 0 && String.class == clazz && columnInfo.getListInfo().isPresent())
		{
			sb.append("\n");

			final String staticVar = ADRefListGenerator.builder()
					.classColumnName(columnInfo.getColumnName())
					.listInfo(columnInfo.getListInfo().get())
					.build()
					.generateConstants();
			sb.insert(0, staticVar);
		}
		// setValue ("ColumnName", xx);
		if (columnInfo.isVirtualColumn())
		{
			sb.append("\t\tthrow new IllegalArgumentException (\"").append(columnName).append(" is virtual column\");");
		}
		// Integer
		else if (clazz.equals(Integer.class))
		{
			if (columnName.endsWith("_ID"))
			{
				final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(columnName);
				// set _ID to null if < 0 for special column or < 1 for others
				sb.append("\t\tif (").append(columnName).append(" < ").append(firstValidId).append(") ").append(NL)
						.append("\t").append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", null);").append(NL)
						.append("\t\telse ").append(NL).append("\t");
			}
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", Integer.valueOf(").append(columnName).append("));").append(NL);
		}
		// Boolean
		else if (clazz.equals(Boolean.class))
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", Boolean.valueOf(").append(columnName).append("));").append(NL);
		else
		{
			sb.append(setValue).append(" (").append("COLUMNNAME_").append(columnName).append(", ")
					.append(columnName).append(");").append(NL);
		}
		sb.append("\t}").append(NL);

		// ****** Get Comment ******
		generateJavaGetComment(columnInfo.getName(), columnInfo.getDescription(), sb);

		// Get ********
		String getValue = "get_Value";
		if (columnInfo.isEncrypted())
			getValue = "get_ValueE";

		sb.append("\t@Override").append(NL); // metas
		sb.append("\tpublic ").append(dataType);
		if (clazz.equals(Boolean.class))
		{
			sb.append(" is");
			if (columnName.toLowerCase().startsWith("is"))
				sb.append(columnName.substring(2));
			else
				sb.append(columnName);
		}
		else
		{
			sb.append(" get").append(columnName);
		}
		sb.append(" () ").append(NL)
				.append("\t{").append(NL)
				.append("\t\t");
		if (clazz.equals(Integer.class))
		{
			sb.append("Integer ii = (Integer)").append(getValue).append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL)
					.append("\t\tif (ii == null)").append(NL)
					.append("\t\t\t return 0;").append(NL)
					.append("\t\treturn ii.intValue();").append(NL);
		}
		else if (clazz.equals(BigDecimal.class))
		{
			sb.append("BigDecimal bd = (BigDecimal)").append(getValue).append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL)
					.append("\t\tif (bd == null)").append(NL)
					.append("\t\t\t return BigDecimal.ZERO;").append(NL)
					.append("\t\treturn bd;").append(NL);
			addImportClass(java.math.BigDecimal.class);
			// addImportClass(org.compiere.util.Env.class); // not needed anymore
		}
		else if (clazz.equals(Boolean.class))
		{
			sb.append("Object oo = ").append(getValue).append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL)
					.append("\t\tif (oo != null) ").append(NL)
					.append("\t\t{").append(NL)
					.append("\t\t\t if (oo instanceof Boolean) ").append(NL)
					.append("\t\t\t\t return ((Boolean)oo).booleanValue(); ").append(NL)
					.append("\t\t\treturn \"Y\".equals(oo);").append(NL)
					.append("\t\t}").append(NL)
					.append("\t\treturn false;").append(NL);
		}
		else if (dataType.equals("Object"))
		{
			sb.append("\t\treturn ").append(getValue)
					.append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
		}
		else
		{
			sb.append("return (").append(dataType).append(")").append(getValue)
					.append("(").append("COLUMNNAME_").append(columnName).append(");").append(NL);
			// addImportClass(clazz);
		}
		sb.append("\t}").append(NL);
		//
		return sb.toString();
	}	// createColumnMethods

	// ****** Set Comment ******
	private void generateJavaSetComment(String columnName, String propertyName, String description, StringBuilder result)
	{

		result.append(NL)
				.append("\t/** Set ").append(propertyName).append(".").append(NL)
				.append("\t\t@param ").append(columnName).append(" ");
		if (description != null && description.length() > 0)
		{
			result.append(NL)
					.append("\t\t").append(description).append(NL);
		}
		else
		{
			result.append(propertyName);
		}
		result.append("\t  */").append(NL);
	}

	// ****** Get Comment ******
	private void generateJavaGetComment(String propertyName, String description, StringBuilder result)
	{

		result.append(NL)
				.append("\t/** Get ").append(propertyName);
		if (description != null && description.length() > 0)
		{
			result.append(".").append(NL)
					.append("\t\t@return ").append(description).append(NL);
		}
		else
		{
			result.append(".\n\t\t@return ").append(propertyName);
		}
		result.append("\t  */").append(NL);
	}

	/**
	 * Create getKeyNamePair() method with first identifier
	 *
	 * @param columnName name
	 *            * @param displayType int
	 * @return method code
	 */
	private StringBuilder createKeyNamePair(String columnName, int displayType)
	{
		if (!ModelInterfaceGenerator.isGenerateLegacy())
		{
			return new StringBuilder();
		}

		String method = "get" + columnName + "()";
		if (displayType != DisplayType.String)
			method = "String.valueOf(" + method + ")";

		StringBuilder sb = new StringBuilder(NL)
				.append("\tpublic org.compiere.util.KeyNamePair getKeyNamePair() ").append(NL)
				.append("\t{").append(NL)
				.append("\t\treturn new org.compiere.util.KeyNamePair(get_ID(), ").append(method).append(");").append(NL)
				.append("\t}").append(NL);
		// addImportClass(org.compiere.util.KeyNamePair.class);
		return sb;
	}	// createKeyNamePair

	/**
	 * Add class name to class import list
	 * 
	 * @param className
	 */
	private void addImportClass(String className)
	{
		if (className == null
				|| (className.startsWith("java.lang.") && !className.startsWith("java.lang.reflect."))
				|| className.startsWith(packageName + "."))
		{
			return;
		}

		classesToImport.add(className);
	}

	/**
	 * Add class to class import list
	 * 
	 * @param cl
	 */
	private void addImportClass(Class<?> cl)
	{
		if (cl.isArray())
		{
			cl = cl.getComponentType();
		}
		if (cl.isPrimitive())
		{
			return;
		}

		addImportClass(cl.getCanonicalName());
	}

	/**
	 * Generate java imports
	 * 
	 * @param sb
	 */
	private void createImports(StringBuilder sb)
	{
		for (String name : classesToImport)
		{
			sb.append("import ").append(name).append(";").append(NL);
		}
		sb.append(NL);
	}
}
