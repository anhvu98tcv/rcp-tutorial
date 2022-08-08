package rcptutorial.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class Utils {
	
	public static final String regexName = "([a-zA-Z]{2,30}\s*)+";
	public static final String regexEmail= "^(.+)@(\\S+)$";

	public static String generateId() {
		String uuidStr = UUID.randomUUID().toString();
		return uuidStr.substring(uuidStr.length() - 5);
	}
	
	public static boolean patternMatches(String input, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(input)
	      .matches();
	}
	
	public static boolean isNullOrEmpty(String input) {
		if (input == null || input.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
}
