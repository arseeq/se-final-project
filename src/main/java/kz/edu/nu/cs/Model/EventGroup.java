package kz.edu.nu.cs.Model;



import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="EventGroup")
@SequenceGenerator(name = "GroupSeq", sequenceName = "Group_Seq", allocationSize=1)
public class EventGroup {
	
	private static final long serialVersionUID = 123121121891L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GroupSeq")
	private int id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String login;
	
	
	@OneToOne
	private Event event;
	
	

	@ManyToMany(targetEntity=User.class)
	private Set<User> participants;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "admin_id", referencedColumnName = "id")
	private User admin;
	
	

	public EventGroup() {
		
	}
	
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Set<User> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<User> participants) {
		this.participants = participants;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((admin == null) ? 0 : admin.hashCode());
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventGroup other = (EventGroup) obj;
		if (admin == null) {
			if (other.admin != null)
				return false;
		} else if (!admin.equals(other.admin))
			return false;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
