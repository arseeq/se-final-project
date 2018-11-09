package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class EventDbManager {
    private EntityManagerFactory emfactory;
    private EntityManager em;

    public EventDbManager() {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
    }

    public void createEvent(Event event) {
        em.persist(event);
        em.getTransaction().commit();
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
                User u = (User)new UserDbManager().getUserById(u1.getId());;
                System.out.println( "2222222222222222222222222222   " + u);
                users.add(u);
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
        closeConnection();
        return event;
    }

    public void join(String email, int groupId) {
        Event event = (Event)em.createNamedQuery("Event.getEventById").setParameter("id", groupId).getSingleResult();
        User user = (User)em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
        event.getParticipants().add(user);
        em.merge(event);
        em.getTransaction().commit();
        closeConnection();
    }

    public void leaveGroup(String email, int groupId) {
        User user = (User)em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
        Event event = (Event)em.createNamedQuery("Event.getEventById").setParameter("id", groupId).getSingleResult();
        System.out.println(user + "  111111    " + event + "\n\n\n\n");
        event.getParticipants().remove(user);
        em.merge(event);
        closeConnection();
    }

    public boolean deactivateGroup(String email, int groupId) {
        Event event = (Event) em.createNamedQuery("Event.getEventById").setParameter("id", groupId).getSingleResult();
        if (!event.getAdmin().equals(email)) {
            return false;
        }
        event.setIsactive(0);
        em.merge(event);
        em.getTransaction().commit();
        closeConnection();
        return true;
    }

    public void closeConnection() {
        em.close();
        emfactory.close();
    }

    public List getList() {
        List result = em.createNamedQuery("Event.findAll").getResultList();
        closeConnection();
        return result;
    }
}
