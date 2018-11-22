package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MeetingsDAOSpringBoot implements MeetingsDAO {
	
	private static final String  MEETING_NAME = "meetingName";
	private static final String DATE_TIME = "dateTime";
	private static final String DURATION = "duration";
	private static final String LOCATION = "location";
	
	public MeetingsDAOSpringBoot() {}

	@Override
	public void insertMeeting(Meeting newMeeting) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject jsnObj = new JSONObject();
			jsnObj.put(MEETING_NAME, newMeeting.getName());
			jsnObj.put(DATE_TIME, newMeeting.getStartTime());
			jsnObj.put(DURATION, newMeeting.getDuration());
			jsnObj.put(LOCATION, newMeeting.getMeetingSpaceID());
			
			String url = "http://10.26.43.141:8080/meetings/create";
			
			/*
			 // GET
		      HttpResponse<String> response = client.send(
		          HttpRequest
		              .newBuilder(new URI("http://www.foo.com/"))
		              .headers("Foo", "foovalue", "Bar", "barvalue")
		              .GET()
		              .build(),
		          BodyHandler.asString()
		      );
		      int statusCode = response.statusCode();
		      String body = response.body();
		      
		
			HttpClient client = HttpClient.newHttpClient();
		    HttpResponse<JSONObject> response = client.send
		    StringEntity params =new StringEntity("details={\"name\":\"myname\",\"age\":\"20\"} ");
		    request.addHeader("content-type", "application/x-www-form-urlencoded");
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
	*/
			
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		
	}
	

	@Override
	public void insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMeeting(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Meeting> getAllMeetings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeetingByMeetingSpaceID(int meetingSpaceID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeetingByStartTime(Timestamp startTime) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override //TODO Change this to return Meeting
	public String getMeetingByName(String name) {
		String result = null;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost putRequest = new HttpPost("http://10.26.43.141:8080/meetings/search/name");
//			Timestamp datetime = new Timestamp(2018,11,20,13,0,0,0);
			JSONObject json = new JSONObject();
			json.put(MEETING_NAME, name);
//			json.put("dateTime", datetime);
//			json.put("duration", 120);
//			json.put("location", 2);
		    String jsonString = json.toString();
		    //System.out.println(jsonString);
			putRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			putRequest.setEntity((HttpEntity) new StringEntity(jsonString));
			
			CloseableHttpResponse httpResponse = httpClient.execute(putRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			result = res.toString();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
