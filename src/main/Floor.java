package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Floor extends Pane {
	public StringProperty name;
	public final ImageView image; //public for testing purposes TODO: change to private
	private Group rectGroup;
	
	public Floor(Image image) {
		name = new SimpleStringProperty();
		this.image = new ImageView(image);
		rectGroup = new Group();
		getChildren().addAll(this.image, rectGroup);
	}
	
	public void addMeetingSpace(MeetingSpace m) {
		rectGroup.getChildren().add(m);
	}
	
	public void removeMeetingSpace(MeetingSpace m) {
		rectGroup.getChildren().remove(m);
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