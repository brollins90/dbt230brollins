package edu.neumont.dbt230;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This assignment is to let us play with Riak
 * @author Blake Rollins
 *
 */
public class Main {

	private final String USER_AGENT = "Mozilla/5.0";
	private final String SERVER_NAME = "localhost";
	private final String SERVER_PORT = "10018";

	public static void main(String[] args) {

		Main m = new Main();
		m.go();
	}

	public void go() {
		long startTime = 0;
		long stopTime = 0;
		long duration = 0;

		try {
			EmployeeDatabase ed = new EmployeeDatabase();
			System.out.println("loading");
			ed.loadFromFiles("/home/blake/Downloads/long serialized");//\\Employees.ser");
			System.out.println("loaded");
			
			for (int i : ed.db.keySet()){
				addEmployeeToRiak(i, ed.db.get(i), ed.db.get(i).getLastName());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addEmployeeToRiak(int id, Employee e, String lname) {
		try {
			sendToRiak("tryNumber1", "" + id, e.toString(), lname);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void getFromRiak(String bucketName, String keyName) throws Exception {
		 
		String url = "http://" + SERVER_NAME + ":" + SERVER_PORT + "/buckets/" + bucketName + "/keys/" + keyName;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	
	private void sendToRiak(String bucketName, String keyName, String value, String lnameIndex) throws Exception {
		 
		String url = "http://" + SERVER_NAME + ":" + SERVER_PORT + "/buckets/" + bucketName + "/keys/" + keyName;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-type", "text/json");
		con.setRequestProperty("x-riak-index-lname_bin", lnameIndex);

		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(value);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + value);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
}
