package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

class EventDbManager {
    private EntityManagerFactory emfactory;
    private EntityManager em;
    private Logger logger;

    public EventDbManager() {
        logger = LoggerFactory.getLogger(EventDbManager.class);
        openConnection();
    }

    public void createEvent(Event event) {
        em.persist(event);
        em.getTransaction().commit();
        logger.info("event persisted: {}", event.toString());
        closeConnection();
    }


    public List getActiveEventsByEmail(String email) {
        List<Event> events = (List<Event>)em.createNamedQuery("Event.getActiveEventsByParticipantEmail").setParameter("email", email).getResultList();
        for(Event ev: events)
            ev.setParticipants(null);
        System.out.println( "0000000000000000000000000000000000000000000000000000000000000" + events);
        closeConnection();
        return events;
    }
    public List getPassiveEventsByEmail(String email) {
        List<Event> events = (List<Event>)em.createNamedQuery("Event.getPassiveEventsByParticipantEmail").setParameter("email", email).getResultList();
        for(Event ev: events)
            ev.setParticipants(null);
        System.out.println( "0000000000000000000000000000000000000000000000000000000000000" + events);
        closeConnection();
        return events;
    }

    public List getEventsByPartId(String userId) {
        List<Event> events = (List<Event>)em.createNamedQuery("Event.getEventsByParticipantId").setParameter("id", Integer.parseInt(userId)).getResultList();
        for(Event ev: events)
            ev.setParticipants(null);
        System.out.println( "0000000000000000000000000000000000000000000000000000000000000" + events);
        closeConnection();
        return events;
    }

    public Event getEventById(int id){
        Event event = (Event) em.createNamedQuery("Event.getEventById").setParameter("id", id).getSingleResult();
        logger.info("query: select e from Event e where e.id = {}", id);
        closeConnection();
        return event;
    }

    public String join(String email, int groupId) {
        Event event = getEventById(groupId);
        User user = new UserDbManager().getUserByEmail(email);
        event.getParticipants().add(user);
        openConnection();
        em.merge(event);
        em.getTransaction().commit();
        closeConnection();
        return event.getName();
    }
    public String leaveGroup(String email, int groupId) {
        User user = new UserDbManager().getUserByEmail(email);
        Event event = (Event) em.createNamedQuery("Event.getEventById").setParameter("id", groupId).getSingleResult();
        if(event==null){
            logger.error("event by id {} not found", groupId);
            return null;
        }
        else if(event.getAdmin().equals(email)) {
            logger.error("event cannot be leaved by its admin");
            return null;
        }
        em.getTransaction().commit();


        em.getTransaction().begin();
        event.getParticipants().remove(user);

//        Event res = em.merge(event);
        em.getTransaction().commit();
//        System.out.println( "-------------------------------" + res);
//        List<User> newParts = new ArrayList<User>(res.getParticipants());

//        System.out.println("--------------------------------" + newParts);
        closeConnection();
        return event.getName();
    }

    public String deactivateGroup(String email, int groupId) {
        Event event = (Event) em.createNamedQuery("Event.getEventById").setParameter("id", groupId).getSingleResult();
        logger.info("query: select e from Event e where e.id = {}", groupId);
        if (!event.getAdmin().equals(email)) {
            logger.error("{} is not admin of {}", email, event.getName());
            return null;
        }
        event.setIsactive(0);
        em.merge(event);
        em.getTransaction().commit();
        closeConnection();
        return event.getName();
    }

    private void closeConnection() {
        logger.info("closing connection");
        em.close();
        emfactory.close();
    }

    private void openConnection() {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
    }

    public List<Event> getList(String email) {
        List<Event> result = (List<Event>)em.createNamedQuery("Event.findAll").getResultList();
        List<Event> myEvents = getActiveEventsByEmail(email);
        for(Event ev: result){
            if (myEvents.contains(ev))
                ev.setAmIParticipant(true);
            ev.setParticipants(null);
        }
        logger.info("query: select e from Event e");
        return result;
    }

    public void updateEvent(Event event) {
        em.merge(event);
        closeConnection();
    }
}
