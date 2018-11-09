package scheduleyTest;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Floor extends Pane {
	public final ImageView image; //public for testing purposes TODO: change to private
	private Group rectGroup;
	
	public Floor(Image image) {
		this.image = new ImageView(image);
		rectGroup = new Group();
		getChildren().addAll(this.image, rectGroup);
	}
	
	public void addMeetingSpace(MeetingSpace m) {
		rectGroup.getChildren().add(m.getRectangle());
	}
}