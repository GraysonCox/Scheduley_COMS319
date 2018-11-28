package main;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Meeting extends AbstractRectangleIcon {

	private String name;
	private Timestamp startTime;
	private int duration;
	private int meetingSpaceID;
	
	public Meeting() {
		super(0, 0, 0, 0);
	}
	
	public Meeting(String name, Timestamp startTime, int duration, int meetingSpaceID) {
		super(0, 0, 80, duration);
		LocalDateTime t = startTime.toLocalDateTime();
		setX(40 + 80 * ((t.getDayOfWeek().ordinal() + 1) % 7));
		setY(15 + (60 * t.getHour()) + t.getMinute());
		this.name = name;
		this.startTime = startTime;
		this.duration = duration;
		this.meetingSpaceID = meetingSpaceID; // TODO: Add this meeting to its MeetingSpace
	}
	
	public Meeting(String name) {
		this(name, new Timestamp(System.currentTimeMillis()), 0, 0);
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
