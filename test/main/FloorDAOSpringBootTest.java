package main;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FloorDAOSpringBootTest {
	
	public static DAOFactory springBootDBFactory;
	public static FloorDAO floorDAO;

	@BeforeAll
	public static void setUp() throws Exception {
		springBootDBFactory = DAOFactory.getDAOFactory(DAOFactory.SPRING_BOOT);
		floorDAO = springBootDBFactory.getFloorDAO();
	}

	@Test
	void getAllFloors_Name() {
		Floor[] expected = new Floor[3];
		
		
		expected[0] = new Floor("floor 13", "main/MrScheduley.png");
		expected[1] = new Floor("floor 29", "main/MrScheduley.png");
		expected[2] = new Floor("floor 69", "main/MrScheduley.png");

		floorDAO.getAllFloors();
		for(int i=0; i<expected.length; i++) 
			Assert.assertTrue(floorDAO.isFloorInDB(expected[i].getName()));
	}

	@Test
	void getFloorByIDTest_Name() {
		Floor f = floorDAO.getFloorByID(672);
		Assert.assertTrue(f.getName().equalsIgnoreCase("floor 29") || f.getName() == "floor 29");
	}
	
	@Test
	void getFloorByIDTest_URL() {
		Floor f = floorDAO.getFloorByID(672);
		Assert.assertTrue(f.getImageURL().equalsIgnoreCase("main/MrScheduley.png"));
	}
	
}
