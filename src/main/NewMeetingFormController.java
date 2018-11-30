package main;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NewMeetingFormController implements Initializable {
	private DataModel model;
	
	@FXML
	private VBox root;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private Spinner<Integer> hourSpinner, minuteSpinner;
	
	@FXML
	private ChoiceBox<String> ampmChoiceBox;
	
	@FXML
	private Slider durationSlider;
	
	@FXML
	private ChoiceBox<Floor> floorChoiceBox;
	
	@FXML
	private ChoiceBox<MeetingSpace> meetingSpaceChoiceBox;
	
	@FXML
	private MenuButton inviteesMenu;
	
	@FXML
	private TextField meetingNameTextField;
	
	@FXML
	private TextArea descriptionTextArea;
	
	@FXML
	private TextArea emailAddresses;
	
	@FXML
	private Button submitButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		root.setVisible(false);
		hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1));
		hourSpinner.getValueFactory().setWrapAround(true);
		minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
		minuteSpinner.getValueFactory().setWrapAround(true);
		ampmChoiceBox.getItems().addAll("AM", "PM");
		root.setOnMouseClicked(e -> hide());
	}
	
	public void initModel(DataModel model) {
		if (this.model != null) {
			throw new IllegalStateException("Model can only be initialized once");
		} else {
			this.model = model;
		}
		
		floorChoiceBox.getItems().addAll(this.model.getFloorList());
		floorChoiceBox.getSelectionModel().selectedItemProperty().addListener((ob, oldFloor, newFloor) -> {
			meetingSpaceChoiceBox.getItems().clear();
			meetingSpaceChoiceBox.getItems().addAll(newFloor.getMeetingSpaces());
		});
		
		for (UserProfile u : this.model.getAllUsers()) {
			inviteesMenu.getItems().add(new CheckMenuItem(u.getName()));
		}
	}
	
	public void show() {
		root.setVisible(true);
	}
	
	public void hide() {
		root.setVisible(false);
	}
	
	public void onSubmit() {
		ZoneId z = ZoneId.systemDefault();
		LocalDate ld = datePicker.getValue();
		ZonedDateTime startTime = ld.atStartOfDay(z).plusHours(hourSpinner.getValue()).plusMinutes(minuteSpinner.getValue());
		if (ampmChoiceBox.getValue() == "PM") {
			startTime = startTime.plusHours(12);
		}
		Timestamp startTimestamp = new Timestamp(startTime.toEpochSecond()*1000);
		Meeting newMeeting = new Meeting(meetingNameTextField.getText(), startTimestamp, (int)durationSlider.getValue(), meetingSpaceChoiceBox.getValue().getUniqueID());
		model.addMeeting(newMeeting);
		
		EmailSender emailSender = new EmailSender("","");
		emailSender.setSubject("[Scheduley App] Meeting Notice: " + meetingNameTextField.getText());
		
		
		UserProfile arr[] = model.getAllUsers();
		
		
		for(UserProfile up : arr) { // change arr
			emailSender.setBody(System.lineSeparator()
					+ "Dear " + up.getName() + "," + System.lineSeparator() + System.lineSeparator()
					+ "A meeting has been scheduled." + System.lineSeparator() + System.lineSeparator()
					+ "You are receiving this email because a meeting has been scheduled with you as an attendee." + System.lineSeparator()
					+ "Details for the meeting are included below." + System.lineSeparator() + System.lineSeparator()
					+ "Meeting: " + meetingNameTextField.getText() + System.lineSeparator()
					+ "Description: " + descriptionTextArea.getText() + System.lineSeparator()
					+ "Date: " + datePicker.getValue() + System.lineSeparator()
					+ "Time: " + hourSpinner.getValue() + ":" + minuteSpinner.getValue() + ampmChoiceBox.getValue() + System.lineSeparator()
					+ "Location: " + meetingSpaceChoiceBox.getValue().getName() + ", " + floorChoiceBox.getValue().getName() + System.lineSeparator() + System.lineSeparator()
					+ "Contact your manager if you have any questions." + System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
					+ "NOTE: This is an automated message. DO NOT reply to this email."
					);
			emailSender.send(up.getEmail());
		}
	}
}
