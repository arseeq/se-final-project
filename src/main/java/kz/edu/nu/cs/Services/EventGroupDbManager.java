package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.EventGroup;
import kz.edu.nu.cs.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class EventGroupDbManager {
    private EntityManagerFactory emfactory;
    private EntityManager em;

    public boolean isValidId(int groupId) {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
        boolean res = false;
        EventGroup u;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + groupId);
        try {
            u = (EventGroup) em.createNamedQuery("Group.findById").setParameter("id", groupId).getSingleResult();
            System.out.println("1111111111111111111111111111111 User is" + u);
            if(u != null)
                res = true;
        }catch(NoResultException e) {
            System.out.println("\n\n\n\n\n\n" + e.getMessage());
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
