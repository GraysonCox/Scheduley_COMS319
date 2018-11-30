package main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FloorDAOSpringBoot implements FloorDAO {
	
	private static final String FLOOR_TABLE_ALL = "/floor/search/all";
	private static final String FLOOR_TABLE_INSERT = "/floor/create";
	private static final String FLOOR_TABLE_DELETE = "/floor/delete";
	
	private static final String FLOOR_ID = "floorID";
	private static final String FLOOR_NAME = "floorName";
	private static final String IMG_URL = "imgURL";
	
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
			long id = (long) temp.get(FLOOR_ID);
			String name = (String) temp.get(FLOOR_NAME);
			String url = (String) temp.get(IMG_URL);
			
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
		json.put(FLOOR_NAME, floor.getName());
		json.put(IMG_URL, floor.getImageURL());
		String jsonString = json.toString();
		
		code = dao.insertIntoDB(FLOOR_TABLE_INSERT, jsonString);
		loadFloors();
		return code;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteFloor(int ID) {
	int code = 500;
	
	JSONObject json = new JSONObject();
	json.put(FLOOR_ID, ID);
	String jsonString = json.toString();
	
	code = dao.deleteFromDB(FLOOR_TABLE_DELETE, jsonString);
	
	loadFloors();
	return code;
	}
}
