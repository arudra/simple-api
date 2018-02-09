package com.simple.user.backend;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.simple.database.DatabaseClient;

public class UserBackendImplTest
{

	private static final String USER = "user";

	private static final String PASSWORD = "pass";

	// SUT
	private UserBackendImpl sut;

	// Collaborators
	@Mock
	private DatabaseClient mockDbClient;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new UserBackendImpl( mockDbClient );
	}

	@Test
	public void test_Given_UserAndPassword_When_AddingUser_Then_AddSuccessful() throws Exception
	{
		sut.addUser( USER, PASSWORD );
		verify( mockDbClient ).addUser( USER, PASSWORD );
	}

	@Test( expected = UserBackendException.class )
	public void test_Given_ExistingUser_When_AddingUser_Then_ThrowException() throws Exception
	{
		doThrow( SQLException.class ).when( mockDbClient ).addUser( USER, PASSWORD );
		sut.addUser( USER, PASSWORD );
	}

	@Test
	public void test_Given_UserAndPassword_When_CheckingUser_Then_UserFound() throws Exception
	{
		when( mockDbClient.checkUser( USER, PASSWORD ) ).thenReturn( true );
		assertThat( sut.checkUser( USER, PASSWORD ), is( true ) );
	}

	@Test( expected = UserBackendException.class )
	public void test_Given_ExistingUserAndDbThrowsException_When_CheckingUser_Then_ThrowException() throws Exception
	{
		doThrow( SQLException.class ).when( mockDbClient ).checkUser( USER, PASSWORD );
		sut.checkUser( USER, PASSWORD );
	}

}