package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuBarController implements Initializable {
	private DataModel model;

	@FXML
	public MenuItem newMeetingSpaceButton, newFloorButton;

	@FXML
	private Menu adminTools;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void initModel(DataModel model) {
		if (this.model != null) {
			throw new IllegalStateException("Model can only be initialized once");
		} else {
			this.model = model;
		}

		switch (this.model.getCurrentUser().getUserType()) { // Set up the tools for the appropriate UserType
		case ADMIN:

		case MANAGER:
			adminTools.setDisable(true);
		case EMPLOYEE:
			
		case OTHER:
			
		case UNKNOWN:

		}
	}

}
