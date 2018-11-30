package main;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {
	
	ArrayList<UserProfile> findAllUsers() throws SQLException;
	
	void insertUser(UserProfile newUser) throws SQLException;
	void insertUser(String email, String name, UserType userType) throws SQLException;
	void insertUser(String email, String name, String userType) throws SQLException;
	void updateUser(String email);
	void deleteUser(String email) throws SQLException;
	boolean verifyUser(String email, String password);
	UserProfile findUser(String email);
}
