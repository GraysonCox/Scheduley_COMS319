package main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class TreeController implements Initializable {
	
	private DataModel model;

	@FXML
	private TreeView<String> tree;

	private HashMap<TreeItem<String>, Floor> floorHashMap = new HashMap<TreeItem<String>, Floor>(); // Maps each Floor
																									// to a TreeItem
	private HashMap<TreeItem<String>, MeetingSpace> meetingSpaceHashMap = new HashMap<TreeItem<String>, MeetingSpace>(); // Maps
	
	@FXML
	public Button treeButton;
	
	@FXML
	private ContextMenu adminContextMenu;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tree.setRoot(new TreeItem<String>(null));
		tree.getRoot().setExpanded(true);
		tree.setShowRoot(false);
		tree.setEditable(false);
		tree.setCellFactory(TextFieldTreeCell.forTreeView());
	}
	
	public void initModel(DataModel model) {
		if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        } else {
        	this.model = model;
        }
		
		if (this.model.getCurrentUser().getUserType() != UserType.ADMIN) {
			tree.setContextMenu(null);
		}
		
        tree.getSelectionModel().selectedItemProperty().addListener((ob, oldItem, newItem) -> {
        	if (newItem != null) {
        		if (isFloor(newItem)) {
        			this.model.setCurrentFloor(floorHashMap.get(newItem));
        		} else {
        			this.model.setCurrentMeetingSpace(meetingSpaceHashMap.get(newItem));
        		}
        	}
        });
        this.model.getMeetingSpaceList().addListener((ListChangeListener<MeetingSpace>)c -> update());
        this.model.getFloorList().addListener((ListChangeListener<Floor>)c -> update());
        
        TreeItem<String> newFloorTreeItem, newMeetingSpaceTreeItem;
        for (Floor f : model.getFloorList()) {
        	newFloorTreeItem = new TreeItem<String>("");
        	newFloorTreeItem.valueProperty().bindBidirectional(f.nameProperty());
        	floorHashMap.put(newFloorTreeItem, f);
        	tree.getRoot().getChildren().add(newFloorTreeItem);
        	newFloorTreeItem.setExpanded(true);
        	for (MeetingSpace m : f.getMeetingSpaces() ) {
        		newMeetingSpaceTreeItem = new TreeItem<String>("");
        		newMeetingSpaceTreeItem.valueProperty().bindBidirectional(m.nameProperty());
        		meetingSpaceHashMap.put(newMeetingSpaceTreeItem, m);
        		newFloorTreeItem.getChildren().add(newMeetingSpaceTreeItem);
        	}
        }
	}
	
	
	
	/**
	 * Reloads the entire DataModel every time an item is added or deleted.
	 * TODO: make this more efficient.
	 * 
	 */
	public void update() {
		tree.getRoot().getChildren().clear();
		meetingSpaceHashMap.clear();
		floorHashMap.clear();
		
		TreeItem<String> newFloorTreeItem, newMeetingSpaceTreeItem;
        for (Floor f : model.getFloorList()) {
        	newFloorTreeItem = new TreeItem<String>("");
        	newFloorTreeItem.valueProperty().bindBidirectional(f.nameProperty());
        	floorHashMap.put(newFloorTreeItem, f);
        	tree.getRoot().getChildren().add(newFloorTreeItem);
        	newFloorTreeItem.setExpanded(true);
        	for (MeetingSpace m : f.getMeetingSpaces() ) {
        		newMeetingSpaceTreeItem = new TreeItem<String>("");
        		newMeetingSpaceTreeItem.valueProperty().bindBidirectional(m.nameProperty());
        		meetingSpaceHashMap.put(newMeetingSpaceTreeItem, m);
        		newFloorTreeItem.getChildren().add(newMeetingSpaceTreeItem);
        	}
        }
	}
	
	@FXML
	public void onDelete() {
		TreeItem<String> t = tree.getSelectionModel().getSelectedItem();
		if (t != null) {
    		if (isFloor(t)) {
    			this.model.removeFloor(floorHashMap.get(t));
    		} else {
    			this.model.removeMeetingSpace(meetingSpaceHashMap.get(t));
    		}
    	}
		update();
	}
	
	private boolean isFloor(TreeItem<String> t) {
		return t.getParent() == tree.getRoot();
	}

}