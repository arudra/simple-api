package com.simple.backend;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.PrivateModule;
import com.simple.database.DatabaseClient;
import com.simple.lcbo.LcboClient;

public class BackendModuleTest
{
	// SUT
	private BackendModule sutModule;

	private Module sut;

	private Module deps;

	// Collaborators
	@Mock
	private DatabaseClient mockDbClient;

	@Mock
	private LcboClient mockLcboClient;

	// Helpers
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks( this );

		sutModule = new BackendModule();
		deps = new PrivateModule()
		{
			@Override
			protected void configure()
			{
				bind( DatabaseClient.class ).toInstance( mockDbClient );
				bind( LcboClient.class ).toInstance( mockLcboClient );

				expose( DatabaseClient.class );
				expose( LcboClient.class );
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
		MatcherAssert.assertThat( Guice.createInjector( sut ), is( notNullValue() ) );
	}

	@Test
	public void test_Given_Module_When_CheckingClass_Then_ContainsClass() throws Exception
	{
		MatcherAssert.assertThat( Guice.createInjector( sut ).getInstance( SimpleBackend.class ),
		                          is( instanceOf( SimpleBackend.class ) ) );
	}
}