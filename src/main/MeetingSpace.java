package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MeetingSpace {
	private final Rectangle rect;
	
	public MeetingSpace(Rectangle rect) {
		this.rect = rect;
		this.rect.setFill(new Color(0, 0, 0.75, 0.25));
        this.rect.setStroke(Color.BLACK);
	}
	
	public Rectangle getRectangle() {
		return this.rect;
	}
}