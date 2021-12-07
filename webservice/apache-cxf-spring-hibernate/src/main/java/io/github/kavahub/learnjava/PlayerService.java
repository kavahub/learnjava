package io.github.kavahub.learnjava;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Web Services 接口，支持XML和JSON数据格式
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Path("/players")
public interface PlayerService {
    @POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
	public Response create(PlayerType playerType);

	@GET
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getById(@PathParam("id") int id);

	@PUT
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_FORM_URLENCODED})
	public Response update(@PathParam("id") int id, PlayerType playerType);

	@DELETE
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,})
	@Produces({MediaType.APPLICATION_FORM_URLENCODED})
	public Response delete(@PathParam("id") int id);

	@GET
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getByName(@QueryParam("name") String name);   
}
