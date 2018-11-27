package main;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MeetingsDAOSpringBootTest {

	@BeforeEach
	void setUp() {
	}
	
	@Test
	void getAllMeetingsTest_Success() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		JSONArray actual = curr.getAllMeetings();
		JSONArray expected = new JSONArray();
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting");
		expected.add(one);
		Meeting two = new Meeting("g7 meeting");
		expected.add(two);
		for(int i = 0; i < actual.size(); i++) {
			Assert.assertTrue(((Meeting) actual.get(i)).equals(((Meeting) expected.get(i))));
		}
	}
	
	@Test
	void getAllMeetingsTest_Success2() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		Meeting[] actual = curr.getAllMeetings2();
		Meeting[] expected = new Meeting[2];
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting");
		Meeting two = new Meeting("g7 meeting");
		expected[0] = one;
		expected[1] = two;
		for(int i = 0; i < actual.length; i++) {
			//System.out.println("Actual: " + actual[i].getName() + ", at index:" + i);
			//System.out.println("Expected: " + expected[i].getName() + ", at index:" + i);
			Assert.assertTrue(expected[i].equals(actual[i]));
		}
	}
	
	@Test 
	void getMeetingByNameTest_Success() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		Meeting actual = curr.getMeetingByName("g7 meeting");
		Meeting expected = new Meeting("g7 meeting");
		Assert.assertTrue(actual.equals(expected));
	}
	
	
	@Test
	void getMeetingsByID_Success() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		JSONArray actual = curr.getMeetingsByMeetingSpaceID(3);
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting", null, 60, 3);
		Meeting two = new Meeting("g7 meeting", null, 120, 1);
		JSONArray expected = new JSONArray();
		expected.add(one);
		expected.add(two);
		for(int i = 0; i < actual.size(); i++) {
			Assert.assertTrue(((Meeting) actual.get(i)).equals(((Meeting) expected.get(i))));
		}
	}
	
	@Test
	void insertMeetingTest_Success() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 1);
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		int actual = curr.insertMeeting(newMeeting);
		Assert.assertEquals(200, actual);
		
		curr.deleteMeeting(newMeeting.getName());
	}
	
	@Test
	void deleteMeeting_Success() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 1);
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		curr.insertMeeting(newMeeting);
		
		String meetingToDelete = "Esoteric Order of Dagon's bi-montly bingo";
		int actual = curr.deleteMeeting(meetingToDelete);
		Assert.assertEquals(200, actual);
	}
	
	@Test 
	void deleteMeeting_Fail() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Meeting newMeeting = new Meeting("Esoteric Order of Dagon's bi-montly bingo", ts, 120, 1);
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		curr.insertMeeting(newMeeting);
		
		String meetingToDelete = "Esoteric Order of Dagon's bi-montly bingo";
		int actual = curr.deleteMeeting(meetingToDelete);
		Assert.assertEquals(200, actual);
	}
	
	@Test
	void insertMeetingTest_Fail() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		int actual = curr.deleteMeeting("Not a real Meeting");
		Assert.assertEquals(400, actual);
	}
	
	@Test
	void getMeetingsByID_Success2() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		Meeting[] actual = curr.getMeetingsByMeetingSpaceID2(3);
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting", null, 60, 3);
		Meeting[] expected = new Meeting[2];
		expected[0] = one;
		for(int i = 0; i < actual.length; i++) {
			Assert.assertTrue(expected[i].equals(actual[i]));
		}
	}
	
	/*
	@Test
	void getAllMeetingsByWeekTest_Success() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse("2018-11-16 12:30", formatter);
		JSONArray actual = curr.getAllMeetingsByWeek(3, dateTime);
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting", null, 60, 3);
		System.out.println(actual.get(0).toString());
		Assert.assertTrue(one.equals((Meeting) actual.get(0)));
	}
	
	*/
}