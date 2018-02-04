package com.simple.server;

import java.util.Set;

import com.google.inject.TypeLiteral;

public interface ApplicationResource
{
	/**
	 * Use by Guice for binding this type's class
	 */
	TypeLiteral<Class<? extends ApplicationResource>> TYPE =
		new TypeLiteral<Class<? extends ApplicationResource>>()
		{
		};

	/**
	 * Use by Guice for binding a set of this type's class
	 */
	TypeLiteral<Set<Class<? extends ApplicationResource>>> SET_TYPE =
		new TypeLiteral<Set<Class<? extends ApplicationResource>>>()
		{
		};

}
