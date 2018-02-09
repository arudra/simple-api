package com.simple.database;

import java.sql.SQLException;

public interface DatabaseClient
{

	void addUser( String username, String password ) throws SQLException;

	boolean checkUser( String username, String password ) throws SQLException;
	
}
