package kz.edu.nu.cs.Services;

import java.io.Serializable;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.google.gson.Gson;
import kz.edu.nu.cs.Model.User;
import kz.edu.nu.cs.Utility.CheckRegex;
import kz.edu.nu.cs.Utility.PasswordHasher;
import kz.edu.nu.cs.Utility.TokenUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Path("/auth")
public class AuthService implements Serializable {

    private static final long serialVersionUID = 1236544789552114471L;
    private static TokenUtil tu;
    private String admin = "admin@admin.com";
    private UserDbManager cu; //make EJB in future
    private Logger logger;


    public AuthService() {
        logger = LoggerFactory.getLogger(AuthService.class);
        cu = new UserDbManager();
        tu =  new TokenUtil();
    }



    public static TokenUtil getTokenUtil() {
        return tu;
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signup(String json) {
        logger.info("new signup");
        Gson g = new Gson();
        User user;
        try {
            user  = g.fromJson(json, User.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }

        if (user == null) {
            logger.error("user is null");
            return Response.status(Response.Status.FORBIDDEN).entity("user is null").build();
        }
        logger.debug("new user {}", user);
        CheckRegex checker = new CheckRegex();
        if (!checker.checkEmailRegex(user.getEmail())) {
            logger.error("bad email");
            return Response.status(Response.Status.FORBIDDEN).entity("bad email").build();
        }
        if (!checker.checkPasswordRegex(user.getPassword())) {
            logger.error("bad password");
            return Response.status(Response.Status.FORBIDDEN).entity("bad password").build();
        }

        user.setPassword(new PasswordHasher().getPasswordHash(user.getPassword()));
        cu = new UserDbManager();
        try {
            cu.createUser(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity("email exists").build();
        }
        String token = tu.issueToken(user.getEmail());
        if (token == null) {
            logger.error("token is null");
            return Response.status(Response.Status.FORBIDDEN).entity("token error").build();
        }
        NewCookie cookie = new NewCookie("token", token);
        logger.info("user signed up: {}", user.toString());

        List<String> res = new ArrayList<>();
        res.add(token);
        res.add(String.valueOf(user.getId()));

        return Response.ok(this.getJson(res)).cookie(cookie).build();
    }

    @POST
    @Path("/checktoken")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkToken(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        logger.info("checking token");
        if (tokenToCheck == null || tokenToCheck.equals("")) {
            logger.error("token not valid");
            return Response.status(Response.Status.FORBIDDEN).entity("bad token").build();
        }
        String userMail = tu.isValidToken(tokenToCheck);
        List<String> res = new ArrayList<>();
        if(userMail==null){
            logger.error("invalid token or expired");
            return Response.status(Response.Status.FORBIDDEN).entity("invalid token or expired").build();
        }
        User user = new UserDbManager().getUserByEmail(userMail);
        if(user ==null){
            logger.error("user with this email is not registered");
            return Response.status(Response.Status.FORBIDDEN).entity("user with this email is not registered").build();
        }
        res.add(userMail);
        res.add(String.valueOf(user.getId()));

        if (userMail != null && !userMail.equals("")) {
            logger.info("token of {} is valid", userMail);
            return Response.ok(this.getJson(res)).build();
        } else {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
    }

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signin(String json) {
        logger.info("new signin");
        JSONObject obj = new JSONObject(json);
        String email = obj.getString("email");
        String password = obj.getString("password");
        try {
            if (!authenticate(email, password)) {
                logger.error("wrong password for {}", email);
                return Response.status(Response.Status.FORBIDDEN).entity("wrong password").build();
            }
        } catch (Exception e) {
            logger.error("{} doesn't exist", email);
            return Response.status(Response.Status.FORBIDDEN).entity("wrong email").build();
        }
        String token = tu.issueToken(email);
        if (token == null) {
            logger.error("token is null");
            return Response.status(Response.Status.FORBIDDEN).entity("token error").build();
        }
        NewCookie cookie = new NewCookie("token", token);
        logger.info("signin for {}", email);
        List<String> res = new ArrayList<>();
        User user = new UserDbManager().getUserByEmail(email);
        if(user ==null){
            logger.error("user with this email is not registered");
            return Response.status(Response.Status.FORBIDDEN).entity("user with this email is not registered").build();
        }
        res.add(token);
        res.add(String.valueOf(user.getId()));

        return Response.ok(this.getJson(res)).build();
    }

    private boolean authenticate(String email, String password) {
        cu = new UserDbManager();
        String passwordObt = cu.getUserByEmail(email).getPassword();
        String hashed = new PasswordHasher().getPasswordHash(password);
        return passwordObt != null && passwordObt.equals(hashed);
    }

    private String getJson(List<String> res) {
        String jsonText = new Gson().toJson(res);
        return jsonText;
    }
}
