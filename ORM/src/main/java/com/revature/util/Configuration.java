package com.revature.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import com.revature.annotations.Id;
import com.revature.demomodels.SQLDataTypes;

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
	public static void main(String[] args) {
		Connection conn1 = getConnection();
        Connection conn2 = getConnection();


        System.out.println(conn1);
        System.out.println(conn2);
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
