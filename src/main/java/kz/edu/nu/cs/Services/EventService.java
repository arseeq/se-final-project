package kz.edu.nu.cs.Services;

import com.google.gson.Gson;
import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.User;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

@Path("/event")
public class EventService implements Serializable {
    private static final long serialVersionUID = 12252632212171L;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroup(String json) {
        Event event = new Gson().fromJson(json, Event.class);
        try {
            new CreateEvent().createEvent(event);
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGroup(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.isValidToken(tokenToCheck);
        

        try {

            Event event = new Gson().fromJson(json, Event.class);
            new CreateEvent().mergeEvent(event);
        } catch (Exception e){
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        return Response.ok("success").build();
    }
    @POST
    @Path("/join")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response joinGroup(String json) {
//        JSONObject obj = new JSONObject(json);
//        String tokenToCheck = obj.getString("token");

        try {
            Event event = new Gson().fromJson(json, Event.class);
            new CreateEvent().mergeEvent(event);
        } catch (Exception e){
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        return Response.ok("success").build();
    }

    @POST
    @Path("/getlist")
    public Response getList() {
//        JSONObject obj = new JSONObject(json);
//        String tokenToCheck = obj.getString("token");
        Gson g = new Gson();
        try {

            List result = new CreateEvent().getList();

            return Response.ok(g.toJson(result)).build();
        } catch (Exception e){
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }
}
