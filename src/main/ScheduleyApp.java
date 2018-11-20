package main;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class ScheduleyApp extends Application {
	private boolean loginSuccessful;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Scheduley");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);
        pwBox.setOnKeyPressed( event ->{
            if(event.getCode() == KeyCode.ENTER) {
        		btn.fire();
        	}
        });
		btn.setOnAction(e -> {
			UserDAOMySQL dataSource = new UserDAOMySQL(new ServerConnection());
			loginSuccessful = dataSource.verifyUser(userTextField.getText(), pwBox.getText());
			if (loginSuccessful) {
				try {
					UserProfile currentUser = dataSource.findUser(userTextField.getText());
					
					BorderPane root = new BorderPane();
			        FXMLLoader treeLoader = new FXMLLoader(getClass().getResource("Tree.fxml"));
			        root.setLeft(treeLoader.load());
			        TreeController treeController = treeLoader.getController();

			        FXMLLoader basisLoader = new FXMLLoader(getClass().getResource("Basis.fxml"));
			        root.setCenter(basisLoader.load());
			        BasisController basisController = basisLoader.getController();
			        
			        FXMLLoader scheduleLoader = new FXMLLoader(getClass().getResource("Schedule.fxml"));
			        root.setRight(scheduleLoader.load());
			        ScheduleController scheduleController = scheduleLoader.getController();

			        DataModel model = new DataModel(currentUser);
			        treeController.initModel(model);
			        basisController.initModel(model);
			        scheduleController.initModel(model);
			        
			        primaryStage.setTitle("Scheduley");
			        primaryStage.setScene(new Scene(root));
			        primaryStage.setMaximized(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				primaryStage.setMaximized(true);
			} else {
				actionTarget.setFill(Color.FIREBRICK);
				actionTarget.setText("Invalid login");
			}
		});
		
		primaryStage.setScene(scene);
		primaryStage.setMaximized(false);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
