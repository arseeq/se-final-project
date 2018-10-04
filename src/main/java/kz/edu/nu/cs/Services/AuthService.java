package kz.edu.nu.cs.Services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import kz.edu.nu.cs.Model.User;

@Path("/auth")
public class AuthService{
	private static final long serialVersionUID = 1236544789552114471L;
	
	@GET
	@Path("/signup")
	public Response signin() {
		System.out.println("in sign up-----------------------");
		return Response.ok("Hello world!").build();
	}
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signin(String json) {
		Gson g = new Gson();
		User user  = g.fromJson(json, User.class);
		CreateUser cu = new CreateUser();
		cu.createUser(user);
		
		return Response.ok("Hello world!").build();
	}
	
	
	
}