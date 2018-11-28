package main;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.DAOFactory;
import main.UserDAOMySQL;
import main.UserProfile;
import main.UserType;

public class UserDAOMySQLTest {

	private UserDAOMySQL dataSource;
	
	@BeforeEach
	void setUp() {
		dataSource = new UserDAOMySQL();
	}
	
	@Test
	void insertUser() throws SQLException {
		UserProfile tom = new UserProfile("tomDodge@tom.com", "Tom Dodge", UserType.ADMIN);
		dataSource.insertUser(tom);
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		
		Assert.assertTrue(dataSource.verifyUser(tom.getEmail(), tom.getPassword()));
		
		//Clean up
		dataSource.deleteUser("tomDodge@tom.com");	
	}
	
	/**
	 * This Test only tests the first 4 users. Since we would have to update this test every single time
	 * there are new users added to the DB. 
	 * @throws SQLException
	 */
	@Test
	void findAllUserTest() throws SQLException {
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		ArrayList<UserProfile> expectedUsers = new ArrayList<UserProfile>();
		UserProfile one = new UserProfile("nht209@iastate.edu", "Thien Nguyen", 0);
		expectedUsers.add(one);
		UserProfile three = new UserProfile("jonathanJohnson@tom.com", "John Johnson", UserType.OTHER);
		expectedUsers.add(three);
		UserProfile two = new UserProfile("wasartin@iastate.edu", "Will Sartin", 0);
		expectedUsers.add(two);
		UserProfile four = new UserProfile("putin@iastate.edu", "Vladimir Putin", UserType.OTHER);
		expectedUsers.add(four);
		for(UserProfile u : users) {
			System.out.println(u.getEmail());
		}
		
		for(int i = 0; i < expectedUsers.size(); i++) {
			Assert.assertTrue(expectedUsers.get(i).equals(users.get(i)));
		}
	}

	
	@Test
	void deleteUser() throws SQLException {
		UserProfile tom = new UserProfile("tomDodge@tom.com", "Tom Dodge", UserType.ADMIN);
		//Add User
		dataSource.insertUser(tom);
		//Delete User
		dataSource.deleteUser(tom.getEmail());
		ArrayList<UserProfile> users = dataSource.findAllUsers();

		Assert.assertFalse(dataSource.verifyUser(tom.getEmail(), tom.getPassword()));
	}
	
	@Test
	void verifyUserTest() {
		String sampleEmail = "wasartin@iastate.edu";
		String password = "group29";
		Assert.assertTrue(dataSource.verifyUser(sampleEmail, password));
	}
}