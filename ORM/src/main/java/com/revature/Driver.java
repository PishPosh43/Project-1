package com.revature;

import com.revature.inspection.ClassInspector;

public class Driver {
	
	public static void main(String[] args) {
		
		ClassInspector.listPublicConstructor(TestInspection.class);
		ClassInspector.listNonPublicField(TestInspection.class);
		
	}
	
}
