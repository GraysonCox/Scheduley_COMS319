package main;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ServerConnection;
import main.UserDAOMySQL;
import main.UserProfile;
import main.UserType;

public class MeetingsDAOSpringBootTest {

	@BeforeEach
	void setUp() {
	}
	
	@Test 
	void getMeetingByNameTest() {
		MeetingsDAOSpringBoot curr = new MeetingsDAOSpringBoot();
		String meeting = curr.getMeetingByName("Group 29 Thanksgiving");
		System.out.println(meeting);
	}
	
}
