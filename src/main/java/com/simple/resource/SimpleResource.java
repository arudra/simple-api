package com.simple.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

import com.simple.backend.SimpleBackend;
import com.simple.server.ApplicationResource;
import jersey.repackaged.com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("v1/simple")
public class SimpleResource implements ApplicationResource
{
	private final Logger LOGGER = LogManager.getLogger(SimpleResource.class);

	private final SimpleBackend backend;

	@Inject
	SimpleResource(final SimpleBackend backend)
	{
		this.backend = Preconditions.checkNotNull(backend, "SimpleBackend is null in SimpleResource");
	}

	@GET
	@Path("/data")
	@Produces("text/plain")
	public Response getData()
	{
		try
		{
			return Response.ok(backend.getTheData(), MediaType.TEXT_PLAIN_TYPE).build();
		} catch (final SQLException e)
		{
			LOGGER.catching(e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

}
