package com.revature;

import java.sql.Connection;

import com.revature.models.DummyOtherClass;
import com.revature.models.DummyUser;
import com.revature.util.Configuration;

public class DriverProject1 {
	
	public static void main(String[] args) {
		
		Configuration cfg = new Configuration();
		Configuration.setDbUrl(System.getenv("DB_URL"));
		Configuration.setDbUsername(System.getenv("DB_USERNAME"));
		Configuration.setDbPassword(System.getenv("DB_PWORD"));
		
		cfg.addAnnotatedClass(DummyUser.class);
		
		Connection con = cfg.getConnection();
		
		DummyOtherClass otherDummy = new DummyOtherClass(5, "testerinog");
		
		
		DummyUser dUser = new DummyUser(10, "dUser", "One");
		DummyUser dUser2 = new DummyUser(11, "dUser2", "Two");
		DummyUser dUser3 = new DummyUser(13, "dUser3", "Three");
		dUser3.setFirstName("myLastName");
		
		//cfg.insert(dUser2);
		//cfg.insert(dUser3);
		
		
		//cfg.read(dUser);
		
		cfg.delete(dUser3);
		
		//cfg.update(dUser3);
		
		
		}

	}


