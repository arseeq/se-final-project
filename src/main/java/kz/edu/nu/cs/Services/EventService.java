package kz.edu.nu.cs.Services;

import com.google.gson.Gson;
import kz.edu.nu.cs.Model.Event;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/event")
public class EventService implements Serializable {
    private static final long serialVersionUID = 12252632212171L;
    private Logger logger;

    public EventService() {
        logger = LoggerFactory.getLogger(EventService.class);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroup(String json) {
        String email = getParamFromJson(json, "token");

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }

        Event event;
        try {
            event = getEventFromJson(email, json);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity("check json parameters").build();
        }

        logger.info("create group from {}", email);

        try {
            new EventDbManager().createEvent(event);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        logger.info("event created: {}, {}", email, event.toString());
        return Response.ok().entity("success").build();
    }

    @POST
    @Path("/join")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response joinGroup(String json) {
        String email = getParamFromJson(json, "token");

        if (email == null) {
            logger.error("{} token expired", email);
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }

        int groupId = Integer.parseInt(getParamFromJson(json, "id"));
        String groupName;
        try {
            groupName = new EventDbManager().join(email, groupId);
            if(groupName == null){
                return Response.status(Response.Status.FORBIDDEN).entity("event is full or is completed").build();
            }
        } catch (Exception e) {
            logger.error("this group name does not exist!");
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        logger.info("joined {} with {}", email, groupName);
        return Response.ok().entity("success").build();
    }



    @POST
    @Path("/leave")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response leaveGroup(String json) {
        String email = getParamFromJson(json, "token");
        String id = getParamFromJson(json, "id");
        if (email == null) {
            logger.error("{} token expired", email);
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        if(id == null){
            logger.error("incorrect event id");
            return Response.status(Response.Status.FORBIDDEN).entity("incorrect event id").build();
        }
        String groupName;
        try {
            groupName = new EventDbManager().leaveGroup(email, Integer.parseInt(id));
        } catch (Exception e) {
            logger.error("{} is not in {}", email, id);
            return Response.status(Response.Status.FORBIDDEN).entity("not participant").build();
        }
        if(groupName==null){
            return Response.status(Response.Status.FORBIDDEN).entity("incorrect event id or you are admin of this group").build();
        }
        logger.info("{} left group {}", email, groupName);
        return Response.ok().entity("success").build();
    }

    @POST
    @Path("/deactivate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeGroup(String json) {
        String email = getParamFromJson(json, "token");
        String id = getParamFromJson(json, "id");
        if (email == null) {
            logger.error("{} token expired", email);
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        String groupName = new EventDbManager().deactivateGroup(email, Integer.parseInt(id));
        if (groupName != null) {
            logger.info("deactivated {}", groupName);
            return Response.ok().entity("success").build();
        }
        logger.error("{} is not admin of {}", email, id);
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
    @Path("/getmyactiveevents")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMyActiveEvents(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        Gson gson = new Gson();
        logger.info("{} getting events", email);
        try {
            List<Event> events = new EventDbManager().getActiveEventsByEmail(email);
            for (Event event: events){
                event.setParticipants(null);
            }
            logger.info("events sent to {}", email);
            return Response.status(Response.Status.OK).entity(gson.toJson(events)).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/getmypassiveevents")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMyPassiveEvents(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        Gson gson = new Gson();
        logger.info("{} getting events", email);
        try {
            List<Event> events = new EventDbManager().getPassiveEventsByEmail(email);
            for (Event event: events){
                event.setParticipants(null);
            }
            logger.info("events sent to {}", email);
            return Response.status(Response.Status.OK).entity(gson.toJson(events)).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/getallevents")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getList(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        try {

            List<Event> result = new EventDbManager().getList(email);
            logger.info("all events were sent", result);


            for (Event event: result){
                event.setParticipants(null);
            }
            String jsonText = new Gson().toJson(result);
            return Response.ok(jsonText).build();
        } catch (Exception e){
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/getEventById")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEventById(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        String eventId = obj.getString("id");
        if(eventId == null) {
            logger.error("no event with this id");
            return Response.status(Response.Status.FORBIDDEN).entity("no event with this id").build();
        }
        try {

            Event result = new EventDbManager().getEventById(Integer.parseInt(eventId));
            logger.info("get event by id was sent", result);
            String jsonText = new Gson().toJson(result);
            return Response.ok(jsonText).build();
        } catch (Exception e){
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/completeEvent")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response completeEvent(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        String eventId = obj.getString("id");
        if(eventId == null) {
            logger.error("no event with this id");
            return Response.status(Response.Status.FORBIDDEN).entity("no event with this id").build();
        }
        try {

            String eventName = new EventDbManager().updateEvent(Integer.parseInt(eventId), email);
            if(eventName == null) {
                return Response.status(Response.Status.FORBIDDEN).entity("you're not admin of this event or event with this id is not found or event is already completed").build();
            }

            return Response.ok("updated the with id " + eventId).build();
        } catch (Exception e){
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/getUserEvents")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserEvents(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.getTokenUtil().isValidToken(tokenToCheck);
        String userId = obj.getString("id");

        if (email == null) {
            logger.error("token expired");
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        if(userId == null) {
            logger.error("user id cannot be null");
            return Response.status(Response.Status.FORBIDDEN).entity("user id cannot be null").build();
        }
        Gson gson = new Gson();
        logger.info("{} getting events", userId);
        try {
            List<Event> events = new EventDbManager().getEventsByPartId(userId);
            for (Event event: events){
                event.setParticipants(null);
            }
            logger.info("events sent to {}", email);
            return Response.status(Response.Status.OK).entity(gson.toJson(events)).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
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
//            new EventDbManager().mergeEvent(event);
//        } catch (Exception e){
//            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
//        }
//        return Response.ok("success").build();
//    }
    private Event getEventFromJson(String email, String json) throws Exception {
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
        Date d = formatter.parse(getParamFromJson(json, "meetingdate"));
        event.setMeetingdate(d);
        event.setAdmin(email);
        event.getParticipants().add(new UserDbManager().getUserByEmail(email));
        return event;
    }
}
