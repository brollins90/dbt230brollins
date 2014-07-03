package edu.neumont.dbt230;

import java.util.Scanner;


/**
 * This lab is to demonstrate the speed that it takes Java to read text files and serialize objects and show the importance of indexes to keep access times low
 * 
 * @author Blake Rollins (Richard Rollins if that is what your roll says)
 *
 */
public class Main {

	private static String defaultDir = "C:\\_\\src\\Neumont\\DBT230-SB1\\HashMapSerial_people\\long serialized";
	private static String defaultSer = "C:\\_\\src\\Neumont\\DBT230-SB1\\HashMapSerial\\Employees.ser";
	private static String defaultLName = "SMITH";
	private static Scanner scan;

	public static void main(String[] args) {

		scan = new Scanner(System.in);

		EmployeeDatabase ed = new EmployeeDatabase();

		long startTime = 0;
		long stopTime = 0;
		long duration = 0;

		boolean running = true;
		
		while(running)
		{
			System.out.println("What do you wanna do?");
			System.out.println("1: Load from a directory.");
			System.out.println("2: Load from a serialized file.");
			System.out.println("3: Search by ID.");
			System.out.println("4: Search by Last Name.");
			System.out.println("5: Add a new Employee.");
			System.out.println("6: Save to a serialized file.");
			System.out.println("0: Quit");
			int runVal = readInt();
			switch (runVal) {
			case 0: // QUIT
				startTime = System.currentTimeMillis();
				stopTime = System.currentTimeMillis();
				running = false;
				break;
			case 1: // Load from dir
				System.out.println("Enter the directory: [" + defaultDir + "]");
				String directory = readLine();
				directory = (directory.equals("")) ? defaultDir : directory;
				System.out.println("Loading from '" + directory + "'");
				
				startTime = System.currentTimeMillis();
				ed.loadFromFiles(directory);
				stopTime = System.currentTimeMillis();
				break;
			case 2: // Load from ser
				System.out.println("Enter the serialized file: [" + defaultSer + "]");
				String file = readLine();
				file = (file.equals("")) ? defaultSer : file;
				System.out.println("Loading from '" + file + "'");
				
				startTime = System.currentTimeMillis();
				ed.loadFromFile(file);
				stopTime = System.currentTimeMillis();
				break;
			case 3: // Search by ID
				System.out.println("Search for what Employee ID?");
				int searchId = readInt();
				System.out.println("Searching for Employee " + searchId);
				
				startTime = System.currentTimeMillis();
				ed.printEmployeeMatchingID(searchId);
				stopTime = System.currentTimeMillis();
				break;
			case 4: // Search by Last Name
				System.out.println("Search for what Employee last name? [" + defaultLName + "]");
				String lName = readLine();
				lName = (lName.equals("")) ? defaultLName : lName;
				System.out.println("Searching for Employee " + lName);
				
				startTime = System.currentTimeMillis();
				ed.printEmployeesWithLastName(lName);
				stopTime = System.currentTimeMillis();
				break;
			case 5: // Add an emp
				
				boolean employeeValid = false;
				String firstName = "";
				String lastName = "";
				int hireDate = 0;
				int id = 0;
				
				while (!employeeValid) {
					System.out.println("Enter the employee's first name:");
					firstName = readLine();
					System.out.println("Enter the employee's last name:");
					lastName = readLine();
					System.out.println("Enter the employee's hire year:");
					hireDate = readInt();

					System.out.println("firstName: " + firstName);
					System.out.println("lastName: " + lastName);
					System.out.println("hireDate: " + hireDate);
					System.out.println("Is this correct?");
					String yesOrNo = readLine();
					if (yesOrNo.toLowerCase().charAt(0) == 'y') {
						employeeValid = true;
					}
				}
				Employee newEmployee = new Employee(firstName, lastName, hireDate);

				startTime = System.currentTimeMillis();
				id = ed.getHighestId();
				ed.addEmployee(newEmployee, id);
				stopTime = System.currentTimeMillis();
				
				break;
			case 6: // save
				System.out.println("Enter the serialized file: [" + defaultSer + "]");
				String file2 = readLine();
				file2 = (file2.equals("")) ? defaultSer : file2;
				System.out.println("Saving to '" + file2 + "'");
				
				startTime = System.currentTimeMillis();
				ed.saveToFile(file2);
				stopTime = System.currentTimeMillis();
				break;
			default:
				break;
			}
			duration = stopTime - startTime;
			System.out.println("This round took " + duration + " ms.");
			System.out.println("");
		}
	}
	
	/**
	 * reads a string from the console and returns it
	 * @return The string entered at the console
	 */
	static String readLine() {
		return scan.nextLine();
	}

	/**
	 * reads an int from the console and returns it
	 * @return The int entered at the console
	 */
	static int readInt() {
		try{
		return Integer.parseInt(readLine());
		} catch (Exception e) {
			return -1;
		}
	}
}
