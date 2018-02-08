package com.simple.lcbo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class DrinkResultImplTest
{

	private static final int INVENTORY = 2000;

	private static final long ID = 10;

	private static final String NAME = "stella";

	// SUT
	private DrinkResultImpl sut;

	// Collaborators

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new DrinkResultImpl( ID, NAME, INVENTORY );
	}

	@Test
	public void test_Given_Sut_When_GettingFields_Then_ReturnCorrectValues() throws Exception
	{
		assertThat( sut.productId(), is( ID ) );
		assertThat( sut.name(), is( NAME ) );
		assertThat( sut.inventoryCount(), is( INVENTORY ) );
	}

}