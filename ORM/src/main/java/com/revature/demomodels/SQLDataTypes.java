package com.revature.demomodels;

import java.math.BigInteger;
import java.sql.Array;

public enum SQLDataTypes {

	VARCHAR(String.class, 0),
	CHAR(String.class, 1),
	BIT(Boolean.class, 2),
	TINYINT(Byte.class, 3),
	SMALLINT(Short.class, 4),
	INTEGER(Integer.class, 5),
	BIGINT(Long.class, 6),
	REAL(Float.class, 7),
	DOUBLE(Double.class, 8),
	ARRAY(Array.class, 9);
	;
	

	private Class<?> content;
	private int index;
	
	SQLDataTypes(Class<?> content, int index){//, int index) {
		this.content = content;
		this.index = index;
	}
	
	
	public Class<?> getContent() {
		return content;
	}

	public int getIndex() {
		return index;
	}






//	public static SQLDataTypes byOrdinal(int ordinal) {
//		for(SQLDataTypes type: SQLDataTypes.values()) {
//			if(type.ordinal() == ordinal) {
//				return type;
//			}
//		}
//		return null;
//	}
//	
//	public static SQLDataTypes byOrdinalValue(int ordinal) {
//		for(SQLDataTypes type: SQLDataTypes.values()) {
//			if(type.ordinal() == ordinal) {
//				return type;
//			}
//		}
//		return null;
//	}
	
	
}
	

