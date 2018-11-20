package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ScheduleController implements Initializable {
	private DataModel model;
	
	@FXML
	private Label meetingSpaceNameLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void initModel(DataModel model) {
		if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
        
        this.model.currentMeetingSpaceProperty().addListener((ob, oldMeetingSpace, newMeetingSpace) -> { 
        	meetingSpaceNameLabel.textProperty().unbind();
        	meetingSpaceNameLabel.textProperty().bind(newMeetingSpace.nameProperty());
        });
	}
	

}
