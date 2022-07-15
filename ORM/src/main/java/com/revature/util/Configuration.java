package com.revature.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.revature.annotations.Entity;
import com.revature.annotations.Id;

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

	
//*************************************************************************************************************************************************************		

	public <T> int insert(Object object) {
		Class<T> clazz = (Class<T>) object.getClass();
		MetaModel<T> metaModel = new MetaModel<T>(clazz);
		MetaModel.of(clazz);

		try {
			Connection cfg = ds.getConnection();
			metaModel.getColumns();
			metaModel.getForeignKeys();
			metaModel.getPrimaryKey();

			int sumOfAllColumns = metaModel.actuallyGetColumns().size() + 1 + metaModel.actuallyGetForeignKeys().size();
			List<Field> allCols = Arrays.asList(clazz.getDeclaredFields());
			List<Object> obList = new LinkedList<Object>();

			for (int i = 0; i < allCols.size(); i++) {
				allCols.get(i).setAccessible(true);
				obList.add(allCols.get(i).get(object));
			}
			String sql = "INSERT INTO " + clazz.getAnnotation(Entity.class).tableName() + " VALUES( ";
			for (int i = 0; i < allCols.size(); i++) {
				if (obList.get(i).getClass() == String.class) {
					sql = sql.concat("'" + obList.get(i).toString() + "'");
				} else {
					sql = sql.concat(obList.get(i).toString());
				}
				if (i < allCols.size() - 1) {
					sql = sql.concat(", ");
				}
			}
			sql = sql.concat(");");

			Statement stmt = cfg.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			obList.clear();
			while (rs.next()) {
				int j = 0;

				Object obj = rs.getObject(j + 1);
				obList.add(obj);

				if (allCols.get(j) == metaModel.actuallyGetPrimaryKey().getPrimField()) {
					System.out.println("Successfully inserted");
					return (int) allCols.get(j).get(object);
				}
			}

		} catch (Exception e) {
			System.out.println("Unsuccessfull");
			e.printStackTrace();
		}
		return -1;
	}

