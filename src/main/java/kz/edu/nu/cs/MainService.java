package kz.edu.nu.cs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/main")
public class MainService{
	private static final long serialVersionUID = 123654478955211411L;
	
	@GET
	public Response getHelloMessage() {
		
		return Response.ok("Hello world!").build();
	}
}