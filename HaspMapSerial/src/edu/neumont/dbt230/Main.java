package edu.neumont.dbt230;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public void readFiles(String path) {
		long startTime = System.currentTimeMillis();

		File root = new File(path);
		for (File f : root.listFiles()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(f));
				String line = "";
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
				br.close();

			} catch (FileNotFoundException e) {
				System.out.println("Unable to open the file.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();

		long duration = endTime - startTime;
		System.out.println("serialize text files.");
		System.out.println(duration + " milliseconds");

	}

	public void serializeFiles(String path) {
		long startTime = System.currentTimeMillis();

		File root = new File(path);
		for (File f : root.listFiles()) {
			// BufferedReader br = null;
			try {
				ObjectInput input = new ObjectInputStream(new FileInputStream(f));
				Employee cur = (Employee) input.readObject();
				System.out.println(cur);

			} catch (FileNotFoundException e) {
				System.out.println("Unable to open the file.");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();

		long duration = endTime - startTime;
		System.out.println("Read text files.");
		System.out.println(duration + " milliseconds");

	}

	public HashMap<String, Employee> getSomeEmployees(String path) {
		HashMap<String, Employee> retVal = new HashMap<String, Employee>();

		File root = new File(path);
		for (File f : root.listFiles()) {
			try {
				ObjectInput input = new ObjectInputStream(new FileInputStream(f));
				Employee cur = (Employee) input.readObject();
				// System.out.println(cur);
				retVal.put(f.getName(), cur);

			} catch (FileNotFoundException e) {
				System.out.println("Unable to open the file.");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return retVal;
	}

	private void serializeMapToFile(Map<String, Employee> map, String path) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, Employee> getHashMapFromFile(String path) {

		HashMap<String, Employee> retVal = new HashMap<String, Employee>();
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			retVal = (HashMap<String, Employee>) ois.readObject();

			ois.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public static void main(String[] args) {
		args = new String[3];
		args[0] = "1";
		args[1] = "C:\\_\\src\\Neumont\\DBT230-SB1\\HaspMapSerial\\people\\simple serialized";
		args[2] = "C:\\_\\src\\Neumont\\DBT230-SB1\\HaspMapSerial\\hashout.ser";

		Main m = new Main();

		int runVal = Integer.parseInt(args[0]);

		switch (runVal) {

		// 1: Read all of the files in a given directory (make this changeable) into a HashMap and then serialize the HashMap to disk.
		case 1:
			HashMap<String, Employee> emps = m.getSomeEmployees(args[1]);
			m.serializeMapToFile(emps, args[2]);
			break;

		// 2: Read in the HashMap and print each record.
		case 2:
			break;
			
		// 3: Read in the HashMap and retrieve records based on employee id
		case 3:
			break;
			
		// 4: Read in the HashMap and retrieve records based on a last name search
		case 4:
			break;
		}
		// m.readFiles("C:\\_\\src\\Neumont\\DBT230-SB1\\HaspMapSerial\\people\\simple");
		// m.serializeFiles("C:\\_\\src\\Neumont\\DBT230-SB1\\HaspMapSerial\\people\\simple serialized");
//
//		HashMap<String, Employee> emps2 = m.getHashMapFromFile(args[1]);
//
//		System.out.println(emps2.size());

	}

}
