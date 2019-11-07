package fr.usmb.m2isc.mesure.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * TODO JAVADOC
 */
@Entity
public class ColumnItem implements Serializable {
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
	private ColumnItem prevColumnItem;
	@OneToOne
	private ColumnItem nextColumnItem;


	public ColumnItem() {
		this.creationDate = new Date();
	}

	public ColumnItem(String name) {
		this.name = name;
		this.creationDate = new Date();
	}

	public String toString(){
		return name;
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

	public ColumnItem getPrevColumnItem() {
		return prevColumnItem;
	}

	public void setPrevColumnItem(ColumnItem prevColumnItem) {
		this.prevColumnItem = prevColumnItem;
	}

	public ColumnItem getNextColumnItem() {
		return nextColumnItem;
	}

	public void setNextColumnItem(ColumnItem nextColumnItem) {
		this.nextColumnItem = nextColumnItem;
	}
}
