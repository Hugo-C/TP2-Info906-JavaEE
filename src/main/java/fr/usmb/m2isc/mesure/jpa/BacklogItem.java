package fr.usmb.m2isc.mesure.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * TODO JAVADOC
 */
@Entity
public class BacklogItem implements Serializable {
	/**
	 * Identifiant de la mesure (unique).
	 * 
	 * Il s'agit de la clef primaire associée à l'objet persitant.
	 * S'il est nul, il sera généré lors de l'ajout de la mesure dans le base de données.
	 */
	@Id @GeneratedValue
	private long id;
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	private int priority;
	private int estimation;
	private String description;
//	private ArrayList<String> comments;  // TODO handle comments

	public BacklogItem() {
	}
	
	public BacklogItem(String name, int priority, int estimation, String description) {
		this.name = name;
		this.priority = priority;
		this.estimation = estimation;
		this.description = description;
		this.creationDate = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getEstimation() {
		return estimation;
	}

	public void setEstimation(int estimation) {
		this.estimation = estimation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
