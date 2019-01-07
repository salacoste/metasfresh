package de.metas.server.rest;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.compiere.Adempiere;
import org.compiere.server.AdempiereServer;
import org.compiere.server.AdempiereServerMgr;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.util.web.MetasfreshRestAPIConstants;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@RestController
@RequestMapping(value = ServerMonitorRestController.ENDPOINT)
public class ServerMonitorRestController
{
	static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/serverMonitor";

	private AdempiereServerMgr getAdempiereServerMgr()
	{
		return AdempiereServerMgr.get();
	}

	@GetMapping("/summary")
	public JSONSummary getSummary()
	{
		final AdempiereServerMgr serverMgr = getAdempiereServerMgr();
		return JSONSummary.builder()
				.name(Adempiere.getName())
				.buildAndDateVersion(Adempiere.getBuildAndDateVersion())
				.implementationVendor(Adempiere.getImplementationVendor())
				.implementationVersion(Adempiere.getImplementationVersion())
				//
				.startTime(serverMgr.getStartTime().toString())
				.startDuration(Duration.between(serverMgr.getStartTime(), ZonedDateTime.now()).toString())
				//
				.serversCount(serverMgr.getServerCount())
				//
				.build();
	}

	@GetMapping("/servers")
	public List<JSONServerInfo> getServers()
	{
		final AdempiereServerMgr serverMgr = getAdempiereServerMgr();

		return Stream.of(serverMgr.getAll())
				.map(this::toJSONServerInfo)
				.collect(ImmutableList.toImmutableList());
	}

	@GetMapping("/servers/startAll")
	public void startAllServers()
	{
		getAdempiereServerMgr().startAll();
	}

	@GetMapping("/servers/stopAll")
	public void stopAllServers()
	{
		getAdempiereServerMgr().stopAll();
	}

	@GetMapping("/server/{serverId}/start")
	public void startServer(@PathVariable("serverid") final String serverId)
	{
		getAdempiereServerMgr().start(serverId);
	}

	@GetMapping("/server/{serverId}/stop")
	public void stopServer(@PathVariable("serverid") final String serverId)
	{
		getAdempiereServerMgr().stop(serverId);
	}

	private JSONServerInfo toJSONServerInfo(final AdempiereServer server)
	{
		final Timestamp dateLastRun = server.getDateLastRun();
		final Timestamp dateNextRun = server.getDateNextRun(false);

		return JSONServerInfo.builder()
				.serverId(server.getServerID())
				.name(server.getName())
				.info(server.getServerInfo())
				//
				.status(extractServerStatus(server))
				//
				.dateLastRun(dateLastRun != null ? dateLastRun.toString() : null)
				.dateNextRun(dateNextRun != null ? dateNextRun.toString() : null)
				//
				.statistics(server.getStatistics())
				//
				.build();
	}

	private static String extractServerStatus(final AdempiereServer server)
	{
		return server.isAlive() ? "running" : "stopped";
	}
}
