package edu.iastate.graysonc.scheduley;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Floor extends Location {
	private final Pane pane;
	private final ImageView image;
	private Group rectGroup;
	
	public Floor(Image image) {
		super("New Floor");
		this.image = new ImageView(image);
		rectGroup = new Group();
		pane = new Pane();
		pane.getChildren().addAll(this.image, rectGroup);
	}
	
	public void addMeetingSpace(MeetingSpace m) {
		rectGroup.getChildren().add(m.getRectangle());
	}
	
	public Pane getPane() {
		System.out.println("Getting pane");
		return this.pane;
	}
	
	@Override
	public Rectangle getRectangle() {
		return null;
	}
}
