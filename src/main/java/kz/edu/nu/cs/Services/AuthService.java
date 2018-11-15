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


@Path("/auth")
public class AuthService implements Serializable {

    private static final long serialVersionUID = 1236544789552114471L;
    private static TokenUtil tu = new TokenUtil();
    private String admin = "admin@admin.com";
    private UserDbManager cu; //make EJB in future
    private Logger logger;


    public AuthService() {
        logger = LoggerFactory.getLogger(AuthService.class);
        cu = new UserDbManager();
    }

    @POST
    @Path("/admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLog(String json) {
        String tokenToCheck = new JSONObject(json).getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);
        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        if (!email.equals(admin)) {
            logger.error("access to {} denied", email);
            return Response.status(Response.Status.FORBIDDEN).entity("access denied").build();
        }
        byte[] file;
        try {
            file = Files.readAllBytes(Paths.get("mainlog.log"));
            String log = new String(file);
            return Response.ok().entity(log).build();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return Response.status(Response.Status.FORBIDDEN).entity("error").build();
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
        return Response.ok(token).cookie(cookie).build();
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
        String res = tu.isValidToken(tokenToCheck);
        if (res != null && !res.equals("")) {
            logger.info("token of {} is valid", res);
            return Response.ok(res).build();
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
        return Response.ok(token).cookie(cookie).build();
    }

    private boolean authenticate(String email, String password) {
        cu = new UserDbManager();
        String passwordObt = cu.getUserByEmail(email).getPassword();
        String hashed = new PasswordHasher().getPasswordHash(password);
        return passwordObt != null && passwordObt.equals(hashed);
    }


}
