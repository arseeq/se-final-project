package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.Message;
import kz.edu.nu.cs.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class MessageDbManager {
    private EntityManagerFactory emfactory;
    private EntityManager em;

    public void insertMessage(Message message) {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(message);
        em.getTransaction().commit();
        em.close();
        emfactory.close();
    }



    /*public User getUserByEmail(String email) {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
        User u;
        try {
            u = (User)em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
        }catch(NoResultException e) {
            u = null;
        }
        em.getTransaction().commit();
        em.close();
        emfactory.close();
        return u;
    }*/
}
