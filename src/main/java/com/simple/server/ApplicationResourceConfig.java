package com.simple.server;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;

public class ApplicationResourceConfig extends AbstractApplicationResourceConfig
{

	@Inject
	public ApplicationResourceConfig(final ServiceLocator serviceLocator)
	{
		super(serviceLocator, ApplicationGuiceInjectorSource.getInstance());
	}
}
