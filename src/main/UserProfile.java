package main;

/**
 * 
 * @author watis
 *
 */
public class UserProfile {
	
	private String email;
	private String userName;
	private UserType type;
	
	UserProfile(){
		email = "";
		userName = "";
		type = UserType.UNKNOWN;
	}
	
	UserProfile(String email, String name, UserType type){
		if(isValidEmail(email)) this.email = email;
		if(isValidName(name)) this.userName = name;
		this.type = type;
	}
	
	boolean isValidEmail(String inputEmail) {
		//TODO
		return true;
	}
	
	boolean isValidName(String inputName) {
		//TODO
		return true;
	}
	
	//add info to a database
}

