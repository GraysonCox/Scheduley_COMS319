package main;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.json.simple.JSONArray;

public interface MeetingsDAO {

	public int insertMeeting(Meeting newMeeting);
	public int insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID);
	public int deleteMeeting(String name);
	public JSONArray getAllMeetings();
	public JSONArray getMeetingsByMeetingSpaceID(int meetingSpaceID);//'locationId in table'
	public Meeting getMeetingByName(String name);
	public JSONArray getAllMeetingsByWeek(int meetingSpaceID, LocalDateTime targetTime);
	
	public Meeting[] getAllMeetings2();
	public Meeting[] getMeetingsByMeetingSpaceID2(int meetingSpaceID);
	public Meeting[] getAllMeetingsByWeek2(int meetingSpaceID, LocalDateTime targetTime);
}