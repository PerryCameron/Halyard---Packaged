package com.ecsail;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

public class StringTools {

	public static String getBirthday(LocalDate birthday) {
		if (birthday == null) {
			return "1900-01-01"; // Default to "1900-01-01" when null
		}
		return birthday.toString();
	}

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

	public static String unwrapToString(Object obj) {
		if (obj == null) {
			return "";
		} else if (obj instanceof SimpleStringProperty) {
			SimpleStringProperty stringProperty = (SimpleStringProperty) obj;
			return stringProperty.get() != null ? stringProperty.get() : "";
		} else if (obj instanceof SimpleIntegerProperty) {
			SimpleIntegerProperty intProperty = (SimpleIntegerProperty) obj;
			return Integer.toString(intProperty.get());
		} else if (obj instanceof SimpleBooleanProperty) {
			SimpleBooleanProperty boolProperty = (SimpleBooleanProperty) obj;
			return boolProperty.get() != false ? Boolean.toString(boolProperty.get()) : "";
		} else {
			return obj.toString() != null ? obj.toString() : "";
		}
	}

	public static <T> String returnFieldValueAsString(Field field, T pojo) {
		String result;
		try {
			result = unwrapToString(field.get(pojo));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static boolean isValidInteger(String msId) {
		if (msId == null || msId.trim().isEmpty()) {
			return false;
		}
		try {
			Integer.parseInt(msId.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
