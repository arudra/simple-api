package com.simple.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	public void test_Given_ResourceModule_When_CreatingInjector_Then_ReturnNotNullValue()
	{
		assertThat( Guice.createInjector( sut ), is( notNullValue() ) );
	}

	@Test
	public void test_Given_ResourceModule_When_GettingMapperProvider_Then_ReturnNotNullValue() throws Exception
	{
		assertThat( Guice.createInjector( sut ).getProvider( ObjectMapper.class ), is( notNullValue() ) );
	}

}