package com.simple.server;

import javax.ws.rs.ext.ContextResolver;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.inject.Injector;
import com.google.inject.Key;
import jersey.repackaged.com.google.common.collect.ImmutableSet;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

public abstract class AbstractApplicationResourceConfig extends ResourceConfig
{
	private final GuiceIntoHK2Bridge guiceBridge;

	private final Injector injector;

	public AbstractApplicationResourceConfig(final ServiceLocator serviceLocator, final GuiceInjectorSource guiceInjectorSource)
	{
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		injector = guiceInjectorSource.injector();
		guiceBridge.bridgeGuiceInjector(injector);
		bindResources();
	}

	private void bindResources()
	{
		for (final Class<?> featureClass : defaultFeatureClasses())
		{
			register(featureClass);
		}
		for (final Class<?> apiResource : apiClasses())
		{
			register(apiResource);
		}
	}


	private Set<Class<? extends ApplicationResource>> apiClasses()
	{
		return injector.getInstance(Key.get(ApplicationResource.SET_TYPE));
	}

	private Set<Class<?>> defaultFeatureClasses()
	{
		return ImmutableSet.of(ObjectMapperProvider.class);
	}

	public static class ObjectMapperProvider implements ContextResolver<ObjectMapper>
	{
		private final ObjectMapper mapper;

		public ObjectMapperProvider()
		{
			mapper = new ObjectMapper().registerModule(new Jdk8Module());
		}

		@Override
		public ObjectMapper getContext(final Class<?> type)
		{
			return mapper;
		}

	}
}
