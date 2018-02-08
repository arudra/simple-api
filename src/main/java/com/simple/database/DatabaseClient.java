package com.simple.database;

import java.sql.SQLException;

public interface DatabaseClient
{

	String run() throws SQLException;

	void addUser( String username, String password ) throws SQLException;

	boolean checkUser( String username, String password ) throws SQLException;
}
