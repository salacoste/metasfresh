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
package org.adempiere.serverRoot.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.serverRoot.util.WebEnv;
import org.adempiere.serverRoot.util.WebUtil;
import org.compiere.Adempiere;
import org.compiere.db.AdempiereDatabase;
import org.compiere.db.CConnection;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.model.I_AD_System;
import org.compiere.model.MClient;
import org.compiere.model.MStore;
import org.compiere.server.AdempiereServer;
import org.compiere.server.AdempiereServerMgr;
import org.compiere.util.CMemoryUsage;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.TimeUtil;
import org.compiere.util.WebDoc;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;

import ch.qos.logback.classic.Level;
import de.metas.Profiles;
import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import j2html.tags.ContainerTag;

import static j2html.TagCreator.*;

/**
 * Server Monitor
 *
 * @author Jorg Janke
 * @author tsa
 */
@WebServlet(value = "/serverMonitor", loadOnStartup = 1)
@Profile(Profiles.PROFILE_App)
public class ServerMonitor extends HttpServlet
{
	/**
	 *
	 */
	private static final long serialVersionUID = 2640663091564185714L;

	/** Logger */
	private static final Logger log = LogManager.getLogger(ServerMonitor.class);

	private static final String NAME = "serverMonitor";

	/**
	 * Shall we allow cache reset options?
	 *
	 * Default: false, because we had so many problems with this.
	 */
	private static final boolean ALLOW_CacheReset = false;

	/** The Server */
	private AdempiereServerMgr m_serverMgr = null;
	/** Message */
	private p m_message = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		m_message = null;
		if (processLogParameter(request, response))
		{
			return;
		}
		if (processTraceParameter(request, response))
		{
			return;
		}
		if (processEMailParameter(request, response))
		{
			return;
		}
		if (processCacheParameter(request, response))
		{
			return;
		}
		//
		if (!processRunNowParameter(request))
		{
			processActionParameter(request);
		}

