package main;

import java.sql.*;

public class ServerConnection {
	
	private static final String DB_URL = "jdbc:mysql://proj-319-080.misc.iastate.edu/group29";
	private static final String USER = "group29";
	private static final String PASSWORD = "Password!23";

	private static Connection con = null;
	
	public ServerConnection() {
		
	}
	
	public Connection getConnection() throws SQLException {
		return  getConnection(DB_URL, USER, PASSWORD);
	}
	
	private Connection getConnection(String db, String user, String password) throws SQLException {
		try {
			// Load the driver (registers itself)
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception E) {
			System.err.println("Unable to load driver.");
			E.printStackTrace();
		}
		try {
			 con = (Connection) DriverManager.getConnection(db, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
}
