package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private TextField meetingNameTextField;
	
	@FXML
	private TextArea descriptionTextArea;
	
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
}
