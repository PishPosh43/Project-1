package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Superhero;
import com.revature.util.HibernateUtil;

/*
 * 3 different ways to write complex queries
 * 
 * HQL - Hibernate Query Language
 * Criteria API
 * Native SQL
 */

public class SuperheroDao {

	// Let's make another insert method
	// Let's create an insert method like before

	public int insert(Superhero sHero) {
		// Capture the session
		Session session = HibernateUtil.getSession();

		// Start transaction
		Transaction tx = session.beginTransaction();

		// Transact with the data base and save pk as int
		int pk = (int) session.save(sHero);

		// Commit changes
		tx.commit();

		// Return primary key
		return pk;
	}
	
	// Let's do a selectAll() method
	public List<Superhero> selectAll(){
		
		// First thing is to capture a session
		Session ses = HibernateUtil.getSession();
		
		// We'll use some HQL which is basically a combo of SQL and OOP to query
		List<Superhero> heroList = ses.createQuery("from Superhero", Superhero.class).list();
		
		// HQL will return instances of the Superhero,java class
		
		return heroList;
	}
	
	// Let's do a select by name
	public Superhero selectByName(String name) {
		
		Session ses = HibernateUtil.getSession();
		
		// HQL Version
		// Select * from superHero where name = 'name'
		Superhero hero = (Superhero) ses.createQuery("from Superhero where name = '" + name + "'", Superhero.class);
		
		// Native SQL querying
		
		//Superhero hero = (Superhero) ses.createNativeQuery("SELECT * FROM Super_heroes WHERE hero_name = = '" + name + "'", Superhero.class);
		
		// Criteria API
		// Superhero hero = (Superhero) ses.createCriteria(Seuperhero.class).add(Restrictions.like("hero_name", name))
		
		return hero;
	}
	
	// An update method so we can see what this looks like
	public void update(Superhero sHero) {
		// Capture the session
		Session session = HibernateUtil.getSession();

		// Start transaction
		Transaction tx = session.beginTransaction();

		// Transact with the data base and save pk as int
		session.update(sHero);

		// Commit changes
		tx.commit();
	}
	

}
