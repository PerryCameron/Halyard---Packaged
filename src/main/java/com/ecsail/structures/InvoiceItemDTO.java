package com.ecsail.structures;

import javafx.beans.property.*;

public class InvoiceItemDTO {

    IntegerProperty id;
    IntegerProperty invoiceId;
    IntegerProperty msId;
    IntegerProperty year;
    StringProperty itemType;
    BooleanProperty multiplied;
    BooleanProperty credit;
    StringProperty value;
    IntegerProperty qty;

    public InvoiceItemDTO(Integer id, Integer invoiceId, Integer msId, Integer year, String itemType, Boolean multiplied, Boolean credit, String value, Integer qty) {
        this.id = new SimpleIntegerProperty(id);
        this.invoiceId = new SimpleIntegerProperty(invoiceId);
        this.msId = new SimpleIntegerProperty(msId);
        this.year = new SimpleIntegerProperty(year);
        this.itemType = new SimpleStringProperty(itemType);
        this.multiplied = new SimpleBooleanProperty(multiplied);
        this.credit = new SimpleBooleanProperty(credit);
        this.value = new SimpleStringProperty(value);
        this.qty = new SimpleIntegerProperty(qty);
    }

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


    public final void setMsId(final int msId) {
        this.msIdProperty().set(msId);
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


    public final StringProperty itemTypeProperty() {
        return this.itemType;
    }


    public final String getItemType() {
        return this.itemTypeProperty().get();
    }


    public final void setItemType(final String itemType) {
        this.itemTypeProperty().set(itemType);
    }


    public final BooleanProperty multipliedProperty() {
        return this.multiplied;
    }


    public final boolean isMultiplied() {
        return this.multipliedProperty().get();
    }


    public final void setMultiplied(final boolean multiplied) {
        this.multipliedProperty().set(multiplied);
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
                ", itemType=" + itemType +
                ", multiplied=" + multiplied +
                ", credit=" + credit +
                ", value=" + value +
                ", qty=" + qty +
                '}';
    }
}