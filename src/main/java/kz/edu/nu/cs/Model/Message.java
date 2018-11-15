package kz.edu.nu.cs.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table
@SequenceGenerator(name = "MesSeq", sequenceName = "Mes_Seq", allocationSize=1)
@NamedQueries({
		@NamedQuery(name = "Message.getMessagesByEventId", query = "select u from Message u where u.belGroup.id = :eventId ORDER BY u.date ASC")
})
public class Message implements Serializable {
	private static final long serialVersionUID = 1231123521891L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MesSeq")
	private int id;

	@ManyToOne(targetEntity = User.class)
	//@PrimaryKeyJoinColumn(name = "AUTHOR")
	private User author;


	@ManyToOne(targetEntity = Event.class)
	//@PrimaryKeyJoinColumn(name = "EVENT_ID")
	private Event belGroup;

	@Column(name = "CONTENT")
	private String msg;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Message() {}





	public int getId() {return id;}
	public String getMsg() {return msg;}
	public void setMsg(String msg) {this.msg = msg;}
	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Event getBelGroup() {
		return belGroup;
	}

	public void setBelGroup(Event belGroup) {
		this.belGroup = belGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		//result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", author=" + author +
				", belGroup=" + belGroup +
				", msg='" + msg + '\'' +
				", date=" + date +
				'}';
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
