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
	public String run() throws SQLException
	{
		try (final Connection connection = dataSource.getConnection();
		     final Statement statement = connection.createStatement())
		{
			final ResultSet resultSet = statement.executeQuery("SELECT * FROM cars");

			final StringBuilder builder = new StringBuilder();

			while (resultSet.next())
			{
				builder.append("name: ").append(resultSet.getString("name")).append(" ");
				builder.append("count: ").append(resultSet.getInt("count")).append(" ");
				builder.append("colour: ").append(resultSet.getString("colour")).append(" ");
				builder.append("\n");
			}

			return builder.toString();
		}
	}

}
