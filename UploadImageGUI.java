package project;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploadImageGUI extends Application{
	
	public static void main(String args[]) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		GridPane gridPane = new GridPane();
	
		gridPane.setMinSize(500, 500);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.BASELINE_CENTER);
		
		Text inputLabel = new Text("Select floorplan image to upload");
		TextField input = new TextField();
		
		FileChooser fileChooser = new FileChooser();
		
		Button browseButton = new Button("...");
		Button submitButton = new Button("Submit");
		Button clearButton = new Button("Clear");
		browseButton.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(stage);
			if(file != null) {
				//TODO, data validation.
				input.setText(file.getAbsolutePath());
			}
		});
		
		clearButton.setOnAction(e->{
			input.clear();
		});
		
		//TODO submit set on Action should go to a new screen,
		//TODO save image to something
		
		gridPane.add(inputLabel, 0, 0);
		gridPane.add(input, 1, 0);
		gridPane.add(browseButton, 2, 0);
		gridPane.add(submitButton, 1, 2);
		gridPane.add(clearButton, 2, 2);
		
		Scene scene = new Scene(gridPane);
		stage.setTitle("Add A Floor Plan");
		stage.setScene(scene);
		stage.show();
		
		
	}
}
