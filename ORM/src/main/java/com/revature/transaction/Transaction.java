package com.revature.transaction;

import java.sql.Savepoint;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.SQLException;

import com.revature.util.Configuration;

public class Transaction {
	private Connection conn;
	
	

	public Transaction(Connection conn) {
		this.conn = conn;

	}

	public void beginTransaction()throws SQLException{
		 conn.beginRequest();
		 
	}
	
	public void commit() {

		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void rollback() {

		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void setSavepoint()throws SQLException{
		
		 conn.setSavepoint();
		 
	}
	
	public void deleteSavepoint(Savepoint s)throws SQLException{
		 conn.releaseSavepoint(s);
		 
	}



	public void setAutoCommit(boolean isEnabled) {

		try {
			conn.setAutoCommit(isEnabled);
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}
}
