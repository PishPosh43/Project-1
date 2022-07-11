package com.revature.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

//******************************************************************************************
//******************************************************************************************

public class Configuration {

	private static BasicDataSource ds = new BasicDataSource();
	private static String dbUrl;
	private static String dbUsername;
	private static String dbPassword;
	private List<MetaModel<Class<?>>> metaModelList;

//******************************************************************************************
//******************************************************************************************

	public Configuration addAnnotatedClass(Class<?> annotatedClass) {
		if (metaModelList == null) {
			metaModelList = new LinkedList<>();
		}
		metaModelList.add(MetaModel.of(annotatedClass));
		return this;
	}

//******************************************************************************************	

	public List<MetaModel<Class<?>>> getMetaModels() {
		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
	}

//******************************************************************************************	

	public Connection getConnection() {// (String dbUrl, String dbUsername, String dbPassword) {

		Connection conn;

		;
		// ENTER CODE & LOGIC HERE
		// REPLACE NULL AFTER WRITING CODE BODY;

		// ds.setUrl(Configuration.getDbUrl());
		ds.setUrl(dbUrl);
		// ds.setUsername(Configuration.getDbUsername());
		ds.setUsername(dbUsername);
		// ds.setPassword(Configuration.getDbPassword());
		ds.setPassword(dbPassword);
		ds.setMinIdle(5);
		ds.setMaxIdle(10);
		ds.setMaxOpenPreparedStatements(100);
		try {
			conn = ds.getConnection();
			if (conn != null && !conn.isClosed()) {
				System.out.println("Returning previously-established connection");
				return conn;
			}
		} catch (SQLException e) {
			System.out.println("Unable to establish a connection");
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	public static String getDbUrl() {
		return dbUrl;
	}

	public static void setDbUrl(String dbUrl) {
		Configuration.dbUrl = dbUrl;
	}

	public static String getDbUsername() {
		return dbUsername;
	}

	public static void setDbUsername(String dbUsername) {
		Configuration.dbUsername = dbUsername;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static void setDbPassword(String dbPassword) {
		Configuration.dbPassword = dbPassword;
	}

//******************************************************************************************

}
