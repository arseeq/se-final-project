package kz.edu.nu.cs.Services;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import kz.edu.nu.cs.Model.User;

class UserDbManager {
	private EntityManagerFactory emfactory;
	private EntityManager em;

	public UserDbManager() {
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		em = emfactory.createEntityManager();
	}
	
	public void createUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		emfactory.close();
	}
	
	public User getUserByEmail(String email) {
		em.getTransaction().begin();
		User u;
		u = (User)em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
		em.getTransaction().commit();
		em.close();
		emfactory.close();
		return u;
	}



}
