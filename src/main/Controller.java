package main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller implements Initializable {
	
	@FXML
	private TreeView<String> tree;
	
	@FXML
	private Label userTypeLabel;
	
	private HashMap<TreeItem<String>, Floor> floorHashMap; // Maps each Floor to a TreeItem
	private HashMap<TreeItem<String>, MeetingSpace> meetingSpaceHashMap;
	
	@FXML
	private Pane basis;
	
	private TreeItem<String> currentFloorTreeItem;
	
	private Rectangle tempRect;
	private double x1, y1, x2, y2;
	
	private UserProfile currentUser;
	
	@FXML
	private TextField email;
	
	@FXML
	private TextField password;
	
	@FXML
	private VBox adminTools;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Set up tree
		floorHashMap = new HashMap<TreeItem<String>, Floor>();
		meetingSpaceHashMap = new HashMap<TreeItem<String>, MeetingSpace>();
		initializeTree();
	}
	
	public void setUser(UserProfile currentUser) {
		this.currentUser = currentUser;
		String inputLabel = (currentUser.getName() != null) ? currentUser.getName() : "John Doe";
		userTypeLabel.setText(inputLabel);
		if(currentUser.getUserType() != UserType.ADMIN) {
			adminTools = null;
		}
	}
	
	public UserProfile getUser() {
		return currentUser;
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
	
	@FXML
	public void clearTextFields() {
		email.clear();
		password.clear();
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
		MeetingSpace m1 = new MeetingSpace(new Rectangle(88,40,213,113), tempFloor);
		tempFloor.addMeetingSpace(m1);
		TreeItem<String> m1Item = new TreeItem<String>("Room 101");
		meetingSpaceHashMap.put(m1Item, m1);
		MeetingSpace m2 = new MeetingSpace(new Rectangle(88,153,139,119), tempFloor);
		tempFloor.addMeetingSpace(m2);
		TreeItem<String> m2Item = new TreeItem<String>("Room 102");
		meetingSpaceHashMap.put(m2Item, m2);
		tempTreeItem.getChildren().addAll(m1Item, m2Item);
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
		TreeItem<String> newTreeItem = new TreeItem<String>("New meeting space");
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
			MeetingSpace newMeetingSpace = new MeetingSpace(tempRect, currentFloor);
			newMeetingSpace.setFloor(currentFloor);
			basis.getChildren().remove(tempRect);
			tempRect = null;
			newMeetingSpace.nameProperty().bindBidirectional(newTreeItem.valueProperty());
			currentFloor.addMeetingSpace(newMeetingSpace);
			meetingSpaceHashMap.put(newTreeItem, newMeetingSpace);
			currentFloorTreeItem.getChildren().add(newTreeItem);
			tree.getSelectionModel().select(newTreeItem);
		});
	}
	
	@FXML
	public void onNewFloor() {
		FileChooser fileChooser = new FileChooser();
		TreeItem<String> newTreeItem = new TreeItem<String>("New floor");
		fileChooser.setTitle("Choose Image");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		String s = null;
		try {
			s = fileChooser.showOpenDialog(basis.getScene().getWindow()).toURI().toURL().toString(); // Converts URL of image file to a string
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		if (s != null) {
			Floor newFloor = new Floor(new Image(s));
			newFloor.nameProperty().bindBidirectional(newTreeItem.valueProperty());
			floorHashMap.put(newTreeItem, newFloor);
			tree.getRoot().getChildren().add(newTreeItem);
			tree.getSelectionModel().select(newTreeItem);
		}
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
		if (isFloor(currentTreeItem)) {
			currentTreeItem.getParent().getChildren().remove(currentTreeItem);
			floorHashMap.remove(currentTreeItem);
		} else {
			currentTreeItem.getParent().getChildren().remove(currentTreeItem);
			meetingSpaceHashMap.get(currentTreeItem).removeFromFloor();
			meetingSpaceHashMap.remove(currentTreeItem);
		}
	}
	
	private boolean isFloor(TreeItem<String> item) {
		return tree.getRoot().equals(item.getParent());
	}
	
}