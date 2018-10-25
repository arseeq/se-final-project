package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.EventGroup;
import kz.edu.nu.cs.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class EventGroupManager {
    private EntityManagerFactory emfactory;
    private EntityManager em;

    public boolean isValidId(int groupId) {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
        boolean res = false;
        EventGroup u;
        try {
            u = (EventGroup) em.createNamedQuery("Group.findById").setParameter("id", groupId).getSingleResult();
            if(u != null)
                res = true;
        }catch(NoResultException e) {
        }
        em.getTransaction().commit();
        em.close();
        emfactory.close();
        return res;
    }

    public EventGroup getGroupById(int groupId){
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
        EventGroup u = null;
        try {
            u = (EventGroup) em.createNamedQuery("Group.findById").setParameter("id", groupId).getSingleResult();
        }catch(NoResultException e) {
        }
        em.getTransaction().commit();
        em.close();
        emfactory.close();
        return u;
    }

    public void updateGroup(EventGroup eg) {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(eg);
        em.getTransaction().commit();
        em.close();
        emfactory.close();
    }
}
