package scheduleyTest;

import java.sql.SQLException;
import java.util.ArrayList;

import main.UserProfile;
import main.UserType;

public interface UserDAO {
	
	ArrayList<UserProfile> findAllUsers() throws SQLException;
	
	void insertUser(UserProfile newUser) throws SQLException;
	void insertUser(String email, String name, UserType userType) throws SQLException;
	boolean updateUser(UserProfile user);
	void deleteUser(String email) throws SQLException;
	boolean verifyUser(String email, String password);
	UserProfile findUser(String email);
}


