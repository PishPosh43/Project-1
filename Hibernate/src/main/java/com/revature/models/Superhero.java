package com.revature.models;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Super_Heroes")
public class Superhero {
	
	@Id
	@Column(name = "hero_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shId;
	
	@Column(name = "hero_name", unique = true, nullable = false)
	private String shName;
	
	@Column(name="super_power")
	private String superPower;
	
	private int rank;
	
	// Super Schools should be able to have multiple super Heroes
	// One super hero can graduate from multiple schools
	
	// Fetch type lazy means the data won't be loaded into memory until we call getSuperSchool
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Cascade defines the interactions between entities)
	private List<SuperSchool> superSchool;
	
	// Let's set up the interaction between superheroes and their powers
//	@ManyToOne()
//	@JoinColumn(name = "super_fk")
//	private Superpowers superheroHolder; // This is a foreign key that will be mapped to the other column in superpower

	public Superhero() {
		super();
	}

	public Superhero(String shName, String superPower, int rank, List<SuperSchool> superSchool) {
		super();
		this.shName = shName;
		this.superPower = superPower;
		this.rank = rank;
		this.superSchool = superSchool;
	}

	public Superhero(int shId, String shName, String superPower, int rank, List<SuperSchool> superSchool) {
		super();
		this.shId = shId;
		this.shName = shName;
		this.superPower = superPower;
		this.rank = rank;
		this.superSchool = superSchool;
	}

	public int getShId() {
		return shId;
	}

	public void setShId(int shId) {
		this.shId = shId;
	}

	public String getShName() {
		return shName;
	}

	public void setShName(String shName) {
		this.shName = shName;
	}

	public String getSuperPower() {
		return superPower;
	}

	public void setSuperPower(String superPower) {
		this.superPower = superPower;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<SuperSchool> getSuperSchool() {
		return superSchool;
	}

	public void setSuperSchool(List<SuperSchool> superSchool) {
		this.superSchool = superSchool;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank, shId, shName, superPower, superSchool);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Superhero other = (Superhero) obj;
		return rank == other.rank && shId == other.shId && Objects.equals(shName, other.shName)
				&& Objects.equals(superPower, other.superPower) && Objects.equals(superSchool, other.superSchool);
	}

	@Override
	public String toString() {
		return "Superhero [shId=" + shId + ", shName=" + shName + ", superPower=" + superPower + ", rank=" + rank
				+ ", superSchool=" + superSchool + "]";
	}
	

}
