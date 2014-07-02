package edu.neumont.dbt230;


/**
 * This lab is to demonstrate the speed that it takes Java to read text files and serialize objects and show the importance of indexes to keep access times low
 * 
 * @author Blake Rollins (Richard Rollins if that is what your roll says)
 *
 */
public class Main {

	/*
	 * I have the args hardcoded here to ease testing and because the order / usage of the args weren't really specified in the lab doc But it is easy to comment this back in.
	 */
	public static void main(String[] args) {
		args = new String[5];
		args[0] = "1";
		args[1] = "C:\\_\\src\\Neumont\\DBT230-SB1\\HashMapSerial_people\\long serialized";
		args[2] = "C:\\_\\src\\Neumont\\DBT230-SB1\\HashMapSerial\\longMap.ser";
		// args[2] = "C:\\_\\src\\Neumont\\DBT230-SB1\\HashMapSerial\\largeMap.ser";
		// args[2] = "C:\\_\\src\\Neumont\\DBT230-SB1\\HashMapSerial\\simple.ser";
		args[3] = "7";
		args[4] = "SMITH";

		Main m = new Main();
		EmployeeDatabase ed = new EmployeeDatabase();

		// Time it all!!
		long startTime = 0;
		long stopTime = 0;
		long duration = 0;

		int runVal = Integer.parseInt(args[0]);

		// 1: Read all of the files in a given directory (make this changeable) into a HashMap and then serialize the HashMap to disk.
		if (runVal == 1) {
			System.out.println("Creating new HashMap:");
			startTime = System.currentTimeMillis();
			ed.loadFromFiles(args[1]);
			ed.saveToFile(args[2] + "secondtry");
			stopTime = System.currentTimeMillis();
		} else {
			System.out.println("Loading DB:");
			startTime = System.currentTimeMillis();
			ed.loadFromFile(args[2] + "secondtry");
			stopTime = System.currentTimeMillis();
			duration = stopTime - startTime;
			System.out.println("Took " + duration + " ms to load DB");

			startTime = System.currentTimeMillis();
			switch (runVal) {
			case 1:
				break;

			// 2: Read in the HashMap and print each record.
			case 2:
				System.out.println("Reading all from HashMap:");
				ed.printAll();
				break;

			// 3: Read in the HashMap and retrieve records based on employee id
			case 3:
				System.out.println("Searching for Employee with ID:");
				ed.printEmployeeMatchingID(Integer.parseInt(args[3]));
				break;

			// 4: Read in the HashMap and retrieve records based on a last name search
			case 4:
				System.out.println("Searching for Employee with LName:");
				ed.printEmployeesWithLastName(args[4]);
				break;

			// 5: add a new emp
			case 5:
				System.out.println("Adding an Employee");
				Employee toAdd = new Employee("firstname", "lastName", 2014);

				ed.addEmployee(toAdd, 99_999_999);

				System.out.println("searching for new emp by id");
				ed.printEmployeeMatchingID(99_999_999);

				System.out.println("searching for new emp by lname");
				ed.printEmployeesWithLastName("lastName");
			}
			stopTime = System.currentTimeMillis();
		}
		duration = stopTime - startTime;
		System.out.println(duration + " milliseconds");

	}

}
