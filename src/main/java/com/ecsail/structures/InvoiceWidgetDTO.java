package com.ecsail.structures;

public class InvoiceWidgetDTO {
    private int id;
    private String date;
    private String objectName;
    private String widgetType;
    private double width;
    private int order;
    private boolean multiplied;
    private boolean price_editable;
    private boolean is_credit;
    private String listener_type;
    private FeeDTO fee;

    private InvoiceItemDTO item;


    public InvoiceWidgetDTO(int id, String date, String objectName, String widgetType, double width, int order, boolean multiplied, boolean price_editable, boolean is_credit, String listener_type) {
        this.id = id;
        this.date = date;
        this.objectName = objectName;
        this.widgetType = widgetType;
        this.width = width;
        this.order = order;
        this.multiplied = multiplied;
        this.price_editable = price_editable;
        this.is_credit = is_credit;
        this.listener_type = listener_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getListener_type() {
        return listener_type;
    }

    public void setListener_type(String listener_type) {
        this.listener_type = listener_type;
    }

    public FeeDTO getFee() {
        return fee;
    }

    public void setFee(FeeDTO fee) {
        this.fee = fee;
    }

    public InvoiceItemDTO getItem() {
        return item;
    }

    public void setItem(InvoiceItemDTO item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "InvoiceWidgetDTO{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", objectName='" + objectName + '\'' +
                ", widgetType='" + widgetType + '\'' +
                ", width=" + width +
                ", order=" + order +
                ", multiplied=" + multiplied +
                ", price_editable=" + price_editable +
                ", is_credit=" + is_credit +
                ", listener_type='" + listener_type + '\'' +
                ", fee=" + fee +
                '}';
    }
}
