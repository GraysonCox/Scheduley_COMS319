package main;

import java.io.BufferedReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

public class MeetingsDAOSpringBoot implements MeetingsDAO {
	
	private static final String  MEETING_NAME = "meetingName";
	private static final String DATE_TIME = "dateTime";
	private static final String DURATION = "duration";
	private static final String MEETING_SPACE_ID = "location";
	
	private static final String BASE_URL = "http://proj-319-080.misc.iastate.edu:8080";
	private static final String MEDIA_TYPE = "application/json";
	
	public MeetingsDAOSpringBoot() {}

	@Override
	public int insertMeeting(Meeting newMeeting) {
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
			int status = Integer.valueOf(obj.get("status").toString());
			httpResponse.close();
			httpClient.close();
			return status;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
	}
	

	@Override
	public int insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID) {
		Meeting newMeeting = new Meeting(name, startTime, duration, meetingSpaceID);
		return insertMeeting(newMeeting);

	}

	@Override
	public int deleteMeeting(String name) {
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
			int status = Integer.valueOf(obj.get("status").toString());
			httpResponse.close();
			httpClient.close();
			return status;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return -1;
	}

	@Override
	public JSONArray getAllMeetings() {
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
		return result;
	}

	@Override
	public JSONArray getMeetingsByMeetingSpaceID(int meetingSpaceID) {
		JSONArray allMeetings = getAllMeetings();
		JSONArray result = new JSONArray();
		for(int i = 0; i < allMeetings.size(); i++) {
			if(((Meeting) allMeetings.get(i)).getMeetingSpaceID() == meetingSpaceID) {
				result.add(allMeetings.get(i));
			}
		}
		return result;
	}

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

	public JSONArray getAllMeetingsByWeek(int meetingSpaceID, LocalDateTime targetTime) {
		JSONArray tempArrList =  getMeetingsByMeetingSpaceID(meetingSpaceID);
		JSONArray result = new JSONArray();
		for(int i = 0; i < tempArrList.size(); i++) {
			Meeting currMeeting = (Meeting) tempArrList.get(i);
			LocalDateTime currTime = currMeeting.getStartTime().toLocalDateTime();
			if(currTime.getMonth().equals(targetTime.getMonth())) {
				if(currTime.getDayOfMonth() == targetTime.getDayOfMonth()){
					result.add(i);
				}
			}
		}
		return result;
	}

	@Override
	public Meeting[] getAllMeetings2() {
		JSONArray tempArr = getAllMeetings();
		Meeting[] result = JSONArrayToMeetingArray(tempArr);
		return result;
	}

	@Override
	public Meeting[] getMeetingsByMeetingSpaceID2(int meetingSpaceID) {
		JSONArray tempArr = getMeetingsByMeetingSpaceID(meetingSpaceID);
		Meeting[] result = JSONArrayToMeetingArray(tempArr);
		return result;
	}

	@Override
	public Meeting[] getAllMeetingsByWeek2(int meetingSpaceID, LocalDateTime targetTime) {
		JSONArray tempArr = getAllMeetingsByWeek(meetingSpaceID, targetTime);
		Meeting[] result = JSONArrayToMeetingArray(tempArr);
		return result;
	}
	
	private Meeting[] JSONArrayToMeetingArray(JSONArray inputArr) {
		Meeting[] result = new Meeting[inputArr.size()];
		for(int i = 0; i < inputArr.size(); i++) {
			Meeting obj = (Meeting) inputArr.get(i);
			//Timestamp ts = parseTime((String) obj.get(DATE_TIME));
			//Meeting temp = new Meeting((String)obj.get(MEETING_NAME), ts, Integer.valueOf(obj.get(DURATION).toString()), Integer.valueOf(obj.get(MEETING_SPACE_ID).toString()));
			result[i] = obj;
		}
		return result;
	}
}
