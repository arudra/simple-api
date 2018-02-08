package com.simple.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.simple.backend.BackendException;
import com.simple.backend.SimpleBackend;

public class SimpleResourceTest
{

	private static final String TORONTO = "Toronto";

	private static final String DRINK = "beer";

	// SUT
	private SimpleResource sut;

	// Collaborators
	@Mock
	private SimpleBackend mockBackend;

	@Mock
	private Response mockResponse;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new SimpleResource( mockBackend );
	}

	@Test
	public void test_Given_BackendReturnsResponse_When_GettingStores_Then_ReturnOkResponse() throws Exception
	{
		when( mockBackend.getStoresForProduct( DRINK, TORONTO ) ).thenReturn( mockResponse );
		assertThat( sut.getStores( DRINK, TORONTO ), is( mockResponse ) );
	}

	@Test
	public void test_Given_BackendThrowsError_When_GettingStores_Then_ReturnErrorResponse() throws Exception
	{
		doThrow( BackendException.class ).when( mockBackend ).getStoresForProduct( DRINK, TORONTO );
		assertThat( sut.getStores( DRINK, TORONTO ).getStatus(),
		            is( Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() ) );
	}

}