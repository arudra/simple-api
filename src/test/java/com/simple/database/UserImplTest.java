package com.simple.database;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserImplTest
{

	private static final String PASS = "password";

	private static final String USER = "user";

	// SUT
	private UserImpl sut;

	// Collaborators
	@Mock
	Object mockClass;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new UserImpl( USER, PASS );
	}

	@Test
	public void test_Given_Sut_When_GettingFields_Then_ReturnCorrectFields() throws Exception
	{
		assertThat( sut.username(), is( USER ) );
		assertThat( sut.password(), is( PASS ) );
	}


}