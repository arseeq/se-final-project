package kz.edu.nu.cs.Services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/log")
public class LogService implements Serializable {

    private static final long serialVersionUID = 12252632212171L;
    private Logger logger;
    private String admin ;

    public LogService() {
        logger = LoggerFactory.getLogger(LogService.class);
        admin = "admin@admin.com";
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

        String categoryApi = new JSONObject(json).getString("api");
        String categorySql = new JSONObject(json).getString("sql");
        String categoryLogin = new JSONObject(json).getString("login");
        String categoryChat = new JSONObject(json).getString("chat");

        StringBuilder sb = new StringBuilder();
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


}
