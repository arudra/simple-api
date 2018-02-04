package com.simple.database;

import com.google.inject.PrivateModule;

public class DatabaseModule extends PrivateModule
{
	@Override
	protected void configure()
	{
		bind(DatabaseClient.class).to(DatabaseClientImpl.class);
		expose(DatabaseClient.class);
	}
}
