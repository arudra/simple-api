package com.simple.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.simple.AllModules;

public class ApplicationGuiceInjectorSource implements GuiceInjectorSource
{

	private final Injector injector;


	private ApplicationGuiceInjectorSource()
	{
		injector = Guice.createInjector(new AllModules());
	}

	@Override
	public Injector injector()
	{
		return injector;
	}

	public static GuiceInjectorSource getInstance()
	{
		return Holder.INSTANCE;
	}

	private static class Holder
	{
		private static final GuiceInjectorSource INSTANCE = new ApplicationGuiceInjectorSource();
	}
}
