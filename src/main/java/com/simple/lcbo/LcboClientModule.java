package com.simple.lcbo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class LcboClientModule extends PrivateModule
{

	@Override
	protected void configure()
	{
		bind(LcboClient.class).to(LcboClientImpl.class);
		expose(LcboClient.class);
	}


	@Provides
	@Singleton
	public Client getClient()
	{
		return ClientBuilder.newClient();
	}
}
