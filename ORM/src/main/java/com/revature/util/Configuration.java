package com.revature.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.revature.annotations.Id;
import com.revature.demomodels.SQLDataTypes;

//******************************************************************************************
//******************************************************************************************

public class Configuration {

	private static BasicDataSource ds = new BasicDataSource();
	public static Connection conn;
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

//******************************************************************************************	

	public static String getDbUrl() {
		return dbUrl;
	}

//******************************************************************************************	

	public static void setDbUrl(String dbUrl) {
		Configuration.dbUrl = dbUrl;
	}

//******************************************************************************************	

	public static String getDbUsername() {
		return dbUsername;
	}

//******************************************************************************************	

	public static void setDbUsername(String dbUsername) {
		Configuration.dbUsername = dbUsername;
	}

//******************************************************************************************	

	public static String getDbPassword() {
		return dbPassword;
	}

//******************************************************************************************	

	public static void setDbPassword(String dbPassword) {
		Configuration.dbPassword = dbPassword;
	}

//******************************************************************************************

	public <T> void createTable(Class<?> clazz) {
		try {
			Connection cfg = ds.getConnection();
			MetaModel<T> metaModel = new MetaModel<T>(clazz);
			String sql = "CREATE TABLE (" + metaModel.getSimpleClassName();
			for (ColumnField c : metaModel.getColumns()) {
				sql.concat("?");
			}
			sql.concat(");");

			PreparedStatement stmt = cfg.prepareStatement(sql);
			for(ColumnField c : metaModel.getColumns()) {
				
				int i = 1;
				int j =0;
				
				//stmt.setString(i, if(metaModel.getPrimaryKey() != null) { metaModel.getPrimaryKey().getName() + "serial PRIMARY KEY"});
				//stmt.setString(i, c.getColumnName() + (c.isPrimaryKey() == true ? "serial" + "PRIMARY KEY," : "" + while(j< SQLDataTypes.values().length) {if(SQLDataTypes) {j++}} );/*(i == 1 ? "serial PRIMARY KEY," : " ") +  );*/
				
				i++;
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		// metaModel.getClass()

	}

}
