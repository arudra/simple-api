package com.simple.user.backend;

import com.google.inject.PrivateModule;

public class UserBackendModule extends PrivateModule
{
	@Override
	protected void configure()
	{
		bind( UserBackend.class ).to( UserBackendImpl.class );
		expose( UserBackend.class );
	}
}
