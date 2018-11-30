package main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FloorDAOSpringBoot implements FloorDAO {
	
	private static final String BASE_URL = "http://proj-319-080.misc.iastate.edu:8080";
	private static final String MEDIA_TYPE = "application/json";
	
	private static final String FLOOR_TABLE_ALL = "/floor/search/all";
	private static final String FLOOR_TABLE_INSERT = "/floor/create";
	private static final String FLOOR_TABLE_DELETE = "/floor/delete";
	
	private SpringBoot_DAOFactory dao;
	
	private Floor floors[];
	
	public FloorDAOSpringBoot() {
		dao = new SpringBoot_DAOFactory();
		loadFloors();
	}

	/**
	 * This is basically just get all from DB
	 */
	public void loadFloors() {
		Floor[] arr = null;
		JSONArray jsonArrayFromDB = dao.getJSONArrayFromDB(FLOOR_TABLE_ALL);
		arr = new Floor[jsonArrayFromDB.size()];
		
		for (int i = 0; i < jsonArrayFromDB.size(); i++) {
			JSONObject temp = (JSONObject) jsonArrayFromDB.get(i);
			long id = (long) temp.get("floorID");
			String name = (String) temp.get("floorName");
			String url = (String) temp.get("imgURL");
			
			//Really have to figure this out
			if(!url.equals("main/floorplan1.jpg")) {
				System.out.println("Current floor " + name + ", url: " +url);
				url = "main/MrScheduley.png";
			}
			else {
				System.out.println("DEBUG HERE");//I have no excuse for this
			}
			Floor ms = new Floor(name, url); 
			ms.setFloorID((int) id);
			arr[i] = ms;
		}
		floors = arr;
	}
	
	@Override
	public Floor[] getAllFloors() {
		return floors;
	}

	@Override
	public Floor getFloorByID(int ID) {
		for (Floor f : floors) {
			if (f.getFloorID() == ID)
				return f;
		}
		return null;
	}

	@Override
	public Floor getFloorByName(String name) {
		for (Floor f : floors) {
			if (f.getName().equalsIgnoreCase(name))
				return f;
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addFloor(Floor floor) {
		int code = 500;
		
		JSONObject json = new JSONObject();
		json.put("floorName", floor.getName());
		json.put("imgURL", floor.getImageURL());
		String jsonString = json.toString();
		
		code = dao.insertIntoDB(FLOOR_TABLE_INSERT, jsonString);
		return code;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteFloor(int ID) {
	int code = 500;
	
	JSONObject json = new JSONObject();
	json.put("floorID", ID);
	String jsonString = json.toString();
	
	code = dao.deleteFromDB(FLOOR_TABLE_DELETE, jsonString);

	return code;
	}
}
