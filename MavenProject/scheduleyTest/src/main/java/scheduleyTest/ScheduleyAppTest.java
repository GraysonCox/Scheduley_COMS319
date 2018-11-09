package scheduleyTest;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;
import org.testfx.*;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import junit.framework.Assert;

public class ScheduleyAppTest extends TestFXBase{	
	
	final String TREE_VIEW = "#tree";
	final String TREE_CONTEXT = "#treeContextMenu";
	
	@Before
	public void setUpClass() throws Exception {
		ApplicationTest.launch(ScheduleyApp.class);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Layout.fxml"))));
		primaryStage.setTitle("Scheduley");
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	/*
	@Test 
	public void ensureFloorText() {
		
		//when:
		clickOn(TREE_VIEW);
		sleep(1000);
		moveTo(TREE_CONTEXT);
		sleep(1000); //TODO delete later (for understanding )
		clickOn(TREE_CONTEXT);
		sleep(1000);
		
		//then:
		verifyThat(TREE_CONTEXT, (TextArea textArea)-> {
			String text = textArea.getText();
			return text.contains("Room 1");	
			
		});	
	}
	*/
	
	@Test
	public void ensureTreeViewIsPopulated() {
		verifyThat(TREE_CONTEXT, NodeMatchers.isNotNull());
		clickOn(TREE_VIEW);
		sleep(1000); //TODO delete all sleep timers
		moveTo(TREE_CONTEXT);
		sleep(1000);
		clickOn(TREE_CONTEXT);
		
		ListView floorView = find(TREE_CONTEXT);
		
		WaitForAsyncUtils.waitForFxEvents();
		
		assertEquals(2, floorView.getItems().size());
		
	}
	
	@Test
	public void test() {
		//clickOn("Floor 1");
		
	}
	
}
