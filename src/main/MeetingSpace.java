package main;

import org.json.simple.JSONArray;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tooltip;

public class MeetingSpace extends AbstractRectangleIcon {
	private StringProperty name;
	private Floor floor; // Reference to this MEetingSpace's Floor for easy retrieval
	private int floor_id;
	private int uniqueID;
	private JSONArray meetingsAtThisLocation;
	
	private ObservableList<Meeting> meetingSpaceList = FXCollections.observableArrayList();
	
	public MeetingSpace(String name, double x, double y, double w, double h, int floor_id) {
		super(x, y, w, h);
		this.name = new SimpleStringProperty(name);
		this.floor_id = floor_id;
		this.floor = null;
		Tooltip.install(this, new Tooltip(name));
	}
	
	public void addMeeting(Meeting m) {
		meetingSpaceList.add(m);
	}
	
	public void removeMeeting(Meeting m) {
		meetingSpaceList.remove(m);
	}
	
	public ObservableList<Meeting> getMeetingList() {
		return meetingSpaceList;
	}
	
	public Floor getFloor() {
		return floor;
	}
	
	public int getFloorID() {
		return floor_id;
	}
	
	public void setFloor(Floor parent) {
		parent.addMeetingSpace(this);
		floor = parent;
	}
	
	public void removeFromFloor() {
		floor.removeMeetingSpace(this);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);;
	}
	
	public String toString() {
		return name.get();
	}
	
	public void setUniqueID(int id) {
		this.uniqueID = id;
	}
	
	public int getUniqueID() {
		return uniqueID;
	}
	
	public JSONArray getAllMeetings() {
		//TODO
		return meetingsAtThisLocation;
	}
	
	public JSONArray getMeetingsOnThisDate() {
		//TODO
		return null;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof MeetingSpace && this.uniqueID == ((MeetingSpace) other).getUniqueID()){
			return true;
		}
		return false;
	}
}
