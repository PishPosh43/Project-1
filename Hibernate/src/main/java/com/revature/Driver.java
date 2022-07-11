package com.revature;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.SuperSchoolDao;
import com.revature.dao.SuperheroDao;
import com.revature.dao.SuperpowerDao;
import com.revature.models.SuperSchool;
import com.revature.models.Superhero;
import com.revature.models.Superpowers;

public class Driver {
	
	public static void main(String[] args) {
		
		System.out.println("Running our hibernate demo");
		
		Superpowers s1 = new Superpowers("Time Manipulation", "The ability to control time.");
		Superpowers s2 = new Superpowers("Teleportation", "The ability to appear at any location you desire.");
		Superpowers s3 = new Superpowers("Telekenesis", "The ability to defy the laws of physics and maanipulate gravity to move things with your mind");
		
		SuperpowerDao sdao = new SuperpowerDao();
		
		// As of right now we're only mapping the crime class so this is the only class that's being created
		// When we make more we need to make sure to map them as well
		
		sdao.insert(s1);
		sdao.insert(s2);
		sdao.insert(s3);
		
		// Let's make a list of super powers for a super hero
		List<Superpowers> sPowers = new ArrayList<>();
		sPowers.add(s1);
		sPowers.add(s3);
		
		SuperSchool superUniversity = new SuperSchool("Super University", "Ohio");

		
		List<SuperSchool> sSchool = new ArrayList<>();
		sSchool.add(superUniversity);
		
		
		Superhero tickTock = new Superhero("TickTock", "Time Manipulation", 1, sSchool);
		
		// Let's add them to the database
		SuperSchoolDao ssdao = new SuperSchoolDao();
		SuperheroDao shdao = new SuperheroDao();
		
		// Use the insert methods to populate tables
		ssdao.insert(superUniversity);
		shdao.insert(tickTock);
		
		System.out.println(shdao.selectAll());
		
		tickTock.setRank(2);
		
		shdao.update(tickTock);
	}

}
