package main;

import java.sql.*;

public class MySqlCon {
	public static void main(String args[]){  
		try {
			// Load the driver (registers itself)
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception E) {
			System.err.println("Unable to load driver.");
			E.printStackTrace();
		}
		try{  
			Connection conn1;
			String dbUrl = "jdbc:mysql://proj-319-080.misc.iastate.edu/group29";
			String user = "group29";
			String password = "Password!23";
			conn1 = DriverManager.getConnection(dbUrl, user, password);
			System.out.println("*** Connected to the database ***");

			// Create Statement and ResultSet variables to use throughout the project
			Statement statement = conn1.createStatement();
			ResultSet rs;

			// get salaries of all instructors
			rs = statement.executeQuery("select * from Users;");
			// Close all statements and connections
			
			
			String emails = "";
			while (rs.next()) {
				//get value of salary from each tuple
				emails += rs.getString("email");	
				emails+= " ";
			}
			System.out.println("Emails: " +emails);
			
			
			/*//this commented out code is for inserting into
			emails+= ", ";
			int rs2;
			rs2 = statement.executeUpdate("INSERT into Users(email, password, name, userType) "
					+ "values(\"wasartin@iastate.edu\", \"group29\", \"Will Sartin\", 0);");
			*/
			
			statement.close();
			rs.close();
			conn1.close();
		}
		catch(Exception e){ System.out.println(e);}  
	}  
}  		
