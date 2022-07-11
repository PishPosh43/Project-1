package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	/*
	 * The purpose of the HibernateUtil Helper File is to handle startup (DB Connection) and access
	 * Hibernate's SessionFactory Interface to obtain a session object
	 */
	
	private static Session ses; // This is like the connection interface from JDBC
	
	/*
	 * We will use the SessionFactory interface to create a connection object which will call the .configure method to
	 * establish a DB connection based off the creds we give in our other file
	 */
	
	private static SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	// Let's create a get session method which is similar to our previous get connection method
	// This obtains a JDBC connection so we can transact with a database.
	
	public static Session getSession() {
		
		if(ses == null) {
			ses = sf.openSession();
		}
		
		return ses;
	}
	
	// This method will close an active session
	public static void closeSes()
	{
		ses.close();
	}
	
}
