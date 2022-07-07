package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//every time @Entity is used, need to specify the table name

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
	String tableName;
}


//Target: tells where an annotation can be used
//3 Retention Policies:
	//Source - annotation accessible during compilation
	//Class - annot accessible during class loading
	//Runtime - same as above but during runtime
		//Runtime mostly used in orm