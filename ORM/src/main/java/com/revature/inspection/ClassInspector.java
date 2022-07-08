package com.revature.inspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ClassInspector {
	
	public static void listPublicConstructor(Class<?> clazz) {
		System.out.println("Printing out the constructors for the class: " + clazz.getName());
		
		Constructor<?>[] constructors = clazz.getConstructors();
		
		for(Constructor<?> constructor: constructors) {
			System.out.println("\tConstructor Name: " + constructor.getName());
			System.out.println("\tConstructor Param Types: " + Arrays.toString(constructor.getParameterTypes()));
			System.out.println();
		}
	}
	
	
	public static void listNonPublicField(Class<?> clazz) {
		
		System.out.println("Printing the non-public fields of class " + clazz.getName());
		Field[] fields = clazz.getDeclaredFields();
		
		if(fields.length == 0) {
			System.out.println("There are no fields in " + clazz.getName());
			
		}
		
		for(Field field: fields) {
			if(field.getModifiers() == (Modifier.PUBLIC)) {
				continue;
			}
			
			System.out.println("\tField name: " + field.getName());
			System.out.println("\tField type: " + field.getType());
			System.out.println("\tIs the field primitive? " + field.getType().isPrimitive());
			System.out.println();
		}
		
	}
	
	
}
