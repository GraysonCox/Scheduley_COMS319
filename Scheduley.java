package edu.iastate.graysonc.scheduley;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Scheduley extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("scheduley.fxml"))));
		primaryStage.setTitle("Scheduley");
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

}
