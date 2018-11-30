package main;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MeetingsDAOSpringBootTest {
	
	public static MeetingsDAOSpringBoot meetingsDAOSB;
	public static Timestamp ts;

	@BeforeAll
	public static void setUp() {
		meetingsDAOSB = new MeetingsDAOSpringBoot();
		ts = new Timestamp(System.currentTimeMillis());
	}
	
	@Test
	public void getAllMeetingsTest_Success() {
		Meeting[] actual = meetingsDAOSB.getAllMeetings();
		Meeting[] expected = new Meeting[2];
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting");
		Meeting two = new Meeting("g7 meeting");
		expected[0] = one;
		expected[1] = two;
		for(int i = 0; i < actual.length; i++) {
			Assert.assertTrue(expected[i].equals(actual[i]));
		}
	}
	
	@Test 
	public void getMeetingByNameTest_Success() {
		Meeting actual = meetingsDAOSB.getMeetingByName("g7 meeting");
		Meeting expected = new Meeting("g7 meeting");
		Assert.assertTrue(actual.equals(expected));
	}
	
	@Test
	public void insertMeetingTest_Success() {
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 23);
		int actual = meetingsDAOSB.insertMeeting(newMeeting);
		Assert.assertEquals(200, actual);
		
		meetingsDAOSB.deleteMeeting(newMeeting.getName());
	}
	
	@Test
	public void deleteMeeting_Success() {
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 23);
		meetingsDAOSB.insertMeeting(newMeeting);
		
		String meetingToDelete = "Esoteric Order of Dagon's bi-montly bingo";
		int actual = meetingsDAOSB.deleteMeeting(meetingToDelete);
		Assert.assertEquals(200, actual);
	}
	
	@Test 
	public void deleteMeeting_Fail() {
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 23);
		meetingsDAOSB.insertMeeting(newMeeting);
		
		String meetingToDelete = "Esoteric Order of Dagon's bi-montly bingo";
		int actual = meetingsDAOSB.deleteMeeting(meetingToDelete);
		Assert.assertEquals(200, actual);
	}
	
	@Test
	public void insertMeetingTest_Fail() {
		int actual = meetingsDAOSB.deleteMeeting("Not a real Meeting");
		Assert.assertEquals(400, actual);
	}
	
	@Test
	public void getMeetingsByID_Success() {
		Meeting[] actual = meetingsDAOSB.getMeetingsByMeetingSpaceID(3);
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
		Meeting[] actual = meetingsDAOSB.getAllMeetingsByWeek(3, dateTime);
		Timestamp input = Timestamp.valueOf(dateTime);
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting", input, 60, 23);
		Assert.assertTrue(one.equals((Meeting) actual[0]));
	}
}