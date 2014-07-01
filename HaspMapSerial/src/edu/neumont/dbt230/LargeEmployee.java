package edu.neumont.dbt230;

import java.util.Random;

public class LargeEmployee extends Employee {

	private byte[] someData;
		
	public LargeEmployee(int id, String firstName, String lastName, int hireYear, byte[] someData) {
		super(id, firstName, lastName, hireYear);
		
		this.someData = someData;
		
		new Random().nextBytes(someData);
	
	}

	
	
}
