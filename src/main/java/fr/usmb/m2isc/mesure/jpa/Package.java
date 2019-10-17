package fr.usmb.m2isc.mesure.jpa;

import java.io.Serializable;

import javax.persistence.*;

/**
 * TODO JAVADOC
 */
@Entity
public class Package implements Serializable {
	/**
	 * Identifiant de la mesure (unique).
	 * 
	 * Il s'agit de la clef primaire associée à l'objet persitant.
	 * S'il est nul, il sera généré lors de l'ajout de la mesure dans le base de données.
	 */
	@Id @GeneratedValue
	private long id;
	private double weight;
	private double value;

	@AttributeOverrides({
			@AttributeOverride(name="latitude",
					column=@Column(name="CPOS_LATITUDE")),
			@AttributeOverride(name="longitude",
					column=@Column(name="CPOS_LONGITUDE")),
			@AttributeOverride(name="name",
					column=@Column(name="CPOS_NAME"))
	})
	@Embedded
	private Position currentPosition;

	@AttributeOverrides({
			@AttributeOverride(name="latitude",
					column=@Column(name="ORIGIN_LATITUDE")),
			@AttributeOverride(name="longitude",
					column=@Column(name="ORIGIN_LONGITUDE")),
			@AttributeOverride(name="name",
					column=@Column(name="ORIGIN_NAME"))
	})
	@Embedded
	private Position origin;

	@AttributeOverrides({
			@AttributeOverride(name="latitude",
					column=@Column(name="DEST_LATITUDE")),
			@AttributeOverride(name="longitude",
					column=@Column(name="DEST_LONGITUDE")),
			@AttributeOverride(name="name",
					column=@Column(name="DEST_NAME"))
	})
	@Embedded
	private Position destination;

	private ProcessState state;

	/**
	 * Constructeur sans paramètre obligatoire.
	 */
	public Package() {
	}
	
	public Package(double weight, double value, Position origin, Position destination) {
		this.weight = weight;
		this.value = value;
		this.origin = origin;
		this.destination = destination;
		this.currentPosition = origin;
		this.state = ProcessState.WAITING;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Position getOrigin() {
		return origin;
	}

	public void setOrigin(Position origin) {
		this.origin = origin;
	}

	public Position getDestination() {
		return destination;
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

	public ProcessState getState() {
		return state;
	}

	public void setState(ProcessState state) {
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
