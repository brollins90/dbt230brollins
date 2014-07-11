package edu.neumont.dbt230;

public class Employee implements java.io.Serializable {

	private static final long serialVersionUID = 5814635088039616293L;
	
	private String firstName;
	private String lastName;
	private int hireYear;
	private int id;
	
	public Employee(String firstName, String lastName, int hireYear) {
		super();
		this.id = 0;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hireYear = hireYear;
	}
	
	public Employee(int id, String firstName, String lastName, int hireYear) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hireYear = hireYear;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public int getHireYear() {
		return hireYear;
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName
				+ ", hireYear=" + hireYear + ", id=" + id + "]";
	}

	
}
