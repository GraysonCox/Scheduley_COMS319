package main;

import java.sql.Timestamp;

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
		Meeting two = new Meeting("Group 29 Thanksgiving");
		expected.add(two);
		Meeting three = new Meeting("Supreme Soviet Meeting");
		expected.add(three);
		for(int i = 0; i < actual.size(); i++) {
			Assert.assertTrue(((Meeting) actual.get(i)).equals(((Meeting) expected.get(i))));
		}
	}
	
	@Test 
	void getMeetingByNameTest_Success() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		Meeting actual = curr.getMeetingByName("Group 29 Thanksgiving");
		Meeting expected = new Meeting("Group 29 Thanksgiving");
		Assert.assertTrue(actual.equals(expected));
	}
	
	
	@Test
	void getMeetingsByID_Success() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		JSONArray actual = curr.getMeetingsByMeetingSpaceID(3);
		Meeting one = new Meeting("Flat Earth Society #29's Secret Meeting", null, 60, 3);
		Meeting two = new Meeting("Group 29 Thanksgiving", null, 120, 3);
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
	
	
}
