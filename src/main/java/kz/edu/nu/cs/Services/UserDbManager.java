package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import kz.edu.nu.cs.Model.User;

class UserDbManager {
	private EntityManagerFactory emfactory;
	private EntityManager em;
	private Logger logger;

	public UserDbManager() {
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		em = emfactory.createEntityManager();
	}

	public void createUser(User user) {
		logger = LoggerFactory.getLogger(UserDbManager.class);
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
        logger.info("user persisted: {}", user.toString());
		em.close();
		emfactory.close();
	}
	
	public User getUserByEmail(String email) {
		em.getTransaction().begin();
		User u;
		u = (User)em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
		em.getTransaction().commit();
        logger.info("query: select u from User u where u.email = {}", email);
		em.close();
		emfactory.close();
		return u;
	}

	public User getUserById(int id) {
		em.getTransaction().begin();
		User u;
		u = (User)em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
		em.getTransaction().commit();
		logger.info("query: select u from User u where u.id = {}", id);
		em.close();
		emfactory.close();
		return u;
	}

}
