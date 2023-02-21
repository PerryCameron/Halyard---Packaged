package com.ecsail.dto;

import com.ecsail.sql.select.SqlSelect;
import javafx.beans.property.*;

public class FeeDTO {
    private final IntegerProperty feeId;
    private final StringProperty fieldName;
    private final StringProperty fieldValue;
    private final IntegerProperty dbInvoiceID;
    private final IntegerProperty feeYear;
    private final StringProperty description;
    private final BooleanProperty defaultFee;

    public FeeDTO(Integer feeId, String fieldName, String fieldValue, Integer dbInvoiceID, Integer feeYear, String description, boolean defaultFee) {
        this.feeId = new SimpleIntegerProperty(feeId);
        this.fieldName = new SimpleStringProperty(fieldName);
        this.fieldValue = new SimpleStringProperty(fieldValue);
        this.dbInvoiceID = new SimpleIntegerProperty(dbInvoiceID);
        this.feeYear = new SimpleIntegerProperty(feeYear);
        this.description = new SimpleStringProperty(description);
        this.defaultFee = new SimpleBooleanProperty(defaultFee);
    }

    public FeeDTO(String fieldName, String fieldValue, Integer dbInvoiceID, Integer feeYear, String description) {
        this.feeId = new SimpleIntegerProperty(SqlSelect.getNextAvailablePrimaryKey("fee","fee_id"));
        this.fieldName = new SimpleStringProperty(fieldName);
        this.fieldValue = new SimpleStringProperty(fieldValue);
        this.dbInvoiceID = new SimpleIntegerProperty(dbInvoiceID);
        this.feeYear = new SimpleIntegerProperty(feeYear);
        this.description = new SimpleStringProperty(description);
        this.defaultFee = new SimpleBooleanProperty(true);
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


    public final IntegerProperty dbInvoiceIDProperty() {
        return this.dbInvoiceID;
    }

    public final int getDbInvoiceID() {
        return this.dbInvoiceIDProperty().get();
    }

    public final void setDbInvoiceID(final int dbInvoiceID) {
        this.dbInvoiceIDProperty().set(dbInvoiceID);
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

    public final BooleanProperty defaultFeeProperty() {
        return this.defaultFee;
    }

    public final boolean isDefaultFee() {
        return this.defaultFeeProperty().get();
    }

    public final void setDefaultFee(final boolean defaultFee) {
        this.defaultFeeProperty().set(defaultFee);
    }

    @Override
    public String toString() {
        return "FeeDTO{" +
                "feeId=" + feeId +
                ", fieldName=" + fieldName +
                ", fieldValue=" + fieldValue +
                ", dbInvoiceID=" + dbInvoiceID +
                ", feeYear=" + feeYear +
                ", description=" + description +
                ", defaultFee=" + defaultFee +
                '}';
    }
}
