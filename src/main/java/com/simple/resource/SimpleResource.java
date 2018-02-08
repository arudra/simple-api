package com.simple.resource;

import com.google.common.base.Preconditions;
import com.simple.backend.BackendException;
import com.simple.backend.SimpleBackend;
import com.simple.server.ApplicationResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path( "v1/lcbo/stores" )
public class SimpleResource implements ApplicationResource
{
	private final Logger LOGGER = LogManager.getLogger( SimpleResource.class );

	private final SimpleBackend backend;

	@Inject
	SimpleResource( final SimpleBackend backend )
	{
		this.backend = Preconditions.checkNotNull( backend, "SimpleBackend is null in SimpleResource" );
	}

	@GET
	@Produces( "application/json" )
	public Response getStores( @QueryParam( "drink" ) final String drink, @QueryParam( "location" ) final String location )
	{
		try
		{
			LOGGER.info( "GET /v1/lcbo/stores?drink=" + drink + "&location=" + location );
			return backend.getStoresForProduct( drink, location );
		}
		catch( final BackendException e )
		{
			LOGGER.catching( e );
			return Response.serverError().entity( e.getMessage() ).build();
		}
	}

}
