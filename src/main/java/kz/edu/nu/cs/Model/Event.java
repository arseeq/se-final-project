package kz.edu.nu.cs.Model;

import javax.persistence.*;

@Entity
@Table
@SequenceGenerator(name = "EventSeq", sequenceName = "Event_Seq", allocationSize=1)
@NamedQuery(name="Event.findAll", query="select e from Event e")
public class Event {
	
	private static final long serialVersionUID = 12252632212171L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EventSeq")
	private int id;
	
	private String name;
	private String description;
	private String meetingtime;
	private String location;
	private int price;
	private int points;
	
	public Event() {
		
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMeetingtime() {
		return meetingtime;
	}
	public void setMeetingtime(String meetingTime) {
		this.meetingtime = meetingTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((meetingtime == null) ? 0 : meetingtime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + points;
		result = prime * result + price;
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
		Event other = (Event) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (meetingtime == null) {
			if (other.meetingtime != null)
				return false;
		} else if (!meetingtime.equals(other.meetingtime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (points != other.points)
			return false;
		return price == other.price;
	}
}
