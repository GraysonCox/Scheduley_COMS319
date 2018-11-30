package main;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class SpringBoot_DAOFactory extends DAOFactory{
	
	protected static final String BASE_URL = "http://proj-319-080.misc.iastate.edu:8080";
	protected static final String MEDIA_TYPE = "application/json";
	
	protected static CloseableHttpClient httpClient;
	
	public SpringBoot_DAOFactory() {
		
	}
	
	//This could return a JSONArray, and the methods in the specific DAO turns that into the object it wants.
	//	-This idea sounds pretty good. I'll just have to see how well i can 
	public void getAllFromDB() {
		//init httpClient - same for all
		//put request
		//set header - same for all
		
		//execute requests
		
		//Return some JSONArray?
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
