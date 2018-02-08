package com.simple.user.backend;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.SQLException;
import javax.inject.Inject;

import com.simple.database.DatabaseClient;

public class UserBackendImpl implements UserBackend
{

	private final DatabaseClient databaseClient;


	@Inject
	UserBackendImpl( final DatabaseClient databaseClient )
	{
		this.databaseClient = checkNotNull( databaseClient,
		                                    "Precondition: databaseClient is NULL in UserBackendImpl::UserBackendImpl" );
	}


	@Override
	public void addUser( final String username, final String password ) throws UserBackendException
	{
		try
		{
			databaseClient.addUser( username, password );
		}
		catch( final SQLException e )
		{
			throw new UserBackendException( e );
		}
	}

	@Override
	public boolean checkUser( final String username, final String password ) throws UserBackendException
	{
		try
		{
			return databaseClient.checkUser( username, password );
		}
		catch( final SQLException e )
		{
			throw new UserBackendException( e );
		}
	}


}
