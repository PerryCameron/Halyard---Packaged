package com.ecsail.enums;

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
	       for (PaymentType g : PaymentType.values()) {
	           if (g.code.equals(paymentCode)) {
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
