package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

public class MeetingSpaceDAOSpringBoot implements MeetingSpaceDAO {
	
	private MeetingSpace meetingSpaces[];

	public MeetingSpaceDAOSpringBoot() {
		loadMeetingSpaces();
	}
	
	/**
	 * Get all from DB
	 */
	public void loadMeetingSpaces() {
		MeetingSpace[] arr = null;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet("http://proj-319-080.misc.iastate.edu:8080/meetingspace/search/all");
			getRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			
			CloseableHttpResponse httpResponse = httpClient.execute(getRequest);
			InputStream responseContent = httpResponse.getEntity().getContent(); //new
			JSONParser parser = new JSONParser();
			JSONArray result = (JSONArray)parser.parse(new InputStreamReader(responseContent, "UTF-8")); //new
			arr = new MeetingSpace[result.size()];
			
			for(int i=0; i<result.size(); i++) {
				JSONObject temp = (JSONObject) result.get(i);
				long x = (long)temp.get("x");
				long y = (long)temp.get("y");
				long w = (long)temp.get("width");
				long h = (long)temp.get("height");
				long f = (long)temp.get("floorID");
				long z = (long)temp.get("meetingSpaceID");
				
				
				MeetingSpace ms = new MeetingSpace((String)temp.get("meetingSpaceName"),x,y,w,h,(int) f);
				ms.setUniqueID((int)z);
				arr[i] = ms;
			}
			
			httpResponse.close();
			httpClient.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		meetingSpaces = arr;
	}
	
	@Override
	public MeetingSpace[] getAllMeetingSpace() {
		return meetingSpaces;
	}

	@Override
	public MeetingSpace[] getMeetingSpaceByFloor(int floorID) {
		ArrayList<MeetingSpace> arrlist = new ArrayList<MeetingSpace>();
		for(int i=0; i<meetingSpaces.length; i++) {
			if(meetingSpaces[i].getFloorID() == floorID)
				arrlist.add(meetingSpaces[i]);
		}
		MeetingSpace[] result = new MeetingSpace[arrlist.size()];
		for(int i = 0; i < arrlist.size(); i++) {
			result[i] = arrlist.get(i);
		}
		return result;
	}

	/**
	 * Returns 200 on success
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addMeetingSpace(MeetingSpace meetingSpace) {
		int code = 500;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPut putRequest = new HttpPut("http://proj-319-080.misc.iastate.edu:8080/meetingspace/create");
			JSONObject json = new JSONObject();
			json.put("meetingSpaceName", meetingSpace.getName());
			json.put("x", meetingSpace.getX());
			json.put("y", meetingSpace.getY());
			json.put("width", meetingSpace.getWidth());
			json.put("height", meetingSpace.getHeight());
			json.put("floorID", meetingSpace.getFloorID());
		    String jsonString = json.toString();
			putRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			putRequest.setEntity(new StringEntity(jsonString));
		
			CloseableHttpResponse httpResponse = httpClient.execute(putRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(res);
			code = (int)((long) obj.get("status"));
			httpResponse.close();
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		loadMeetingSpaces();//Update the instance variable to the new table
		return code;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteMeetingSpace(int x, int y, int floor) {
		int code = 500;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost("http://proj-319-080.misc.iastate.edu:8080/meetingspace/delete");
			JSONObject json = new JSONObject();
			json.put("x", x);
			json.put("y", y);
			json.put("floorID", floor);
		    String jsonString = json.toString();
			postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			postRequest.setEntity(new StringEntity(jsonString));
		
			CloseableHttpResponse httpResponse = httpClient.execute(postRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(res);
			code = (int)((long) obj.get("status"));
			httpResponse.close();
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		loadMeetingSpaces();//Update the instance variable to the new table
		return code;
	}
	
	public int deleteMeetingSpace(MeetingSpace toDelete) {
		return deleteMeetingSpace((int)toDelete.getX(), (int)toDelete.getY(), toDelete.getFloorID());
	}

	public MeetingSpace findMeetingSpaceByName(String name) {
		for(MeetingSpace ms : meetingSpaces) {
			if(ms.getName() == name || name.equals(ms.getName())) {
				return ms;
			}
		}
		return null;	
	}
	
	public boolean isMeetingSpaceInDB(String name) {
		return (findMeetingSpaceByName(name) != null) ? true : false;
	}
}