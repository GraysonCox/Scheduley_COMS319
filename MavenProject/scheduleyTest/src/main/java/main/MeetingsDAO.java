package main;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface MeetingsDAO {

	public int insertMeeting(Meeting newMeeting);
	public int insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID);
	public int deleteMeeting(String name);	
	public Meeting getMeetingByName(String name);
	public Meeting[] getAllMeetings();
	public Meeting[] getMeetingsByMeetingSpaceID(int meetingSpaceID);
	public Meeting[] getAllMeetingsByWeek(int meetingSpaceID, LocalDateTime targetTime);
}