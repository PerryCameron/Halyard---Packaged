package com.ecsail.structures;

import java.time.LocalDate;

public class InvoiceWidget {
    int id;
    String date;
    String objectName;
    String widgetType;
    boolean multiplied;
    boolean price_editable;
    boolean is_credit;
    String listener_type;

    public InvoiceWidget(int id, String date, String objectName, String widgetType, boolean multiplied, boolean price_editable, boolean is_credit, String listener_type) {
        this.id = id;
        this.date = date;
        this.objectName = objectName;
        this.widgetType = widgetType;
        this.multiplied = multiplied;
        this.price_editable = price_editable;
        this.is_credit = is_credit;
        this.listener_type = listener_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getListener_type() {
        return listener_type;
    }

    public void setListener_type(String listener_type) {
        this.listener_type = listener_type;
    }
}
