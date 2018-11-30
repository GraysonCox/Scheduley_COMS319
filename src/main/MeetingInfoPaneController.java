package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class MeetingInfoPaneController implements Initializable {
	private DataModel model;
	

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
	}

}
