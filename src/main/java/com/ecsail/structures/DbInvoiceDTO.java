package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class DbInvoiceDTO {
    private int id;
    private String year;
    private String fieldName;
    private String widgetType;
    private double width;
    private IntegerProperty order;
    private boolean multiplied;
    private boolean price_editable;
    private boolean is_credit;
    private int maxQty;

    private boolean autoPopulate;
    /**
     * below not included in database
     */
    private FeeDTO fee;
    private ObservableList<InvoiceItemDTO> items;


    public DbInvoiceDTO(int id, String year, String fieldName, String widgetType, double width, Integer order, boolean multiplied, boolean price_editable, boolean is_credit, int maxQty, boolean autoPopulate) {
        this.id = id;
        this.year = year;
        this.fieldName = fieldName;
        this.widgetType = widgetType;
        this.width = width;
        this.order = new SimpleIntegerProperty(order);
        this.multiplied = multiplied;
        this.price_editable = price_editable;
        this.is_credit = is_credit;
        this.maxQty = maxQty;
        this.autoPopulate = autoPopulate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public final IntegerProperty orderProperty() {
        return this.order;
    }

    public final int getOrder() {
        return this.orderProperty().get();
    }


    public final void setOrder(final int order) {
        this.orderProperty().set(order);
    }

    public boolean isMultiplied() {
        return multiplied;
    }

    public void setMultiplied(boolean multiplied) {
        this.multiplied = multiplied;
    }

    public boolean isPrice_editable() {
        return price_editable;
    }

    public void setPrice_editable(boolean price_editable) {
        this.price_editable = price_editable;
    }

    public boolean isCredit() {
        return is_credit;
    }

    public void setIsCredit(boolean is_credit) {
        this.is_credit = is_credit;
    }

    public int getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(int maxQty) {
        this.maxQty = maxQty;
    }

    public FeeDTO getFee() {
        return fee;
    }

    public void setFee(FeeDTO fee) {
        this.fee = fee;
    }

    public ObservableList<InvoiceItemDTO> getItems() {
        return items;
    }
    public void setItems(ObservableList<InvoiceItemDTO> items) {
        this.items = items;
    }

    public boolean isAutoPopulate() {
        return autoPopulate;
    }

    public void setAutoPopulate(boolean autoPopulate) {
        this.autoPopulate = autoPopulate;
    }

    @Override
    public String toString() {
        return "InvoiceWidgetDTO{" +
                "id=" + id +
                ", year='" + year + '\'' +
                ", objectName='" + fieldName + '\'' +
                ", widgetType='" + widgetType + '\'' +
                ", width=" + width +
                ", order=" + order +
                ", multiplied=" + multiplied +
                ", price_editable=" + price_editable +
                ", is_credit=" + is_credit +
                ", maxQty=" + maxQty +
                ", autoPopulate=" + autoPopulate +
                '}';
    }
}
