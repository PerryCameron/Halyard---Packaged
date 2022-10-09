package com.ecsail;

import java.math.BigDecimal;

public class FixInput {

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
	
}
