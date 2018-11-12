package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAOMySQL implements UserDAO{
	
	private static final String GET_ALL_USERS = "SELECT * FROM Users;";
	
	private static final String INSERT_NEW_USER = "INSERT into Users(email, password, name, userType)" 
			+ "VALUES(?, ?, ?, ?)";
	
	private static final String UPDATE_USER = "UPDATE Users set ";
	
	private static final String DELETE_USER = "DELETE FROM Users WHERE email = ?;";
	
	private static final String DEFAULT_PASSWORD = "group29";
	
	private static ServerConnection dao;
	
	public UserDAOMySQL(ServerConnection conn) {
		dao = conn;
	}
	
	@Override
	public ArrayList<UserProfile> findAllUsers() throws SQLException {
		ArrayList<UserProfile> users = new ArrayList<UserProfile>();
		
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			dbConnection = dao.getConnection();
			statement = dbConnection.createStatement();
			rs = statement.executeQuery(GET_ALL_USERS);
		
			while (rs.next()) {
<<<<<<< HEAD
				//get value of salary from each tuple
=======
>>>>>>> adminMakeUserDBMethods
				String email = rs.getString("email");	
				String name = rs.getString("name");
				int type = rs.getInt("userType");
				UserProfile temp = new UserProfile(email, name, type);
				users.add(temp);
			}
		} finally {
			if(dbConnection != null) {
				dbConnection.close();
			}
			if(statement != null) {
				statement.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
		// Close all statements and connections
		return users;
	}
	
	@Override
	public void insertUser(String email, String name, UserType userType) throws SQLException {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		UserProfile newUser = new UserProfile(email, name, userType);
		insertUser(newUser);
	}

	@Override
	public void insertUser(UserProfile newUser) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement pStatement = null;
		try {
			dbConnection = dao.getConnection();
			pStatement = dbConnection.prepareStatement(INSERT_NEW_USER);
			
			pStatement.setString(1, newUser.getEmail());
			pStatement.setString(2, DEFAULT_PASSWORD);
			pStatement.setString(3, newUser.getName());
			pStatement.setInt(4, newUser.getUserType());
			pStatement.executeUpdate();	
			
		}finally {
			if(pStatement != null) {
				pStatement.close();
			}
			if(dbConnection != null) {
				dbConnection.close();
			}
		}
=======
		UserProfile newUser = new UserProfile(email, name, userType);
		insertUser(newUser);
>>>>>>> adminMakeUserDBMethods
	}

	@Override
	public void insertUser(UserProfile newUser) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement pStatement = null;
		try {
			dbConnection = dao.getConnection();
			pStatement = dbConnection.prepareStatement(INSERT_NEW_USER);
			
			pStatement.setString(1, newUser.getEmail());
			pStatement.setString(2, DEFAULT_PASSWORD);
			pStatement.setString(3, newUser.getName());
			pStatement.setInt(4, newUser.getUserType());
			pStatement.executeUpdate();	
			
		}finally {
			if(pStatement != null) {
				pStatement.close();
			}
			if(dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	public void updateUser(String email) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteUser(String email) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement pStatement = null;
		try {
			dbConnection = dao.getConnection();
			pStatement = dbConnection.prepareStatement(DELETE_USER);
			
			pStatement.setString(1, email);
			pStatement.execute();
		}
		finally {
			if(dbConnection != null) {
				dbConnection.close();
			}
			if(pStatement != null) {
				pStatement.close();
			}
		}
	}

	@Override
	public boolean verifyUser(String email, String password) {
<<<<<<< HEAD
		ArrayList<UserProfile> currentUsers;
		try {
			currentUsers = findAllUsers();
			for(UserProfile u : currentUsers) {
				if(email.equals(u.getEmail()) && password.equals(u.getPassword())) {
					return true;
				}
			}
		}
		catch(SQLException e) {
			System.out.print(e.getStackTrace());
		}
		return false;
=======
		return (findUser(email) != null) ? true : false;
>>>>>>> adminMakeUserDBMethods
	}

	@Override
	public UserProfile findUser(String email) {
		ArrayList<UserProfile> currentUsers;
		UserProfile foundUser = null;
		try {
			currentUsers = findAllUsers();
			for(UserProfile u : currentUsers) {
				if(email.equals(u.getEmail())) {
					foundUser = u;
				}
			}
		}
		catch(SQLException e) {
			System.out.print(e.getStackTrace());
		}
		return foundUser;
	}
}