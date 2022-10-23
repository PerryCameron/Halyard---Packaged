package com.ecsail.enums;

import java.util.Arrays;

public enum MembershipType {
	   FAMILYM("FM", "Family"), 
	   REGULAR("RM", "Regular"),
	   SOCIALM("SO", "Social"),
	   LAKEASS("LA", "Lake Associate"),
	   ACTASSO("AA", "Active Associate"),
	   LIFEMEM("LM", "Life Member"),
	   STUDENT("SM", "Student"),
	   RACEFELLOW("RF", "Race Fellow"),
	   NONRENE("NR", "Non-Renew")
	   ;
	 
	   private String code;
	   private String text;
	 
	   private MembershipType(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static MembershipType getByCode(String memTypeCode) {
		   return Arrays.stream(MembershipType.values())
				   .filter(g -> g.code == memTypeCode)
				   .findFirst().orElse(null);
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
