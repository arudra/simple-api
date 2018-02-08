package com.simple.lcbo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LcboClientImplTest
{

	private static final String API_TOKEN = "Token "
	                                        + "MDphNDA5YWFhYS0wYjllLTExZTgtOTgyZS03ZmU2MTJhMTg5YzU6cjlEaHF0d2VISEJwYWJ5SVdTWW4zZjhxaDVYUWdsOEs0WDFL";

	private static final String LCBO_ENDPOINT = "http://lcboapi.com";

	private static final String AUTHORIZATION = "Authorization";

	// SUT
	private LcboClientImpl sut;

	// Collaborators
	@Mock
	private Client mockClient;

	@Mock
	private WebTarget mockWebTarget;

	@Mock
	private Builder mockBuilder;

	@Mock
	private Response mockResponse;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new LcboClientImpl( mockClient );

		when( mockClient.target( LCBO_ENDPOINT ) ).thenReturn( mockWebTarget );
		when( mockWebTarget.path( anyString() ) ).thenReturn( mockWebTarget );
		when( mockWebTarget.queryParam( anyString(), any() ) ).thenReturn( mockWebTarget );
		when( mockWebTarget.request() ).thenReturn( mockBuilder );
		when( mockBuilder.header( AUTHORIZATION, API_TOKEN ) ).thenReturn( mockBuilder );
		when( mockBuilder.get() ).thenReturn( mockResponse );
	}

	@Test
	public void test_Given_ProductQueryString_When_QueryingForProduct_Then_ReturnResponse() throws Exception
	{
		assertThat( sut.getProducts( "beer" ), is( mockResponse ) );
	}

	@Test
	public void test_Given_ProductIdAndLocation_When_QueryingForStores_Then_ReturnResponse() throws Exception
	{
		assertThat( sut.getStores( 10L, "Toronto" ), is( mockResponse ) );
	}

}