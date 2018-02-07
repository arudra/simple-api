package com.simple.backend;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.database.DatabaseClient;
import com.simple.lcbo.LcboClient;

public class SimpleBackendImplTest
{

	// SUT
	private SimpleBackendImpl sut;

	// Collaborators
	@Mock
	private DatabaseClient mockDbClient;

	@Mock
	private LcboClient mockLcboClient;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new SimpleBackendImpl( mockDbClient, mockLcboClient, new ObjectMapper() );
	}

	@Test
	public void test_Given_A_When_B_Then_C() throws Exception
	{

	}


}