package com.ecsail.enums;

public enum Officer {
		// Officers
	   COMMODO("CO", "Commodore"),
	   VICECOM("VC", "Vice Commodore"),
	   PASTCOM("PC", "Past Commodore"),
	   CHAIRMA("CB", "Chairman of the Board"),
	   GROUNDS("GC", "Grounds Committee"),
	   FACILIT("FM", "Facility Manager"),
	   TREASUR("TR", "Treasurer"),
	   SECRETA("SE", "Secretary"),
		// Chairs
	   HARBORM("HM", "Harbormaster"),
	   AHARBOR("AH", "Assistant Harbormaster"),
	   AGROUND("AG", "Assistant Grounds Committee"),
	   MEMBERS("MS", "Membership"),
	   AMEMBER("AM", "Assistant Membership"),
	   PUBLICI("PU", "Publicity"),
	   APUBLIC("AP", "Assistant Publicity"),
	   RACINGC("RA", "Racing"),
	   ARACING("AR", "Assistant Racing"),
	   SAFETYA("SM", "Safety and Education"),
	   JUNIORP("JP", "Junior Program"),
	   AJUNIOR("AJ", "Assistant Junior Program"),
	   ASAFETY("AS", "Assistant S and E"),
	   SOCIALC("SO", "Social"),
	   ASOCIAL("SA", "Assistant Social"),
	   SHIPSTO("SS", "Ships Store"),
	   WINTERA("WA", "Winter Activities"),

	   BDMEMBE("BM", "Board Member")
	   ;
	 
	   private String code;
	   private String text;
	 
	   private Officer(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static Officer getByCode(String officerCode) {
	       for (Officer g : Officer.values()) {
	           if (g.code.equals(officerCode)) {
	               return g;
	           }
	       }
	       return null;
	   }
	   
	   public static String getNameByCode(String officerCode) {
	       for (Officer g : Officer.values()) {
	           if (g.code.equals(officerCode)) {
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
