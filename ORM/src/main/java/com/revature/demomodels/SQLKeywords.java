package com.revature.demomodels;

public enum SQLKeywords {

	CREATE(String.class),
	
	
	
	
	
	
	AND(Integer.class);

	private Class<?> content;
	
	private SQLKeywords(Class<?> content) {
		this.content = content;
		
	
	}
	
	
}
