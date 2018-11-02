package main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

//this class is dumb
public class Controller implements Initializable {
	
	@FXML
	private TreeView<Location> tree;
	
	@FXML
	private Pane basis;
	
	private TreeItem<Location> currentFloor;
	
	private Rectangle tempRect;
	private double x1, y1, x2, y2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		
		// Set up tree
		initializeTree();
	}
	
	private void initializeTree() {
		Floor floor1 = new Floor(new Image("main/floorplan1.jpg"));
		floor1.setName("Floor 1");
		tree.setRoot(new TreeItem<Location>(null));
		tree.getRoot().getChildren().add(new TreeItem<Location>(floor1));
		tree.getRoot().setExpanded(true);
		tree.setShowRoot(false);
		tree.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onItemSelected());
	}
	
	public void onItemSelected() {
		System.out.println("Item selected");
		if (isFloor(tree)) { // Item is a floor
			currentFloor = tree.getSelectionModel().getSelectedItem();
			basis.getChildren().clear();
			basis.getChildren().add(currentFloor.getValue().getPane());
		} else { // Item is a meeting space
			
		}
	}
	
	public void onNewMeetingSpace() {
		currentFloor.setExpanded(true);
		currentFloor.getValue().getPane().setOnMousePressed(e -> {
            x1 = e.getX();
            y1 = e.getY();
            tempRect = new Rectangle(x1, y1, 0, 0);
            tempRect.setFill(new Color(0.75, 0, 0.75, 0.25));
            tempRect.setStroke(Color.BLACK);
            basis.getChildren().add(tempRect);
        });
		currentFloor.getValue().getPane().setOnMouseDragged(e -> {
            x2 = e.getX();
            y2 = e.getY();
            if (x2 < x1) {
                tempRect.setX(x2);
                tempRect.setWidth(x1 - x2);
            } else {
                tempRect.setWidth(x2 - x1);
            }
            if (y2 < y1) {
                tempRect.setY(y2);
                tempRect.setHeight(y1 - y2);
            } else {
                tempRect.setHeight(y2 - y1);
            }
        });
		currentFloor.getValue().getPane().setOnMouseReleased(e -> {
			currentFloor.getValue().getPane().setOnMousePressed(null);
			currentFloor.getValue().getPane().setOnMouseDragged(null);
			currentFloor.getValue().getPane().setOnMouseReleased(null);
			MeetingSpace m = new MeetingSpace(tempRect);
			tempRect = null;
			currentFloor.getValue().addMeetingSpace(m);
			currentFloor.getChildren().add(new TreeItem<Location>(m));
			System.out.println("New meeting location created");
		});
	}
	
	public void onNewFloor() {
		System.out.println("Creating new floor ...");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Image");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		String s = null;
		try {
			s = fileChooser.showOpenDialog(basis.getScene().getWindow()).toURI().toURL().toString();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		TreeItem<Location> t = new TreeItem<Location>(new Floor(new Image(s)));
		tree.getRoot().getChildren().add(t);
	}
	
	private boolean isFloor(TreeView<Location> t) { // Checks if the currently selected item in the param tree is a Floor
		return t.getSelectionModel().getSelectedItem().getValue().getRectangle() == null;
	}
}