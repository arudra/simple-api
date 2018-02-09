package com.simple.database;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.PrivateModule;

public class DatabaseModuleTest
{
	// SUT
	private DatabaseModule sutModule;

	private Module sut;

	private Module deps;

	// Collaborators

	// Helpers
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks( this );

		sutModule = new DatabaseModule();
		deps = new PrivateModule()
		{
			@Override
			protected void configure()
			{

			}
		};

		sut = new AbstractModule()
		{
			@Override
			protected void configure()
			{
				install( sutModule );
				install( deps );
			}
		};
	}
	
	@Test
	public void test_GivenResourceModule_WhenCreatingInjector_ThenReturnNotNullValue()
	{
		MatcherAssert.assertThat( Guice.createInjector( sut ), CoreMatchers.is( CoreMatchers.notNullValue() ) );
	}

	@Test
	public void test_Given_Module_When_CheckingClass_Then_ContainsClass() throws Exception
	{
		MatcherAssert.assertThat( Guice.createInjector( sut ).getInstance( DatabaseClient.class ),
		                          CoreMatchers.is( CoreMatchers.instanceOf( DatabaseClient.class ) ) );
	}
}