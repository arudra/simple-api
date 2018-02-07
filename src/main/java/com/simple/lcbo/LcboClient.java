package com.simple.lcbo;

import javax.ws.rs.core.Response;

public interface LcboClient
{

	Response getProducts(String query);

	Response getStores(long productId, String location);
}