//*************************************************************************************************************************************************************	

	
	// *************************************************************************************************************************************************************
	public void delete(Object obj) {
		// Extracting the metaClassModel from the object.
		Class<?> cla;
		cla = obj.getClass();
		MetaModel<Class<?>> Class = MetaModel.of(obj.getClass());
		// Getting to know the obj
		String tableName = Class.getClassName();
		PrimaryKeyField objPkField = Class.getPrimaryKey();

		int PKValue = 0;

		// This is the object PK Field Name
		String PKFieldName = objPkField.getName();

		try {
			Field valF = cla.getDeclaredField(objPkField.getName());
			valF.setAccessible(true);
			// Getting the object PK value
			PKValue = (int) valF.get(obj);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Building sql query to delete table
		// Using String Builder cause apparently String just ain't it
		StringBuilder sql_sb = new StringBuilder();
		sql_sb.append("DELETE FROM " + cla.getAnnotation(Entity.class).tableName() + " WHERE " + objPkField.getPrimField().getAnnotation(Id.class).columnName() + " = " + PKValue);
		System.out.println(sql_sb);
		try (Connection conn = ds.getConnection()) {

			PreparedStatement st = conn.prepareStatement(sql_sb.toString());
			st.executeUpdate();
			System.out.println("Item has been deleted (hopefully)");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// *************************************************************************************************************************************************************
	
	public <T> void update(Object obj) {

		// Getting METAMODEL from Object.
			// Named cla cause ide got angry at me for trying to use class
			// Not sure why, but a happy ide is a good ide
		Class<?> cla;
		cla = obj.getClass();
		MetaModel<Class<?>> theClass = MetaModel.of(obj.getClass());

		// Getting the values/types from the given obj
		List<T> values = new ArrayList<>();
		int PK_value=0;
		List<T> types = new ArrayList<>();
		List<String> allColNames = new ArrayList<>();

		// Getting Name, columns, fk, and pk from class using metamodel methods
		String tableName = theClass.getClassName();
		List<ColumnField> columns = theClass.getColumns();
		List<ForeignKeyField> fkColums = theClass.getForeignKeys();
		PrimaryKeyField thePKofObj = theClass.getPrimaryKey();

		// Using string builder to make appending easier
		StringBuilder sql_sb = new StringBuilder();

		// filling the values/types arrays then appending them to string builder above
		for (int i = 0; i < columns.size(); i++) {
			allColNames.add(columns.get(i).getColumnName().toString());
			try {
				Field valF = cla.getDeclaredField(columns.get(i).getName());
				// filling values.
				valF.setAccessible(true);
				values.add((T) valF.get(obj));
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// filling types.
			types.add((T) columns.get(i).getType());
		}

		// Checking for Foreign keys and adding them as needed
		if (!fkColums.isEmpty()) {
			for (int i = 0; i < fkColums.size(); i++) {
				allColNames.add(fkColums.get(i).getColumnName().toString());
				try {
					Field valF = cla.getDeclaredField(fkColums.get(i).getName());
					// filling values.
					valF.setAccessible(true);
					values.add((T) valF.get(obj));
				} catch (NoSuchFieldException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// filling types.
				types.add((T) fkColums.get(i).getType());
			}
		}

		//Making SQL Statement to update the table
		sql_sb.append("UPDATE " + cla.getAnnotation(Entity.class).tableName() + " SET ");
		for(int i=0; i<values.size(); i++) {
			
			if(i+1!=values.size()) {
				sql_sb.append(allColNames.get(i)+" = ");
				if (types.get(i).toString().equalsIgnoreCase("int")) {
					sql_sb.append((int) values.get(i)+" , ");
				} else if (types.get(i).toString().equalsIgnoreCase("class java.lang.String")) {
					sql_sb.append(" '"+values.get(i).toString()+"' , ");
				} else {
					sql_sb.append((int) values.get(i)+" , ");
				}
				
			} else {
				sql_sb.append(allColNames.get(i)+" = ");
				if (types.get(i).toString().equalsIgnoreCase("int")) {
					sql_sb.append((int) values.get(i));
				} else if (types.get(i).toString().equalsIgnoreCase("class java.lang.String")) {
					sql_sb.append(" '"+values.get(i).toString()+"' ");
				} else {
					sql_sb.append((int) values.get(i));
				}
				
			}
			
		}
		
		//Getting the pk fields 
		try {
			Field valF = cla.getDeclaredField(thePKofObj.getName());
			// filling values.
			valF.setAccessible(true);
			PK_value = valF.getInt(obj);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sql_sb.append(" WHERE "+thePKofObj.getPrimField().getAnnotation(Id.class).columnName()+" = "+PK_value);
		 
		try (Connection conn = ds.getConnection()) {
			System.out.println(sql_sb);
			PreparedStatement st = conn.prepareStatement(sql_sb.toString());
			st.executeUpdate();
			System.out.println("Successfully updated table!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void read(Object obj) {
		// Extracting the metaClassModel from the object.
		Class<?> clays;
		clays = obj.getClass();
		MetaModel<Class<?>> theClass = MetaModel.of(obj.getClass());
		// Getting to know the obj
		String tableName = theClass.getClassName();
		PrimaryKeyField objPkField = theClass.getPrimaryKey();

		int PKValue = 0;

		// This is the object PK Field Name
		String PKFieldName = objPkField.getName();

		try {
			Field valF = clays.getDeclaredField(objPkField.getName());
			valF.setAccessible(true);
			// Getting the object PK value
			PKValue = (int) valF.get(obj);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// StringBuilder again
		StringBuilder sql_sb = new StringBuilder();
		sql_sb.append("SELECT * FROM " + clays.getAnnotation(Entity.class).tableName() + " WHERE " + objPkField.getPrimField().getAnnotation(Id.class).columnName() + " = " + PKValue + ";");
		System.out.println(sql_sb);
		try (Connection conn = ds.getConnection()) {

			PreparedStatement st = conn.prepareStatement(sql_sb.toString());

			ResultSet rs = st.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				System.out.println(
						"Now reading out " + rsmd.getTableName(1) + " with ID of " + rs.getObject(1).toString());
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getObject(i).toString();
					System.out.print(rsmd.getColumnName(i) + ": " + columnValue + "\n");
					System.out.println("*************************************************");
				}
				System.out.println("Finished reading from table: " + rsmd.getTableName(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
}
