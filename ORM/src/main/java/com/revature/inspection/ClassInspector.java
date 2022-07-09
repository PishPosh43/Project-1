package com.revature.inspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
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
	
	
	public static void listPublicMethods(Class<?> clazz) {
		
		System.out.println("Printing public methods" + clazz.getName());
		Method[] methods = clazz.getMethods();
		
		for (Method method: methods) {
			if(method.getDeclaringClass() == Object.class) {
				continue;
			}
			if(!(method.getModifiers() == (Modifier.PUBLIC))) {
				continue;
			}
			System.out.println("\tMethod Name: " + method.getName());
			System.out.println("\tMethod Param Count: " + method.getParameterCount());
			System.out.println("\tMethod declared class: " + method.getDeclaringClass());
			System.out.println("\tMethod declared annotations: " + Arrays.toString(method.getDeclaredAnnotations()));
			
			Parameter[] params = method.getParameters();
			
			for (Parameter param: params) {
				System.out.println("\tParameter Name: " + param.getName());
				System.out.println("\tParameter Type: " + param.getType());
			}
		}
				
			
				
				
				
				
				
				
				
				
				
				
				
				
			}
		}
		
	}
	
	
	
	
}
