package main;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MeetingsDAOSpringBootTest {
	
	public static DAOFactory springBootDBFactory;
	public static MeetingsDAO meetingsDAO;
	public static Timestamp ts;

	@BeforeAll
	public static void setUp() {
		springBootDBFactory = DAOFactory.getDAOFactory(DAOFactory.SPRING_BOOT);
		meetingsDAO = springBootDBFactory.getMeetingsDAO();
		
		ts = new Timestamp(System.currentTimeMillis());
	}
	
	@Test 
	public void getMeetingByNameTest_Success() {
		Meeting actual = meetingsDAO.getMeetingByName("g7 meeting");
		Meeting expected = new Meeting("g7 meeting");
		Assert.assertTrue(actual.equals(expected));
	}
	
	@Test
	public void insertMeetingTest_Success() {
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 23);
		int actual = meetingsDAO.insertMeeting(newMeeting);
		Assert.assertEquals(200, actual);
		
		meetingsDAO.deleteMeeting(newMeeting.getName());
	}
	
	@Test
	public void deleteMeeting_Success() {
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 23);
		meetingsDAO.insertMeeting(newMeeting);
		
		String meetingToDelete = "Esoteric Order of Dagon's bi-montly bingo";
		int actual = meetingsDAO.deleteMeeting(meetingToDelete);
		Assert.assertEquals(200, actual);
	}
	
	@Test 
	public void deleteMeeting_Fail() {
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 23);
		meetingsDAO.insertMeeting(newMeeting);
		
		String meetingToDelete = "Esoteric Order of Dagon's bi-montly bingo";
		int actual = meetingsDAO.deleteMeeting(meetingToDelete);
		Assert.assertEquals(200, actual);
	}
	
	@Test
	public void insertMeetingTest_Fail() {
		int actual = meetingsDAO.deleteMeeting("Not a real Meeting");
		Assert.assertEquals(400, actual);
	}
	
	@Test
	public void getMeetingsByID_Success() {
		Meeting[] actual = meetingsDAO.getMeetingsByMeetingSpaceID(3);
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting", ts, 60, 23);
		Meeting[] expected = new Meeting[2];
		expected[0] = one;
		for(int i = 0; i < actual.length; i++) {
			Assert.assertTrue(expected[i].equals(actual[i]));
		}
	}
	
	@Test
	public void getAllMeetingsByWeekTest_Success() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse("2018-11-16 12:30", formatter);
		Meeting[] actual = meetingsDAO.getAllMeetingsByWeek(3, dateTime);
		Timestamp input = Timestamp.valueOf(dateTime);
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting", input, 60, 23);
		Assert.assertTrue(one.equals((Meeting) actual[0]));
	}
	
	@Test
	public void getAllMeetingsTest_Success() {
		Meeting[] actual = meetingsDAO.getAllMeetings();
		Assert.assertTrue(actual.length == 11);
		
	}
	
}