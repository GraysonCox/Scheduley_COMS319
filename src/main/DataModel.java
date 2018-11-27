package main;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableMapValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import main.Floor;
import main.MeetingSpace;

public class DataModel {
	UserProfile currentUser;
	
	private final ObservableList<MeetingSpace> meetingSpaceList = FXCollections.observableArrayList();
	private final ObservableMap<Integer, Floor> floorMap = FXCollections.observableHashMap();

	private final ObjectProperty<MeetingSpace> currentMeetingSpace = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Floor> currentFloor = new SimpleObjectProperty<>(null);
	
	public DataModel(UserProfile user) {
		currentUser = user;
		loadData();
		System.out.println("" + floorMap + meetingSpaceList); // For debugging
	}
	
	public void loadData() { // TODO : Make this load data from database instead of making dummy values
		Floor newFloor;
		MeetingSpace m1, m2, m3, m4;
		newFloor = new Floor("Floor 2", "main/floorplan1.jpg");
		m1 = new MeetingSpace("Meetings/Creative Space", 88, 40, 213, 113, 1);
		m2 = new MeetingSpace("Conference/Lab Room", 88, 153, 139, 119, 1);
		m3 = new MeetingSpace("Break Area", 307.0, 47.0, 61.0, 101.0, 1);
		m4 = new MeetingSpace("Retreat", 471.0, 283.0, 115.0, 114.0, 1);
		meetingSpaceList.addAll(m1, m2, m3, m4);
		floorMap.put(new Integer(1), newFloor);
	}
	
	/**
	 * Adds MeetingSpace to current floor
	 * @param m
	 */
	public void addMeetingSpace(MeetingSpace m) {
		currentFloor.get().addMeetingSpace(m);
		meetingSpaceList.add(m);
	}
	
	public void removeMeetingSpace(MeetingSpace m) {
		meetingSpaceList.remove(m);
	}
	
	public void addFloor(Floor f) {
		// 
	}
	
	public void removeFloor(int ID, Floor f) {
		floorMap.remove(ID, f);
	}
	
	public ObjectProperty<MeetingSpace> currentMeetingSpaceProperty() {
        return currentMeetingSpace;
    }

    public final MeetingSpace getCurrentMeetingSpace() {
        return currentMeetingSpace.get();
    }

    public final void setCurrentMeetingSpace(MeetingSpace meetingSpace) {
    	currentMeetingSpace.set(meetingSpace);
    }

    public ObservableList<MeetingSpace> getMeetingSpaceList() {
        return meetingSpaceList;
    }
	
	public ObjectProperty<Floor> currentFloorProperty() {
        return currentFloor;
    }

    public final Floor getCurrentFloor() {
        return currentFloor.get();
    }

    public final void setCurrentFloor(Floor floor) {
    	currentFloor.set(floor);
    }

    public ObservableMap<Integer, Floor> getFloorMap() {
        return floorMap;
    }
}
