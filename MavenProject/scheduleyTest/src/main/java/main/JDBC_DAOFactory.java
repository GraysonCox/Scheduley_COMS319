package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is better, but this isn't perfect. It would be best to switch UserDAO to spring boot, 
 * but this is a short term solution. 
 * 
 * When I say this isn't perfect, I am referring to the naming conventions I am using, and the fact
 * that three methods are not being instantiated here.
 * 
 * Ideally if we moved UserDAO over to spring boot, then the name would become just nameOfDB_DAOFactory
 * then we wouldn't have this class and the SpringBoot_DAOFactory.
 * @author watis
 *
 */
public class JDBC_DAOFactory extends DAOFactory{
	
	private static final String DB_URL = "jdbc:mysql://proj-319-080.misc.iastate.edu/group29";
	private static final String USER = "group29";
	private static final String PASSWORD = "Password!23";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static Connection con = null;
	
	public JDBC_DAOFactory() {
		
	}
	
	public Connection getConnection() throws SQLException {
		return  getConnection(DB_URL, USER, PASSWORD);
	}
	
	private Connection getConnection(String db, String user, String password) throws SQLException {
		try {
			// Load the driver (registers itself)
			Class.forName(DRIVER);
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
	
	@Override
	public UserDAO getUserDAO() {
		return new UserDAOMySQL();
	}
	
	@Override
	public MeetingsDAO getMeetingsDAO() {
		return null;
	}
	@Override
	public MeetingSpaceDAO getMeetingSpaceDAO() {
		return null;
	}
	@Override
	public FloorDAO getFloorDAO() {
		return null;
	}
	
	
}
