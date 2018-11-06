package kz.edu.nu.cs.Services;

import com.google.gson.Gson;
import kz.edu.nu.cs.Model.Event;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/event")
public class EventService implements Serializable {
    private static final long serialVersionUID = 12252632212171L;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroup(String json) {
//        String
        Event event = new Event();
        event.setIsactive(1);
        event.setDescription(getParamFromJson(json, "description"));
        event.setImg(getParamFromJson(json, "img"));
        event.setLocation(getParamFromJson(json, "location"));
        event.setMaxsize(Integer.parseInt(getParamFromJson(json, "maxsize")));
        event.setName(getParamFromJson(json, "name"));
        event.setPoints(Integer.parseInt(getParamFromJson(json, "points")));
        event.setPrice(Integer.parseInt(getParamFromJson(json, "price")));

        SimpleDateFormat formatter = new SimpleDateFormat("hh-mm dd/mm/yyyy");
        Date d = null;
        try {
            d = formatter.parse(getParamFromJson(json, "meetingdate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(d);
        event.setMeetingdate(d);

        String email = getParamFromJson(json, "token");

        if (email == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }

        event.setAdmin(email);
        System.out.println("\n\n\n" + event + "\n" + email);

        try {
            new CreateEvent().createEvent(event);
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        return Response.ok().entity("success").build();
    }




//    @POST
//    @Path("/update")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateGroup(String json) {
//        JSONObject obj = new JSONObject(json);
//        String tokenToCheck = obj.getString("token");
//        String email = AuthService.isValidToken(tokenToCheck);
//
//
//        try {
//
//            Event event = new Gson().fromJson(json, Event.class);
//            new CreateEvent().mergeEvent(event);
//        } catch (Exception e){
//            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
//        }
//        return Response.ok("success").build();
//    }

    @POST
    @Path("/join")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response joinGroup(String json) {
        String email = getParamFromJson(json, "token");
        String id = getParamFromJson(json, "id");
        if (email == null) return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        int groupId = Integer.parseInt(id);
        try {
            new CreateEvent().join(email, groupId);
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
//        EventGroupManager evm = new EventGroupManager();
//        EventGroup eg = evm.getGroupById(groupId);
//        if(email != null){
//            try {
//                int groupId = Integer.parseInt(obj.getString("groupId"));
//                User u = new CreateUser().getUserByEmail(email);
//                EventGroupManager evm = new EventGroupManager();
//                System.out.println("\n user=" + u);
//                System.out.println("\n isvalid=" + evm.isValidId(groupId));
//                if(evm.isValidId(groupId) && u != null){
//                    EventGroup eg = evm.getGroupById(groupId);
//                    eg.getParticipants().add(u);
//                    evm.updateGroup(eg);
//                }else{
//                    return Response.status(Response.Status.FORBIDDEN).entity("incorrect userId or groupId").build();
//                }
//            } catch (Exception e){
//                return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
//            }
//            return Response.ok("success").build();
//        }
        return Response.ok().entity("success").build();
    }



    @POST
    @Path("/leave")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response leaveGroup(String json) {
        String email = getParamFromJson(json, "token");
        String id = getParamFromJson(json, "id");
        if (email == null) return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        try {
            new CreateEvent().leaveGroup(email, Integer.parseInt(id));
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity("not participant").build();
        }
        return Response.ok().entity("success").build();
    }

    @POST
    @Path("/deactivate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeGroup(String json) {
        String email = getParamFromJson(json, "token");
        String id = getParamFromJson(json, "id");
        if (email == null) return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        boolean statusOk = new CreateEvent().deactivateGroup(email, Integer.parseInt(id));
        if (statusOk) return Response.ok().entity("success").build();
        return Response.status(Response.Status.FORBIDDEN).entity("not admin").build();
    }

    private String getParamFromJson(String json, String parameter) {
        JSONObject obj = new JSONObject(json);
        if (parameter.equals("token")) {
            String tokenToCheck = obj.getString("token");
            return AuthService.getTokenUtil().isValidToken(tokenToCheck);
        }
        return obj.getString(parameter);
    }

    @POST
    @Path("/getmyevents")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMyEvents(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        Gson gson = new Gson();

        System.out.println("\n\n\nemail=" + email);
        try {
            List events = new CreateEvent().getMyEvents(email);
            return Response.status(Response.Status.OK).entity(gson.toJson(events)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
//        EventGroupManager evm = new EventGroupManager();
//        EventGroup eg = evm.getGroupById(groupId);
//        if(email != null){
//            try {
//                int groupId = Integer.parseInt(obj.getString("groupId"));
//                User u = new CreateUser().getUserByEmail(email);
//                EventGroupManager evm = new EventGroupManager();
//                System.out.println("\n user=" + u);
//                System.out.println("\n isvalid=" + evm.isValidId(groupId));
//                if(evm.isValidId(groupId) && u != null){
//                    EventGroup eg = evm.getGroupById(groupId);
//                    eg.getParticipants().add(u);
//                    evm.updateGroup(eg);
//                }else{
//                    return Response.status(Response.Status.FORBIDDEN).entity("incorrect userId or groupId").build();
//                }
//            } catch (Exception e){
//                return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
//            }
//            return Response.ok("success").build();
//        }
    }


    @GET
    @Path("/getallevents")
    public Response getList() {
        try {
            List result = new CreateEvent().getList();
            return Response.ok(new Gson().toJson(result)).build();
        } catch (Exception e){
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }
}