		createSummaryPage(request, response);
	}	// doGet

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}	// doPost

	private boolean isAllowCacheReset()
	{
		return ALLOW_CacheReset;
	}

	/**
	 * Process Log Parameter and return log page
	 *
	 * @param request request
	 * @param response response
	 * @return true if it was a log request
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processLogParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		final String serverId = WebUtil.getParameter(request, "Log");
		if (serverId == null || serverId.isEmpty())
		{
			return false;
		}

		final AdempiereServer server = m_serverMgr.getServer(serverId);
		if (server == null)
		{
			m_message = new p();
			m_message.with(new strong("Server not found: "));
			m_message.with(serverId);
			return false;
		}

		final WebDoc doc = WebDoc.create("Server Monitor Log");

		// Body
		final ContainerTag body = doc.getBody();
		{
			//
			final ContainerTag para = p();
			final ContainerTag link = a("Return")
					.attr("href", NAME + "#" + serverId);
			para.with(link);
			body.with(para);
			//
			body.with(h2(server.getName()));
		}

		//
		final ContainerTag table = newPropertiesTable();

		// Header
		{
			final ContainerTag line = tr();
			line.with(th().withText("Created"));
			line.with(th().withText("Summary"));
			// line.with(th().withText("Error"));
			line.with(th().withText("Reference"));
			line.with(th().withText("TextMsg"));
			// line.with(th().withText("Description"));
			table.with(line);
		}

		for (final AdempiereProcessorLog pLog : server.getLogs())
		{
			final ContainerTag line = tr();
			line.with(td().withText(WebEnv.getCellContent(pLog.getCreated())));
			line.with(td().withText(WebEnv.getCellContent(pLog.getSummary())));
			line.with(td().withText(WebEnv.getCellContent(pLog.getReference())));
			line.with(td().withText(WebEnv.getCellContent(pLog.getTextMsg())));
			table.with(line);
		}
		//
		{
			body.with(table);
			final ContainerTag link = a("Top")
					.attr("href", "#top");
			body.with(link);
		}

		// fini
		WebUtil.createResponse(request, response, this, null, doc, false);
		return true;
	}	// processLogParameter

	/**
	 * Process Run Parameter
	 *
	 * @param request request
	 * @return true if it was a Run request
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processRunNowParameter(HttpServletRequest request)
			throws ServletException, IOException
	{
		final String serverID = WebUtil.getParameter(request, "RunNow");
		if (serverID == null || serverID.length() == 0)
		{
			return false;
		}
		log.info("ServerID=" + serverID);
		final AdempiereServer server = m_serverMgr.getServer(serverID);
		if (server == null)
		{
			m_message = new p();
			m_message.with(new strong("Server not found: "));
			m_message.with(serverID);
			return false;
		}
		//
		server.runNow();
		//
		return true;
	}	// processRunParameter

	/**
	 * Process Action Parameter
	 *
	 * @param request request
	 */
	private void processActionParameter(HttpServletRequest request)
	{
		final String action = WebUtil.getParameter(request, "Action");
		if (action == null || action.length() == 0)
		{
			return;
		}
		log.info("Action=" + action);
		try
		{
			final boolean start = action.startsWith("Start");
			m_message = new p();
			final String msg = (start ? "Started" : "Stopped") + ": ";
			m_message.with(new strong(msg));
			//
			final String serverID = action.substring(action.indexOf('_') + 1);
			boolean ok = false;
			if ("All".equals(serverID))
			{
				if (start)
				{
					ok = m_serverMgr.startAll();
				}
				else
				{
					ok = m_serverMgr.stopAll();
				}
				m_message.with("All");
			}
			else
			{
				final AdempiereServer server = m_serverMgr.getServer(serverID);
				if (server == null)
				{
					m_message = new p();
					m_message.with(new strong("Server not found: "));
					m_message.with(serverID);
					return;
				}
				else
				{
					if (start)
					{
						ok = m_serverMgr.start(serverID);
					}
					else
					{
						ok = m_serverMgr.stop(serverID);
					}
					m_message.with(server.getName());
				}
			}
			m_message.with(ok ? " - OK" : " - Error!");
		}
		catch (Exception e)
		{
			m_message = new p();
			m_message.with(new strong("Error processing parameter: " + action));
			m_message.with(new br());
			m_message.with(e.toString());
		}
	}	// processActionParameter

	/**
	 * Process Trace Parameter
	 *
	 * @param request request
	 * @param response response
	 * @return true if it was a trace request with output
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processTraceParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		final String traceCmd = WebUtil.getParameter(request, "Trace");
		final String traceLevel = WebUtil.getParameter(request, "TraceLevel");
		if (traceLevel != null && traceLevel.length() > 0)
		{
			log.info("New Level: " + traceLevel);
			Ini.setProperty(Ini.P_TRACELEVEL, traceLevel);
			Ini.saveProperties();
			LogManager.updateConfigurationFromIni();
			return false;
		}

		if (traceCmd == null || traceCmd.length() == 0)
			return false;

		log.info("Command: " + traceCmd);
		//
		if ("ROTATE".equals(traceCmd))
		{
			LogManager.rotateLogFile();
			log.info("Log file rotated on demand");
			return false;	// re-display
		}
		else if ("DELETE".equals(traceCmd))
		{
			for (final File logFile : LogManager.getLogFiles())
			{
				// don't delete the current active log file
				if (LogManager.isActiveLogFile(logFile))
				{
					continue;
				}

				if (logFile.delete())
				{
					log.warn("Deleted: " + logFile);
				}
				else
				{
					log.warn("Not Deleted: " + logFile);
				}
			}
			return false;	// re-display
		}

		//
		// Spool File
		{
			String logFileName = traceCmd;
			if (logFileName.contains(".."))
			{
				log.warn("Invalid log file name: " + logFileName);
				return false;
			}
			File logFile = new File(logFileName);
			try
			{
				logFile = logFile.getCanonicalFile();
			}
			catch (IOException e)
			{
			}

			final List<File> allLogFiles = LogManager.getLogFiles();

			if (!allLogFiles.contains(logFile))
			{
				log.warn("Requested file " + logFile + " is not a valid log file");
				return false;
			}

			if (!logFile.exists())
			{
				log.warn("Did not find File: " + logFileName);
				return false;
			}
			// if (logFile.length() == 0)
			// {
			// log.warn("File Length=0: " + logFileName);
			// return false;
			// }

			if (LogManager.isActiveLogFile(logFile))
			{
				LogManager.flushLogFile();
			}

			// Stream Log
			log.info("Streaming: " + logFileName);
			try
			{
				long time = System.currentTimeMillis();		// timer start
				final int fileLength = (int)logFile.length();
				final int bufferSize = 2048; // 2k Buffer
				final byte[] buffer = new byte[bufferSize];
				//
				response.setContentType("text/plain");
				response.setBufferSize(bufferSize);
				response.setContentLength(fileLength);
				//
				final FileInputStream fis = new FileInputStream(logFile);
				final ServletOutputStream out = response.getOutputStream();
				int read = 0;
				while ((read = fis.read(buffer)) > 0)
					out.write(buffer, 0, read);
				out.flush();
				out.close();
				fis.close();
				//
				time = System.currentTimeMillis() - time;
				final double speed = (fileLength / 1024) / ((double)time / 1000);
				log.info("length="
						+ fileLength + " - "
						+ time + " ms - "
						+ speed + " kB/sec");
			}
			catch (IOException ex)
			{
				log.error("stream", ex);
			}
		}

		return true;
	}	// processTraceParameter

	/**
	 * Process EMail Parameter
	 *
	 * @param request request
	 * @param response response
	 * @return true if it was a email request with output
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processEMailParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		final String email = WebUtil.getParameter(request, "EMail");
		if (email == null || email.length() == 0)
		{
			return false;
		}

		int AD_Client_ID = -1;
		try
		{
			AD_Client_ID = Integer.parseInt(email);
		}
		catch (Exception e)
		{
			log.warn("Parsing: " + email + " - " + e.toString());
		}
		if (AD_Client_ID < 0)
		{
			m_message = new p();
			m_message.with("No EMail: " + email);
			return false;
		}

		// log.info("Test EMail: " + AD_Client_ID);
		final Properties ctx = Env.newTemporaryCtx();
		final MClient client = MClient.get(ctx, AD_Client_ID);
		log.info("Test: " + client);

		m_message = new p();
		m_message.with(client.getName() + ": " + client.testEMail());
		return false;
	}	// processEMailParameter

	/**
	 * Process Cache Parameter
	 *
	 * @param request request
	 * @param response response
	 * @return true if it was a email request with output
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processCacheParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		if (!isAllowCacheReset())
		{
			return false;
		}

		final String cmd = WebUtil.getParameter(request, "CacheReset");
		if (cmd == null || cmd.length() == 0)
		{
			return false;
		}
		final String tableName = WebUtil.getParameter(request, "CacheTableName");
		final String record_ID = WebUtil.getParameter(request, "CacheRecord_ID");

		m_message = new p();
		try
		{
			if (tableName == null || tableName.length() == 0)
			{
				CacheMgt.get().reset();
				m_message.with("Cache Reset: All");
			}
			else if (record_ID == null || record_ID.isEmpty())
			{
				CacheMgt.get().reset(tableName);
				m_message.with("Cache Reset: " + tableName);
			}
			else
			{
				CacheMgt.get().reset(tableName, Integer.parseInt(record_ID));
				m_message.with("Cache Reset: " + tableName + ", Record_ID=" + record_ID);
			}
		}
		catch (Exception e)
		{
			log.error("Error", e);
			m_message.with("Error: " + e.toString());
		}
		return false;	// continue
	}	// processEMailParameter

	private final ContainerTag newPropertiesTable()
	{
		return table()
				.attr("class", "propertiesTable")
				.attr("cellSpacing", 2)
				.attr("cellPadding", 2);
	}

	/**************************************************************************
	 * Create & Return Summary Page
	 *
	 * @param request request
	 * @param response response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void createSummaryPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		final WebDoc doc = WebDoc.create("Server Monitor");

		// Body
		final body bb = doc.getBody();
		// Message
		if (m_message != null)
		{
			bb.with(new hr());
			bb.with(m_message);
			bb.with(new hr());
		}

		// Summary
		table table = newPropertiesTable();
		//
		tr line = new tr();
		line.with(new th().with(Adempiere.getName()));
		line.with(new td().with(Adempiere.getBuildAndDateVersion()));
		table.with(line);
		line = new tr();
		line.with(new th().with(Adempiere.getImplementationVendor()));
		line.with(new td().with(Adempiere.getImplementationVersion()));
		table.with(line);
		line = new tr();
		line.with(new th().with("Manager"));
		line.with(new td().with(WebEnv.getCellContent(m_serverMgr.getDescription())));
		table.with(line);
		line = new tr();
		line.with(new th().with("Start - Elapsed"));
		line.with(new td().with(WebEnv.getCellContent(m_serverMgr.getStartTime()) + " - " + TimeUtil.formatElapsed(m_serverMgr.getStartTime())));
		table.with(line);
		line = new tr();
		line.with(new th().with("Servers"));
		line.with(new td().with(WebEnv.getCellContent(m_serverMgr.getServerCount())));
		table.with(line);
		line = new tr();
		line.with(new th().with("Last Updated"));
		line.with(new td().with(new Timestamp(System.currentTimeMillis()).toString()));
		table.with(line);
		bb.with(table);
		//
		p para = new p();
		a link = new a(NAME + "?Action=Start_All", "Start All");
		para.with(link);
		para.with(" - ");
		link = new a(NAME + "?Action=Stop_All", "Stop All");
		para.with(link);
		para.with(" - ");
		link = new a(NAME, "Refresh");
		para.with(link);
		bb.with(para);

		// ***** Server Links *****
		bb.with(new hr());
		para = new p();
		final AdempiereServer[] servers = m_serverMgr.getAll();
		for (int i = 0; i < servers.length; i++)
		{
			if (i > 0)
			{
				para.with(new br());
			}
			final AdempiereServer server = servers[i];
			link = new a("#" + server.getServerID(), server.getName());
			para.with(link);
			font status = null;
			if (server.isAlive())
			{
				status = new font().setColor(HtmlColor.GREEN).with(" (Running)");
			}
			else
			{
				status = new font().setColor(HtmlColor.RED).with(" (Stopped)");
			}
			para.with(status);
		}
		bb.with(para);

		// **** Log Management ****
		createLogMgtPage(bb);

		// ***** Server Details *****
		for (int i = 0; i < servers.length; i++)
		{
			final AdempiereServer server = servers[i];
			bb.with(new hr());
			bb.with(new a().setName(server.getServerID()));
			bb.with(new h2(server.getName()));
			//
			table = newPropertiesTable();
			// Status
			line = new tr();
			if (server.isAlive())
			{
				String msg = "Stop";
				if (server.isInterrupted())
				{
					msg += " (Interrupted)";
				}
				link = new a(NAME + "?Action=Stop_" + server.getServerID(), msg);
				if (server.isSleeping())
				{
					line.with(new th().with("Sleeping"));
					line.with(new td().with(link));
				}
				else
				{
					line.with(new th().with("Running"));
					line.with(new td().with(link));
				}
				table.with(line);
				line = new tr();
				line.with(new th().with("Start - Elapsed"));

				final Timestamp startTime = server.getStartTime();
				line.with(new td().with(WebEnv.getCellContent(startTime) + " - " + TimeUtil.formatElapsed(startTime)));
			}
			else
			{
				String msg = "Start";
				if (server.isInterrupted())
				{
					msg += " (Interrupted)";
				}
				line.with(new th().with("Not Started"));
				link = new a(NAME + "?Action=Start_" + server.getServerID(), msg);
				line.with(new td().with(link));
			}
			table.with(line);
			//
			line = new tr();
			line.with(new th().with("Description"));
			line.with(new td().with(WebEnv.getCellContent(server.getDescription())));
			table.with(line);
			//
			line = new tr();
			line.with(new th().with("Last Run"));
			line.with(new td().with(WebEnv.getCellContent(server.getDateLastRun())));
			table.with(line);
			line = new tr();
			line.with(new th().with("Info"));
			line.with(new td().with(WebEnv.getCellContent(server.getServerInfo())));
			table.with(line);
			//
			line = new tr();
			line.with(new th().with("Next Run"));
			final td td = new td();
			td.with(WebEnv.getCellContent(server.getDateNextRun(false)));
			td.with(" - ");
			link = new a(NAME + "?RunNow=" + server.getServerID(), "(Run Now)");
			td.with(link);
			line.with(td);
			table.with(line);
			//
			line = new tr();
			line.with(new th().with("Statistics"));
			line.with(new td().with(server.getStatistics()));
			table.with(line);
			//

			// Add table to Body
			bb.with(table);
			link = new a("#top", "Top");
			bb.with(link);
			bb.with(" - ");
			link = new a(NAME + "?Log=" + server.getServerID(), "Log");
			bb.with(link);
			bb.with(" - ");
			link = new a(NAME, "Refresh");
			bb.with(link);
		}

		// fini
		WebUtil.createResponse(request, response, this, null, doc, false);
	}	// createSummaryPage

	/**
	 * Add Log Management to page
	 *
	 * @param body body
	 */
	private void createLogMgtPage(final ContainerTag body)
	{
		final Properties ctx = Env.newTemporaryCtx();

		body.with(hr());

		{
			// Ini Parameters
			final ContainerTag table = newPropertiesTable();
			body.with(table);

			//
			final I_AD_System system = Services.get(ISystemBL.class).get(ctx);
			{
				ContainerTag line = tr();
				line.with(th(system.getDBAddress()));
				line.with(td(Ini.getMetasfreshHome()));
				table.with(line);
			}

			// OS + Name
			{
				ContainerTag line = tr();
				String info = System.getProperty("os.name")
						+ " " + System.getProperty("os.version");
				final String s = System.getProperty("sun.os.patch.level");
				if (s != null && s.length() > 0)
				{
					info += " (" + s + ")";
				}
				line.with(th(info));
				info = system.getName();
				if (system.getCustomPrefix() != null)
				{
					info += " (" + system.getCustomPrefix() + ")";
				}
				line.with(td(info));
				table.with(line);
			}

			// Java + email
			{
				final ContainerTag line = tr();
				String info = System.getProperty("java.vm.name")
						+ " " + System.getProperty("java.vm.version");
				line.with(th(info));
				line.with(td(system.getUserName()));
				table.with(line);
			}

			// DB + Instance
			{
				final ContainerTag line = tr();
				final CConnection cc = CConnection.get();
				final AdempiereDatabase db = cc.getDatabase();
				String info = db.getDescription();
				line.with(th(info));
				line.with(td(cc.getConnectionURL()));
				// line.with(new td().with(system.getDBInstance()));
				table.with(line);
			}

			// Processors/Support
			{
				final ContainerTag line = tr();
				line.with(th("Processor/Support"));
				line.with(td(system.getNoProcessors() + "/" + system.getSupportUnits()));
				table.with(line);
			}

			// Memory
			{
				final MemoryMXBean memory = ManagementFactory.getMemoryMXBean();

				final ContainerTag line = tr();
				line.with(th("VM Memory"));
				line.with(td(new CMemoryUsage(memory.getNonHeapMemoryUsage()).toString()));
				table.with(line);
			}
			{
				final MemoryMXBean memory = ManagementFactory.getMemoryMXBean();

				final ContainerTag line = tr();
				line.with(th("Heap Memory"));
				line.with(td(new CMemoryUsage(memory.getHeapMemoryUsage()).toString()));
				table.with(line);
			}

			// Runtime
			{
				final RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();

				final ContainerTag line = tr();
				line.with(th("Runtime " + rt.getName()));
				line.with(td(TimeUtil.formatElapsed(rt.getUptime())));
				table.with(line);
			}

			// Threads
			{
				final ContainerTag line = tr();
				final ThreadMXBean th = ManagementFactory.getThreadMXBean();
				line.with(th("Threads " + th.getThreadCount()));
				line.with(td("Peak=" + th.getPeakThreadCount()
						+ ", Demons=" + th.getDaemonThreadCount()
						+ ", Total=" + th.getTotalStartedThreadCount()));
				table.with(line);
			}

			// Transactions
			final ITrx[] trxs = Services.get(ITrxManager.class).getActiveTransactions();
			for (ITrx trx : trxs)
			{
				if (trx != null && trx.isActive())
				{
					final ContainerTag line = tr();
					line.with(th("Active Transaction "));
					line.with(td("Name=" + trx.getTrxName() + ", StartTime=" + trx.getStartTime()));
					table.with(line);
				}
			}

			// Cache Reset
			createLogMgtPage_CacheReset(table);

			// Trace Level
			{
				final ContainerTag line = tr();
				line.with(th(label("TraceLevel").withText("Trace Log Level")));
				final form myForm = form(NAME, form.METHOD_POST, form.ENC_DEFAULT);
				// LogLevel Selection

				final List<Level> logLevels = LogManager.getAvailableLoggingLevels();
				final option[] options = new option[logLevels.size()];
				for (int i = 0; i < options.length; i++)
				{
					final Level logLevel = logLevels.get(i);
					final String logLevelName = logLevel.toString();
					options[i] = new option(logLevelName);
					options[i].with(logLevelName);
					if (LogManager.isLevel(logLevel))
					{
						options[i].setSelected(true);
					}
				}
				final select sel = new select("TraceLevel", options);
				myForm.with(sel);
				myForm.with(new input(input.TYPE_SUBMIT, "Set", "Set"));
				line.with(new td().with(myForm));
				table.with(line);
			}

			// Current Log File
			final File logFile = LogManager.getActiveLogFile();
			if (logFile != null)
			{
				tr line = new tr();
				line.with(new th().with("Trace File"));

				final String logFilePath = logFile.getAbsolutePath();
				final a href = new a(NAME + "?Trace=" + encodeURLValue(logFilePath), logFile.getName());
				href.setTarget("_blank");
				href.setTitle(logFilePath);
				line.with(new td().with(href).with(" (" + bytesToString(logFile.length()) + ")"));
				table.with(line);
			}

			// Log File options (rotate, delete all)
			{
				tr line = new tr();
				line.with(new td().with(new a(NAME + "?Trace=ROTATE", "Rotate Trace Log")));
				line.with(new td().with(new a(NAME + "?Trace=DELETE", "Delete all Trace Logs")));
				table.with(line);
			}
		}

		// List Log Files
		body.with(createLogMgtPage_LogFiles());

		// Clients and Web Stores
		{
			table table = newPropertiesTable();
			//
			tr line = new tr();
			final MClient[] clients = MClient.getAll(ctx);
			line.with(new th().with("Client #" + clients.length + " - EMail Test:"));
			p p = new p();
			for (int i = 0; i < clients.length; i++)
			{
				final MClient client = clients[i];
				if (i > 0)
				{
					p.with(" - ");
				}
				p.with(new a(NAME + "?EMail=" + client.getAD_Client_ID(), client.getName()));
			}
			if (clients.length == 0)
			{
				p.with("&nbsp;");
			}
			line.with(new td().with(p));
			table.with(line);
			//
			line = new tr();
			final MStore[] wstores = MStore.getActive();
			line.with(new th().with("Active Web Stores #" + wstores.length));
			p = new p();
			for (int i = 0; i < wstores.length; i++)
			{
				final MStore store = wstores[i];
				if (i > 0)
				{
					p.with(" - ");
				}
				a a = new a(store.getWebContext(), store.getName());
				a.setTarget("t" + i);
				p.with(a);
			}
			if (wstores.length == 0)
			{
				p.with("&nbsp;");
			}
			line.with(new td().with(p));
			table.with(line);
			//
			body.with(table);
		}
	}	// createLogMgtPage

	private void createLogMgtPage_CacheReset(final ContainerTag table)
	{
		if (!isAllowCacheReset())
		{
			return;
		}

		ContainerTag line = tr();
		line.with(th(CacheMgt.get().toStringX()));
		line.with(td(a("Reset Cache")
				.attr("href", NAME + "?CacheReset=Yes")));
		table.with(line);
	}

	private ContainerTag createLogMgtPage_LogFiles()
	{
		final ContainerTag containerElement = p();
		containerElement.with(b("All Log Files: "));

		// All in dir
		boolean first = true;
		for (final File logFile : LogManager.getLogFiles())
		{
			// Skip if is not a file - teo_sarca [ 1726066 ]
			if (!logFile.isFile())
			{
				continue;
			}

			if (!first)
			{
				containerElement.withText(" - ");
			}

			final String fileName = logFile.getAbsolutePath();
			final ContainerTag link = a(logFile.getName())
					.attr("href", NAME + "?Trace=" + encodeURLValue(fileName))
					.attr("target", "_blank")
					.attr("title", fileName);
			containerElement.with(link);
			final long sizeBytes = logFile.length();
			containerElement.withText(" (" + bytesToString(sizeBytes) + ")");

			first = false;
		}

		return containerElement;
	}

	private static final String encodeURLValue(final String value)
	{
		try
		{
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		}
		catch (UnsupportedEncodingException e)
		{
			log.warn("Failed encoding '{}'. Returning it as is.", value, e);
			return value;
		}
	}

	private static final String bytesToString(final long sizeBytes)
	{
		if (sizeBytes < 1024)
		{
			return sizeBytes + " bytes";
		}
		else
		{
			final long sizeKb = sizeBytes / 1024;
			if (sizeKb < 1024)
			{
				return sizeKb + "k";
			}
			else
			{
				final long sizeMb = sizeKb / 1024;
				return sizeMb + "M";
			}
		}
	}

	/**************************************************************************
	 * Init
	 *
	 * @param config config
	 * @throws javax.servlet.ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		m_serverMgr = AdempiereServerMgr.get();
	}

	/**
	 * Destroy
	 */
	@Override
	public void destroy()
	{
		log.info("destroy");
		m_serverMgr = null;
	}	// destroy

	/**
	 * Log error/warning
	 *
	 * @param message message
	 * @param e exception
	 */
	@Override
	public void log(final String message, final Throwable e)
	{
		if (e == null)
		{
			log.warn(message);
		}
		log.error(message, e);
	}	// log

	/**
	 * Log debug
	 *
	 * @param message message
	 */
	@Override
	public void log(String message)
	{
		log.debug(message);
	}	// log

	@Override
	public String getServletName()
	{
		return NAME;
	}

	@Override
	public String getServletInfo()
	{
		return "Server Monitor";
	}
}
