package com.ecsail.enums;

public enum MemberType {
	   PRIMA(1, "Primary"), 
	   SECON(2, "Secondary"),
	   CHILD(3, "Dependant");
	 
	   private Integer code;
	   private String text;
	 
	   private MemberType(Integer code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public Integer getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static MemberType getByCode(int memberCode) {
	       for (MemberType g : MemberType.values()) {
	           if (g.code == memberCode) {
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
