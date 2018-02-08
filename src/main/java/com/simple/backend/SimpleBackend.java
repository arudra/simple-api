package com.simple.backend;

import javax.ws.rs.core.Response;

public interface SimpleBackend
{

	Response getStoresForProduct( String drink, String location ) throws BackendException;

}
