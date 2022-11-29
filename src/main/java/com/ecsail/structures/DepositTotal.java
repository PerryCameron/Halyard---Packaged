package com.ecsail.structures;

public class DepositTotal {
    String[] labels = {"Fees","Credit","Paid"};
    String[] values = new String[3];

    public DepositTotal(String fees, String credit, String paid) {
        this.values[0] = fees;
        this.values[1] = credit;
        this.values[2] = paid;
    }

    public String[] getLabels() {
        return labels;
    }

    public String[] getValues() {
        return values;
    }
}
