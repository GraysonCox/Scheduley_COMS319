package main;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {
	
	ArrayList<UserProfile> findAllUsers() throws SQLException;
	
	void insertUser(UserProfile newUser) throws SQLException;
	void insertUser(String email, String name, UserType userType) throws SQLException;
<<<<<<< HEAD
	boolean updateUser(UserProfile user);
=======
	void updateUser(String email);
>>>>>>> adminMakeUserDBMethods
	void deleteUser(String email) throws SQLException;
	boolean verifyUser(String email, String password);
	UserProfile findUser(String email);
}

