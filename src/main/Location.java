package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class Location {
	private StringProperty name;
	
	public Location(String name) {
		this.name = new SimpleStringProperty(name);
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