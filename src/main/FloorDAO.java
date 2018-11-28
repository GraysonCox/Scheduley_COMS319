package main;

public interface FloorDAO {
	
	public Floor[] getAllFloors();
	public Floor getFloorByID(int ID);
	public Floor getFloorByName(String name);
	public int addFloor(Floor floor);
	public int deleteFloor(int ID);
}
