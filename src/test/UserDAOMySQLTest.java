package test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ServerConnection;
import main.UserDAOMySQL;
import main.UserProfile;

class UserDAOMySQLTest {

	private UserDAOMySQL dataSource;
	
	@BeforeEach
	void setUp() {
		System.out.println("Setting up serverConnection");
		ServerConnection conn = new ServerConnection();
		System.out.println("Setting up datasource");
		dataSource = new UserDAOMySQL(conn);
	}
	
	@Test
	void findAllUserTest() throws SQLException {
		System.out.println("Testing find all users");
		ArrayList<UserProfile> users = dataSource.findAllUsers();
		for(UserProfile u : users) {
			System.out.println("email: " + u.getEmail() + ", name: " + u.getName()
					+ ", userType: " + u.getUserType());
		}
		ArrayList<UserProfile> expectedUsers = new ArrayList<UserProfile>();
		UserProfile one = new UserProfile("nht209@iastate.edu", "Thien Nguyen", 0);
		expectedUsers.add(one);
		UserProfile two = new UserProfile("wasartin@iastate.edu", "Will Sartin", 0);
		expectedUsers.add(two);
		
		Assert.assertTrue(expectedUsers.equals(users));
	}
}
