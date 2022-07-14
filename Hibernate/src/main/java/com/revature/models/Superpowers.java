package com.revature.models;

import java.util.Objects;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;


@Entity(tableName =  "superpowers")
public class Superpowers {
	
	@Id(columnName = "superpower_id") // Id denotes that this column is a primary key
	private int superpowerId;
	
	@Column(columnName = "superpower_name") // This is use adding additional constraints to our column
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
