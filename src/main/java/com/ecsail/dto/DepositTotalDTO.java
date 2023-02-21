package com.ecsail.dto;

public class DepositTotalDTO {
    String[] labels = {"Fees","Credit","Paid"};
    String[] fullLabels = {"Total Fees","Total Credit","Total Funds Received"};
    String[] values = new String[3];

    public DepositTotalDTO(String fees, String credit, String paid) {
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
    public String[] getFullLabels() { return fullLabels; }
}
