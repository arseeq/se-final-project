package kz.edu.nu.cs.Services;

import com.google.gson.Gson;
import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.User;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/user")
public class UserService implements Serializable {
    private static final long serialVersionUID = 12252636358171L;
    private Logger logger;

    public UserService() {
        logger = LoggerFactory.getLogger(UserService.class);
    }

    @POST
    @Path("/getUserInfoById")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserInfoById(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        String userId = obj.getString("id");
        if(userId == null) {
            logger.error("user id cannot be null");
            return Response.status(Response.Status.FORBIDDEN).entity("user id cannot be null").build();
        }
        try {
            User user = new UserDbManager().getUserById(Integer.parseInt(userId));
            logger.info("get user by id {} was sent", userId);
            String jsonText = new Gson().toJson(user);
            return Response.ok(jsonText).build();
        } catch (Exception e){
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }


    //added this
    @POST
    @Path("/getUserInfoByEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserInfoByEmail(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }

        String emailToGet = obj.getString("email");

        if (emailToGet == null) {
            logger.error("email is null");
            return Response.status(Response.Status.FORBIDDEN).entity("email is null").build();
        }

        try {
            User user = new UserDbManager().getUserByEmail(emailToGet);
            logger.info("get user by email {} was sent", emailToGet);
            String jsonText = new Gson().toJson(user);
            return Response.ok(jsonText).build();
        } catch (Exception e){
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }



}
