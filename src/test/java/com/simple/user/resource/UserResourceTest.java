package com.simple.user.resource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.database.User;
import com.simple.user.backend.UserBackend;
import com.simple.user.backend.UserBackendException;

public class UserResourceTest
{

	private static final String USER_REQUEST = "{\"username\":\"user\",\"password\":\"pass\"}";

	private static final String USERNAME = "user";

	private static final String PASSWORD = "pass";

	private static final int RESPONSE_OK = Response.Status.OK.getStatusCode();

	private static final int RESPONSE_NOT_ACCEPTABLE = Response.Status.NOT_ACCEPTABLE.getStatusCode();

	private static final int RESPONSE_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

	private static final int RESPONSE_SERVER_ERROR = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

	// SUT
	private UserResource sut;

	// Collaborators
	@Mock
	private ObjectMapper mockMapper;

	@Mock
	private UserBackend mockBackend;

	@Mock
	private User mockUser;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new UserResource( mockMapper, mockBackend );

		when( mockUser.username() ).thenReturn( USERNAME );
		when( mockUser.password() ).thenReturn( PASSWORD );
		when( mockMapper.readValue( USER_REQUEST, User.class ) ).thenReturn( mockUser );
	}

	@Test
	public void test_Given_Resource_When_CallingOptionsLoginApi_Then_ReturnResponse() throws Exception
	{
		assertThat( sut.userLoginOptions(), is( instanceOf( Response.class ) ) );
	}

	@Test
	public void test_Given_Resource_When_CallingOptionsSignupApi_Then_ReturnResponse() throws Exception
	{
		assertThat( sut.addUserOptions(), is( instanceOf( Response.class ) ) );
	}

	@Test
	public void test_Given_ExistingUser_When_CallingLoginApi_Then_ReturnSuccessResponse() throws Exception
	{
		when( mockBackend.checkUser( USERNAME, PASSWORD ) ).thenReturn( true );
		assertThat( sut.userLogin( USER_REQUEST ).getStatus(), is( RESPONSE_OK ) );
	}

	@Test
	public void test_Given_NonExistingUser_When_CallingLoginApi_Then_ReturnErrorResponse() throws Exception
	{
		when( mockBackend.checkUser( USERNAME, PASSWORD ) ).thenReturn( false );
		assertThat( sut.userLogin( USER_REQUEST ).getStatus(), is( RESPONSE_NOT_ACCEPTABLE ) );
	}

	@Test
	public void test_Given_ObjectMappingError_When_CallingLoginApi_Then_ReturnErrorResponse() throws Exception
	{
		doThrow( IOException.class ).when( mockMapper ).readValue( USER_REQUEST, User.class );
		assertThat( sut.userLogin( USER_REQUEST ).getStatus(), is( RESPONSE_BAD_REQUEST ) );
	}

	@Test
	public void test_Given_BackendException_When_CallingLoginApi_Then_ReturnErrorResponse() throws Exception
	{
		doThrow( UserBackendException.class ).when( mockBackend ).checkUser( USERNAME, PASSWORD );
		assertThat( sut.userLogin( USER_REQUEST ).getStatus(), is( RESPONSE_SERVER_ERROR ) );
	}

	@Test
	public void test_Given_NonExistingUser_When_CallingSignUpApi_Then_ReturnSuccessResponse() throws Exception
	{
		assertThat( sut.addUser( USER_REQUEST ).getStatus(), is( RESPONSE_OK ) );
	}

	@Test
	public void test_Given_ObjectMappingError_When_CallingSignUpApi_Then_ReturnErrorResponse() throws Exception
	{
		doThrow( IOException.class ).when( mockMapper ).readValue( USER_REQUEST, User.class );
		assertThat( sut.addUser( USER_REQUEST ).getStatus(), is( RESPONSE_BAD_REQUEST ) );
	}

	@Test
	public void test_Given_BackendException_When_CallingSignUpApi_Then_ReturnErrorResponse() throws Exception
	{
		doThrow( UserBackendException.class ).when( mockBackend ).addUser( USERNAME, PASSWORD );
		assertThat( sut.addUser( USER_REQUEST ).getStatus(), is( RESPONSE_NOT_ACCEPTABLE ) );
	}

}