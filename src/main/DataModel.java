package main;

import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {
	private UserProfile currentUser;

	private final DAOFactory springBootDBFactory = DAOFactory.getDAOFactory(DAOFactory.SPRING_BOOT);
	private final DAOFactory JDBCFactory = DAOFactory.getDAOFactory(DAOFactory.JDBC);

	private final FloorDAO floorDataSource = springBootDBFactory.getFloorDAO();
	private final MeetingSpaceDAO meetingSpaceDataSource = springBootDBFactory.getMeetingSpaceDAO();
	private final MeetingsDAO meetingDataSource = springBootDBFactory.getMeetingsDAO();
	private final UserDAO userDataSource = JDBCFactory.getUserDAO();

	private final ObservableList<MeetingSpace> meetingSpaceList = FXCollections.observableArrayList();
	private final ObservableList<Floor> floorList = FXCollections.observableArrayList();

	private final ObjectProperty<Meeting> currentMeeting = new SimpleObjectProperty<>(null);
	private final ObjectProperty<MeetingSpace> currentMeetingSpace = new SimpleObjectProperty<>(null);
	private final ObjectProperty<Floor> currentFloor = new SimpleObjectProperty<>(null);

	private final ObjectProperty<LocalDate> sundayDisplayedProperty;

	public DataModel(UserProfile user) {
		currentUser = user;
		loadData();
		LocalDate currentDate = new Date(System.currentTimeMillis()).toLocalDate(); // Temporary object to help
																					// initialize
																					// dateOfSundayDisplayedProperty
		sundayDisplayedProperty = new SimpleObjectProperty<LocalDate>(
				currentDate.minusDays((currentDate.getDayOfWeek().ordinal() + 1) % 7));
	}

	public void loadData() {
		for (Floor f : floorList) {
			f.clearMeetingSpaces();
		}
		floorList.clear();
		meetingSpaceList.clear();
		floorList.addAll(floorDataSource.getAllFloors()); // Retrieve all Floors
		Floor tempFloor;
		for (MeetingSpace m : meetingSpaceDataSource.getAllMeetingSpace()) { // For every MeetingSpace, use its floorID
																				// to add a pointer to its Floor object
			tempFloor = floorDataSource.getFloorByID(m.getFloorID());
			if (tempFloor != null) { // Add to list if it has valid floorID
				m.setFloor(tempFloor);
				meetingSpaceList.add(m);
			}
		}
	}

	public UserProfile getCurrentUser() {
		return currentUser;
	}
	
	public UserProfile getUserByEmail(String email) {
		return userDataSource.findUser(email);
	}

	public UserProfile[] getAllUsers() {
		try {
			return userDataSource.findAllUsers().toArray(new UserProfile[0]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void insertUser(String email, String name, UserType userType) throws SQLException {
		UserProfile newUser = new UserProfile(email, name, userType);
		userDataSource.insertUser(newUser);
	}
	
	public void deleteUser(String email) {
		try {
			userDataSource.deleteUser(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addMeeting(Meeting m) {
		meetingDataSource.insertMeeting(m);
	}

	public Meeting[] getMeetingsByMeetingSpaceID(int ID) {
		return meetingDataSource.getMeetingsByMeetingSpaceID(ID);
	}
	
	public ObjectProperty<Meeting> currentMeetingProperty() {
		return currentMeeting;
	}

	public final Meeting getCurrentMeeting() {
		return currentMeeting.get();
	}

	public final void setCurrentMeeting(Meeting meeting) {
		currentMeeting.set(meeting);
	}
	
	public void removeMeeting(Meeting meeting) {
		meetingDataSource.deleteMeeting(meeting.getName());
	}

	public void addMeetingSpace(MeetingSpace m) {
		meetingSpaceDataSource.addMeetingSpace(m);
		loadData();
	}

	public void removeMeetingSpace(MeetingSpace m) {
		meetingSpaceDataSource.deleteMeetingSpace(m);
		loadData();
	}

	public void addFloor(Floor f) {
		floorDataSource.addFloor(f);
		loadData();
	}

	public void removeFloor(Floor f) {
		floorDataSource.deleteFloor(f.getFloorID());
		loadData();
	}

	public ObjectProperty<MeetingSpace> currentMeetingSpaceProperty() {
		return currentMeetingSpace;
	}

	public final MeetingSpace getCurrentMeetingSpace() {
		return currentMeetingSpace.get();
	}

	public final void setCurrentMeetingSpace(MeetingSpace meetingSpace) {
		currentMeetingSpace.set(meetingSpace);
	}

	public ObservableList<MeetingSpace> getMeetingSpaceList() {
		return meetingSpaceList;
	}

	public ObjectProperty<Floor> currentFloorProperty() {
		return currentFloor;
	}

	public final Floor getCurrentFloor() {
		return currentFloor.get();
	}

	public final void setCurrentFloor(Floor floor) {
		currentFloor.set(floor);
	}

	public ObservableList<Floor> getFloorList() {
		return floorList;
	}

	public ObjectProperty<LocalDate> sundayDisplayedProperty() {
		return sundayDisplayedProperty;
	}

	public LocalDate getSundayDisplayed() {
		return sundayDisplayedProperty.get();
	}

	public void setSundayDisplayed(LocalDate sundayDisplayed) {
		if (sundayDisplayed.getDayOfWeek() == DayOfWeek.SUNDAY) {
			sundayDisplayedProperty.set(sundayDisplayed);
		} else {
			throw new IllegalArgumentException("Given LocalDate is not a Sunday.");
		}
	}
}
