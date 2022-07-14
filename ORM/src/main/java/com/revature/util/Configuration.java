package com.revature.util;


import java.lang.reflect.InvocationTargetException;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;



//******************************************************************************************
//******************************************************************************************

public class Configuration {

	private static BasicDataSource ds = new BasicDataSource();
	public static Connection conn = null;
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

	public static Connection getConnection() {// (String dbUrl, String dbUsername, String dbPassword) {


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
	
	
		


	// Main to check connection
	public static void main(String[] args) throws SQLException {
		Connection conn1 = getConnection();
        Connection conn2 = getConnection();


        System.out.println(conn1);
        System.out.println(conn2);
        
	}


//******************************************************************************************

	//UNFINISHED - NOT A CRUD METHOD ANYWAY
//	public <T> void createTable(Class<?> clazz) {
//		try {
//			Connection cfg = ds.getConnection();
//			MetaModel<T> metaModel = new MetaModel<T>(clazz);
//			String sql = "CREATE TABLE " + metaModel.getSimpleClassName() + "( ";
//			for (ColumnField c : metaModel.getColumns()) {
//				sql = sql.concat("?, ");
//			}
//			sql = sql.concat(");");
//			int i = 1;
//
//			PreparedStatement stmt = cfg.prepareStatement(sql);
//
//			for (ColumnField c : metaModel.getColumns()) {
//
//				System.out.println("Breadcrumb 1: " + c.getColumnName());
//				System.out.println("Breadcrumb 2: " + metaModel.getColumns().size());
//				System.out.println("Breadcrumb 3: " + c.getType());
//				System.out.println("Breadcrumb 4: " + sql);
//
//				stmt.setString(i, c.getColumnName()
//						+ (c.isPrimaryKey() == true ? "serial" + "PRIMARY KEY," : c.getType().toString()) + ",");
//				i++;
//			}
//
//			System.out.println("Table created successfully!");
//
//		} catch (SQLException e) {
//			System.out.println("Unable to create table!");
//			e.printStackTrace();
//
//		}
//	}

//*************************************************************************************************************************************************************
	
	public int insert(Class<?> clazz) {
		
		
		
		
		
		return -1;
	}

//*************************************************************************************************************************************************************		


//*************************************************************************************************************************************************************	

	//UNFINISHED
	public <T> List<Class<?>> getAll(Class<T> clazz) {

		//Class<T> clazz = new Class<T>(null, clazz.class);
		List<Class<?>> classes = new LinkedList<Class<?>>();
		List<Object> allCols = new LinkedList<Object>();
		int i = 1;
		
		try {
			Connection cfg = ds.getConnection();
			MetaModel<T> metaModel = new MetaModel<T>(clazz);
			metaModel.getColumns();
			metaModel.getForeignKeys();
			metaModel.getPrimaryKey();
			
			int sumOfAllColumns = metaModel.actuallyGetColumns().size() + 1
					+ metaModel.actuallyGetForeignKeys().size();

			Class<?>[] colClass = new Class<?>[sumOfAllColumns];
			//System.out.println("Number of foreign keys" + metaModel.actuallyGetForeignKeys().size());
			String sql = "SELECT * FROM users";// metaModel.getSimpleClassName() + ";";

			Statement stmt = cfg.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsMeta = rs.getMetaData();
			
			while (rs.next()) {
				
				allCols.clear();

				System.out.println("Get the dam name: " + rsMeta.getColumnTypeName(i));
				for (int k = 1; k <= sumOfAllColumns; k++) {

					allCols.add(rs.getObject(k));
					System.out.println(allCols.get(k - 1));
					//colClass.add(Class.forName(rsMeta.getColumnClassName(k)));
					colClass[k-1] = Class.forName(rsMeta.getColumnClassName(k));
				}
							
				T classInstance = (T) clazz.getClass().getConstructor(colClass).newInstance();
				classes.add((Class<?>) classInstance);
			System.out.println("Out of the loop but still in try block");
			return classes;
			}
		} catch (SQLException | IllegalArgumentException
				| SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			System.out.println("Unable to get information!");
			e.printStackTrace();

		}
		System.out.println("Did this even work?");
		return classes;
	
	}
	// *************************************************************************************************************************************************************

}
