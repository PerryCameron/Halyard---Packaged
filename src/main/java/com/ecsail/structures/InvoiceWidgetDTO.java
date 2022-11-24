package com.ecsail.structures;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

public class InvoiceWidgetDTO {
    private int id;
    private String year;
    private String objectName;
    private String widgetType;
    private double width;
    private int order;
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


    public InvoiceWidgetDTO(int id, String year, String objectName, String widgetType, double width, int order, boolean multiplied, boolean price_editable, boolean is_credit, int maxQty, boolean autoPopulate) {
        this.id = id;
        this.year = year;
        this.objectName = objectName;
        this.widgetType = widgetType;
        this.width = width;
        this.order = order;
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

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public boolean isIs_credit() {
        return is_credit;
    }

    public void setIs_credit(boolean is_credit) {
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
                ", objectName='" + objectName + '\'' +
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
