package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class MeetingsDAOSpringBoot implements MeetingsDAO {
	
	private static final String  MEETING_NAME = "meetingName";
	private static final String DATE_TIME = "dateTime";
	private static final String DURATION = "duration";
	private static final String LOCATION = "location";

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

}
