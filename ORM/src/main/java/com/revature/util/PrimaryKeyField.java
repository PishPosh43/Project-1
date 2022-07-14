package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.Id;

public class PrimaryKeyField {
public Field field;
	
	public PrimaryKeyField(Field field) {
		if(field.getAnnotation(Id.class) == null) {
			throw new IllegalStateException("Cannot create PrimaryKeyField object. Provided field: "
					+ getName() + " is not annotate	d with @Id");
		}
		this.field=field;
	}
	
	public String getName() {
		return field.getName();
	}
	
	public Class<?> getType(){
		return field.getType();
	}
	
	public String getColumnName() {
		return field.getAnnotation(Id.class).columnName();
	}
	
	
}
