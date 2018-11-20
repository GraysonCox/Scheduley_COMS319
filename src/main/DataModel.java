package main;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import main.Floor;
import main.MeetingSpace;

public class DataModel {
	UserProfile currentUser;
	
	private final ObservableList<MeetingSpace> meetingSpaceList = FXCollections.observableArrayList();
	private final ObservableList<Floor> floorList = FXCollections.observableArrayList();
	
	private final ObjectProperty<MeetingSpace> currentMeetingSpace = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Floor> currentFloor = new SimpleObjectProperty<>(null);
	
	public DataModel(UserProfile user) {
		currentUser = user;
		loadData();
		System.out.println("" + floorList + meetingSpaceList); // For debugging
	}
	
	public void loadData() { // TODO : Make this load data from database instead of making dummy values
		Floor newFloor = null;
		MeetingSpace m1 = null, m2 = null;
		for (int i = 5; i >= 1; i--) {
			newFloor = new Floor("Floor " + i, new Image("main/floorplan1.jpg"));
			m1 = new MeetingSpace("Room " + i + "0" + 1, 88, 40, 213, 113);
			m2 = new MeetingSpace("Room " + i + "0" + 2, 88, 153, 139, 119);
			newFloor.addMeetingSpace(m1);
			newFloor.addMeetingSpace(m2);
			
			meetingSpaceList.addAll(m1, m2);
			floorList.add(newFloor);
		}
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
		floorList.add(f);
	}
	
	public void removeFloor(Floor f) {
		floorList.remove(f);
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

    public ObservableList<Floor> getFloorList() {
        return floorList;
    }
}
