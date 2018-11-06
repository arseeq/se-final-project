package kz.edu.nu.cs.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@SequenceGenerator(name = "UserGroupSeq", sequenceName = "UserGroup_Seq", allocationSize=1)
@NamedQueries({
        @NamedQuery(name = "UserGroup.findNameById", query = "select u from UserGroup u where u.id = :id"),
        @NamedQuery(name = "UserGroup.deleteRow", query = "DELETE FROM UserGroup WHERE name = :name and email = :email")
})
public class UserGroup implements Serializable {
    private static final long serialVersionUID = 1236598776754L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserGroupSeq")
    private int id;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String name;

    public UserGroup(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
