package project;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an enum type for Users, which helps define what the user will be able to do.
 * @author watis
 */
public enum UserType {
	ADMIN,
	MANAGER,
	EMPLOYEE,
	OTHER, //i.e. 3rd party or contractor
	UNKNOWN;
	
	private static Map<String, UserType> enumMap = 
			new HashMap<String, UserType>(UserType.values().length);
	
	static {
		for(UserType value : UserType.values()) {
			enumMap.put(value.name(), value);
		}
	}
	
	public static UserType getValueOf(String value) {
		return enumMap.containsKey(value) ? enumMap.get(value) : UNKNOWN;
	}
}