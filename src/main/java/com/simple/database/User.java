package com.simple.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize( as = UserImpl.class )
public interface User
{

	@JsonProperty( "username" )
	String username();

	@JsonProperty( "password" )
	String password();

}
