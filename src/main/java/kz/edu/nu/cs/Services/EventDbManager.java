package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

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
        List events = em.createNamedQuery("Event.getEventsByEmail").setParameter("email", email).getResultList();
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
