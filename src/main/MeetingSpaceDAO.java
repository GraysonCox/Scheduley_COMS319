package main;

import org.json.simple.JSONArray;

public interface MeetingSpaceDAO {
	
	public MeetingSpace[] getAllMeetingSpace();
	public MeetingSpace[] getMeetingSpaceByFloor(int floorID);
	public int addMeetingSpace(MeetingSpace meetingSpace);
	public int deleteMeetingSpace(int id);
}
