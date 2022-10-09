package com.ecsail.enums;

public enum KeelType {
	   FIN("FI", "Fin"), 
	   WING("WI", "Wing"), 
	   SWING("SW", "Swing"),
	   CENT("CE", "Centerboard"),
	   DAGG("DA", "Daggerboard"),
	   FULL("FU", "Full"),
	   BULB("BU", "Bulb"),
	   RETR("RE", "Retractable"),
	   OTHER("OT", "Other");
	 
	   private String code;
	   private String text;
	 
	   private KeelType(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static KeelType getByCode(String genderCode) {
	       for (KeelType g : KeelType.values()) {
	           if (g.code.equals(genderCode)) {
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
