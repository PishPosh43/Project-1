package com.revature.models;

import java.util.List;
import java.util.Objects;



@com.revature.annotations.Entity(tableName = "Super_Heroes")
public class Superhero {
	
	@com.revature.annotations.Id(columnName = "hero_id")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shId;
	
	@com.revature.annotations.Column(columnName = "hero_name")
	private String shName;
	
	@com.revature.annotations.Column( columnName = "super_power")
	private String superPower;
	
	private int rank;
	

	public Superhero() {
		super();
	}

	public Superhero(String shName, String superPower, int rank, List<SuperSchool> superSchool) {
		super();
		this.shName = shName;
		this.superPower = superPower;
		this.rank = rank;
		
	}

	public Superhero(int shId, String shName, String superPower, int rank, List<SuperSchool> superSchool) {
		super();
		this.shId = shId;
		this.shName = shName;
		this.superPower = superPower;
		this.rank = rank;
		
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



	@Override
	public int hashCode() {
		return Objects.hash(rank, shId, shName, superPower);
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
				&& Objects.equals(superPower, other.superPower);
	}

	@Override
	public String toString() {
		return "Superhero [shId=" + shId + ", shName=" + shName + ", superPower=" + superPower + ", rank=" + rank
				+ "]";
	}
	

}
