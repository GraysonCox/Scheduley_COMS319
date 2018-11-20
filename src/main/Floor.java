package main;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.MeetingSpace;

public class Floor extends Pane {
	public StringProperty name;
	public final ImageView image; //public for testing purposes TODO: change to private
	private ArrayList<MeetingSpace> meetingSpaces;
	
	public Floor(String name, Image image) {
		this.name = new SimpleStringProperty(name);
		this.image = new ImageView(image);
		meetingSpaces = new ArrayList<MeetingSpace>();
		getChildren().add(this.image);
	}
	
	public void addMeetingSpace(MeetingSpace m) {
		m.setFloor(this);
		meetingSpaces.add(m);
	}
	
	public void removeMeetingSpace(MeetingSpace m) {
		meetingSpaces.remove(m);
	}
	
	public MeetingSpace[] getMeetingSpaces() {
		return meetingSpaces.toArray(new MeetingSpace[0]);
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
}
