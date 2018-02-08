package com.simple.lcbo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.PrivateModule;

public class LcboClientModuleTest
{
	// SUT
	private LcboClientModule sutModule;

	private Module sut;

	private Module deps;

	private Injector injector;

	// Collaborators

	// Helpers
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks( this );

		sutModule = new LcboClientModule();
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

		injector = Guice.createInjector( sut );
	}


	@Test
	public void test_GivenResourceModule_WhenCreatingInjector_ThenReturnNotNullValue()
	{
		assertThat( injector, is( notNullValue() ) );
	}

	@Test
	public void test_Given_Module_When_CheckingClass_Then_ContainsClass() throws Exception
	{
		assertThat( injector.getInstance( LcboClient.class ), is( instanceOf( LcboClient.class ) ) );
	}

}