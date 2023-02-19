package com.ecsail;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class StringTools {

	public static String changeEmptyStringToZero(String input) {
		if(input != null) {
			if(input.equals("")) input= "0";
			else {
				if(!isNumeric(input)) input = "0";
			}
		}
		return input;
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	public static boolean isBigDecimal(String str) {
		try {
			new BigDecimal(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String ParseQuery(String query, String[] parameters) {
		String[] slices = query.split("\\?");
		for(int i = 0; i < parameters.length; i++) slices[i] = slices[i].concat(parameters[i]);
		StringJoiner joiner = new StringJoiner("");
		for(int i = 0; i < slices.length; i++) joiner.add(slices[i]);
		return joiner.toString();
	}
	
}
