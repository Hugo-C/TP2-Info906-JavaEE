package fr.usmb.m2isc.mesure.jpa;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;

/**
 * TODO JAVADOC
 */
@Entity
public class Item implements Serializable {
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
	private ArrayList<String> comments;

	public Item() {
	}
	
	public Item(String name, Date creationDate, int priority, int estimation, String description) {
		this.name = name;
		this.creationDate = creationDate;
		this.priority = priority;
		this.estimation = estimation;
		this.description = description;
		this.comments = new ArrayList<String>();
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

	public ArrayList<String> getComments() {
		return comments;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
