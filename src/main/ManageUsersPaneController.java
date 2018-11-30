package main;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ManageUsersPaneController implements Initializable {
	private DataModel model;
	
	@FXML
	private VBox root;

	@FXML
	private TextField nameTextField;
	
	@FXML
	private TextField addUserEmailTextField;
	
	@FXML
	private ChoiceBox<UserType> userTypeChoiceBox;
	
	@FXML
	private TextField deleteUserEmailTextField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		root.setVisible(false);
		userTypeChoiceBox.getItems().addAll(UserType.values());
	}
	
	public void initModel(DataModel model) {
		if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        } else {
        	this.model = model;
        }
	}
	
	public void addUser() {
		try {
			model.insertUser(addUserEmailTextField.getText(), nameTextField.getText(), userTypeChoiceBox.getValue());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteUser() {
		
	}
	
	public void show() {
		root.setVisible(true);
	}
	
	public void hide() {
		root.setVisible(false);
	}
}
