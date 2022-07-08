package com.revature;

public class TestInspection {
	
	private String name;
	private int value;
	private double balance;
	private boolean trueFalse;
	
	
	public TestInspection() {
		super();
	}


	public TestInspection(String name, boolean trueFalse) {
		super();
		this.name = name;
		this.trueFalse = trueFalse;
	}


	public TestInspection(boolean trueFalse) {
		super();
		this.trueFalse = trueFalse;
	}


	public TestInspection(String name, int value, double balance, boolean trueFalse) {
		super();
		this.name = name;
		this.value = value;
		this.balance = balance;
		this.trueFalse = trueFalse;
	}
	
	
	
	
	
	
}
