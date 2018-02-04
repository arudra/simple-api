package com.simple.api;

import com.simple.backend.BackendModule;
import com.simple.database.DatabaseModule;
import com.simple.resource.SimpleResource;
import com.simple.server.AbstractApplicationResourceModule;

public class AllApiModules extends AbstractApplicationResourceModule
{
	@Override
	protected void configureAdditional()
	{
		install(new BackendModule());
		install(new DatabaseModule());

		bindResource(SimpleResource.class);
	}
}
