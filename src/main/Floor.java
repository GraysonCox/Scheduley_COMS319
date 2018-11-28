package main;

import java.io.InputStream;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Floor extends Pane {
	public StringProperty name;
	public final ImageView image; //public for testing purposes TODO: change to private
	private ArrayList<MeetingSpace> meetingSpaces;
	private String imageURL;
    private int floorID;

	
	public Floor(String name, String URL) {
		this.name = new SimpleStringProperty(name);
		this.imageURL = URL;
		meetingSpaces = new ArrayList<MeetingSpace>();
		image = null;

		//NOT DONE, nothing after this works
		/*
		ClassLoader k = getClass().getClassLoader();
		String imageLocation = k.getResource(URL).getFile();
		Image tempImg = new Image(imageLocation);
		this.image = new ImageView(tempImg);
		getChildren().add(this.image);
		//
		 */
	}
	
	public void addMeetingSpace(MeetingSpace m) {
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

