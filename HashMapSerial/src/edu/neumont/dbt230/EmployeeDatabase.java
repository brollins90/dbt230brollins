package edu.neumont.dbt230;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeDatabase {
	
	HashMap<Integer, Employee> db = null;
	HashMap<String, ArrayList<Integer>> iFirstName = null;
	HashMap<Integer, ArrayList<Integer>> iHireDate = null;	
	HashMap<String, ArrayList<Integer>> iLastName = null;
	private int highestId;
	
	public EmployeeDatabase() {
		db = new HashMap<Integer, Employee>();
		iFirstName = new HashMap<String, ArrayList<Integer>>();
		iHireDate = new HashMap<Integer, ArrayList<Integer>>();
		iLastName = new HashMap<String, ArrayList<Integer>>();
		highestId = 0;
	}
	
	/**
	 * Clears the database and all the indexes
	 */
	private void clearAll() {
		db.clear();
		iFirstName.clear();
		iHireDate.clear();
		iLastName.clear();
	}
	
	/**
	 * Adds a new employee to the db
	 * @param e	The new employee
	 * @param id The ID of the employee
	 */
	public void addEmployee(Employee e, int id) {
		this.highestId = (id > highestId) ? id : highestId;
		db.put(id, e);
		
		String curFirst = e.getFirstName().toUpperCase();
		if (iFirstName.containsKey(curFirst)) {
			iFirstName.get(curFirst).add(id);
		} else {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(id);
			iFirstName.put(curFirst, temp);
		}

		
		int curDate = e.getHireYear();
		if (iHireDate.containsKey(curDate)) {
			iHireDate.get(curDate).add(id);
		} else {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(id);
			iHireDate.put(curDate, temp);
		}

		
		String curLast = e.getLastName().toUpperCase();
		if (iLastName.containsKey(curLast)) {
			iLastName.get(curLast).add(id);
		} else {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(id);
			iLastName.put(curLast, temp);
		}		
	}
	
	/**
	 * Loads serialized object files from a directory
	 * @param directory The directory that contains the serialized files
	 */
	public void loadFromFiles(String directory) {
		clearAll();

		File root = new File(directory);
		for (File f : root.listFiles()) {
			try {
				ObjectInput input = new ObjectInputStream(new FileInputStream(f));
				Employee cur = (Employee) input.readObject();
				int id = Integer.parseInt(f.getName().substring(0, f.getName().length() - 4));
				addEmployee(cur, id);
				input.close();
			} catch (FileNotFoundException e) {
				System.out.println("Unable to open the file.");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns the highest ID in the DB
	 * @return  THe highest ID in the DB
	 */
	public int getHighestId() {
//		int highest = 0;
//		for (int i : db.keySet()) {
//			highest = (i > highest) ? i : highest;
//		}
//		return highest;
		return ++this.highestId;
	}

	/**
	 * Loads a serialized hashmap from a file
	 * @param file The file containing the HashMap
	 */
	@SuppressWarnings("unchecked")
	public void loadFromFile(String file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			db = (HashMap<Integer, Employee>) ois.readObject();
			iFirstName = (HashMap<String, ArrayList<Integer>>) ois.readObject();
			iHireDate = (HashMap<Integer, ArrayList<Integer>>) ois.readObject();
			iLastName = (HashMap<String, ArrayList<Integer>>) ois.readObject();
			ois.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Serializes the database and saves it to a file
	 * @param file The location to save the file
	 */
	public void saveToFile(String file) {
		FileOutputStream fos;
		try {
			File yourFile = new File(file);
			if(!yourFile.exists()) {
			    yourFile.createNewFile();
			} 
			fos = new FileOutputStream(yourFile, false); 

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(db);
			oos.writeObject(iFirstName);
			oos.writeObject(iHireDate);
			oos.writeObject(iLastName);
			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints all the records to the Console
	 */
	public void printAll() {
		for (int i : db.keySet()) {
			System.out.println(db.get(i));
		}
	}

	/**
	 * Prints the employee that matches the ID to the console
	 * @param id The ID to search for
	 */
	public void printEmployeeMatchingID(int id) {
		System.out.println(db.get(id));
	}

	/**
	 * Prints all the employees that have the specified last name to the console
	 * @param lName The search last name
	 */
	public void printEmployeesWithLastName(String lName) {
		for (int i : iLastName.get(lName.toUpperCase())) {
			System.out.println(db.get(i));
		}
	}

	/**
	 * Prints all the employees that have the specified first name to the console
	 * @param lName The search first name
	 */
	public void printEmployeesWithFirstName(String fName) {
		for (int i : iFirstName.get(fName.toUpperCase())) {
			System.out.println(db.get(i));
		}
	}

	/**
	 * Prints all the employees that have the specified hire date to the console
	 * @param lName The search hire date
	 */
	public void printEmployeesWithHireDate(int date) {
		for (int i : iHireDate.get(date)) {
			System.out.println(db.get(i));
		}
	}
}
