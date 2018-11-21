package main;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONException;

public interface MeetingsDAO {

	void insertMeeting(Meeting newMeeting);
	void insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID);
	void deleteMeeting(String name) throws JSONException;
	ArrayList<Meeting> getAllMeetings() throws JSONException;
	Meeting getMeetingByMeetingSpaceID(int meetingSpaceID) throws JSONException;//'locationId in table'
	Meeting getMeetingByStartTime(Timestamp startTime) throws JSONException;	
}
