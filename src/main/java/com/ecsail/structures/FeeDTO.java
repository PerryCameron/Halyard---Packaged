package com.ecsail.structures;

import java.math.BigDecimal;

public class FeeDTO {
    private int feeId;
    private String fieldName;
    private BigDecimal fieldValue;
    private int fieldQuantity;
    private int feeYear;
    private String description;

    private int feeGroup;

    public FeeDTO(int feeId, String fieldName, BigDecimal fieldValue, int fieldQuantity, int feeYear, String description, int feeGroup) {
        this.feeId = feeId;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.fieldQuantity = fieldQuantity;
        this.feeYear = feeYear;
        this.description = description;
        this.feeGroup = feeGroup;
    }

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public BigDecimal getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(BigDecimal fieldValue) {
        this.fieldValue = fieldValue;
    }

    public int getFieldQuantity() {
        return fieldQuantity;
    }

    public void setFieldQuantity(int fieldQuantity) {
        this.fieldQuantity = fieldQuantity;
    }

    public int getFeeYear() {
        return feeYear;
    }

    public void setFeeYear(int feeYear) {
        this.feeYear = feeYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFeeGroup() {
        return feeGroup;
    }

    public void setFeeGroup(int feeGroup) {
        this.feeGroup = feeGroup;
    }

    @Override
    public String toString() {
        return "FeeDTO{" +
                "feeId=" + feeId +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue=" + fieldValue +
                ", fieldQuantity=" + fieldQuantity +
                ", feeYear=" + feeYear +
                ", description='" + description + '\'' +
                ", feeGroup=" + feeGroup +
                '}';
    }
}
