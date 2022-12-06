package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class FeeDTO {
    private IntegerProperty feeId;
    private StringProperty fieldName;
    private StringProperty fieldValue;
    private IntegerProperty fieldQuantity;
    private IntegerProperty feeYear;
    private StringProperty description;
    private StringProperty groupName;

    public FeeDTO(Integer feeId, String fieldName, String fieldValue, Integer fieldQuantity, Integer feeYear, String description, String groupName) {
        this.feeId = new SimpleIntegerProperty(feeId);
        this.fieldName = new SimpleStringProperty(fieldName);
        this.fieldValue = new SimpleStringProperty(fieldValue);
        this.fieldQuantity = new SimpleIntegerProperty(fieldQuantity);
        this.feeYear = new SimpleIntegerProperty(feeYear);
        this.description = new SimpleStringProperty(description);
        this.groupName = new SimpleStringProperty(groupName);
    }

    public final IntegerProperty feeIdProperty() {
        return this.feeId;
    }


    public final int getFeeId() {
        return this.feeIdProperty().get();
    }


    public final void setFeeId(final int feeId) {
        this.feeIdProperty().set(feeId);
    }


    public final StringProperty fieldNameProperty() {
        return this.fieldName;
    }


    public final String getFieldName() {
        return this.fieldNameProperty().get();
    }


    public final void setFieldName(final String fieldName) {
        this.fieldNameProperty().set(fieldName);
    }


    public final StringProperty fieldValueProperty() {
        return this.fieldValue;
    }


    public final String getFieldValue() {
        return this.fieldValueProperty().get();
    }


    public final void setFieldValue(final String fieldValue) {
        this.fieldValueProperty().set(fieldValue);
    }


    public final IntegerProperty fieldQuantityProperty() {
        return this.fieldQuantity;
    }


    public final int getFieldQuantity() {
        return this.fieldQuantityProperty().get();
    }


    public final void setFieldQuantity(final int fieldQuantity) {
        this.fieldQuantityProperty().set(fieldQuantity);
    }


    public final IntegerProperty feeYearProperty() {
        return this.feeYear;
    }


    public final int getFeeYear() {
        return this.feeYearProperty().get();
    }


    public final void setFeeYear(final int feeYear) {
        this.feeYearProperty().set(feeYear);
    }


    public final StringProperty descriptionProperty() {
        return this.description;
    }


    public final String getDescription() {
        return this.descriptionProperty().get();
    }


    public final void setDescription(final String description) {
        this.descriptionProperty().set(description);
    }


    public final StringProperty groupNameProperty() {
        return this.groupName;
    }


    public final String getGroupName() {
        return this.groupNameProperty().get();
    }


    public final void setGroupName(final String groupName) {
        this.groupNameProperty().set(groupName);
    }

    @Override
    public String toString() {
        return "FeeDTO{" +
                "feeId=" + feeId +
                ", fieldName=" + fieldName +
                ", fieldValue=" + fieldValue +
                ", fieldQuantity=" + fieldQuantity +
                ", feeYear=" + feeYear +
                ", description=" + description +
                ", groupName=" + groupName +
                '}';
    }
}
