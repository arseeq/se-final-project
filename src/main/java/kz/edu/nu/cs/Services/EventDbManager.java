package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public List getMyEvents(String email) {
//        List events = (List<Event>)em.createNativeQuery("SELECT e.ID, e.ADMIN, e.DESCRIPTION, e.IMG, e.ISACTIVE, e.LOCATION, e.MAXSIZE, e.MEETINGDATE, e.NAME, e.POINTS, e.PRICE FROM EVENT e, EVENT_USER eu, USER u where u.email=?1 and eu.participants_ID = u.id and e.id = eu.Event_ID", ).setParameter(1, email).getResultList();
//        List events = em.createNamedQuery("Event.getEventsByEmail").setParameter("email", email).getResultList();

        List<Event> events = (List<Event>)em.createNamedQuery("Event.getParticipants", Event.class).setParameter(1, email).getResultList();


        for(Event event: events) {
            List<User> participants = (List)em.createNamedQuery("Event.getParts", User.class).setParameter(1, event.getId()).getResultList();
            Set<User> users = new HashSet<>();
            for(User u1: participants){
                User u = (User)new UserDbManager().getUserById(u1.getId());
                if (u != null)
                    users.add(u);
                else
                    logger.error("user by id {} cannot be found", u1.getId());
            }
            event.setParticipants(users);
        }
        /*User users = (User)new UserDbManager().getUserByEmail("vova.ruski@nu.edu.kz");
        User user = (User)em.createNamedQuery("User.findByEmail").setParameter("email","vova.ruski@nu.edu.kz" ).getSingleResult();
        System.out.println(users);
        System.out.println("0000000000000000000000   " + user);*/

        closeConnection();
        return events;
    }

    public Event getEventById(int id){
        Event event = (Event) em.createNamedQuery("Event.getEventById").setParameter("id", id).getSingleResult();
        logger.info("query: select e from Event e where e.id = {}", id);
        //closeConnection();
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
        Event event = getEventById(groupId);
        event.getParticipants().remove(user);
        openConnection();
        em.merge(event);
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

    public List getList() {
        List result = em.createNamedQuery("Event.findAll").getResultList();
        logger.info("query: select e from Event e");
        closeConnection();
        return result;
    }
}
