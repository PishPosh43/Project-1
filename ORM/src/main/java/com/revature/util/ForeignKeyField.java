package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.Column;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

public class ForeignKeyField {
	
	public Field field;
	
	public ForeignKeyField(Field field) {
		if(field.getAnnotation(JoinColumn.class) == null) {
			throw new IllegalStateException("Cannot create ForeignKeyField object. Provided field: "
					+ getName() + " is not annotated with @JoinColumn");
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
		return field.getAnnotation(JoinColumn.class).columnName();
	}
	
	public Field getForField() {
		return field;
	}
	
}
