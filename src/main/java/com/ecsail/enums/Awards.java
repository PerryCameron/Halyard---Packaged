package com.ecsail.enums;

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
	       for (Awards g : Awards.values()) {
	           if (g.code.equals(awardCode)) {
	               return g;
	           }
	       }
	       return null;
	   }
	   
	   public static String getNameByCode(String awardCode) {
	       for (Awards g : Awards.values()) {
	           if (g.code.equals(awardCode)) {
	               return g.getText();
	           }
	       }
	       return null;
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
