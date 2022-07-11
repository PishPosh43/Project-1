package com.revature.dao;

import java.util.List;

import com.revature.demomodels.DummyUser;
public interface IDao {
	

	public interface IUserDao {
		// List off our CRUD (Create, Read, Update, and Delete) methods to be implemented
		// Will only operate on User Objects
		
		//Insert a user object
		int insert(DummyUser u); // return new primary key of whatever user was inserted
		
		// Read: return 1 or return all
		DummyUser findById(int id); // returns user object
		DummyUser findByUsername(String username); //returns user object with username
		List<DummyUser> findAll(); // Create List of all users
		
		// update
		boolean update(DummyUser u); // update user in db and return true if successful
		
		// delete
		boolean delete(int id); // delete user
		
		//List<DummyUser> findAllCustomers(); Later implementation for more specific find
	}
}
