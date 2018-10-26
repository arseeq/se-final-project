package kz.edu.nu.cs.Services;

import com.google.gson.Gson;
import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.EventGroup;
import kz.edu.nu.cs.Model.User;
import kz.edu.nu.cs.Model.UserGroup;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
        String tokenToCheck = new JSONObject(json).getString("token");

        if (tokenToCheck == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }

        String email = AuthService.isValidToken(tokenToCheck);

        event.setAdmin(email);
        System.out.println("\n\n\n" + event + "\n" + email);

        try {
            new CreateEvent().createEvent(event);
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }


    // TODO implement this!
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
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");
        String email = AuthService.isValidToken(tokenToCheck);

        if (email == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }

        int groupId = Integer.parseInt(obj.getString("id"));

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        try {
            String name = (String)em.createNamedQuery("Event.getNameById").setParameter("id", groupId).getSingleResult();
            System.out.println("\n\n\n!!!!!!!!!!!!!!!!!111123123132123" + name);
            UserGroup ug = new UserGroup();
            ug.setEmail(email);
            ug.setName(name);
            em.persist(ug);
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
        em.getTransaction().commit();
        em.close();
        emfactory.close();

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
        return Response.status(Response.Status.OK).entity("success").build();
    }

    @POST
    @Path("/getmyevents")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMyEvents(String json) {
        JSONObject obj = new JSONObject(json);
        String tokenToCheck = obj.getString("token");

        String email = AuthService.isValidToken(tokenToCheck);

        if (email == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("token expired").build();
        }
        Gson gson = new Gson();

        System.out.println("\n\n\nemail=" + email);
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        try {
            List events = em.createNamedQuery("Event.getEventsByEmail").setParameter("email", email).getResultList();
            em.getTransaction().commit();
            em.close();
            emfactory.close();
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
