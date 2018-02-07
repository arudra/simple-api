package com.simple.backend;

public class BackendException extends Exception
{
	BackendException(final String s)
	{
		super(s);
	}

	BackendException(final Throwable throwable)
	{
		super(throwable);
	}
}
