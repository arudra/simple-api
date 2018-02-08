package com.simple.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.PrivateModule;

public class AllApiModulesTest
{
	// SUT
	private AllApiModules sutModule;

	private Module sut;

	private Module deps;

	// Collaborators

	// Helpers
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks( this );

		sutModule = new AllApiModules();
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
		assertThat( Guice.createInjector( sut ), is( notNullValue() ) );
	}

}