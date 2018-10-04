package kz.edu.nu.cs.Model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table
@SequenceGenerator(name = "MesSeq", sequenceName = "Mes_Seq", allocationSize=1)
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MesSeq")
	private int id;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name = "id")
	private User author;
	
	
	@ManyToOne(targetEntity = EventGroup.class)
	@PrimaryKeyJoinColumn(name = "group_id", referencedColumnName = "id")
	private EventGroup belGroup;
	
	
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public Message() {}
	

	


	public int getId() {return id;}
	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}
	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public EventGroup getGroup() {
		return belGroup;
	}

	public void setGroup(EventGroup eventGroup) {
		this.belGroup = eventGroup;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		//result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + id;
		return result;
	}


	/*@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (belGroup == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (id != other.id)
			return false;
		return true;
	}*/
}
