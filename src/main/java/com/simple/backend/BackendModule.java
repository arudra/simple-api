package com.simple.backend;

import com.google.inject.PrivateModule;

public class BackendModule extends PrivateModule
{
	@Override
	protected void configure()
	{
		bind(SimpleBackend.class).to(SimpleBackendImpl.class);
		expose(SimpleBackend.class);
	}
}
