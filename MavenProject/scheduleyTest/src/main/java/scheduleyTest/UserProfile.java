package scheduleyTest;

/**
 * 
 * @author watis
 *
 */
public class UserProfile {
	
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
	
	public int getUserType() {
		return userType.ordinal();
	}
	
	//TODO add equals6
	@Override
	public boolean equals(Object other) {
		if(this.email.equals(((UserProfile) other).getEmail())) {
			return true;
		}
		return false;
	}
	
	
	//add info to a database
}

