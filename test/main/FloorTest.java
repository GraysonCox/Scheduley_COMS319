package main;

import org.junit.Test;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FloorTest {
	
	//Testing whether an image has been uploaded successfully
	@Test
	public void testFloorImageUpload()
	{
		Floor tester = new Floor("Basement", "resources/images/floorplan1.jpg"); //error TODO
		ImageView i = tester.image; //change from public to private in floor.java to test
		Image image = i.getImage();
		
		System.out.println(!image.isError());
		
	}

	/*
	@Test
	public void testAddMeetingSpace() 
		
	}
	*/
}
