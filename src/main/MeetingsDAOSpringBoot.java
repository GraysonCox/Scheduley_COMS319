package main;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author watis
 *
 */
public class MeetingsDAOSpringBoot implements MeetingsDAO {
	private JSONArray meetingsJSON; //Floor and MSDAO both have their object array...?
	private Meeting[] meetings;
	
	private static final String MEETING_NAME = "meetingName";
	private static final String DATE_TIME = "dateTime";
	private static final String DURATION = "duration";
	private static final String MEETING_SPACE_ID = "location";
	
	//NEW
	private static final String MEETINGS_TABLE_ALL = "/meetings/search/all";
	private static final String MEETINGS_TABLE_INSERT = "/meetings/create";
	private static final String MEETINGS_TABLE_DELETE = "/meetings/delete";
	
	private SpringBoot_DAOFactory dao;
	
	public MeetingsDAOSpringBoot() {
		dao = new SpringBoot_DAOFactory();
		loadMeetings();
	}
	
	/**
	 * Get all from DB
	 */
	@SuppressWarnings("unchecked")
	public void loadMeetings() {
		JSONArray jsonArrFromDB = dao.getJSONArrayFromDB(MEETINGS_TABLE_ALL);
		//deciphering JSONArray
		JSONArray result = new JSONArray();
		Meeting[] result2 = new Meeting[jsonArrFromDB.size()];//adding
		for(int i = 0; i < jsonArrFromDB.size(); i ++) {
			JSONObject obj = (JSONObject) jsonArrFromDB.get(i);
			Timestamp ts = parseTime((String) obj.get(DATE_TIME));
			Meeting temp = new Meeting((String)obj.get(MEETING_NAME), ts, Integer.valueOf(obj.get(DURATION).toString()), Integer.valueOf(obj.get(MEETING_SPACE_ID).toString()));
			result2[i] = temp;
			result.add(temp);
		}
		meetings = result2;
		meetingsJSON = result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertMeeting(Meeting newMeeting) {
		//Create Object to put into DB
		JSONObject jsnObj = new JSONObject();
		jsnObj.put(MEETING_NAME, newMeeting.getName());
		char[] c = newMeeting.getStartTime().toString().toCharArray();//probably a better way to handle the time
		char[] d = Arrays.copyOfRange(c, 0, 19);// "
		d[10] = 'T';// "
		String inputTime = new String(d);// "
		jsnObj.put(DATE_TIME, inputTime);
		jsnObj.put(DURATION, newMeeting.getDuration());
		jsnObj.put(MEETING_SPACE_ID, newMeeting.getMeetingSpaceID());

	    String jsonString = jsnObj.toString();
	    int returnCode = dao.insertIntoDB(MEETINGS_TABLE_INSERT, jsonString);
	    
		loadMeetings(); //update instance variable to match DB
		return returnCode;
	}
	

	@Override
	public int insertMeeting(String name, Timestamp startTime, int duration, int meetingSpaceID) {
		Meeting newMeeting = new Meeting(name, startTime, duration, meetingSpaceID);
		return insertMeeting(newMeeting);

	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteMeeting(String name) {
		JSONObject jsnObj = new JSONObject();
		jsnObj.put(MEETING_NAME, name);
	    String jsonString = jsnObj.toString();//need better name

	    int returnCode = dao.deleteFromDB(MEETINGS_TABLE_DELETE, jsonString);
		loadMeetings(); //update instance variable to match DB
		return returnCode;
	}


	@SuppressWarnings("unchecked")
	private JSONArray getMeetingsByMeetingsSpaceID_JSONArray(int meetingSpaceID) {
		JSONArray result = new JSONArray();
		for(int i = 0; i < meetingsJSON.size(); i++) {
			if(((Meeting) meetingsJSON.get(i)).getMeetingSpaceID() == meetingSpaceID) {
				result.add(meetingsJSON.get(i));
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray getAllMeetingsByWeek_JSONArray(int meetingSpaceID, LocalDateTime targetTime) {
		JSONArray tempArrList =  getMeetingsByMeetingsSpaceID_JSONArray(meetingSpaceID);
		JSONArray result = new JSONArray();
		for(int i = 0; i < tempArrList.size(); i++) {
			Meeting currMeeting = (Meeting) tempArrList.get(i);
			LocalDateTime currTime = currMeeting.getStartTime().toLocalDateTime();
			if(currTime.getMonth().equals(targetTime.getMonth())) {
				if(currTime.getDayOfMonth() == targetTime.getDayOfMonth()){
					result.add(currMeeting);
				}
			}
		}
		return result;
	}

	public Meeting getMeetingByName(String name) {
		for(Meeting m : meetings) {
			if(m.getName() == name || name.equals(m.getName())) {
				return m;
			}
		}
		return null;
	}
	
	private Timestamp parseTime(String time) {
		char[] c = time.toCharArray();
		char[] d = Arrays.copyOfRange(c, 0, 19);
		d[10] = ' ';
		return Timestamp.valueOf(String.copyValueOf(d));
	}

	@Override
	public Meeting[] getAllMeetings() {
		return meetings;
	}

	@Override
	public Meeting[] getMeetingsByMeetingSpaceID(int meetingSpaceID) {
		JSONArray tempArr = getMeetingsByMeetingsSpaceID_JSONArray(meetingSpaceID);
		Meeting[] result = JSONArrayToMeetingArray(tempArr);
		return result;
	}

	/**
	 *This Method will only check by month and day.
	 */
	@Override
	public Meeting[] getAllMeetingsByWeek(int meetingSpaceID, LocalDateTime targetTime) {
		JSONArray tempArr = getAllMeetingsByWeek_JSONArray(meetingSpaceID, targetTime);
		Meeting[] result = JSONArrayToMeetingArray(tempArr);
		return result;
	}
	
	private Meeting[] JSONArrayToMeetingArray(JSONArray inputArr) {
		Meeting[] result = new Meeting[inputArr.size()];
		for(int i = 0; i < inputArr.size(); i++) {
			Meeting obj = (Meeting) inputArr.get(i);
			result[i] = obj;
		}
		return result;
	}
}