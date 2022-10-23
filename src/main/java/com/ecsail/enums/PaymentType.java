package com.ecsail.enums;

import java.util.Arrays;

public enum PaymentType {
	 CHECK("CH", "Check"), 
	 CASH("CA", "Cash"), 
	 CREDI("CR", "Credit");	
	 
	   private String code;
	   private String text;
	 
	   private PaymentType(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static PaymentType getByCode(String paymentCode) {
		   return Arrays.stream(PaymentType.values())
				   .filter(g -> g.code.equals(paymentCode))
				   .findFirst().orElse(null);
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
