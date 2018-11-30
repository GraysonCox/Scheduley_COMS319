package main;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScheduleyApp extends Application {
	DataModel model;

	VBox root = new VBox();

	AnchorPane basisRoot = new AnchorPane();
	Pane menuBar, basis, tree, schedule, newMeetingForm;

	MenuBarController menuBarController;
	BasisController basisController;
	TreeController treeController;
	ScheduleController scheduleController;
	NewMeetingFormController newMeetingFormController;

	TranslateTransition openTree, closeTree, openSchedule, closeSchedule;

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
		pwBox.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				btn.fire();
			}
		});
		btn.setOnAction(e -> {
			DAOFactory userDB = DAOFactory.getDAOFactory(DAOFactory.JDBC); //NEW Implementing the DAO properly
			UserDAO dataSource = userDB.getUserDAO(); //NEW 
			loginSuccessful = dataSource.verifyUser(userTextField.getText(), pwBox.getText()); //ORIGINAL
			if (loginSuccessful) {
				try {
					VBox.setVgrow(basisRoot, Priority.ALWAYS);

					FXMLLoader menuBarLoader = new FXMLLoader(getClass().getResource("MenuBar.fxml"));
					menuBar = menuBarLoader.load();
					menuBarController = menuBarLoader.getController();

					FXMLLoader basisLoader = new FXMLLoader(getClass().getResource("Basis.fxml"));
					basis = basisLoader.load();
					basisController = basisLoader.getController();

					FXMLLoader treeLoader = new FXMLLoader(getClass().getResource("Tree.fxml"));
					tree = treeLoader.load();
					treeController = treeLoader.getController();

					FXMLLoader scheduleLoader = new FXMLLoader(getClass().getResource("Schedule.fxml"));
					schedule = scheduleLoader.load();
					scheduleController = scheduleLoader.getController();
					
					FXMLLoader newMeetingFormLoader = new FXMLLoader(getClass().getResource("NewMeetingForm.fxml"));
					newMeetingForm = newMeetingFormLoader.load();
					newMeetingFormController = newMeetingFormLoader.getController();

					AnchorPane.setTopAnchor(basis, 0.0);
					AnchorPane.setBottomAnchor(basis, 0.0);
					AnchorPane.setLeftAnchor(basis, 30.0);
					AnchorPane.setTopAnchor(tree, 0.0);
					AnchorPane.setLeftAnchor(tree, -170.0);
					AnchorPane.setBottomAnchor(tree, 0.0);
					AnchorPane.setLeftAnchor(schedule, 30.0);
					AnchorPane.setRightAnchor(schedule, 0.0);
					AnchorPane.setBottomAnchor(schedule, -440.0);
					AnchorPane.setTopAnchor(newMeetingForm, 0.0);
					AnchorPane.setLeftAnchor(newMeetingForm, 30.0);

					basisRoot.getChildren().addAll(basis, tree, schedule, newMeetingForm);
					root.getChildren().addAll(menuBar, basisRoot);

					openTree = new TranslateTransition(new Duration(350), basisRoot);
					openTree.setToX(0);
					closeTree = new TranslateTransition(new Duration(350), basisRoot);
					treeController.treeButton.setOnAction(event -> doTreeTransition());

					openSchedule = new TranslateTransition(new Duration(350), schedule);
					openSchedule.setToY(0);
					closeSchedule = new TranslateTransition(new Duration(350), schedule);
					scheduleController.scheduleButton.setOnAction(event -> doScheduleTransition());
					
					menuBarController.newMeetingSpaceButton.setOnAction(event -> basisController.createMeetingSpace());
					menuBarController.newFloorButton.setOnAction(event -> this.createFloor());
					menuBarController.newMeetingButton.setOnAction(event -> newMeetingFormController.show());

					model = new DataModel(dataSource.findUser(userTextField.getText()));
					menuBarController.initModel(model);
					treeController.initModel(model);
					basisController.initModel(model);
					scheduleController.initModel(model);
					newMeetingFormController.initModel(model);
					

					primaryStage.setTitle("Scheduley");
					primaryStage.setScene(new Scene(root));
					primaryStage.setMaximized(true);
					primaryStage.show();
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

	@SuppressWarnings("unused")
	private void initAdminTools() {

	}

	private void createFloor() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Image");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		String s = null;
		try {
			s = fileChooser.showOpenDialog(root.getScene().getWindow()).toURI().toURL().toString();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		if (s != null) {
			model.addFloor(new Floor("New floor", s));
		}
	}

	private void doTreeTransition() {
		if (basisRoot.getTranslateX() != 0) {
			openTree.play();
		} else {
			closeTree.setToX(170);
			closeTree.play();
		}
	}

	private void doScheduleTransition() {
		if (schedule.getTranslateY() != 0) {
			openSchedule.play();
		} else {
			closeSchedule.setToY(-400);
			closeSchedule.play();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
