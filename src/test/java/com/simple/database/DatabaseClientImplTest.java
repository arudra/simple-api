package com.simple.database;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DatabaseClientImplTest
{

	private static final String PASS = "password";

	private static final String USER = "user";

	// SUT
	private DatabaseClient sut;

	// Collaborators
	@Mock
	private MysqlDataSource mockDatasource;

	@Mock
	private Connection mockConnection;

	@Mock
	private PreparedStatement mockStatement;

	@Mock
	private ResultSet mockResultSet;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new DatabaseClientImpl( mockDatasource );

		when( mockDatasource.getConnection() ).thenReturn( mockConnection );
		when( mockConnection.prepareStatement( anyString() ) ).thenReturn( mockStatement );
	}

	@Test
	public void test_Given_UsernameAndPassword_When_AddingUser_Then_Successful() throws Exception
	{
		sut.addUser( USER, PASS );
		verify( mockStatement ).setString( 1, USER );
		verify( mockStatement ).setString( 2, PASS );
		verify( mockStatement ).execute();
	}

	@Test
	public void test_Given_UsernameAndPassword_When_CheckingUser_Then_ReturnTrue() throws Exception
	{
		when( mockStatement.executeQuery() ).thenReturn( mockResultSet );
		when( mockResultSet.next() ).thenReturn( true ).thenReturn( false );
		when( mockResultSet.getString( USER ) ).thenReturn( USER );
		when( mockResultSet.getString( PASS ) ).thenReturn( PASS );
		assertThat( sut.checkUser( USER, PASS ), is( true ) );
	}

	@Test
	public void test_Given_InvalidUsernameAndPassword_When_CheckingUser_Then_ReturnFalse() throws Exception
	{
		when( mockStatement.executeQuery() ).thenReturn( mockResultSet );
		when( mockResultSet.next() ).thenReturn( false );
		assertThat( sut.checkUser( USER, PASS ), is( false ) );
	}

}