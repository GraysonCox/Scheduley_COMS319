package main;

public class SpringBoot_DAOFactory extends DAOFactory{
	
	protected static final String BASE_URL = "http://proj-319-080.misc.iastate.edu:8080";
	protected static final String MEDIA_TYPE = "application/json";
	
	public SpringBoot_DAOFactory() {
		
	}
	
	/**
	 * I guess I could probably hack the two Factories together
	 */
	@Override
	public UserDAO getUserDAO() {
		return null;
	}

	@Override
	public MeetingsDAO getMeetingsDAO() {
		return new MeetingsDAOSpringBoot();
	}

	@Override
	public MeetingSpaceDAO getMeetingSpaceDAO() {
		return new MeetingSpaceDAOSpringBoot();
	}

	@Override
	public FloorDAO getFloorDAO() {
		return new FloorDAOSpringBoot();
	}

}
