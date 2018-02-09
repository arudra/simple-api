package com.simple.user.resource;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.database.User;
import com.simple.server.ApplicationResource;
import com.simple.user.backend.UserBackend;
import com.simple.user.backend.UserBackendException;

@Path( "v1/user" )
public class UserResource implements ApplicationResource
{

	private static final String CORS_ORIGIN = "Access-Control-Allow-Origin";

	private static final String CORS_METHODS = "Access-Control-Allow-Methods";

	private static final String CORS_HEADERS = "Access-Control-Allow-Headers";

	private static final Response ALLOW_CORS_RESPONSE = Response.ok()
	                                                            .header( HttpHeaders.ALLOW, "POST" )
	                                                            .header( HttpHeaders.ALLOW, "OPTIONS" )
	                                                            .header( CORS_ORIGIN, "*" )
	                                                            .header( CORS_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS" )
	                                                            .header( CORS_HEADERS, "Origin, Content-Type, X-Auth-Token" )
	                                                            .build();

	private final Logger LOGGER = LogManager.getLogger( UserResource.class );

	private final ObjectMapper mapper;

	private final UserBackend backend;

	@Inject
	UserResource( final ObjectMapper mapper, final UserBackend backend )
	{
		this.mapper = checkNotNull( mapper, "Precondition: mapper is NULL in UserResource::UserResource" );
		this.backend = checkNotNull( backend, "Precondition: backend is NULL in UserResource::UserResource" );
	}

	@OPTIONS
	@Path( "login" )
	public Response userLoginOptions()
	{
		return ALLOW_CORS_RESPONSE;
	}

	@POST
	@Path( "login" )
	@Consumes( "application/json" )
	public Response userLogin( final String request )
	{
		try
		{
			final User user = mapper.readValue( request, User.class );
			if( backend.checkUser( user.username(), user.password() ) )
			{
				return Response.ok()
				               .header( CORS_ORIGIN, "*" )
				               .header( CORS_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS" )
				               .header( CORS_HEADERS, "Origin, Content-Type, X-Auth-Token" )
				               .build();
			}
			else
			{
				return Response.status( Response.Status.NOT_ACCEPTABLE )
				               .header( CORS_ORIGIN, "*" )
				               .header( CORS_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS" )
				               .header( CORS_HEADERS, "Origin, Content-Type, X-Auth-Token" )
				               .entity( "Invalid user or password." )
				               .build();
			}
		}
		catch( final IOException e )
		{
			LOGGER.catching( e );
			return Response.status( Response.Status.BAD_REQUEST )
			               .header( CORS_ORIGIN, "*" )
			               .header( CORS_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS" )
			               .header( CORS_HEADERS, "Origin, Content-Type, X-Auth-Token" )
			               .entity( "Request is malformed." )
			               .build();
		}
		catch( final UserBackendException e )
		{
			LOGGER.catching( e );
			return Response.serverError()
			               .header( CORS_ORIGIN, "*" )
			               .header( CORS_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS" )
			               .header( CORS_HEADERS, "Origin, Content-Type, X-Auth-Token" )
			               .entity( "Failed to login." )
			               .build();
		}
	}

	@OPTIONS
	@Path( "add" )
	public Response addUserOptions()
	{
		return ALLOW_CORS_RESPONSE;
	}

	@POST
	@Path( "add" )
	@Consumes( "application/json" )
	public Response addUser( final String request )
	{
		try
		{
			final User user = mapper.readValue( request, User.class );
			backend.addUser( user.username(), user.password() );
			return Response.ok()
			               .header( CORS_ORIGIN, "*" )
			               .header( CORS_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS" )
			               .header( CORS_HEADERS, "Origin, Content-Type, X-Auth-Token" )
			               .build();
		}
		catch( final IOException e )
		{
			LOGGER.catching( e );
			return Response.status( Response.Status.BAD_REQUEST )
			               .header( CORS_ORIGIN, "*" )
			               .header( CORS_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS" )
			               .header( CORS_HEADERS, "Origin, Content-Type, X-Auth-Token" )
			               .entity( "Request is malformed." )
			               .build();
		}
		catch( final UserBackendException e )
		{
			return Response.status( Response.Status.NOT_ACCEPTABLE )
			               .header( CORS_ORIGIN, "*" )
			               .header( CORS_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS" )
			               .header( CORS_HEADERS, "Origin, Content-Type, X-Auth-Token" )
			               .entity( "Failed to add user. This user already exists." )
			               .build();
		}
	}

}
