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



	public void setAutoCommit(boolean isEnabled) {

		try {
			conn.setAutoCommit(isEnabled);
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}
}
