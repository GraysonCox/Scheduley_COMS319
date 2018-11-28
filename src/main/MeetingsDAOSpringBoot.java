package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

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

/**
 * I interact with the DB through the JSONArray methods, because it is so much easier with them, and then
 * the Meeting[] methods use them to get a JSONArray and convert them to a Meeting[]. 
 * 
 * The user will use the Meeting[] methods, they currently do not have access to the JSONArray methods.
 * @author watis
 *
 */
public class MeetingsDAOSpringBoot implements MeetingsDAO {
	private JSONArray meetings;
	
	private static final String  MEETING_NAME = "meetingName";
	private static final String DATE_TIME = "dateTime";
	private static final String DURATION = "duration";
	private static final String MEETING_SPACE_ID = "location";
	
	private static final String BASE_URL = "http://proj-319-080.misc.iastate.edu:8080";
	private static final String MEDIA_TYPE = "application/json";
	
	public MeetingsDAOSpringBoot() {
		loadMeetings();
	}
	
	@SuppressWarnings("unchecked")
	public void loadMeetings() {
		JSONArray result = new JSONArray();
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet putRequest = new HttpGet(BASE_URL + "/meetings/search/all");
			putRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			
			CloseableHttpResponse httpResponse = httpClient.execute(putRequest);

			InputStream responseContent = httpResponse.getEntity().getContent();
			JSONParser jsonParser = new JSONParser();
			JSONArray tempArr = (JSONArray)jsonParser.parse(new InputStreamReader(responseContent, "UTF-8"));
			
			for(int i = 0; i < tempArr.size(); i ++) {
				JSONObject obj = (JSONObject) tempArr.get(i);
				Timestamp ts = parseTime((String) obj.get(DATE_TIME));
				Meeting temp = new Meeting((String)obj.get(MEETING_NAME), ts, Integer.valueOf(obj.get(DURATION).toString()), Integer.valueOf(obj.get(MEETING_SPACE_ID).toString()));
				result.add(temp);
			}
			httpResponse.close();
			httpClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		meetings = result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertMeeting(Meeting newMeeting) {
		int returnCode = -1;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPut putRequest = new HttpPut(BASE_URL + "/meetings/create");
			JSONObject jsnObj = new JSONObject();
			jsnObj.put(MEETING_NAME, newMeeting.getName());
			char[] c = newMeeting.getStartTime().toString().toCharArray();
			char[] d = Arrays.copyOfRange(c, 0, 19);
			d[10] = 'T';
			String inputTime = new String(d);
			jsnObj.put(DATE_TIME, inputTime);
			jsnObj.put(DURATION, newMeeting.getDuration());
			jsnObj.put(MEETING_SPACE_ID, newMeeting.getMeetingSpaceID());
	
		    String jsonString = jsnObj.toString();
			putRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			putRequest.setEntity(new StringEntity(jsonString));
			
			CloseableHttpResponse httpResponse = httpClient.execute(putRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(res);
			returnCode = Integer.valueOf(obj.get("status").toString());
			httpResponse.close();
			httpClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		loadMeetings(); //update instance variable to match DB
		return returnCode;
	}
	

	@Override
	public int insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID) {
		Meeting newMeeting = new Meeting(name, startTime, duration, meetingSpaceID);
		return insertMeeting(newMeeting);

	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteMeeting(String name) {
		int returnCode = -1;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(BASE_URL + "/meetings/delete");
			JSONObject jsnObj = new JSONObject();
			jsnObj.put(MEETING_NAME, name);

		    String jsonString = jsnObj.toString();
			postRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			postRequest.setEntity(new StringEntity(jsonString));
			
			CloseableHttpResponse httpResponse = httpClient.execute(postRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(res);
			returnCode = Integer.valueOf(obj.get("status").toString());
			httpResponse.close();
			httpClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		loadMeetings(); //update instance variable to match DB
		return returnCode;
	}

	private JSONArray getAllMeetings_JSONArray() {
		return meetings;
	}

	@SuppressWarnings("unchecked")
	private JSONArray getMeetingsByMeetingsSpaceID_JSONArray(int meetingSpaceID) {
		JSONArray allMeetings = getAllMeetings_JSONArray();
		JSONArray result = new JSONArray();
		for(int i = 0; i < allMeetings.size(); i++) {
			if(((Meeting) allMeetings.get(i)).getMeetingSpaceID() == meetingSpaceID) {
				result.add(allMeetings.get(i));
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray getAllMeetingsByWeek_JSONArray(int meetingSpaceID, LocalDateTime targetTime) {
		JSONArray tempArrList =  getMeetingsByMeetingsSpaceID_JSONArray(meetingSpaceID);
		JSONArray result = new JSONArray();
		for(int i = 0; i < tempArrList.size(); i++) {
			Meeting currMeeting = (Meeting) tempArrList.get(i);
			LocalDateTime currTime = currMeeting.getStartTime().toLocalDateTime();
			if(currTime.getMonth().equals(targetTime.getMonth())) {
				if(currTime.getDayOfMonth() == targetTime.getDayOfMonth()){
					result.add(currMeeting);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Meeting getMeetingByName(String name) {
		Meeting result = null;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(BASE_URL + "/meetings/search/name");
			JSONObject json = new JSONObject();
			json.put(MEETING_NAME, name);
		    String jsonString = json.toString();
			postRequest.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
			postRequest.setEntity(new StringEntity(jsonString));
			
			CloseableHttpResponse httpResponse = httpClient.execute(postRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(res);
			Timestamp ts = parseTime((String) obj.get(DATE_TIME));
			result = new Meeting((String)obj.get(MEETING_NAME), ts, Integer.valueOf(obj.get(DURATION).toString()), Integer.valueOf(obj.get(MEETING_SPACE_ID).toString()));
			httpResponse.close();
			httpClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 	
		return result;
	}
	
	private Timestamp parseTime(String time) {
		char[] c = time.toCharArray();
		char[] d = Arrays.copyOfRange(c, 0, 19);
		d[10] = ' ';
		return Timestamp.valueOf(String.copyValueOf(d));
	}

	@Override
	public Meeting[] getAllMeetings() {
		JSONArray tempArr = getAllMeetings_JSONArray();
		Meeting[] result = JSONArrayToMeetingArray(tempArr);
		return result;
	}

	@Override
	public Meeting[] getMeetingsByMeetingSpaceID(int meetingSpaceID) {
		JSONArray tempArr = getMeetingsByMeetingsSpaceID_JSONArray(meetingSpaceID);
		Meeting[] result = JSONArrayToMeetingArray(tempArr);
		return result;
	}

	/**
	 *This Method will only check by month and day.
	 */
	@Override
	public Meeting[] getAllMeetingsByWeek(int meetingSpaceID, LocalDateTime targetTime) {
		JSONArray tempArr = getAllMeetingsByWeek_JSONArray(meetingSpaceID, targetTime);
		Meeting[] result = JSONArrayToMeetingArray(tempArr);
		return result;
	}
	
	private Meeting[] JSONArrayToMeetingArray(JSONArray inputArr) {
		Meeting[] result = new Meeting[inputArr.size()];
		for(int i = 0; i < inputArr.size(); i++) {
			Meeting obj = (Meeting) inputArr.get(i);
			result[i] = obj;
		}
		return result;
	}
}
