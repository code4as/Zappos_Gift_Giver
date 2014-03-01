package code4as.com.zappos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.*;
public class Connector {
    public JSONArray a = null;
	public Connector(){
		
	}
	
	public JSONArray getProductInfo(String productName,String price_look,String api_key) {
	String url = "http://api.zappos.com/Search?term=" + productName
				+ "&filters={\"price\":[\"" + price_look + "\"]}"
				+ "&key=" + api_key;
	URL obj;
	try {
		obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		System.out.println("conn estbl");
		con.setRequestMethod("GET");
		if(con.getResponseCode() == 200){
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine = "";
		String ip = "";
		while ((inputLine = in.readLine()) != null) 
			ip = ip + inputLine;
			in.close();
		    //System.out.println(ip);	
		    JSONObject json_string = new JSONObject(ip);
		   //System.out.println(json_string.get("results"));
		    //System.out.println(json_string);
			JSONArray jsonArray = new JSONArray(json_string.getJSONArray("results").toString());
			return jsonArray;
		}
		else
			{System.out.println("error within connection...check the API connection please..status is " + con.getResponseCode());
			return null;
			}
	} 
	catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return a;
	}

	public JSONArray getPrices(String productName, String price_look, String api_key) {
		// TODO Auto-generated method stub
		
		String url1 = "http://api.zappos.com/Search?term=" + productName
				+ "&facets=[\"" + "price" + "\"]"
				+ "&limit=" + 100
				+ "&facetSort=" + "name" 
				+ "&key=" + api_key;
		JSONArray jsonValue = null;
		try{
			URL obj = new URL(url1);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		System.out.println("conn estbl for second time");
		con.setRequestMethod("GET");
		if(con.getResponseCode() == 200){
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine = "";
		String ip = "";
		while ((inputLine = in.readLine()) != null) 
			ip = ip + inputLine;
			in.close();
		    System.out.println(ip);	
		    JSONObject json_string = new JSONObject(ip);
		    System.out.println(json_string.get("facets"));
		    //System.out.println(json_string);
			JSONArray jsonArray = new JSONArray(json_string.getJSONArray("facets").toString());
			jsonValue = new JSONArray(jsonArray.getJSONObject(0).get("values").toString());
			System.out.println("jsonValue.length  " + jsonValue.length());
			
		}
		else
			System.out.println("error within connection...");
		
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonValue;
	}
	
	
}
