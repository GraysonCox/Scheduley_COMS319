package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SpringBoot_DAOFactory extends DAOFactory{
	
	protected static final String BASE_URL = "http://proj-319-080.misc.iastate.edu:8080";
	protected static final String MEDIA_TYPE = "application/json";
	
	protected static CloseableHttpClient httpClient;
	
	public SpringBoot_DAOFactory() {
		
	}
	
	//This could return a JSONArray, and the methods in the specific DAO turns that into the object it wants.
	//	-This idea sounds pretty good. I'll just have to see how well i can 
	public JSONArray getJSONArrayFromDB(String table) {
		JSONArray result = new JSONArray();
		try {
			//Create or Use ClientDelegate **TRY to understand this better
			httpClient = HttpClientBuilder.create().build();
			//Get request BASE + specific ending (take as arg)
			HttpGet getRequest = new HttpGet(BASE_URL + table);
			//set Header
			getRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			//execute request
			CloseableHttpResponse httpResponse = httpClient.execute(getRequest);
			//input stream that holds response
			InputStream responseContent = httpResponse.getEntity().getContent();
			//parse received data
			JSONParser jsonParser = new JSONParser();
			//turn into JSONArr
			result = (JSONArray)jsonParser.parse(new InputStreamReader(responseContent, "UTF-8"));
			//close?
			httpResponse.close(); 
			httpClient.close();
			//catches
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//return JSONArr for specifc DAO to decipher
		return result;
	}
	
	public int insertIntoDB(String urlExtension, String inputToDB) {
		int returnCode = -1; //failure
		try {
			httpClient = HttpClientBuilder.create().build(); //same
			HttpPut putRequest = new HttpPut(BASE_URL + urlExtension); //same base + extension

			putRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			putRequest.setEntity(new StringEntity(inputToDB));
			
			//execute request
			CloseableHttpResponse httpResponse = httpClient.execute(putRequest);
			
			//decipher status code returned
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser(); 
			JSONObject obj = (JSONObject) parser.parse(res);
			returnCode = Integer.valueOf(obj.get("status").toString());//translate
			httpResponse.close();
			httpClient.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return returnCode;
	}
	
	public int deleteFromDB(String urlExtension, String removeFromDB) {
		int returnCode = -1;//return code (Status) //some have this as 500? research
		try {
			httpClient = HttpClientBuilder.create().build();//same client
			HttpPost postRequest = new HttpPost(BASE_URL + urlExtension);//Post base + extension
			
			postRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			postRequest.setEntity(new StringEntity(removeFromDB));
			
			CloseableHttpResponse httpResponse = httpClient.execute(postRequest);
			
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(res);
			returnCode = Integer.valueOf(obj.get("status").toString());
			httpResponse.close();
			httpClient.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return returnCode;
	}
	
	public JSONArray getObjectByNameFromDB() {
		//TODO?
		return null;
	}
	
	
	/**
	 * I guess I could probably hack the two Factories together
	 */
	@Override
	public UserDAO getUserDAO() {
		return null;
	}

	@Override
	public MeetingsDAO getMeetingsDAO() {
		return new MeetingsDAOSpringBoot();
	}

	@Override
	public MeetingSpaceDAO getMeetingSpaceDAO() {
		return new MeetingSpaceDAOSpringBoot();
	}

	@Override
	public FloorDAO getFloorDAO() {
		return new FloorDAOSpringBoot();
	}

}
