package kz.edu.nu.cs.Services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/main")
public class MainService implements Serializable {
	private static final long serialVersionUID = 123654478955211411L;
	
	@GET
	public Response getHelloMessage() {
		CreateUser  cu = new CreateUser();
		return Response.ok("Hello world!").build();
	}
	
	@GET
	@Path("/hello")
	public Response getMessage() {
		return Response.ok("Dastan pidr!").build();
	}
}