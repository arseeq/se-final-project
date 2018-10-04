package kz.edu.nu.cs.Services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import kz.edu.nu.cs.Model.User;

public class CreateUser {
	public void dbChecker() {
		System.out.println("first line of dbchecker");
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		User user = new User();
		user.setId(1);
		user.setName("NIcolas");
		user.setSurname("sarcosi");
		user.setLogin("nic_sarco");
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		emfactory.close();
		System.out.println("last line of dbchecker");
	}
	
	
}
