package main;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MeetingSpace extends Location {
	private final Rectangle rect;
	
	public MeetingSpace(Rectangle rect) {
		super("New Meeting Space");
		this.rect = rect;
		this.rect.setFill(new Color(0, 0, 0.75, 0.25));
        this.rect.setStroke(Color.BLACK);
	}
	
	@Override
	public Pane getPane() {
		return null;
	}
	
	public Rectangle getRectangle() {
		return this.rect;
	}
	
	@Override
	public void addMeetingSpace(MeetingSpace m) {}
}