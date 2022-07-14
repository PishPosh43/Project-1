package com.revature.models;

import java.util.List;
import java.util.Objects;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

@Entity(tableName = "super_school")
public class SuperSchool {
	
	@Id(columnName = "school_id")
	private int ssId;
	
	@Column(columnName = "school_name")
	private String name;
	
	private String location;
	
	// We want to map a superhero to our super chool's primary key
	// so we need to set up a many to one relationship between super heros and our super schools
	@JoinColumn(columnName = "superhero")
	private List<Superhero> superheroList;

	public SuperSchool(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}

	public SuperSchool() {
		super();
	}

	public SuperSchool(String name, String location, List<Superhero> superheroList) {
		super();
		this.name = name;
		this.location = location;
		this.superheroList = superheroList;
	}

	public SuperSchool(int ssId, String name, String location, List<Superhero> superheroList) {
		super();
		this.ssId = ssId;
		this.name = name;
		this.location = location;
		this.superheroList = superheroList;
	}

	public int getSsId() {
		return ssId;
	}

	public void setSsId(int ssId) {
		this.ssId = ssId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Superhero> getSuperheroList() {
		return superheroList;
	}

	public void setSuperheroList(List<Superhero> superheroList) {
		this.superheroList = superheroList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, name, ssId, superheroList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuperSchool other = (SuperSchool) obj;
		return Objects.equals(location, other.location) && Objects.equals(name, other.name) && ssId == other.ssId
				&& Objects.equals(superheroList, other.superheroList);
	}

	@Override
	public String toString() {
		return "SuperSchool [ssId=" + ssId + ", name=" + name + ", location=" + location + ", superheroList="
				+ superheroList + "]";
	}

	

}
