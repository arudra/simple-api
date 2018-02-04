package com.simple.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

public class Application extends AbstractApplication
{
	private static final Logger LOGGER = LogManager.getLogger(Application.class);

	private static final int PORT = 8080;
	
	private Application()
	{
		super(ResourceConfig.forApplicationClass(ApplicationResourceConfig.class), PORT);
	}

	public static void main(final String[] args)
	{
		LOGGER.info("APP STARTING");

		final Application app = new Application();
		try
		{
			app.start();
		} catch (final Exception e)
		{
			LOGGER.error("Failed to start application.");
		}
	}
}
