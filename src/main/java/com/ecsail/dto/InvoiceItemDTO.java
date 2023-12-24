package com.ecsail.dto;

import javafx.beans.property.*;

public class InvoiceItemDTO {

    IntegerProperty id;
    IntegerProperty invoiceId;
    IntegerProperty msId;
    IntegerProperty year;
    StringProperty fieldName;
    BooleanProperty credit;
    StringProperty value;
    IntegerProperty qty;

    public InvoiceItemDTO(Integer id, Integer invoiceId, Integer msId, Integer year, String fieldName, Boolean credit, String value, Integer qty) {
        this.id = new SimpleIntegerProperty(id);
        this.invoiceId = new SimpleIntegerProperty(invoiceId);
        this.msId = new SimpleIntegerProperty(msId);
        this.year = new SimpleIntegerProperty(year);
        this.fieldName = new SimpleStringProperty(fieldName);
        this.credit = new SimpleBooleanProperty(credit);
        this.value = new SimpleStringProperty(value);
        this.qty = new SimpleIntegerProperty(qty);
    }

    public InvoiceItemDTO(Integer invoiceId, Integer msId, Integer year, String fieldName) {
        this.id = new SimpleIntegerProperty(0);
        this.invoiceId = new SimpleIntegerProperty(invoiceId);
        this.msId = new SimpleIntegerProperty(msId);
        this.year = new SimpleIntegerProperty(year);
        this.fieldName = new SimpleStringProperty(fieldName);
        this.credit = new SimpleBooleanProperty(false);
        this.value = new SimpleStringProperty("0.00");
        this.qty = new SimpleIntegerProperty(0);
    }

    public InvoiceItemDTO(Integer invoiceId, Integer msId, Integer year, String fieldName, Boolean credit) {
        this.id = new SimpleIntegerProperty(0);
        this.invoiceId = new SimpleIntegerProperty(invoiceId);
        this.msId = new SimpleIntegerProperty(msId);
        this.year = new SimpleIntegerProperty(year);
        this.fieldName = new SimpleStringProperty(fieldName);
        this.credit = new SimpleBooleanProperty(credit);
        this.value = new SimpleStringProperty("0.00");
        this.qty = new SimpleIntegerProperty(0);
    }

    public InvoiceItemDTO(Integer year, String fieldName) {
        this.id = new SimpleIntegerProperty(0);
        this.invoiceId = new SimpleIntegerProperty(0);
        this.msId = new SimpleIntegerProperty(0);
        this.year = new SimpleIntegerProperty(year);
        this.fieldName = new SimpleStringProperty(fieldName);
        this.credit = new SimpleBooleanProperty(false);
        this.value = new SimpleStringProperty("0.00");
        this.qty = new SimpleIntegerProperty(0);
    }



//    (0, invoiceId, msid, year, dbInvoiceDTO.getFieldName()
//            , dbInvoiceDTO.isCredit(), "0.00", 0);

    public final IntegerProperty idProperty() {
        return this.id;
    }


    public final int getId() {
        return this.idProperty().get();
    }


    public final void setId(final int id) {
        this.idProperty().set(id);
    }


    public final IntegerProperty invoiceIdProperty() {
        return this.invoiceId;
    }


    public final int getInvoiceId() {
        return this.invoiceIdProperty().get();
    }


    public final void setInvoiceId(final int invoiceId) {
        this.invoiceIdProperty().set(invoiceId);
    }


    public final IntegerProperty msIdProperty() {
        return this.msId;
    }


    public final int getMsId() {
        return this.msIdProperty().get();
    }


    public final IntegerProperty yearProperty() {
        return this.year;
    }


    public final int getYear() {
        return this.yearProperty().get();
    }


    public final void setYear(final int year) {
        this.yearProperty().set(year);
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



    public final BooleanProperty creditProperty() {
        return this.credit;
    }


    public final boolean isCredit() {
        return this.creditProperty().get();
    }


    public final void setCredit(final boolean credit) {
        this.creditProperty().set(credit);
    }


    public final StringProperty valueProperty() {
        return this.value;
    }


    public final String getValue() {
        return this.valueProperty().get();
    }


    public final void setValue(final String value) {
        this.valueProperty().set(value);
    }


    public final IntegerProperty qtyProperty() {
        return this.qty;
    }


    public final int getQty() {
        return this.qtyProperty().get();
    }


    public final void setQty(final int qty) {
        this.qtyProperty().set(qty);
    }

    @Override
    public String toString() {
        return "InvoiceItemDTO{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", msId=" + msId +
                ", year=" + year +
                ", fieldName=" + fieldName +
                ", credit=" + credit +
                ", value=" + value +
                ", qty=" + qty +
                '}';
    }
}
