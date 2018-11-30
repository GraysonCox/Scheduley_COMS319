package main;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
	private Spinner<Integer> durationSpinner;

	@FXML
	private ChoiceBox<Floor> floorChoiceBox;

	@FXML
	private ChoiceBox<MeetingSpace> meetingSpaceChoiceBox;

	@FXML
	private TextField meetingNameTextField;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private TextField emailAddressTextField;

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
		durationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3600, 15));
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
	}

	public void show() {
		root.setVisible(true);
	}

	public void hide() {
		root.setVisible(false);
	}
	
	public UserProfile[] getInvitees() {
		ArrayList<String> arr = new ArrayList<String>();
		String s = emailAddressTextField.getText();
		s.replace(" ", "");
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == ',') {
				arr.add(s.substring(i + 1));
				s = s.substring(0, i);
				System.out.println(s);
			} else if (i == 0) {
				arr.add(s);
			}
		}
		ArrayList<UserProfile> users = new ArrayList<UserProfile>();
		UserProfile up;
		for (String email : arr) {
			up = model.getUserByEmail(email);
			if (up != null) {
				users.add(up);
			}
		}
		System.out.println(users);
		return users.toArray(new UserProfile[0]);
	}

	public void onSubmit() {
		ZoneId z = ZoneId.systemDefault();
		LocalDate ld = datePicker.getValue();
		ZonedDateTime startTime = ld.atStartOfDay(z).plusHours(hourSpinner.getValue())
				.plusMinutes(minuteSpinner.getValue());
		if (ampmChoiceBox.getValue() == "PM") {
			startTime = startTime.plusHours(12);
		}
		Timestamp startTimestamp = new Timestamp(startTime.toEpochSecond() * 1000);
		Meeting newMeeting = new Meeting(meetingNameTextField.getText(), startTimestamp,
				durationSpinner.getValue(), meetingSpaceChoiceBox.getValue().getUniqueID());
		model.addMeeting(newMeeting);

		EmailSender emailSender = new EmailSender("", "");
		emailSender.setSubject("[Scheduley App] Meeting Notice: " + meetingNameTextField.getText());

		for (UserProfile up : getInvitees()) { // change arr
			EmailHTMLBody body = new EmailHTMLBody(up.getName(), newMeeting.getName(), descriptionTextArea.getText(),
					datePicker.getValue().toString(), hourSpinner.getValue() + ": " + minuteSpinner.getValue() + " " + ampmChoiceBox.getValue(),
					meetingSpaceChoiceBox.getValue().getName() + ", " + floorChoiceBox.getValue().getName());
			emailSender.setBody(body.getHTMLBody());
			if (!up.getEmail().equals("admin")) {
				emailSender.send(up.getEmail());
			}
		}
	}
}
