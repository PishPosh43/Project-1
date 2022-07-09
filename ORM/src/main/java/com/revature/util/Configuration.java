package com.revature.util;

import java.sql.Connection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Configuration {

	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private List<MetaModel<Class<?>>> metaModelList;
	

	public Configuration addAnnotatedClass(Class annotatedClass) {
		
		if(metaModelList == null) {
			metaModelList = new LinkedList<>();
		}
		metaModelList.add(MetaModel.of(annotatedClass));
		
		return this;
		
	}
	
	public List<MetaModel<Class<?>>> getMetaModels(){
		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
	}
	
	public Connection getConnection(String dbUrl, String dbUsername, String dbPassword) {
		
		this.dbUrl = dbUrl;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		
		//ENTER CODE & LOGIC HERE
		
		//REPLACE NULL AFTER WRITING CODE BODY;
		return null;
	}
	
}
