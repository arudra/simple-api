package com.simple.lcbo;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import com.google.common.base.Preconditions;

public class LcboClientImpl implements LcboClient
{

	private static final String API_TOKEN = "Token "
	                                        +
	                                        "MDphNDA5YWFhYS0wYjllLTExZTgtOTgyZS03ZmU2MTJhMTg5YzU6cjlEaHF0d2VISEJwYWJ5SVdTWW4zZjhxaDVYUWdsOEs0WDFL";

	private static final String LCBO_ENDPOINT = "http://lcboapi.com";

	private static final String AUTHORIZATION = "Authorization";

	private final Client client;

	@Inject
	LcboClientImpl( final Client client )
	{
		this.client = Preconditions.checkNotNull( client, "Client is null in LcboClientImpl" );
	}

	@Override
	public Response getProducts( final String query )
	{
		return client.target( LCBO_ENDPOINT )
		             .path( "/products" )
		             .queryParam( "q", query )
		             .request()
		             .header( AUTHORIZATION, API_TOKEN )
		             .get();
	}

	@Override
	public Response getStores( final long productId, final String location )
	{
		return client.target( LCBO_ENDPOINT )
		             .path( "/stores" )
		             .queryParam( "product_id", productId )
		             .queryParam( "q", location )
		             .request()
		             .header( AUTHORIZATION, API_TOKEN )
		             .get();
	}
}
