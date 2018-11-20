package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MeetingSpace extends Rectangle {
	private StringProperty name;
	private Floor floor;
	
	public MeetingSpace(String name, double x, double y, double w, double h) {
		super(x, y, w, h);
		this.name = new SimpleStringProperty(name);
		setFill(new Color(0, 0, 0.75, 0.25));
        setStroke(Color.BLACK);
        floor = null;
        setOnMouseEntered(e -> setFill(new Color(0.75, 0, 0, 0.25)));
        setOnMouseExited(e -> setFill(new Color(0, 0, 0.75, 0.25)));
	}
	
	public Floor getFloor() {
		return floor;
	}
	
	public void setFloor(Floor parent) {
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
}
