package com.simple.user.backend;

public interface UserBackend
{

	void addUser( String username, String password ) throws UserBackendException;

	boolean checkUser( String username, String password ) throws UserBackendException;
}
