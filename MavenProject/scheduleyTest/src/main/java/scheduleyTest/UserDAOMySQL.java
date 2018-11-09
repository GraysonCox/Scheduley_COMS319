package scheduleyTest;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOMySQL implements UserDAO{
	
	private static final String GET_ALL_USERS = "SELECT * FROM Users;";
	
	private static final String INSERT_NEW_USER = "INSERT INTO User" 
			+ "(email, password, name, userType)" 
			+ "VALUES (?, ?, ?, ?)";
	
	private static final String UPDATE_USER = "UPDATE User";
	
	private static final String DELETE_USER = "DELETE USER WHERE email = ?";
	
	private static final String DEFAULT_PASSWORD = "group29";
	
	private static ServerConnection dao;
	
	public UserDAOMySQL(ServerConnection conn) {
		dao = conn;
	}
	
	@Override
	public ArrayList<UserProfile> findAllUsers() throws SQLException {
		// TODO Auto-generated method stub
		Connection dbConnection = dao.getConnection();
		Statement statement = dbConnection.createStatement();
		ResultSet rs;

		// get salaries of all instructors
		rs = statement.executeQuery(GET_ALL_USERS);
		// Close all statements and connections
		
		
		ArrayList<UserProfile> users = new ArrayList<UserProfile>();
		while (rs.next()) {
			//get value of salary from each tuple
			String email = rs.getString("email");	
			String name = rs.getString("name");
			int type = rs.getInt("userType");
			UserProfile temp = new UserProfile(email, name, type);
			users.add(temp);
		}
		return users;
	}

	@Override
	public void insertUser(UserProfile newUser) throws SQLException {
		
		Connection dbConnection = dao.getConnection();
		
		PreparedStatement stmt1 = null;
		stmt1 = (PreparedStatement) dbConnection.prepareStatement(INSERT_NEW_USER);
		stmt1.setString(1, newUser.getEmail());
		stmt1.setString(2, DEFAULT_PASSWORD);
		stmt1.setString(3, newUser.getName());
		stmt1.setInt(4, newUser.getUserType());
		
		stmt1.executeUpdate();	
	}

	@Override
	public boolean insertUser(String email, String name, UserType userType) throws SQLException {
		// TODO Auto-generated method stub
		UserProfile newUser = new UserProfile(email, name, userType);
		insertUser(newUser);
		return false;
	}

	@Override
	public boolean updateUser(UserProfile user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(UserProfile user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findUser(UserProfile user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeUserPassword(UserProfile user) {
		// TODO Auto-generated method stub
		return false;
	}
}
