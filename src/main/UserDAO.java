package main;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {
	
	ArrayList<UserProfile> findAllUsers() throws SQLException;
	
	//These are currently boolean, but they should just be 
	//void and throw an exception. Exception throwing is much
	//better and easer to understand than just true/false.
	void insertUser(UserProfile newUser) throws SQLException;
	void insertUser(String email, String name, UserType userType) throws SQLException;
	boolean updateUser(UserProfile user);
	void deleteUser(String email) throws SQLException;
	boolean verifyUser(String email, String password);
	UserProfile findUser(String email);
}
