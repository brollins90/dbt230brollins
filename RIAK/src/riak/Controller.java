package riak;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Controller {

	private final String USER_AGENT = "Mozilla/5.0";
	private final String SERVER_NAME = "localhost";
	private final String SERVER_PORT = "10018";
	
	public static void main(String[] args) throws Exception {
		Controller c = new Controller();
		c.getFromRiak("jsonbucket","1.json");
//		c.sendToRiak("jsonbucket","1.json", "{\"firstName\":\"Blake\",\"lastName\":\"Rollins\"}");		
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
	
	private void sendToRiak(String bucketName, String keyName, String value) throws Exception {
		 
		String url = "http://" + SERVER_NAME + ":" + SERVER_PORT + "/buckets/" + bucketName + "/keys/" + keyName;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-type", "text/json");
 
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

// curl -v -XPUT -H "x-riak-index-lname_bin:Smith" -d '{"fname":"Fred","lname":"Smith"}' http://localhost:10018/buckets/people/keys/1.json

// http://www.xyzws.com/Javafaq/how-to-use-httpurlconnection-post-data-to-web-server/139


//String urlParameters =
//"fName=" + URLEncoder.encode("???", "UTF-8") +
//"&lName=" + URLEncoder.encode("???", "UTF-8");
//
//public static String excutePost(String targetURL, String urlParameters)
//{
//  URL url;
//  HttpURLConnection connection = null;  
//  try {
//    //Create connection
//    url = new URL(targetURL);
//    connection = (HttpURLConnection)url.openConnection();
//    connection.setRequestMethod("POST");
//    connection.setRequestProperty("Content-Type", 
//         "application/x-www-form-urlencoded");
//			
//    connection.setRequestProperty("Content-Length", "" + 
//             Integer.toString(urlParameters.getBytes().length));
//    connection.setRequestProperty("Content-Language", "en-US");  
//			
//    connection.setUseCaches (false);
//    connection.setDoInput(true);
//    connection.setDoOutput(true);
//
//    //Send request
//    DataOutputStream wr = new DataOutputStream (
//                connection.getOutputStream ());
//    wr.writeBytes (urlParameters);
//    wr.flush ();
//    wr.close ();
//
//    //Get Response	
//    InputStream is = connection.getInputStream();
//    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//    String line;
//    StringBuffer response = new StringBuffer(); 
//    while((line = rd.readLine()) != null) {
//      response.append(line);
//      response.append('\r');
//    }
//    rd.close();
//    return response.toString();
//
//  } catch (Exception e) {
//
//    e.printStackTrace();
//    return null;
//
//  } finally {
//
//    if(connection != null) {
//      connection.disconnect(); 
//    }
//  }
//}