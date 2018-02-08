package com.simple.database;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import javax.inject.Inject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserImpl implements User
{

	private final String username;

	private final String password;

	@Inject
	@JsonCreator
	UserImpl( @JsonProperty( "username" ) final String username, @JsonProperty( "password" ) final String password )
	{
		checkArgument( !isNullOrEmpty( username ),
		               "Precondition: The condition !isNullOrEmpty(username) was not satisfied in UserImpl::UserImpl" );
		checkArgument( !isNullOrEmpty( password ),
		               "Precondition: The condition !isNullOrEmpty( password ) was not satisfied in UserImpl::UserImpl" );
		this.username = username;
		this.password = password;
	}

	@Override
	public String username()
	{
		return username;
	}

	@Override
	public String password()
	{
		return password;
	}
}
