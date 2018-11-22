package main;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONException;

public interface MeetingsDAO {

	public void insertMeeting(Meeting newMeeting);
	public void insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID);
	public void deleteMeeting(String name) throws JSONException;
	public ArrayList<Meeting> getAllMeetings() throws JSONException;
	public Meeting getMeetingByMeetingSpaceID(int meetingSpaceID) throws JSONException;//'locationId in table'
	public Meeting getMeetingByStartTime(Timestamp startTime) throws JSONException;	
	public String getMeetingByName(String name);
}
