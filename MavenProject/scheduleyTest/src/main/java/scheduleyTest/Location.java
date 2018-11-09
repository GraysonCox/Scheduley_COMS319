package scheduleyTest;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class Location {
	private String name;
	
	public Location(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	abstract Pane getPane();
	
	abstract Rectangle getRectangle();
	
	abstract void addMeetingSpace(MeetingSpace m);
	
	public String toString() {
		return this.name;
	}
}