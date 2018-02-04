package com.simple.server;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public abstract class AbstractApplicationResourceModule extends AbstractModule
{
	private Multibinder<Class<? extends ApplicationResource>> appResources;

	public AbstractApplicationResourceModule()
	{
	}

	@Override
	protected final void configure()
	{
		appResources = Multibinder.newSetBinder(binder(), ApplicationResource.TYPE);
		configureAdditional();
	}

	protected abstract void configureAdditional();

	protected void bindResource(final Class<? extends ApplicationResource> clazz)
	{
		appResources.addBinding().toInstance(clazz);
	}
}
