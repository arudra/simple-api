package com.simple.user.backend;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Test;

public class UserBackendExceptionTest
{
	@Test
	public void test_Given_IOException_When_ConstructingException_Then_ReturnException() throws Exception
	{
		assertThat( new UserBackendException( new IOException() ), isA( UserBackendException.class ) );
	}
}