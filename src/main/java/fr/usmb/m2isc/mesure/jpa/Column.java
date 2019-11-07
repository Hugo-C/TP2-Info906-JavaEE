package fr.usmb.m2isc.mesure.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * TODO JAVADOC
 */
@Entity
public class Column implements Serializable {
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
	@OneToOne
	private Column prevColumn;
	@OneToOne
	private Column nextColumn;


	public Column() {
		this.creationDate = new Date();
	}

	public Column(String name) {
		this.name = name;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Column getPrevColumn() {
		return prevColumn;
	}

	public void setPrevColumn(Column prevColumn) {
		this.prevColumn = prevColumn;
	}

	public Column getNextColumn() {
		return nextColumn;
	}

	public void setNextColumn(Column nextColumn) {
		this.nextColumn = nextColumn;
	}
}
