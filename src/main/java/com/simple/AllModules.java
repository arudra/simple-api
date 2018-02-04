package com.simple;

import com.google.inject.AbstractModule;
import com.simple.api.AllApiModules;

public class AllModules extends AbstractModule
{
	@Override
	protected void configure()
	{
		install(new AllApiModules());
	}
}
