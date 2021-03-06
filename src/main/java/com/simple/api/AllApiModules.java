package com.simple.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.simple.backend.BackendModule;
import com.simple.database.DatabaseModule;
import com.simple.lcbo.LcboClientModule;
import com.simple.resource.SimpleResource;
import com.simple.server.AbstractApplicationResourceModule;
import com.simple.user.backend.UserBackendModule;
import com.simple.user.resource.UserResource;

public class AllApiModules extends AbstractApplicationResourceModule
{
	@Override
	protected void configureAdditional()
	{
		install( new BackendModule() );
		install( new DatabaseModule() );
		install( new LcboClientModule() );
		install( new UserBackendModule() );

		bindResource( SimpleResource.class );
		bindResource( UserResource.class );
	}

	@Provides
	@Singleton
	public ObjectMapper getMapper()
	{
		return new ObjectMapper().registerModule( new Jdk8Module() );
	}
}
