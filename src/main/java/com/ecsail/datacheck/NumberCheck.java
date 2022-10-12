package com.ecsail.datacheck;

import java.math.BigDecimal;


public class NumberCheck {

    public NumberCheck() {
    }

    public BigDecimal StringToBigDecimal(String number) {
        BigDecimal bigDecimal;
        if(isNumeric(number))
        bigDecimal = new BigDecimal(number);
        else
        bigDecimal = new BigDecimal("0.00");
        return bigDecimal;
    }

    private boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
