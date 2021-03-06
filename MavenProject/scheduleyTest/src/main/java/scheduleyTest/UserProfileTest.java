package scheduleyTest;


import static org.junit.Assert.*;
import org.junit.Test;

public class UserProfileTest {
	
	UserProfile user1 = new UserProfile("superSaiyanBAWS@gmail.com", "Sebastian", UserType.ADMIN);
	UserProfile user2 = new UserProfile("superSaiyan1999@gmail.com", "Benn", "Admin");
	UserProfile user3 = new UserProfile("superduper@gmail.com", "Lebron", 2);
	UserProfile user4 = new UserProfile("superduper@gmail.com", "Lebron", "ADMIN");
	
	@Test
	public void testGetEmail()
	{
		assertEquals("superSaiyanBAWS@gmail.com", user1.getEmail());
		assertEquals("superSaiyan1999@gmail.com", user2.getEmail());
		assertEquals("superduper@gmail.com", user3.getEmail());
	}
	
	@Test
	public void testGetName()
	{
		assertEquals("Sebastian", user1.getName());
		assertEquals("Benn", user2.getName());
		assertEquals("Lebron", user3.getName());
	}
	
	@Test
	public void testGetPassword()
	{
		assertEquals("group29", user1.getPassword());
		assertEquals("group29", user2.getPassword());
		assertEquals("group29", user3.getPassword());
	}
	
	@Test
	public void testGetUserType()
	{
		assertEquals(0, user1.getUserType());
		assertEquals(4, user2.getUserType());
		assertEquals(2, user3.getUserType());
		assertEquals(0, user4.getUserType());
	}

}
