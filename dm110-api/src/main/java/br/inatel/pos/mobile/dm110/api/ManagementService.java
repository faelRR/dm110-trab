package br.inatel.pos.mobile.dm110.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/poller")
public interface ManagementService {

	@GET
	@Path("/start/{IP}/{Mask}")
	@Produces(MediaType.TEXT_HTML)
	void addNewIP(@PathParam("IP") String ip , @PathParam("Mask") String mask);
	
	@GET
	@Path("/status/{IP}")
	@Produces(MediaType.APPLICATION_JSON)
	boolean listIP(@PathParam("IP") String ip);
	
}
