package com.ecsail.enums;


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
	       for (PhoneType g : PhoneType.values()) {
	           if (g.code.equals(phoneCode)) {
	               return g;
	           }
	       }
	       return null;
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
