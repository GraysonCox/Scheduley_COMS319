package project;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Floorplan {

	private String name;
	private Image basis;
	ArrayList<Location> designatedLocations = new ArrayList<>();
	
	public Floorplan() {
		name = "";
	}
	
	public Floorplan(Image baseImage, String name) {
		this.basis = baseImage;
		this.name = name;
	}
	
	public Floorplan(Image baseImage, String name, ArrayList<Location> initial) {
		this(baseImage, name);
		designatedLocations = initial;
	}
	
	void addLocation(Location loc) {
		//check to make sure location does not clash with any other location.
		designatedLocations.add(loc);
	}

	
}
