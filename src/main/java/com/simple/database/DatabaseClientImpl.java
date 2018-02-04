package com.simple.database;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jersey.repackaged.com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseClientImpl implements DatabaseClient
{
	private static final Logger LOGGER = LogManager.getLogger(DatabaseClientImpl.class);

	private final MysqlDataSource dataSource;


	@Inject
	DatabaseClientImpl(final MysqlDataSource dataSource)
	{
		this.dataSource = Preconditions.checkNotNull(dataSource, "DataSource is null in MysqlDataSource");
	}

	@Override
	public void run() throws SQLException
	{
		try (final Connection connection = dataSource.getConnection();
		     final Statement statement = connection.createStatement())
		{
			final ResultSet resultSet = statement.executeQuery("SELECT * FROM cars");

			while (resultSet.next())
			{
				System.out.println("name: " + resultSet.getString("name"));
				System.out.println("count: " + resultSet.getInt("count"));
				System.out.println("colour: " + resultSet.getString("colour"));
				System.out.println();
			}
		}
	}

}
