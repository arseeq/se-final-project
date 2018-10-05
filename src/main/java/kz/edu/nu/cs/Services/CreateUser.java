package kz.edu.nu.cs.Services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import kz.edu.nu.cs.Model.User;

public class CreateUser {
	EntityManagerFactory emfactory;
	EntityManager em;
	
	public void createUser(User user) {
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		emfactory.close();
	}
	
	
	public String getUserPasswordByLogin(String login) {
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		em = emfactory.createEntityManager();
		em.getTransaction().begin();
		User u;
		try {
			u = (User)em.createNamedQuery("User.findByLogin").setParameter("login", login).getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
		em.getTransaction().commit();
		em.close();
		emfactory.close();
		return u.getPassword();
	}
	
}
