package kz.edu.nu.cs.Services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import kz.edu.nu.cs.Model.Event;

import java.sql.SQLException;
import java.util.List;

class CreateEvent {
    public void createEvent(Event event) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(event);
        em.getTransaction().commit();
        em.close();
        emfactory.close();
    }
    public void mergeEvent(Event event) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(event);
        em.getTransaction().commit();
        em.close();
        emfactory.close();
    }
    public List getList() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = emfactory.createEntityManager();
        return em.createNamedQuery("Event.findAll").getResultList();
    }
}
