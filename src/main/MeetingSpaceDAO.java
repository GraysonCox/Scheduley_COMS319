package main;

public interface MeetingSpaceDAO {
	
	public MeetingSpace[] getAllMeetingSpace();
	public MeetingSpace[] getMeetingSpaceByFloor(int floorID);
	public int addMeetingSpace(MeetingSpace meetingSpace);
	public int deleteMeetingSpace(MeetingSpace toDelete);
	public boolean isMeetingSpaceInDB(String name);
}
