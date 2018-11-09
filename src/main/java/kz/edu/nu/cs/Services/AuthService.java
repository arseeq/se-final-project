package kz.edu.nu.cs.Services;

import com.google.gson.Gson;
import kz.edu.nu.cs.Model.User;
import kz.edu.nu.cs.Utility.CheckRegex;
import kz.edu.nu.cs.Utility.PasswordHasher;
import kz.edu.nu.cs.Utility.TokenUtil;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/auth")
public class AuthService implements Serializable {

    private static final long serialVersionUID = 1236544789552114471L;
    private static TokenUtil tu = new TokenUtil();
    private UserDbManager cu; //make EJB in future

    public static TokenUtil getTokenUtil() {
        return tu;
    }

	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signup(String json) {
		Gson g = new Gson();
		User user;
		try {
			user  = g.fromJson(json, User.class);
		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
		}

		if (user == null) {
			return Response.status(Response.Status.FORBIDDEN).entity("user is null").build();
		}
        CheckRegex checker = new CheckRegex();
        if (!checker.checkEmailRegex(user.getEmail())) {
            System.out.println("\n\nbad email\n");
            return Response.status(Response.Status.FORBIDDEN).entity("bad email").build();
        }
        if (!checker.checkPasswordRegex(user.getPassword())) {
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
        }
        String token = tu.issueToken(user.getEmail());
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
        String res = tu.isValidToken(tokenToCheck);
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
        try {
            if (!authenticate(email, password)) {
                return Response.status(Response.Status.FORBIDDEN).entity("wrong password").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        String token = tu.issueToken(email);
        NewCookie cookie = new NewCookie("token", token);
        return Response.ok(token).cookie(cookie).build();
	}

    private boolean authenticate(String email, String password) {
        cu = getCreateUser();
        String passwordObt = cu.getUserByEmail(email).getPassword();
        String hashed = new PasswordHasher().getPasswordHash(password);
        return passwordObt != null && passwordObt.equals(hashed);
    }



    private UserDbManager getCreateUser() {
        if (cu == null) {
            cu = new UserDbManager();
        }
        return cu;
    }





}
