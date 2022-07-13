package com.revature.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;



//******************************************************************************************
//******************************************************************************************

public class Configuration {

	private static BasicDataSource ds = new BasicDataSource();
	private List<MetaModel<Class<?>>> metaModelList;
	private static Connection conn = null;

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

	public static Connection getConnection() {// (String dbUrl, String dbUsername, String dbPassword) {

		;
		// ENTER CODE & LOGIC HERE
		// REPLACE NULL AFTER WRITING CODE BODY;

		// ds.setUrl(Configuration.getDbUrl());

		if (conn == null) {
			Properties props = new Properties();

			try {
				Class.forName("org.postgresql.Driver");
				props.load(Configuration.class.getClassLoader().getResourceAsStream("connection.properties"));

				// Getting login info from properties file
				
				String url = props.getProperty("url");
				String username = props.getProperty("username");
				String password = props.getProperty("password");
				
				// Connection pooling 
				ds.setUrl(url);
				// ds.setUsername(Configuration.getDbUsername());
				ds.setUsername(username);
				// ds.setPassword(Configuration.getDbPassword());
				ds.setPassword(password);
				ds.setMinIdle(5);
				ds.setMaxIdle(10);
				ds.setMaxOpenPreparedStatements(100);

				conn = ds.getConnection();
				System.out.println("Got connection!");

			} catch (SQLException | IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return conn;
	}

	public static void main(String[] args) {
		Connection conn1 = getConnection();
        Connection conn2 = getConnection();


        System.out.println(conn1);
        System.out.println(conn2);
	}

//******************************************************************************************

}
