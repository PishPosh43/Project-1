package com.revature.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.transaction.Transaction;
import com.revature.util.MetaModel;


public class Session<T> {
	Connection con;
	private Transaction currentTransaction;
	
	
	private List<MetaModel<Class<?>>> metaModelList; // list is used to store and cache metamodels
	private MetaModel<T> model;
	
	// Create session 
	public Session(Connection con, List<MetaModel<Class<?>>> metaModelList) {
		this.metaModelList = metaModelList;
		this.con = con;
		//createTables(); neeed to implement this
	}
	
	public Session(Connection con) {
		this.con = con;
		
	}
	
	public Transaction beginTransaction() {
		if (currentTransaction == null) {
			currentTransaction = new Transaction(con);
			currentTransaction.setAutoCommit(false);
		}
		return currentTransaction;
	}
	
	
	//create list if none is available or add model to list
	public void save(Object o) { 
		Class<?> clazz = o.getClass();
        for (MetaModel<?> model : metaModelList) {
            if (model.getClassName().equals(clazz.getName())) {
            	metaModelList.add((MetaModel<Class<?>>) o);
            }
		
	}
	
	
    //private void createTables() {
      // Implement when createTables method is finished
    	
   // }
    
  
	}
	
	

	public void rollback() {
		this.metaModelList = null;
	}
	
	public void close() {
		this.metaModelList = null;
		this.con = null;
	}
	
	
}