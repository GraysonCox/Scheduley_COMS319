package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class AbstractRectangleIcon extends Rectangle {
	public AbstractRectangleIcon(double x, double y, double w, double h) {
		super(x, y, w, h);
		setFill(new Color(0, 0, 0.75, 0.25));
        setStroke(Color.BLACK);
        setOnMouseEntered(e -> setFill(new Color(0.75, 0, 0, 0.25)));
        setOnMouseExited(e -> setFill(new Color(0, 0, 0.75, 0.25)));
	}
}
