package com.revature.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.SuperSchool;
import com.revature.util.HibernateUtil;

public class SuperSchoolDao {
	
	// Let's create an insert method like before
	
	public int insert(SuperSchool ss)
	{
		// Capture the session
		Session session = HibernateUtil.getSession();
		
		// Start transaction
		Transaction tx = session.beginTransaction();
		
		// Transact with the data base and save pk as int
		int pk = (int) session.save(ss);
		
		// Commit changes
		tx.commit();
		
		// Return primary key
		return pk;
	}
	
	// Let's also make a select by id
	
	public SuperSchool selectById(int id)
	{
		//We don't need to make a transaction we're just querying
		Session ses = HibernateUtil.getSession();
		
		// Use the session method .get() to invoke a simple query to return an object of type SuperSchool
		// with the id we specify from the SuperSchool table
		
		SuperSchool ss = ses.get(SuperSchool.class, id);
		
		return ss;
		
	}

}
