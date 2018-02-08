package com.simple.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.simple.database.DatabaseClient;
import com.simple.lcbo.DrinkResult;
import com.simple.lcbo.LcboClient;
import com.simple.lcbo.ProductResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class SimpleBackendImplTest
{

	private static final String DRINK = "beer";

	private static final int RESPONSE_OK = Response.Status.OK.getStatusCode();

	private static final int RESPONSE_ERROR = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

	private static final String TORONTO = "Toronto";

	private static final String RESPONSE_STRING = "some-response";

	private static final long PRODUCT_ID = 2513L;

	// SUT
	private SimpleBackendImpl sut;

	// Collaborators
	@Mock
	private DatabaseClient mockDbClient;

	@Mock
	private LcboClient mockLcboClient;

	@Mock
	private ObjectMapper mockMapper;

	@Mock
	private Response mockResponse;

	@Mock
	private ProductResponse mockProductResponse;

	@Mock
	private DrinkResult mockDrinkResult;

	@Mock
	private Response mockResponse2;

	@Mock
	private MultivaluedMap< String, Object > mockHeadersMap;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new SimpleBackendImpl( mockDbClient, mockLcboClient, mockMapper );

		when( mockLcboClient.getProducts( DRINK ) ).thenReturn( mockResponse );

	}

	@Test( expected = BackendException.class )
	public void test_Given_LcboApiReturnsEmptyBody_When_GettingProducts_Then_ThrowBackendException() throws Exception
	{
		when( mockResponse.getStatus() ).thenReturn( RESPONSE_OK );
		when( mockResponse.hasEntity() ).thenReturn( false );
		sut.getStoresForProduct( DRINK, TORONTO );
	}

	@Test( expected = BackendException.class )
	public void test_Given_LcboApiReturnsError_When_GettingProducts_Then_ThrowBackendException() throws Exception
	{
		when( mockResponse.getStatus() ).thenReturn( RESPONSE_ERROR );
		sut.getStoresForProduct( DRINK, TORONTO );
	}

	@Test
	public void test_Given_DrinkAndLocation_When_GettingStores_Then_ReturnStoresResponse() throws Exception
	{
		when( mockResponse.getStatus() ).thenReturn( RESPONSE_OK );
		when( mockResponse.hasEntity() ).thenReturn( true );

		when( mockResponse.readEntity( String.class ) ).thenReturn( RESPONSE_STRING );
		when( mockMapper.readValue( RESPONSE_STRING, ProductResponse.class ) ).thenReturn( mockProductResponse );

		when( mockProductResponse.result() ).thenReturn( ImmutableList.of( mockDrinkResult ) );
		when( mockDrinkResult.productId() ).thenReturn( PRODUCT_ID );
		when( mockLcboClient.getStores( PRODUCT_ID, TORONTO ) ).thenReturn( mockResponse2 );
		when( mockResponse2.getHeaders() ).thenReturn( mockHeadersMap );

		assertThat( sut.getStoresForProduct( DRINK, TORONTO ), is( mockResponse2 ) );
	}

	@Test( expected = BackendException.class )
	public void test_Given_LcboApiReturnsMalformedObject_When_GettingStores_Then_ThrowBackendException() throws Exception
	{
		when( mockResponse.getStatus() ).thenReturn( RESPONSE_OK );
		when( mockResponse.hasEntity() ).thenReturn( true );

		when( mockResponse.readEntity( String.class ) ).thenReturn( RESPONSE_STRING );
		doThrow( IOException.class ).when( mockMapper ).readValue( RESPONSE_STRING, ProductResponse.class );

		sut.getStoresForProduct( DRINK, TORONTO );
	}

}