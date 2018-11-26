package main;

import java.sql.Timestamp;

public class Meeting {

	private String name;
	private Timestamp startTime;
	private int duration;
	private int meetingSpaceID;
	
	public Meeting(String name, Timestamp startTime, int duration, int meetingSpaceID) {
		this.name = name;
		this.startTime = startTime;
		this.duration = duration;
		this.meetingSpaceID = meetingSpaceID;
	}
	
	public Meeting(String name) {
		this(name, null, 0, 0);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setMeetingSpaceID(int id) {
		this.meetingSpaceID = id;
	}
	
	public String getName() {
		return name;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getMeetingSpaceID() {
		return meetingSpaceID;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof Meeting && this.name.equals(((Meeting) other).getName())){
			return true;
		}
		return false;
	}
}
