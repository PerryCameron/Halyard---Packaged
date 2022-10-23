package com.ecsail.enums;


import java.util.Arrays;

public enum PhoneType {
	 HOME("H", "Home"), 
	 WORK("W", "Work"), 
	 CELL("C", "Cell"),
     EMER("E", "Emergency");	
	 
	   private String code;
	   private String text;
	 
	   private PhoneType(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static PhoneType getByCode(String phoneCode) {
	       return Arrays.stream(PhoneType.values())
				   .filter(g -> g.code.equals(phoneCode))
				   .findFirst().orElse(null);
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
