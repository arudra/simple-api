package com.simple.backend;

import javax.inject.Inject;
import java.sql.SQLException;

import com.simple.database.DatabaseClient;
import jersey.repackaged.com.google.common.base.Preconditions;

public class SimpleBackendImpl implements SimpleBackend
{

	private final DatabaseClient databaseClient;


	@Inject
	SimpleBackendImpl(final DatabaseClient dbClient)
	{
		databaseClient = Preconditions.checkNotNull(dbClient, "DatabaseClient is null in SimpleBackend");
	}


	@Override
	public String getTheData() throws SQLException
	{
		return databaseClient.run();
	}
}
