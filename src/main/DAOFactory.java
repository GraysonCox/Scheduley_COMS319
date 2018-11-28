package main;

public abstract class DAOFactory {
	
	public static final int JDBC = 1;
	public static final int SPRING_BOOT = 2;
	
	public abstract UserDAO getUserDAO();
	public abstract MeetingsDAO getMeetingsDAO();
	public abstract MeetingSpaceDAO getMeetingSpaceDAO();
	public abstract FloorDAO getFloorDAO();
	
	public static DAOFactory getDAOFactory(int factory) {
		switch(factory) {
		case JDBC:
			return new JDBC_DAOFactory();
		case SPRING_BOOT:
			return new SpringBoot_DAOFactory();
		default:
			return null;
		}
	}
}
