package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Utility.PasswordHasher;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

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
    @Path("/checkAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkAdmin(String json) {

        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        if (!email.equals(admin)) {
            logger.error("access to /admin denied for {}", email);
            return Response.status(Response.Status.FORBIDDEN).entity("false").build();
        }
        return Response.ok().entity("true").build();
    }

    @POST
    @Path("/admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLog(String json) {

        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        if (!email.equals(admin)) {
            logger.error("access to /admin denied for {}", email);
            return Response.status(Response.Status.FORBIDDEN).entity("access denied").build();
        }

        String categories = new JSONObject(json).getString("categories");

        String[] apis = {"AuthService", "EventService", "UserService"};
        String[] dbs = {"EventDbManager", "UserDbManager", "MessageDbManager"};
        String[] utils = {"TokenUtil"};

        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("mainlog.log"));
            String line;
            while ((line = in.readLine()) != null) {
                if (categories.contains("api")) {
                    for (String s : apis) appendLine(sb, line, s);
                }
                else if (categories.contains("chat")) {
                    appendLine(sb, line, "ChatServer");
                }
                else if (categories.contains("db")) {
                    for (String s: dbs) appendLine(sb, line, s);
                }
                else if (categories.contains("util")) {
                    for (String s: utils) appendLine(sb, line, s);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity("error").build();
        }
        return Response.ok().entity(sb.toString()).build();
    }

    private void appendLine(StringBuilder sb, String line, String s) {
        if (line.contains(s)) {
            sb.append(line);
            sb.append("\n");
        }
    }
}
