package com.simple.backend;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Test;

public class BackendExceptionTest
{

	@Test
	public void test_Given_Throwable_When_CreatingBackendException_Then_ReturnBackendException() throws Exception
	{
		assertThat( new BackendException( new IOException() ), is( instanceOf( BackendException.class ) ) );
	}

	@Test
	public void test_Given_FailMessage_When_CreatingBackendException_Then_ReturnBackendException() throws Exception
	{
		assertThat( new BackendException( "fail" ), is( instanceOf( BackendException.class ) ) );
	}

}