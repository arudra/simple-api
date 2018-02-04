package com.simple.server;

import java.util.Optional;

import jersey.repackaged.com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Throwables;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public abstract class AbstractApplication
{
	private static final Logger LOGGER = LogManager.getLogger(AbstractApplication.class);

	private static final int MAX_PORT = 65535;

	protected final org.glassfish.jersey.server.ResourceConfig resourceConfig;

	protected final int port;

	private Optional<Server> server = java.util.Optional.empty();

	public AbstractApplication(final ResourceConfig resourceConfig, final int port)
	{
		this.resourceConfig = Preconditions.checkNotNull(resourceConfig, "ResourceConfig is NULL in AbstractApplication");
		Preconditions.checkArgument(port > 0 && port <= MAX_PORT, "port is invalid.");
		this.port = port;
	}


	public final void start() throws Exception
	{
		if (!server.isPresent() || (server.isPresent() && !server.get().isRunning()))
		{
			try
			{
				final HandlerList handlers = new HandlerList();
				configureHandlers(handlers);

				final Server localServer = new Server();
				configureServer(localServer);

				localServer.setHandler(handlers);
				localServer.start();

				server = Optional.of(localServer);
			} catch (final Exception e)
			{
				LOGGER.catching(e);
				Throwables.rethrow(e);
			}
		}
	}

	private void configureServer(final Server localServer)
	{
		final HttpConfiguration configuration = new HttpConfiguration();
		configuration.setSendServerVersion(false);
		try (ServerConnector http = new ServerConnector(localServer, new HttpConnectionFactory(configuration)))
		{
			http.setPort(port);
			localServer.setConnectors(new Connector[]{http});
		}
	}

	private void configureHandlers(final HandlerList handlers)
	{
		final ServletHolder servletHolder = new ServletHolder(new ServletContainer(resourceConfig));
		final ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS | ServletContextHandler.NO_SECURITY | ServletContextHandler.GZIP);
		handler.addServlet(servletHolder, "/*");
		handlers.addHandler(handler);
	}


	public final void stop()
	{
		if (server.isPresent() && server.get().isRunning())
		{
			try
			{
				server.get().stop();
				server = Optional.empty();
			} catch (final Exception e)
			{
				LOGGER.catching(e);
				LOGGER.error("Failed to stop application.");
			}
		}
	}


}
