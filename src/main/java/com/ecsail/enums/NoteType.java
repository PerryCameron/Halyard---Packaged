package com.ecsail.enums;

public enum NoteType {
	
	   NOTE("N", "Note"), 
	   WING("O", "Other"), 
	   PAYM("P", "Payment"),
	   BALE("B", "Balance"),
	   INVO("I", "Invoice");
	 
	   private String code;
	   private String text;
	 
	   private NoteType(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static NoteType getByCode(String genderCode) {
	       for (NoteType g : NoteType.values()) {
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
