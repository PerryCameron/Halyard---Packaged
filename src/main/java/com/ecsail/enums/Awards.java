package com.ecsail.enums;

import java.util.Arrays;

public enum Awards {
	   SPORTSMANSHIP_AWARD("SA", "Sportsmanship Award"),
	   SAILOR_OF_THE_YEAR("SY", "Sailor of the Year")
	   ;
	 
	   private final String code;
	   private final String text;
	 
	   Awards(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static Awards getByCode(String awardCode) {
		   return Arrays.stream(Awards.values())
				   .filter(g -> g.code == awardCode)
				   .findFirst().orElse(null);
	   }
	   
	   public static String getNameByCode(String awardCode) {
		   return Arrays.stream(Awards.values())
				   .filter(g -> g.code.equals(awardCode))
				   .map(g -> g.getText() )
				   .findFirst().orElse(null);
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
