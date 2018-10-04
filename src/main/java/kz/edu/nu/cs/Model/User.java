package kz.edu.nu.cs.Model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table
public class User {
	
	private static final long serialVersionUID = 1236544789532171L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String name;
	private String surname;
	private int score;
	private String login;
	/*private String email;
	private boolean isAdmin;
	private boolean isActive;
	private Date createdAt;
	private Date updatedAt;
	private String password;*/
	
	public User() {
		
	}
	
	/*public User(int id, String name, String surname, String login, String email, String password) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.email = email;
		this.password = password;
		this.createdAt = new Date();
		this.updatedAt = this.createdAt;
		this.score = 0;
		this.isAdmin = false;
		this.isActive = true;
	}*/
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	/*public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setAvtive(boolean isAvtive) {
		this.isActive = isAvtive;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}*/
	
	
}
