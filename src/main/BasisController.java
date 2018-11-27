package main;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BasisController implements Initializable {
	
	private DataModel model;
	
	@FXML
	private Pane pane;
	
	private Rectangle tempRect;
	private double x1, y1, x2, y2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem newMeetingSpaceMenuItem = new MenuItem("New Meeting Space");
		newMeetingSpaceMenuItem.setOnAction(e -> onNewMeetingSpace());
		contextMenu.getItems().add(newMeetingSpaceMenuItem);
		pane.setOnContextMenuRequested(e -> {
			contextMenu.show(pane.getScene().getWindow(), e.getX(), e.getY());
		});
	}
	
	public void initModel(DataModel m) {
		if (model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        model = m;
        model.currentMeetingSpaceProperty().addListener((ob, oldFloor, newFloor) -> {
        });
        model.currentFloorProperty().addListener((ob, oldFloor, newFloor) -> {
        	pane.getChildren().clear();
        	pane.getChildren().add(newFloor);
        	pane.getChildren().addAll(newFloor.getMeetingSpaces());
        });
        model.getMeetingSpaceList().addListener((ListChangeListener<MeetingSpace>)c -> update());
        
        for (MeetingSpace ms : model.getMeetingSpaceList()) {
        	ms.setOnMouseClicked(e -> {
        		model.setCurrentMeetingSpace((MeetingSpace)e.getSource());
        	});
        }
	}
	
	public void update() {
		pane.getChildren().clear();
		pane.getChildren().add(model.getCurrentFloor());
    	pane.getChildren().addAll(model.getCurrentFloor().getMeetingSpaces());
		for (MeetingSpace ms : model.getMeetingSpaceList()) {
        	ms.setOnMouseClicked(e -> {
        		model.setCurrentMeetingSpace((MeetingSpace)e.getSource());
        	});
        }
	}
	
	public void onNewMeetingSpace() {
		pane.setOnMousePressed(e -> {
            x1 = e.getX();
            y1 = e.getY();
            tempRect = new Rectangle(x1, y1, 0, 0);
            tempRect.setFill(new Color(0.75, 0, 0.75, 0.25));
            tempRect.setStroke(Color.BLACK);
            pane.getChildren().add(tempRect);
        });
		pane.setOnMouseDragged(e -> {
            x2 = e.getX();
            y2 = e.getY();
            if (x2 < x1) {
                tempRect.setX(x2);
                tempRect.setWidth(x1 - x2);
            } else {
                tempRect.setWidth(x2 - x1);
            }
            if (y2 < y1) {
                tempRect.setY(y2);
                tempRect.setHeight(y1 - y2);
            } else {
                tempRect.setHeight(y2 - y1);
            }
        });
		pane.setOnMouseReleased(e -> {
			pane.setOnMousePressed(null);
			pane.setOnMouseDragged(null);
			pane.setOnMouseReleased(null);
			MeetingSpace newMeetingSpace = new MeetingSpace(
					"New meeting space",
					tempRect.getX(),
					tempRect.getY(),
					tempRect.getWidth(),
					tempRect.getHeight(),
					1);
			pane.getChildren().remove(tempRect);
			tempRect = null;
			model.addMeetingSpace(newMeetingSpace);
		});
	}
}
