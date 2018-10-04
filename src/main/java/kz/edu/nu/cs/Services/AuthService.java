package kz.edu.nu.cs.Services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/auth")
public class AuthService{
	private static final long serialVersionUID = 1236544789552114471L;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signin(String json) {
		Gson g = new Gson();
		
		return Response.ok("Hello world!").build();
	}
}