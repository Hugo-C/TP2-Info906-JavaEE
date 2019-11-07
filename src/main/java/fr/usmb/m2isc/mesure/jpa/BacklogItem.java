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
	@ManyToOne
	@JoinColumn(name = "columnId")
	private Column column;

	public BacklogItem() {
		this.creationDate = new Date();
	}
	
	public BacklogItem(String name, int priority, int estimation, String description) {
		this.name = name;
		this.priority = priority;
		this.estimation = estimation;
		this.description = description;
        this.creationDate = new Date();
	}

	public String toString(){ return "[ name : " + name + ", priority : " + priority + ", estimation : " + estimation + ", description : " + description + ", date : " + creationDate + " ]"; }

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

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}
}
