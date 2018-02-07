package com.simple.lcbo;

import java.util.List;
import javax.inject.Inject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class ProductResponseImpl implements ProductResponse
{

	private final int status;

	private final String message;

	private final List< DrinkResult > result;


	@Inject
	@JsonCreator
	ProductResponseImpl(
		@JsonProperty( "status" ) final int status,
		@JsonProperty( "message" ) final String message,
		@JsonProperty( "result" ) final List< DrinkResult > result )
	{
		this.status = status;
		this.message = message;
		this.result = Preconditions.checkNotNull( result, "result is null in ProductResponseImpl" );
	}

	@Override
	public int status()
	{
		return status;
	}

	@Override
	public String message()
	{
		return message;
	}

	@Override
	public List< DrinkResult > result()
	{
		return result;
	}

	@Override
	public final boolean equals( final Object obj )
	{
		return obj instanceof ProductResponseImpl
		       && Objects.equal( status, ( (ProductResponseImpl)obj ).status )
		       && Objects.equal( message, ( (ProductResponseImpl)obj ).message )
		       && Objects.equal( result, ( (ProductResponseImpl)obj ).result );
	}
}
