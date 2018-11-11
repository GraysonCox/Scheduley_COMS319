package test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ServerConnection;
import main.UserDAOMySQL;
import main.UserProfile;
import main.UserType;

class UserDAOMySQLTest {

	private UserDAOMySQL dataSource;
	
	@BeforeEach
	void setUp() {
		ServerConnection conn = new ServerConnection();
		dataSource = new UserDAOMySQL(conn);
	}
	
	@Test
	void findAllUserTest() throws SQLException {
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		ArrayList<UserProfile> expectedUsers = new ArrayList<UserProfile>();
		UserProfile one = new UserProfile("nht209@iastate.edu", "Thien Nguyen", 0);
		expectedUsers.add(one);
		UserProfile two = new UserProfile("wasartin@iastate.edu", "Will Sartin", 0);
		expectedUsers.add(two);
		
		Assert.assertTrue(expectedUsers.equals(users));
	}
	
	@Test
	void insertUser() throws SQLException {
		String inputEmail = "tomDodge@tom.com";
		String inputName = "Tom Dodge";
		UserType inputType = UserType.ADMIN;
		dataSource.insertUser(inputEmail, inputName, inputType);
		
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		
		ArrayList<UserProfile> expectedUsers = new ArrayList<UserProfile>();
		UserProfile one = new UserProfile("nht209@iastate.edu", "Thien Nguyen", 0);
		expectedUsers.add(one);
		UserProfile three = new UserProfile(inputEmail, inputName, inputType);
		expectedUsers.add(three);
		UserProfile two = new UserProfile("wasartin@iastate.edu", "Will Sartin", 0);
		expectedUsers.add(two);

		Assert.assertTrue(expectedUsers.equals(users));
		
		//Clean up
		dataSource.deleteUser(inputEmail);
	}
	
	@Test
	void deleteUser() throws SQLException {
		//Add User
		dataSource.insertUser("tomDodge@tom.com", "Tom Dodge", UserType.ADMIN);
		//Delete User
		dataSource.deleteUser("tomDodge@tom.com");
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		ArrayList<UserProfile> expectedUsers = new ArrayList<UserProfile>();
		UserProfile one = new UserProfile("nht209@iastate.edu", "Thien Nguyen", 0);
		expectedUsers.add(one);
		UserProfile two = new UserProfile("wasartin@iastate.edu", "Will Sartin", 0);
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
