package main;

import java.sql.Timestamp;

import org.json.simple.JSONArray;

public interface MeetingsDAO {

	public int insertMeeting(Meeting newMeeting);
	public int insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID);
	public int deleteMeeting(String name);
	public JSONArray getAllMeetings();
	public JSONArray getMeetingsByMeetingSpaceID(int meetingSpaceID);//'locationId in table'
	public Meeting getMeetingByStartTime(Timestamp startTime);	
	public Meeting getMeetingByName(String name);
}