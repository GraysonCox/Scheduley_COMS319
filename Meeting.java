package project;

import java.util.ArrayList;

public class Meeting {
	
	private final String DATE_FORMAT = ""; //will need to determine proper format to follow

	private String name;
	private ArrayList<UserProfile> members;
	private ArrayList<String> shortDescription; // optional
	private int duration; //Need better data type
	
	
	public Meeting() {
		name = "";
		duration = 0;
	}
}
