package main;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ScheduleController implements Initializable {
private DataModel model;
	
	@FXML
	public Button scheduleButton;

	@FXML
	private VBox root;

	@FXML
	private Label meetingSpaceNameLabel;

	@FXML
	private Label floorLabel;

	@FXML
	private Label sundayLabel, mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel;

	@FXML
	private Pane schedulePane;

	@FXML
	private Group rectangleGroup;

	@FXML
	private DatePicker datePicker;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void initModel(DataModel model) {
		if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        } else {
        	this.model = model;
        }
        
        EventHandler<MouseEvent> onMouseClickedCurrentMeetingSpace = new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			root.setVisible(true);
    		}
        };
        
        this.model.currentMeetingSpaceProperty().addListener((ob, oldMeetingSpace, newMeetingSpace) -> {
        	try {
        		oldMeetingSpace.removeEventFilter(MouseEvent.MOUSE_CLICKED, onMouseClickedCurrentMeetingSpace);
        	} catch (NullPointerException exception) {} // Because first MeetingSpace to be clicked will not have an EventFilter

        	root.setVisible(true);
        	meetingSpaceNameLabel.textProperty().unbind();
        	meetingSpaceNameLabel.textProperty().bind(newMeetingSpace.nameProperty());
        	floorLabel.textProperty().unbind();
        	floorLabel.textProperty().bind(newMeetingSpace.getFloor().nameProperty());
        	rectangleGroup.getChildren().clear();
        	/** 
        	 * TODO: Make this method retrieve meetings from the actual database and 
        	 * show only the meetings for the currently selected week.
        	 */
        	rectangleGroup.getChildren().addAll(newMeetingSpace.getMeetingList());
        	newMeetingSpace.addEventFilter(MouseEvent.MOUSE_CLICKED, onMouseClickedCurrentMeetingSpace);
        });
        
        sundayLabel.setText(model.getSundayDisplayed().toString());
    	mondayLabel.setText(model.getSundayDisplayed().plusDays(1).toString());
    	tuesdayLabel.setText(model.getSundayDisplayed().plusDays(2).toString());
    	wednesdayLabel.setText(model.getSundayDisplayed().plusDays(3).toString());
    	thursdayLabel.setText(model.getSundayDisplayed().plusDays(4).toString());
    	fridayLabel.setText(model.getSundayDisplayed().plusDays(5).toString());
    	saturdayLabel.setText(model.getSundayDisplayed().plusDays(6).toString());
        this.model.sundayDisplayedProperty().addListener((ob, oldDate, newDate) -> {
        	sundayLabel.setText(newDate.toString());
        	mondayLabel.setText(newDate.plusDays(1).toString());
        	tuesdayLabel.setText(newDate.plusDays(2).toString());
        	wednesdayLabel.setText(newDate.plusDays(3).toString());
        	thursdayLabel.setText(newDate.plusDays(4).toString());
        	fridayLabel.setText(newDate.plusDays(5).toString());
        	saturdayLabel.setText(newDate.plusDays(6).toString());
        });
        
        datePicker.setOnAction(e -> {
        	LocalDate chosenDate = datePicker.getValue();
        	this.model.setSundayDisplayed(chosenDate.minusDays((chosenDate.getDayOfWeek().ordinal() + 1) % 7));
        	chosenDate = null;
        });
	}

	public void onPreviousWeek() {
		model.setSundayDisplayed(model.getSundayDisplayed().minusDays(7));
	}

	public void onNextWeek() {
		model.setSundayDisplayed(model.getSundayDisplayed().plusDays(7));
	}

	public void onClose() {
		root.setVisible(false);
	}
}
