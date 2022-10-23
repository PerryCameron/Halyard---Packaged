package com.ecsail.enums;

import java.util.Arrays;

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
		   return Arrays.stream(KeelType.values())
				   .filter(g -> g.code.equals(genderCode))
				   .findFirst().orElse(null);
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
