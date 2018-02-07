package com.simple.lcbo;

import static com.google.common.base.Strings.isNullOrEmpty;

import javax.inject.Inject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

public class DrinkResultImpl implements DrinkResult
{

	private final long productId;

	private final String name;

	private final int inventoryCount;

	@Inject
	@JsonCreator
	DrinkResultImpl(
		@JsonProperty( "id" ) final long productId,
		@JsonProperty( "name" ) final String name,
		@JsonProperty( "inventory_count" ) final int inventoryCount )
	{
		Preconditions.checkArgument( !isNullOrEmpty( name ), "name is null in DrinkResult" );
		this.productId = productId;
		this.name = name;
		this.inventoryCount = inventoryCount;
	}

	@Override
	public long productId()
	{
		return productId;
	}

	@Override
	public String name()
	{
		return name;
	}

	@Override
	public int inventoryCount()
	{
		return inventoryCount;
	}
}
