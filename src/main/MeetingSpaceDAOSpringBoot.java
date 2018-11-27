package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MeetingSpaceDAOSpringBoot implements MeetingSpaceDAO {

	public MeetingSpaceDAOSpringBoot() {}
	
	@Override
	public MeetingSpace[] getAllMeetingSpace() {
		MeetingSpace[] arr = null;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet("http://proj-319-080.misc.iastate.edu:8080/meetings/search/name");
			getRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			
			CloseableHttpResponse httpResponse = httpClient.execute(getRequest);
			String res = EntityUtils.toString(httpResponse.getEntity());
			JSONParser parser = new JSONParser();
			JSONArray result = (JSONArray)((Object) parser.parse(res));
			arr = new MeetingSpace[result.size()];
			
			for(int i=0; i<result.size(); i++) {
				JSONObject temp = (JSONObject) result.get(i);
				MeetingSpace ms = new MeetingSpace((String)temp.get("meetingSpaceName"),(double)temp.get("x"),(double)temp.get("y"),(double)temp.get("width"),(double)temp.get("height"),(int)temp.get("floorID"));
				arr[i] = ms;
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
		return arr;
	}

	@Override
	public MeetingSpace[] getMeetingSpaceByFloor(int floorID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addMeetingSpace(MeetingSpace meetingSpace) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMeetingSpace(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
