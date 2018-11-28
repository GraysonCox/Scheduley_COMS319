package main;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FloorDAOSpringBootTest {
	
	public static FloorDAOSpringBoot fdaosp;

	@BeforeAll
	public static void setUp() throws Exception {
		fdaosp = new FloorDAOSpringBoot();
	}

	@Test
	void getAllFloors_Name() {
		Floor[] expected = new Floor[3];
		expected[0] = new Floor("floor 13", "main/MrScheduley.png");
		expected[1] = new Floor("floor 29", "main/MrScheduley.png");
		expected[2] = new Floor("floor 69", "main/MrScheduley.png");

		Floor[] actual = fdaosp.getAllFloors();
		for(int i=0; i<actual.length; i++) 
			Assert.assertTrue(expected[i].getName().equalsIgnoreCase(actual[i].getName()));
	}
	
	@Test
	void getAllFloors_URL() {
		Floor[] expected = new Floor[3];
		expected[0] = new Floor("floor 13", "main/MrScheduley.png");
		expected[1] = new Floor("floor 29", "main/MrScheduley.png");
		expected[2] = new Floor("floor 69", "main/MrScheduley.png");
		
		Floor[] actual = fdaosp.getAllFloors();
		for(int i=0; i<actual.length; i++) 
			Assert.assertTrue(expected[i].getImageURL().equalsIgnoreCase(actual[i].getImageURL()));
	}

	@Test
	void getFloorByIDTest_Name() {
		Floor f = fdaosp.getFloorByID(672);
		Assert.assertTrue(f.getName().equalsIgnoreCase("floor 29"));
	}
	
	@Test
	void getFloorByIDTest_URL() {

		Floor f = fdaosp.getFloorByID(672);
		Assert.assertTrue(f.getImageURL().equalsIgnoreCase("myfiles/thisfloor/lava"));
	}
	
}
