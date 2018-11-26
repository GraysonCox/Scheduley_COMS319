package main;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ServerConnection;
import main.UserDAOMySQL;
import main.UserProfile;
import main.UserType;

public class UserDAOMySQLTest {

	private UserDAOMySQL dataSource;
	
	@BeforeEach
	void setUp() {
		ServerConnection conn = new ServerConnection();
		dataSource = new UserDAOMySQL(conn);
	}
	
	
	/*
	@Test
	void insertUser() throws SQLException {
		String inputName = "John Johnson";
		String inputEmail = "jonathanJohnson@tom.com";
		dataSource.insertUser(12, inputEmail, inputName, UserType.OTHER);
		
	}
	
	*/
	
	@Test
	void insertUser() throws SQLException {
		String inputName = "Tom Dodge";
		String inputEmail = "tomDodge@tom.com";
		dataSource.insertUser(5, inputEmail, inputName, UserType.ADMIN);
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		
		ArrayList<UserProfile> expectedUsers = new ArrayList<UserProfile>();
		UserProfile one = new UserProfile(1, "nht209@iastate.edu", "Thien Nguyen", 0);
		expectedUsers.add(one);
		UserProfile three = new UserProfile(5, inputEmail, inputName, "ADMIN");
		expectedUsers.add(three);
		UserProfile two = new UserProfile(29, "wasartin@iastate.edu", "Will Sartin", 0);
		expectedUsers.add(two);

		Assert.assertTrue(expectedUsers.equals(users));
		
		//Clean up
		dataSource.deleteUser("tomDodge@tom.com");
		
		
}
	
	
	@Test
	void findAllUserTest() throws SQLException {
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		ArrayList<UserProfile> expectedUsers = new ArrayList<UserProfile>();
		UserProfile one = new UserProfile(1, "nht209@iastate.edu", "Thien Nguyen", 0);
		expectedUsers.add(one);
		UserProfile two = new UserProfile(29, "wasartin@iastate.edu", "Will Sartin", 0);
		expectedUsers.add(two);

		Assert.assertTrue(expectedUsers.equals(users));
	}

	
	@Test
	void deleteUser() throws SQLException {
		//Add User
		dataSource.insertUser(5, "tomDodge@tom.com", "Tom Dodge", UserType.ADMIN);
		//Delete User
		dataSource.deleteUser("tomDodge@tom.com");
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		ArrayList<UserProfile> expectedUsers = new ArrayList<UserProfile>();
		UserProfile one = new UserProfile(1, "nht209@iastate.edu", "Thien Nguyen", 0);
		expectedUsers.add(one);
		UserProfile two = new UserProfile(29, "wasartin@iastate.edu", "Will Sartin", 0);
		expectedUsers.add(two);
		
		Assert.assertTrue(expectedUsers.equals(users));
	}
	
	@Test
	void verifyUserTest() {
		String sampleEmail = "wasartin@iastate.edu";
		String password = "group29";
		Assert.assertTrue(dataSource.verifyUser(sampleEmail, password));
	}
	
	@Test
	void updateUser() {
		
	}
	
	@Test
	void findUser() {
		
	}
	

}