package com.simple.backend;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.simple.database.DatabaseClient;
import com.simple.lcbo.LcboClient;
import com.simple.lcbo.ProductResponse;

public class SimpleBackendImpl implements SimpleBackend
{

	public static final int RESPONSE_OK = Response.Status.OK.getStatusCode();

	private final DatabaseClient databaseClient;

	private final LcboClient lcboClient;

	private final ObjectMapper mapper;

	@Inject
	SimpleBackendImpl( final DatabaseClient dbClient, final LcboClient lcboClient, final ObjectMapper mapper )
	{
		databaseClient = Preconditions.checkNotNull( dbClient, "DatabaseClient is null in SimpleBackend" );
		this.lcboClient = Preconditions.checkNotNull( lcboClient, "LcboClient is null in SimpleBackend" );
		this.mapper = Preconditions.checkNotNull( mapper, "ObjectMapper is null in SimpleBackend" );
	}

	@Override
	public Response getProducts( final String query )
	{
		return lcboClient.getProducts( query );
	}

	@Override
	public Response getStoresForProduct( final String drink, final String location ) throws BackendException
	{
		final Response response = lcboClient.getProducts( drink );

		if( response.getStatus() != RESPONSE_OK )
		{
			throw new BackendException( "Got Response code " + response.getStatus() + " from LCBO API" );
		}
		else if( !response.hasEntity() )
		{
			throw new BackendException( "Got Empty Response from LCBO API" );
		}

		try
		{
			final ProductResponse productResponse = mapper.readValue( response.readEntity( String.class ), ProductResponse.class );

			final long id = productResponse.result().get( 0 ).productId();

			return lcboClient.getStores( id, location );
		}
		catch( final IOException e )
		{
			throw new BackendException( e );
		}
	}
}
