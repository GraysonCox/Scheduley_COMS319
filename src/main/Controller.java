package main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller implements Initializable {
	
	@FXML
	private TreeView<String> tree;
	
	private HashMap<TreeItem<String>, Floor> floorHashMap; // Maps each Floor to a TreeItem
	
	@FXML
	private Pane basis;
	
	private TreeItem<String> currentFloorTreeItem;
	
	private Rectangle tempRect;
	private double x1, y1, x2, y2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		// Set up tree
		floorHashMap = new HashMap<TreeItem<String>, Floor>();
		initializeTree();
	}
	
	private void initializeTree() {
		tree.setRoot(new TreeItem<String>(null));
		tree.getRoot().setExpanded(true);
		tree.setShowRoot(false);
		tree.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> onItemSelected(newVal));
		tree.setEditable(false);
		tree.setCellFactory(TextFieldTreeCell.forTreeView());
		
		setUpDefaultTree();
	}
	
	/**
	 * This just adds dummy locations to the tree.
	 * It won't be in the final project.
	 */
	private void setUpDefaultTree() {
		TreeItem<String> tempTreeItem = new TreeItem<String>("Floor 1");
		Floor tempFloor = new Floor(new Image("main/floorplan1.jpg"));
		floorHashMap.put(tempTreeItem, tempFloor);
		tree.getRoot().getChildren().add(tempTreeItem);
		tempFloor.addMeetingSpace(new MeetingSpace(new Rectangle(88,40,213,113)));
		tempFloor.addMeetingSpace(new MeetingSpace(new Rectangle(88,153,139,119)));
		tempTreeItem.getChildren().addAll(new TreeItem<String>("Room 1"), new TreeItem<String>("Room 2"));
	}
	
	public void onItemSelected(TreeItem<String> target) {
		if (isFloor(target)) {
			currentFloorTreeItem = target;
			basis.getChildren().clear();
			basis.getChildren().add(floorHashMap.get(target));
		} else {
			
		}
	}
	
	@FXML
	public void onNewMeetingSpace() {
		Floor currentFloor = floorHashMap.get(currentFloorTreeItem);
		TreeItem<String> newTreeItem = new TreeItem<String>("New Meeting Space");
		currentFloor.setOnMousePressed(e -> {
            x1 = e.getX();
            y1 = e.getY();
            tempRect = new Rectangle(x1, y1, 0, 0);
            tempRect.setFill(new Color(0.75, 0, 0.75, 0.25));
            tempRect.setStroke(Color.BLACK);
            basis.getChildren().add(tempRect);
        });
		currentFloor.setOnMouseDragged(e -> {
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
		currentFloor.setOnMouseReleased(e -> {
			currentFloor.setOnMousePressed(null);
			currentFloor.setOnMouseDragged(null);
			currentFloor.setOnMouseReleased(null);
			MeetingSpace newMeetingSpace = new MeetingSpace(tempRect);
			tempRect = null;
			currentFloor.addMeetingSpace(newMeetingSpace);
			currentFloorTreeItem.getChildren().add(newTreeItem);
			tree.getSelectionModel().select(newTreeItem);
		});
	}
	
	@FXML
	public void onNewFloor() {
		FileChooser fileChooser = new FileChooser();
		TreeItem<String> newTreeItem = new TreeItem<String>("New Floor");
		fileChooser.setTitle("Choose Image");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		String s = null;
		try {
			s = fileChooser.showOpenDialog(basis.getScene().getWindow()).toURI().toURL().toString(); // Converts URL of image file to a string
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		floorHashMap.put(newTreeItem, new Floor(new Image(s)));
		tree.getRoot().getChildren().add(newTreeItem);
		tree.getSelectionModel().select(newTreeItem);
	}
	
	@FXML
	public void onRename() {
		tree.setEditable(true);
		tree.edit(tree.getSelectionModel().getSelectedItem());
		tree.setEditable(false);
	}
	
	@FXML
	public void onDelete() {
		TreeItem<String> currentTreeItem = tree.getSelectionModel().getSelectedItem(); // Reference to currently selected item in tree
		currentTreeItem.getParent().getChildren().remove(currentTreeItem);
	}
	
	private boolean isFloor(TreeItem<String> item) {
		return tree.getRoot().equals(item.getParent());
	}
}