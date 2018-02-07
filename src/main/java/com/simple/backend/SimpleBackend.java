package com.simple.backend;

import javax.ws.rs.core.Response;

public interface SimpleBackend
{

	Response getProducts(String query);

	Response getStoresForProduct(String drink, String location) throws BackendException;
}
