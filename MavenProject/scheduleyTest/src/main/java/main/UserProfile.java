package main;

/**
 * 
 * @author watis
 *
 */
public class UserProfile {
	
	private int uniqueID;
	private String email;
	private String name;
	private String password;
	private UserType userType;
	
	public UserProfile(){
		email = "";
		name = "";
		userType = UserType.UNKNOWN;
		password = "group29";
	}
	
	public UserProfile(String email, String name, UserType type){
		if(isValidEmail(email)) this.email = email;
		if(isValidName(name)) this.name = name;
		password = "group29";
		this.userType = type;
	}
	
	public UserProfile(String email, String name, String userType){
		this(email, name, UserType.getValueOf(userType));
	}
	
	public UserProfile(String email, String name, int userType){
		this(email, name, UserType.values()[userType]);
	}
	
	boolean isValidEmail(String inputEmail) {
		//TODO
		return true;
	}
	
	boolean isValidName(String inputName) {
		//TODO
		return true;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getUserTypeInt() {
		return userType.ordinal();
	}
	
	public UserType getUserType() {
		return userType;
	}
	
	public void setUniqueID(int id) {
		uniqueID = id;
	}
	
	public int getUniqueID() {
		return uniqueID;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this.email.equals(((UserProfile) other).getEmail())) {
			return true;
		}
		return false;
	}
}
