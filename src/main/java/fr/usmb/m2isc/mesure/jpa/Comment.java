package fr.usmb.m2isc.mesure.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * TODO JAVADOC
 */
@Entity
public class Comment implements Serializable {


	/**
	 * Identifiant de la mesure (unique).
	 *
	 * Il s'agit de la clef primaire associée à l'objet persitant.
	 * S'il est nul, il sera généré lors de l'ajout de la mesure dans le base de données.
	 */
	@Id @GeneratedValue
	private long id;
	private String username;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	private String content;
	@ManyToOne
	private BacklogItem backlogItem;

	public Comment() {
	    this.username = "default username";
	    this.content = "";
		this.creationDate = new Date();
	}

	public Comment(String username, String content, BacklogItem backlogItem) {
		this.username = username;
		this.content = content;
        this.creationDate = new Date();
        this.backlogItem = backlogItem;
	}

    public String toString() {
        String res = "username : " + username + ", content : " + content + ", date : "
                + creationDate;
        if(backlogItem != null)
			res += "backlogItem: " + backlogItem.toString();
        return "< Commentary: " + res + " >";
    }



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BacklogItem getBacklogItem() {
		return backlogItem;
	}

	public void setBacklogItem(BacklogItem backlogItem) {
		this.backlogItem = backlogItem;
	}
}
