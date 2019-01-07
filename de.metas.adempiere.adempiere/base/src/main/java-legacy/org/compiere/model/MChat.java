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
 *****************************************************************************/
package org.compiere.model;

import static j2html.TagCreator.b;
import static j2html.TagCreator.br;
import static j2html.TagCreator.hr;
import static j2html.TagCreator.p;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.user.api.IUserDAO;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Splitter;

import de.metas.adempiere.model.I_AD_User;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import j2html.tags.ContainerTag;

/**
 * Chat Model
 *
 * @author Jorg Janke
 * @version $Id: MChat.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 */
public class MChat extends X_CM_Chat
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5053130533036069784L;

	/**
	 * Get Chats Of Table - of client in context
	 * 
	 * @param ctx context
	 * @param AD_Table_ID table
	 * @return array of chats
	 */
	public static MChat[] getOfTable(Properties ctx, int AD_Table_ID)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		ArrayList<MChat> list = new ArrayList<MChat>();
		//
		String sql = "SELECT * FROM CM_Chat "
				+ "WHERE AD_Client_ID=? AND AD_Table_ID=? ORDER BY Record_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setInt(2, AD_Table_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MChat(ctx, rs, null));
			}
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		MChat[] retValue = new MChat[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// get

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MChat.class);

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param CM_Chat_ID id
	 * @param trxName transcation
	 */
	public MChat(Properties ctx, int CM_Chat_ID, String trxName)
	{
		super(ctx, CM_Chat_ID, trxName);
		if (CM_Chat_ID == 0)
		{
			// setAD_Table_ID (0);
			// setRecord_ID (0);
			setConfidentialType(CONFIDENTIALTYPE_PublicInformation);
			setModerationType(MODERATIONTYPE_NotModerated);
			// setDescription (null);
		}
	}	// MChat

	/**
	 * Full Constructor
	 * 
	 * @param ctx context
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @param Description description
	 * @param trxName transaction
	 */
	public MChat(Properties ctx, int AD_Table_ID, int Record_ID,
			String Description, String trxName)
	{
		this(ctx, 0, trxName);
		setAD_Table_ID(AD_Table_ID);
		setRecord_ID(Record_ID);
		setDescription(Description);
	}	// MChat

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MChat(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MChat

	/** The Lines */
	private MChatEntry[] m_entries = null;
	/** Date Format */
	private SimpleDateFormat m_format = null;

	/**
	 * Get Entries
	 * 
	 * @param reload reload data
	 * @return array of lines
	 */
	public MChatEntry[] getEntries(boolean reload)
	{
		if (m_entries != null && !reload)
			return m_entries;
		ArrayList<MChatEntry> list = new ArrayList<MChatEntry>();
		String sql = "SELECT * FROM CM_ChatEntry WHERE CM_Chat_ID=? ORDER BY Created";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, getCM_Chat_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MChatEntry(getCtx(), rs, get_TrxName()));
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		m_entries = new MChatEntry[list.size()];
		list.toArray(m_entries);
		return m_entries;
	}	// getEntries

	/**
	 * Set Description
	 *
	 * @param Description
	 */
	@Override
	public void setDescription(String Description)
	{
		if (Description != null && Description.length() > 0)
			super.setDescription(Description);
		else
			super.setDescription(getAD_Table_ID() + "#" + getRecord_ID());
	}	// setDescription

	/**
	 * Get History as htlp paragraph
	 * 
	 * @param confidentialType confidentiality
	 * @return html paragraph
	 */
	public String getHistoryAsHtmlString(final String confidentialType)
	{
		final ContainerTag history = p();

		boolean first = true;
		for (final MChatEntry entry : getEntries(false))
		{
			if (!entry.isActive() || !entry.isConfidentialType(confidentialType))
				continue;
			if (first)
				first = false;
			else
				history.with(hr());

			// User & Date
			{
				final ContainerTag userAndDateContainer = b();
				final I_AD_User user = Services.get(IUserDAO.class).retrieveUser(entry.getCreatedBy());
				userAndDateContainer.withText(user.getName());
				userAndDateContainer.withText(" \t");
				final Timestamp created = entry.getCreated();
				if (m_format == null)
					m_format = DisplayType.getDateFormat(DisplayType.DateTime);
				userAndDateContainer.withText(m_format.format(created));
				history.with(userAndDateContainer);
			}

			//
			// Text
			{
				final ContainerTag textContainer = p();
				final String data = entry.getCharacterData();
				if (data != null)
				{
					Splitter.on("\n")
							.splitToList(data)
							.forEach(line -> textContainer.withText(line).with(br()));
				}
				history.with(textContainer);
			}
		}	// entry
		
		//
		return history.render();
	}	// getHistory

}	// MChat
