package kz.edu.nu.cs.Services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import kz.edu.nu.cs.Model.User;

@Path("/auth")
public class AuthService{
	private static final long serialVersionUID = 1236544789552114471L;
	private KeyGenerator keyGenerator;
	private CreateUser cu; //make EJB in future 
	
	private SecretKey sk;
	private Key pk;
	
	@GET
	@Path("/aaa")
	public Response si(@CookieParam("token") Cookie cookie) {
		//System.out.println("Dastan has come                                 ");
		String token = cookie.getValue();
		System.out.println(token);
		if (token == null || token.equals("")) {
			return Response.status(403).build();
		}
		String res = isValidToken(token);
		if (res!=null && !res.equals("")) {
			return Response.ok(res).build();
		} else return Response.ok("Not correct").build();
	}
	
//	@GET
//	@Path("/checkToken")
//	public Response signin(@CookieParam("token") Cookie cookie) {
//		String token = cookie.getValue();
//		if (token == null || token.equals("")) {
//			return Response.status(403).build();
//		}
//		System.out.println(token);
//		String res = isValidToken(token);
//		if (res!=null && !res.equals("")) {
//			return Response.ok(res).build();
//		} else return Response.ok("Not correct").build();
//	}
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signup(String json) {		
		Gson g = new Gson();
		User user  = g.fromJson(json, User.class);
		System.out.println(" - signup: " + user);
		cu = getCreateUser();
		cu.createUser(user);		
		String token = issueToken(user.getEmail());
		NewCookie cookie = new NewCookie("token", token);
		
		return Response.ok(token).cookie(cookie).build();
	}
	
	@POST
	@Path("/checktoken")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response checkToken(String json) {
		JSONObject obj = new JSONObject(json);
		String tokenToCheck = obj.getString("token");
		if (tokenToCheck == null || tokenToCheck.equals("")) {
			return Response.status(403).build();
		}
		System.out.println(" - token: " + tokenToCheck);
		String res = isValidToken(tokenToCheck);
		if (res!=null && !res.equals("")) {
			return Response.ok(res).build();
		} else return Response.status(403).build();
	}
	
	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signin(String json) {
		JSONObject obj = new JSONObject(json);
		String login = obj.getString("email");
		String password = obj.getString("password");

		System.out.println(" - signin: " + login + ", " + password);
		
		Gson g = new Gson();
		if(!authenticate(login, password)) {
			return Response.status(403).build();
		}
		String token = issueToken(login);
		NewCookie cookie = new NewCookie("token", token);
		return Response.ok(token).cookie(cookie).build();
	}
	
	private boolean authenticate(String login, String password) {
		cu = getCreateUser();
		String passwordObt = cu.getUserPasswordByLogin(login);
		if(passwordObt!=null && passwordObt.equals(password)) {
			return true;
		}
		return false;
	}
	
	private String issueToken(String login) {
    	keyGenerator = getKeyGenerator();
        SecretKey key = getKey();
        
        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        Date afterAddingTenMins=new Date(t + (10 * 60000));
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer("baktybek")
                .setIssuedAt(new Date())
                .setExpiration(afterAddingTenMins)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return jwtToken;
    }
	
	public CreateUser getCreateUser() {
		if(cu==null) {
			cu = new CreateUser();
		}
		return cu;
	}
	
	public KeyGenerator getKeyGenerator() {
		if(keyGenerator == null)
			try {
				keyGenerator = KeyGenerator.getInstance("HmacSHA256");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return keyGenerator;
	}
	
	public SecretKey getKey() {
		if(sk==null)
			sk = keyGenerator.generateKey();
		return sk;
	}
	
	public Key getPkey() {
		if(pk==null)
			pk= keyGenerator.generateKey();
		return pk;
	}
	
	public String isValidToken(String token) {
		keyGenerator = getKeyGenerator();
		String res;
		Key key = getKey();
		try {
			//Decoder jwt = ;
			res = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
			System.out.println(res + " reeeeeeeeeeeeeeeees");
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);}
        catch (Exception ex) {
        	System.out.println(ex.getMessage());
        	return null;
        }
        System.out.println("#### valid token : " + token);
		return res;
	}
	
}
