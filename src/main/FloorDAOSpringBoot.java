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
	private Floor floors[];
	
	public FloorDAOSpringBoot() {
		loadFloors();
	}

	public void loadFloors() {
		Floor[] arr = null;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet("http://proj-319-080.misc.iastate.edu:8080/floor/search/all");
			getRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

			CloseableHttpResponse httpResponse = httpClient.execute(getRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONArray result = (JSONArray) parser.parse(res);
			arr = new Floor[result.size()];
			System.out.println("Given array size: " + result.size());
			for (int i = 0; i < result.size(); i++) {
				JSONObject temp = (JSONObject) result.get(i);
				long id = (long) temp.get("floorID");
				String name = (String) temp.get("floorName");
				String url = (String) temp.get("imgURL");
				
				
				if(!url.equals("main/floorplan1.jpg")) {
					System.out.println("Current floor " + name + ", url: " +url);
					url = "main/MrScheduley.png";
				}
				else {
					System.out.println("DEBUG HERE");//I have no excuse for this
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
			HttpPut putRequest = new HttpPut("http://proj-319-080.misc.iastate.edu:8080/floor/create");
			JSONObject json = new JSONObject();
			json.put("floorName", floor.getName());
			json.put("imgURL", floor.getImageURL());
			String jsonString = json.toString();
			putRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
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
		return code;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteFloor(int ID) {
		int code = 500;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost("http://proj-319-080.misc.iastate.edu:8080/floor/delete");
			JSONObject json = new JSONObject();
			json.put("floorID", ID);
			String jsonString = json.toString();
			postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
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
}
