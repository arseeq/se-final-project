package kz.edu.nu.cs.Services;

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
		logger = LoggerFactory.getLogger(UserDbManager.class);
		em.getTransaction().begin();
	}

	public void createUser(User user) {
		em.persist(user);
		em.getTransaction().commit();
		logger.info("user persisted: {}", user.toString());
		closeConnection();
	}

	public void closeConnection() {
		logger.info("closing connection");
		em.close();
		emfactory.close();
	}

	public User getUserByEmail(String email) {
		User u = (User) em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
		logger.info("query: select u from User u where u.email = {}", email);
		em.getTransaction().commit();
		closeConnection();
		return u;
	}


	public User getUserById(int id) {
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		em = emfactory.createEntityManager();
		em.getTransaction().begin();
		User u;
		try {
			u = (User)em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
		}catch(NoResultException e) {
			u = null;
		}
		em.getTransaction().commit();
		em.close();
		emfactory.close();
		return u;
	}



}
