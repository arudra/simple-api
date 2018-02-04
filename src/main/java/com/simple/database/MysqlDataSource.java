package com.simple.database;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class MysqlDataSource implements DataSource
{

	private static final String DEV = "dev";
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/main";
	private Connection connection;

	public MysqlDataSource()
	{
		connection = null;
	}

	@Override
	public Connection getConnection() throws SQLException
	{
		if (connection == null)
		{
			connection = DriverManager.getConnection(JDBC_URL, DEV, DEV);
		}
		return connection;
	}

	@Override
	public Connection getConnection(final String s, final String s1) throws SQLException
	{
		return null;
	}

	@Override
	public <T> T unwrap(final Class<T> aClass) throws SQLException
	{
		return null;
	}

	@Override
	public boolean isWrapperFor(final Class<?> aClass) throws SQLException
	{
		return false;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException
	{
		return null;
	}

	@Override
	public void setLogWriter(final PrintWriter printWriter) throws SQLException
	{

	}

	@Override
	public void setLoginTimeout(final int i) throws SQLException
	{

	}

	@Override
	public int getLoginTimeout() throws SQLException
	{
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException
	{
		return null;
	}
}
