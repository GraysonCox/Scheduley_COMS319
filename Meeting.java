package project;

import java.util.ArrayList;

public class Meeting {
	
	private final String DATE_FORMAT = ""; //will need to determine proper format to follow

	private String name;
	private ArrayList<UserProfile> members;
	private String shortDescription; // optional
	private int duration; //Need better data type

	public Meeting() {
		name = "Unspecified";
		duration = 0;
	}
	
	
}
