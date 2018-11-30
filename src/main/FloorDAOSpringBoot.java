package main;

import java.io.IOException;

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

public class FloorDAOSpringBoot implements FloorDAO {
	
	private static final String BASE_URL = "http://proj-319-080.misc.iastate.edu:8080";
	private static final String MEDIA_TYPE = "application/json";
	
	private Floor floors[];
	
	public FloorDAOSpringBoot() {
		loadFloors();
	}

	/**
	 * This is basically just get all from DB
	 */
	public void loadFloors() {
		Floor[] arr = null;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(BASE_URL + "/floor/search/all");
			getRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);

			CloseableHttpResponse httpResponse = httpClient.execute(getRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONArray result = (JSONArray) parser.parse(res);
			arr = new Floor[result.size()];
			for (int i = 0; i < result.size(); i++) {
				JSONObject temp = (JSONObject) result.get(i);
				long id = (long) temp.get("floorID");
				String name = (String) temp.get("floorName");
				String url = (String) temp.get("imgURL");
				
				//Really have to figure this out
				if(!url.equals("main/floorplan1.jpg")) {
					url = "main/MrScheduley.png";
				}
				Floor ms = new Floor(name, url); 
				ms.setFloorID((int) id);
				arr[i] = ms;
			}
			httpResponse.close();
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		floors = arr;
	}
	
	@Override
	public Floor[] getAllFloors() {
		for(Floor f : floors) {
			f.initImage();
		}
		return floors;
	}

	@Override
	public Floor getFloorByID(int ID) {
		for (Floor f : floors) {
			if (f.getFloorID() == ID)
				return f;
		}
		return null;
	}

	@Override
	public Floor getFloorByName(String name) {
		for (Floor f : floors) {
			if (f.getName().equalsIgnoreCase(name))
				return f;
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addFloor(Floor floor) {
		int code = 500;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPut putRequest = new HttpPut(BASE_URL + "/floor/create");
			JSONObject json = new JSONObject();
			json.put("floorName", floor.getName());
			json.put("imgURL", floor.getImageURL());
			String jsonString = json.toString();
			putRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			putRequest.setEntity(new StringEntity(jsonString));

			CloseableHttpResponse httpResponse = httpClient.execute(putRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(res);
			code = (int) ((long) obj.get("status"));
			httpResponse.close();
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		loadFloors();//Update the instance variable to the new table
		return code;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteFloor(int ID) {
		int code = 500;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(BASE_URL + "/floor/delete");
			JSONObject json = new JSONObject();
			json.put("floorID", ID);
			String jsonString = json.toString();
			postRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			postRequest.setEntity(new StringEntity(jsonString));

			CloseableHttpResponse httpResponse = httpClient.execute(postRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(res);
			code = (int) ((long) obj.get("status"));
			httpResponse.close();
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public boolean isFloorInDB(String name) {
		return (getFloorByName(name) != null) ? true : false;
	}
}
