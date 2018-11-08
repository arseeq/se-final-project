package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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


}
