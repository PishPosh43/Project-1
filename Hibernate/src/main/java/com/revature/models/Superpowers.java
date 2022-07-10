package com.revature.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "superpowers") // This allows us to provide info about the table, we'll give it a new name
public class Superpowers {
	
	@Id // Id denotes that this column is a primary key
	@Column(name = "superpower_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This is going to function as our SERIAL datatype from sql
	private int superpowerId;
	
	@Column(name = "superpower_name", unique = true, nullable = false) // This is use adding additional constraints to our column
	private String superpowerName;
	
	// This will automatically be created as a column called description
	private String description;
	
	public Superpowers() {
		super();
	}
	
	public Superpowers(String superpowerName, String description) {
		super();
		this.superpowerName = superpowerName;
		this.description = description;
	}

	public Superpowers(int superpowerId, String superpowerName, String description) {
		super();
		this.superpowerId = superpowerId;
		this.superpowerName = superpowerName;
		this.description = description;
	}
	
//	@OneToMany(mappedBy = "superheroHolder", fetch = FetchType.LAZY)
//	private List<Superhero> heroList = new ArrayList<>();

	public int getSuperpowerId() {
		return superpowerId;
	}

	public void setSuperpowerId(int superpowerId) {
		this.superpowerId = superpowerId;
	}

	public String getSuperpowerName() {
		return superpowerName;
	}

	public void setSuperpowerName(String crimeName) {
		this.superpowerName = crimeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(superpowerId, superpowerName, description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Superpowers other = (Superpowers) obj;
		return superpowerId == other.superpowerId && Objects.equals(superpowerName, other.superpowerName)
				&& Objects.equals(description, other.description);
	}

	@Override
	public String toString() {
		return "Superpower [superpowerId=" + superpowerId + ", superpowerName=" + superpowerName + ", description=" + description + "]";
	}
	

}
