package com.revature.dao;

import org.hibernate.Transaction;

import org.hibernate.Session;

import com.revature.models.Superpowers;
import com.revature.util.HibernateUtil;

public class SuperpowerDao {

	// This is where we store our CRUD methods, we'll just look at some creation for
	// now

	public void insertWithoutCasting(Superpowers superpowers) {

		// Capture a session
		Session ses = HibernateUtil.getSession();

		// We'll start a transaction
		Transaction tx = ses.beginTransaction(); // Make sure to use the hibernate transaction import
		
		// To save a new entry into the database all we have to use is the .save method
		ses.save(superpowers);
		tx.commit();

	}

	public int insert(Superpowers superpowers) {

		// Capture a session
		Session ses = HibernateUtil.getSession();

		// We'll start a transaction
		Transaction tx = ses.beginTransaction(); // Make sure to use the hibernate transaction import
		
		// We'll do the same thing down here
		// The save method is going to return the primary key
		int pk = (int) ses.save(superpowers);
		
		tx.commit();
		
		// return the primary key
		return pk;

	}

}
