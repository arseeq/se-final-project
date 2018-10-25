package kz.edu.nu.cs.Services;

import java.io.Serializable;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import kz.edu.nu.cs.Utility.PasswordHasher;
import org.json.JSONObject;

import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.edu.nu.cs.Model.User;

@Path("/auth")
public class AuthService implements Serializable {
<<<<<<< HEAD
    private static final long serialVersionUID = 1236544789552114471L;
    private static KeyGenerator keyGenerator;
    private CreateUser cu; //make EJB in future

    private static SecretKey sk;
    private Key pk;

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signup(String json) {
        System.out.println("\nsignup start");
        System.out.println("json=" + json);
        JSONObject obj = new JSONObject(json);
        String passwordConfirm = obj.getString("passwordConfirm");

        Gson g = new Gson();
        User user;
        try {
            user = g.fromJson(json, User.class);
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        if (!passwordConfirm.equals(user.getPassword())) {
            return Response.status(Response.Status.FORBIDDEN).entity("passwords dont match").build();
        }
=======
	private static final long serialVersionUID = 1236544789552114471L;
	private static KeyGenerator keyGenerator;
	private CreateUser cu; //make EJB in future 
	
	private static SecretKey sk;
	private Key pk;

	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signup(String json) {
		System.out.println("\nsignup start");
		System.out.println("json=" + json);
		Gson g = new Gson();
		User user;


		System.out.println("\ng=");
		try {
			user  = g.fromJson(json, User.class);
		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
		}
		System.out.println("\nafter try");

		if (user == null) {
			return Response.status(Response.Status.FORBIDDEN).entity("user is null").build();
		}
>>>>>>> b914050a15db0def828fa618a0614da78d66e6ca

        Pattern emailPattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        System.out.println("\n email");
        if (!emailPattern.matcher(user.getEmail()).matches()) {
            System.out.println("\n\nbad email\n");
            return Response.status(Response.Status.FORBIDDEN).entity("bad email").build();
        }
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        if (!passwordPattern.matcher(user.getPassword()).matches()) {
            System.out.println("\n\nbad password\n");
            return Response.status(Response.Status.FORBIDDEN).entity("bad password").build();
        }

        user.setPassword(new PasswordHasher().getPasswordHash(user.getPassword()));
        cu = getCreateUser();
        try {
            cu.createUser(user);
        } catch (Exception e) {
            System.out.println("\n\nerror creating user: " + e.getMessage() + "\n");
            return Response.status(Response.Status.FORBIDDEN).entity("email exists").build();
//            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
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
            return Response.status(Response.Status.FORBIDDEN).entity("bad token").build();
        }
        System.out.println(" - token: " + tokenToCheck);
        String res = isValidToken(tokenToCheck);
        if (res != null && !res.equals("")) {
            return Response.ok(res).build();
        } else return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
    }

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signin(String json) {
        JSONObject obj = new JSONObject(json);
        String email = obj.getString("email");
        String password = obj.getString("password");

        System.out.println(" - signin: " + email + ", " + password);

        Gson g = new Gson();
        if (!authenticate(email, password)) {
            return Response.status(Response.Status.FORBIDDEN).entity("wrong password").build();
        }
<<<<<<< HEAD
        String token = issueToken(email);
        NewCookie cookie = new NewCookie("token", token);
        return Response.ok(token).cookie(cookie).build();
=======
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
		String passwordObt = cu.getUserByEmail(login).getPassword();
        return passwordObt != null && passwordObt.equals(password);
>>>>>>> b914050a15db0def828fa618a0614da78d66e6ca
    }

    private boolean authenticate(String email, String password) {
        cu = getCreateUser();
        String passwordObt = cu.getUserByEmail(email).getPassword();
        String hashed = new PasswordHasher().getPasswordHash(password);
        return passwordObt != null && passwordObt.equals(hashed);
    }

    private String issueToken(String email) {
        keyGenerator = getKeyGenerator();
        SecretKey key = getKey();
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date afterAddingTenMins = new Date(t + (10 * 60000));
        return Jwts.builder()
                .setSubject(email)
                .setIssuer("baktybek")
                .setIssuedAt(new Date())
                .setExpiration(afterAddingTenMins)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private CreateUser getCreateUser() {
        if (cu == null) {
            cu = new CreateUser();
        }
        return cu;
    }

    public static KeyGenerator getKeyGenerator() {
        if (keyGenerator == null)
            try {
                keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            } catch (Exception e) {
                e.printStackTrace();
            }
        return keyGenerator;
    }

    public static SecretKey getKey() {
        if (sk == null)
            sk = keyGenerator.generateKey();
        return sk;
    }

    public Key getPkey() {
        if (pk == null)
            pk = keyGenerator.generateKey();
        return pk;
    }

    public static String isValidToken(String token) {
        keyGenerator = getKeyGenerator();
        String res;
        Key key = getKey();
        try {
            //Decoder jwt = ;
            res = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            System.out.println(res + " reeeeeeeeeeeeeeeees");
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        System.out.println("#### valid token : " + token);
        return res;
    }

}
