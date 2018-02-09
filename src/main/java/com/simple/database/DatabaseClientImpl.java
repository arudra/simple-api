package com.simple.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Preconditions;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DatabaseClientImpl implements DatabaseClient
{

	private static final Logger LOGGER = LogManager.getLogger( DatabaseClientImpl.class );

	private static final String USER = "user";

	private static final String PASSWORD = "password";

	private static final String USERS_TABLE = "users";

	private static final String INSERT_NEW_USER = "INSERT INTO " + USERS_TABLE + " (" + USER + ", " + PASSWORD + ") "
	                                              + "VALUES (?,?)";

	private static final String FIND_USER = "SELECT * FROM " + USERS_TABLE + " WHERE " + USER + "=? AND " + PASSWORD + "=?";

	private static final String DEV = "dev";

	private static final String DB_NAME = "main";

	private static final String SERVER = "localhost";

	private static final int PORT = 3306;

	private final MysqlDataSource dataSource;


	@Inject
	DatabaseClientImpl( final MysqlDataSource dataSource )
	{
		this.dataSource = Preconditions.checkNotNull( dataSource, "DataSource is null in DatabaseClient" );
		this.dataSource.setUser( DEV );
		this.dataSource.setPassword( DEV );
		this.dataSource.setDatabaseName( DB_NAME );
		this.dataSource.setPort( PORT );
		this.dataSource.setServerName( SERVER );
	}

	@Override
	public void addUser( final String username, final String password ) throws SQLException
	{
		try ( final Connection connection = dataSource.getConnection();
		      final PreparedStatement statement = connection.prepareStatement( INSERT_NEW_USER ) )
		{
			statement.setString( 1, username );
			statement.setString( 2, password );
			statement.execute();
		}
	}

	@Override
	public boolean checkUser( final String username, final String password ) throws SQLException
	{
		try ( final Connection connection = dataSource.getConnection();
		      final PreparedStatement statement = connection.prepareStatement( FIND_USER ) )
		{
			statement.setString( 1, username );
			statement.setString( 2, password );
			final ResultSet resultSet = statement.executeQuery();

			while( resultSet.next() )
			{
				final String dbUser = resultSet.getString( USER );
				final String dbPassword = resultSet.getString( PASSWORD );

				if( dbUser.equals( username ) && dbPassword.equals( password ) )
				{
					return true;
				}
			}
			return false;
		}
	}

}
