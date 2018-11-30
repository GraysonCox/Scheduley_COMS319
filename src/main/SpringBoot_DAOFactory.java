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

	public JSONArray getJSONArrayFromDB(String urlExtension) {
		JSONArray result = new JSONArray();
		try {
			httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(BASE_URL + urlExtension);
			getRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			CloseableHttpResponse httpResponse = httpClient.execute(getRequest);
			InputStream responseContent = httpResponse.getEntity().getContent();
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
		return result;
	}
	
	public int insertIntoDB(String urlExtension, String inputToDB) {
		int returnCode = -1; //failure
		try {
			httpClient = HttpClientBuilder.create().build();
			HttpPut putRequest = new HttpPut(BASE_URL + urlExtension); 

			putRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			putRequest.setEntity(new StringEntity(inputToDB));
			
			CloseableHttpResponse httpResponse = httpClient.execute(putRequest);
			
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
	
	public int deleteFromDB(String urlExtension, String removeFromDB) {
		int returnCode = -1;
		try {
			httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(BASE_URL + urlExtension);
			
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