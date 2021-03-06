package main;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Floor extends Pane {
	public StringProperty name;
	public ImageView image; //public for testing purposes TODO: change to private
	
	private ArrayList<MeetingSpace> meetingSpaces;
	private String imageURL;
    private int floorID;

	
	public Floor(String name, String URL) {
		this.name = new SimpleStringProperty(name);
		this.imageURL = URL;
		meetingSpaces = new ArrayList<MeetingSpace>();
	}
	
	public void initImage() {
		image = new ImageView(new Image(imageURL));
		getChildren().add(this.image);
	}
	public void addMeetingSpace(MeetingSpace m) {
		meetingSpaces.add(m);
	}
	
	public void removeMeetingSpace(MeetingSpace m) {
		meetingSpaces.remove(m);
	}
	
	public void clearMeetingSpaces() {
		meetingSpaces.clear();
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
	
	public String getImageURL() {
        return imageURL;
    }
    
    public void setFloorID(int id) {
        this.floorID = id;
    }
    
    public int getFloorID() {
        return floorID;
    }
	
	public String toString() {
		return name.get();
	}
	
}

