package com.revature.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

//******************************************************************************************
//******************************************************************************************

public class MetaModel<T> {

	private Class<?> clazz;
	private PrimaryKeyField primaryKeyField;
	private List<ColumnField> columnFields;
	private List<ForeignKeyField> foreignKeyFields;

//******************************************************************************************
//******************************************************************************************	
	
	public MetaModel(Class<?> clazz) {
		this.clazz = clazz;
		this.columnFields = new LinkedList<>();
		this.foreignKeyFields = new LinkedList<>();
	}

	// transpose a normal java class to a MetaModel Class
	// to do that we need to check for the @Entity annotation

//******************************************************************************************
	
	public static MetaModel<Class<?>> of(Class<?> clazz) {
		// let's check for the @entity notation
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create MetaModel Object! Provided Class: " + clazz.getName()
					+ " is not annotated with @Entity");
		}
		return new MetaModel<>(clazz);
	}

//******************************************************************************************
	
	public List<ColumnField> getColumns() {

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				columnFields.add(new ColumnField(field));
			}
		}
		if (columnFields.isEmpty()) {
			throw new RuntimeException("No columns found in: " + clazz.getName());
		}
		return columnFields;
	}

//******************************************************************************************	
	
	public List<ForeignKeyField> getForeignKeys() {
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			JoinColumn foreignKey = field.getAnnotation(JoinColumn.class);
			if (foreignKey != null) {
				foreignKeyFields.add(new ForeignKeyField(field));
			}
		}
		if (foreignKeyFields.isEmpty()) {
			throw new RuntimeException("No foreign keys found in: " + clazz.getName());
		}
		return foreignKeyFields;
		
	}

//******************************************************************************************	
	
	public PrimaryKeyField getPrimaryKey() {
		
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field: fields) {
			Id primaryKey = field.getAnnotation(Id.class);
			if( primaryKey != null) {
				return new PrimaryKeyField(field);
			}
		}
		
		throw new RuntimeException("Did not find a field annotated with @Id in class: " + clazz.getName());
	}

//******************************************************************************************	
	
	public String getSimpleClassName() {
		return clazz.getSimpleName();
	}

//******************************************************************************************	
	
	public String getClassName() {
		return clazz.getName();
		
		
	}
	
	public List<ColumnField> actuallyGetColumns(){
		return columnFields;
	}
	
	public PrimaryKeyField actuallyGetPrimaryKey() {
		return primaryKeyField;
	}
	
	public List<ForeignKeyField> actuallyGetForeignKeys() {
		return foreignKeyFields;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
