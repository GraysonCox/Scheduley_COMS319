package main;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FloorDAOSpringBootTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void getAllFloors_Name() {
		Floor[] expected = new Floor[3];
		expected[0] = new Floor("floor 13", "asdfasdf/asdfasdf/asdfasdf/");
		expected[1] = new Floor("floor 29", "myfiles/thisfloor/lava");
		expected[2] = new Floor("floor 69", "myfiles/ilove69");
		FloorDAOSpringBoot fdaosp = new FloorDAOSpringBoot();
		Floor[] actual = fdaosp.getAllFloors();
		for(int i=0; i<actual.length; i++) 
			Assert.assertTrue(expected[i].getName().equalsIgnoreCase(actual[i].getName()));
	}
	
	@Test
	void getAllFloors_URL() {
		Floor[] expected = new Floor[3];
		expected[0] = new Floor("floor 13", "asdfasdf/asdfasdf/asdfasdf/");
		expected[1] = new Floor("floor 29", "myfiles/thisfloor/lava");
		expected[2] = new Floor("floor 69", "myfiles/ilove69");
		FloorDAOSpringBoot fdaosp = new FloorDAOSpringBoot();
		Floor[] actual = fdaosp.getAllFloors();
		for(int i=0; i<actual.length; i++) 
			Assert.assertTrue(expected[i].getImageURL().equalsIgnoreCase(actual[i].getImageURL()));
	}

	@Test
	void getFloorByIDTest_Name() {
		FloorDAOSpringBoot fdaosp = new FloorDAOSpringBoot();
		Floor f = fdaosp.getFloorByID(672);
		Assert.assertTrue(f.getName().equalsIgnoreCase("floor 29"));
	}
	
	@Test
	void getFloorByIDTest_URL() {
		FloorDAOSpringBoot fdaosp = new FloorDAOSpringBoot();
		Floor f = fdaosp.getFloorByID(672);
		Assert.assertTrue(f.getImageURL().equalsIgnoreCase("myfiles/thisfloor/lava"));
	}
	
}
