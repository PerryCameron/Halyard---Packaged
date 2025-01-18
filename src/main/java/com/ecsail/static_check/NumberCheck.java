package com.ecsail.static_check;

import java.math.BigDecimal;


public class NumberCheck {

    public NumberCheck() {
    }

    public static BigDecimal StringToBigDecimal(String number) {
        BigDecimal bigDecimal;
        if(isNumeric(number))
        bigDecimal = new BigDecimal(number);
        else
        bigDecimal = new BigDecimal("0.00");
        return bigDecimal;
    }

    private static boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
