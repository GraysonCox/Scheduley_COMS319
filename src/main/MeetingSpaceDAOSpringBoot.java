package main;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MeetingSpaceDAOSpringBoot implements MeetingSpaceDAO {
	private MeetingSpace meetingSpaces[];
	private SpringBoot_DAOFactory dao;
	
	private static final String X = "x";
	private static final String Y = "y";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String FLOOR_ID = "floorID";
	private static final String MEETING_SPACE_ID = "meetingSpaceID";
	private static final String MEETING_SPACE_NAME = "meetingSpaceName";
	
	private static final String MEETING_SPACES_TABLE_ALL = "/meetingspace/search/all";
	private static final String MEETING_SPACES_TABLE_INSERT = "/meetingspace/create";
	private static final String MEETING_SPACES_TABLE_DELETE = "/meetingspace/delete";
	
	public MeetingSpaceDAOSpringBoot() {
		dao = new SpringBoot_DAOFactory();
		loadMeetingSpaces();
	}
	
	/**
	 * Get all from DB
	 */
	public void loadMeetingSpaces() {
		JSONArray jsonArrFromDB = dao.getJSONArrayFromDB(MEETING_SPACES_TABLE_ALL);
		MeetingSpace[] arr = new MeetingSpace[jsonArrFromDB.size()];
		for(int i=0; i<jsonArrFromDB.size(); i++) {
			JSONObject temp = (JSONObject) jsonArrFromDB.get(i);
			long x = (long)temp.get(X);
			long y = (long)temp.get(Y);
			long w = (long)temp.get(WIDTH);
			long h = (long)temp.get(HEIGHT);
			long f = (long)temp.get(FLOOR_ID);
			long id = (long)temp.get(MEETING_SPACE_ID);
			
			MeetingSpace ms = new MeetingSpace((String)temp.get(MEETING_SPACE_NAME),x,y,w,h,(int) f);
			ms.setUniqueID((int)id);
			arr[i] = ms;
		}
		meetingSpaces = arr;
	}
	
	@Override
	public MeetingSpace[] getAllMeetingSpace() {
		return meetingSpaces;
	}

	@Override
	public MeetingSpace[] getMeetingSpaceByFloor(int floorID) {
		ArrayList<MeetingSpace> arrlist = new ArrayList<MeetingSpace>();
		for(int i=0; i<meetingSpaces.length; i++) {
			if(meetingSpaces[i].getFloorID() == floorID)
				arrlist.add(meetingSpaces[i]);
		}
		MeetingSpace[] result = new MeetingSpace[arrlist.size()];
		for(int i = 0; i < arrlist.size(); i++) {
			result[i] = arrlist.get(i);
		}
		return result;
	}

	/**
	 * Returns 200 on success
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addMeetingSpace(MeetingSpace meetingSpace) {
		int code = 500;
		JSONObject json = new JSONObject();
		json.put(MEETING_SPACE_NAME, meetingSpace.getName());
		json.put(X, meetingSpace.getX());
		json.put(Y, meetingSpace.getY());
		json.put(WIDTH, meetingSpace.getWidth());
		json.put(HEIGHT, meetingSpace.getHeight());
		json.put(FLOOR_ID, meetingSpace.getFloorID());
	    String jsonString = json.toString();
	    
	    code = dao.insertIntoDB(MEETING_SPACES_TABLE_INSERT, jsonString);

		loadMeetingSpaces();//Update the instance variable to the new table
		return code;
	}

	@SuppressWarnings("unchecked")
	public int deleteMeetingSpace(int x, int y, int floor) {
		int code = 500;
		JSONObject json = new JSONObject();
		json.put(X, x);
		json.put(Y, y);
		json.put(FLOOR_ID, floor);
	    String jsonString = json.toString();
	    
	    code = dao.deleteFromDB(MEETING_SPACES_TABLE_DELETE, jsonString);
		loadMeetingSpaces();//Update the instance variable to the new table
		return code;
	}
	
	@Override
	public int deleteMeetingSpace(MeetingSpace toDelete) {
		return deleteMeetingSpace((int)toDelete.getX(), (int)toDelete.getY(), toDelete.getFloorID());
	}

	public MeetingSpace findMeetingSpaceByName(String name) {
		for(MeetingSpace ms : meetingSpaces) {
			if(ms.getName() == name || name.equalsIgnoreCase(ms.getName())) {
				return ms;
			}
		}
		return null;
	}
	
	public boolean isMeetingSpaceInDB(String name) {
		return (findMeetingSpaceByName(name) != null) ? true : false;
	}
}