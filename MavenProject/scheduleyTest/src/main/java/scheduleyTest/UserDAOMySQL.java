package scheduleyTest;

import java.sql.*;
import java.util.ArrayList;

import main.ServerConnection;
import main.UserDAO;
import main.UserProfile;
import main.UserType;

public class UserDAOMySQL implements UserDAO{
	
	private static final String GET_ALL_USERS = "SELECT * FROM Users;";
	
	private static final String INSERT_NEW_USER = "INSERT into Users(email, password, name, userType)" 
			+ "VALUES(?, ?, ?, ?)";
	
	private static final String UPDATE_USER = "UPDATE User";
	
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
		return users;
	}
	
	@Override
	public void insertUser(String email, String name, UserType userType) throws SQLException {
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
	}

	@Override
	public boolean updateUser(UserProfile user) {
		// TODO Auto-generated method stub
		return false;
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
		return (findUser(email) != null) true ? false;
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