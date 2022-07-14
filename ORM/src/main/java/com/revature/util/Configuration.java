package com.revature.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

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
		ds.setDefaultSchema("p1testschema");
		ds.getDefaultSchema();
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

	// UNFINISHED - NOT A CRUD METHOD ANYWAY
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

	public <T> PrimaryKeyField insert(Object object) {

		Class<T> clazz = (Class<T>) object.getClass();
		MetaModel<T> metaModel = new MetaModel<T>(clazz);
		MetaModel.of(clazz);

		try {
			Connection cfg = ds.getConnection();
			metaModel.getColumns();
			metaModel.getForeignKeys();
			metaModel.getPrimaryKey();
			List<Field> allCols = Arrays.asList(clazz.getDeclaredFields());

			metaModel.actuallyGetColumns().get(0).getColField().setAccessible(true);

			int sumOfAllColumns = metaModel.actuallyGetColumns().size() + 1 + metaModel.actuallyGetForeignKeys().size();
			//int sumOfAllConstructor = metaModel.actuallyGetColumns().size() + 1;
			String sql = "INSERT INTO " + clazz.getAnnotation(Entity.class).tableName() + "( ";

			for (int i = 0; i < sumOfAllColumns - 1; i++) {
				sql = sql.concat("? ,");
			}
			
			sql = sql.concat("?) VALUES ( ");

			for (int i = 0; i < sumOfAllColumns - 1; i++) {
				sql = sql.concat("? , ");
			}
			

			sql = sql.concat("? )");
			System.out.println(sql);
			sql = sql.concat(");");

			// PREPARED STATEMENT
			PreparedStatement stmt = cfg.prepareStatement(sql);

			// RESULT SET

			for (int j = 0; j < sumOfAllColumns; j++) {
				if (j < sumOfAllColumns) {
					stmt.setString(j + 1, allCols.get(j).getName());
				}
				if ((allCols.get(j)).getAnnotation(Id.class) != null) {
				
					metaModel.getPrimaryKey().getPrimField().setAccessible(true);
					stmt.setObject(j + sumOfAllColumns + 1,
							(metaModel.getPrimaryKey().getPrimField().get(object)));
				} else if ((allCols.get(j)).getAnnotation(Column.class) != null) {
					for (ColumnField col : metaModel.actuallyGetColumns()) {
						if (j + 1 <= metaModel.actuallyGetColumns().size()) {

							System.out.println(j < metaModel.actuallyGetColumns().size());
							metaModel.actuallyGetColumns().get(j).getColField().setAccessible(true);
							
							stmt.setObject(j + sumOfAllColumns + 1,
									(metaModel.actuallyGetColumns().get(j).getColField().get(object)));
						}
					}
				} else if ((allCols.get(j)).getAnnotation(JoinColumn.class) != null) {
					for (ForeignKeyField col : metaModel.actuallyGetForeignKeys()) {
						if (j + 1 < metaModel.actuallyGetForeignKeys().size()) {
							metaModel.actuallyGetForeignKeys().get(j).getForField().setAccessible(true);
							// stmt.setString(j + 1, col.getColumnName());
							stmt.setObject(j + sumOfAllColumns,
									(metaModel.actuallyGetForeignKeys().get(j).getForField().get(object)));
						}
					}
				}
			}

			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			
			PrimaryKeyField prim;
			if(rs.next()) {
				prim = (PrimaryKeyField) rs.getObject(metaModel.actuallyGetPrimaryKey().getName());
				System.out.println("We returned a primary key: " + prim.getPrimField().get(object));
				return prim;
			}
		} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
			System.out.println("Unable to insert!");
			e.printStackTrace();
		}
		return null;
		
	}

//*************************************************************************************************************************************************************		

//*************************************************************************************************************************************************************	


	// UNFINISHED
	public <T> List<Object> getAll(Class<T> clazz) {

		List<Class<?>> classes = new LinkedList<Class<?>>();
		List<Object> allCols = new LinkedList<Object>();
		int i = 1;

		try {
			Connection cfg = ds.getConnection();
			MetaModel<T> metaModel = new MetaModel<T>(clazz);
			
			metaModel.getColumns();
			metaModel.getForeignKeys();
			metaModel.getPrimaryKey();

			int sumOfAllColumns = metaModel.actuallyGetColumns().size() + 1 + metaModel.actuallyGetForeignKeys().size();

			Class<?>[] colClass = new Class<?>[sumOfAllColumns];
			String sql = "SELECT * FROM " + clazz.getAnnotation(Entity.class);

			Statement stmt = cfg.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsMeta = rs.getMetaData();

			while (rs.next()) {

				allCols.clear();

				
				for (int k = 1; k <= sumOfAllColumns; k++) {

					allCols.add(rs.getObject(k));
					System.out.println(allCols.get(k - 1));
					colClass[k - 1] = Class.forName(rsMeta.getColumnClassName(k));
				}

				T classInstance = (T) clazz.getClass().getConstructor(colClass).newInstance();
				classes.add((Class<?>) classInstance);
				System.out.println("Out of the loop but still in try block");
				return allCols;
			}
		} catch (SQLException | IllegalArgumentException | SecurityException | ClassNotFoundException
				| InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			System.out.println("Unable to get information!");
			e.printStackTrace();

		}
		System.out.println("Did this even work?");
		return allCols;

	}
	// *************************************************************************************************************************************************************

}
