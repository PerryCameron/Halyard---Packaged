package com.ecsail.dto;

import com.ecsail.sql.select.SqlSelect;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class is for designing the polymorphic look of an invoice item row
 */

public class DbInvoiceDTO {
    private int id;
    private String fiscalYear;
    private String fieldName;
    private String widgetType;
    private double width;
    private IntegerProperty order;
    private boolean multiplied;
    private boolean price_editable;
    private boolean is_credit;
    private int maxQty;
    private boolean autoPopulate;
    private boolean itemized;
    /**
     * below not included in database
     */
    private FeeDTO fee;


    public DbInvoiceDTO(int id, String fiscalYear, String fieldName, String widgetType, double width, Integer order, boolean multiplied, boolean price_editable, boolean is_credit, int maxQty, boolean autoPopulate, boolean itemized) {
        this.id = id;
        this.fiscalYear = fiscalYear;
        this.fieldName = fieldName;
        this.widgetType = widgetType;
        this.width = width;
        this.order = new SimpleIntegerProperty(order);
        this.multiplied = multiplied;
        this.price_editable = price_editable;
        this.is_credit = is_credit;
        this.maxQty = maxQty;
        this.autoPopulate = autoPopulate;
        this.itemized = itemized;
    }

    public DbInvoiceDTO(String fiscalYear, Integer order) {  // for creation of new DTO
        this.id = 0;
        this.fiscalYear = fiscalYear;
        this.fieldName = "new entry";
        this.widgetType = "none";
        this.width = 65;
        this.order = new SimpleIntegerProperty(order);
        this.multiplied = false;
        this.price_editable = false;
        this.is_credit = false;
        this.maxQty = 0;
        this.autoPopulate = false;
        this.itemized = false;
    }

    public DbInvoiceDTO(String fiscalYear) {  // for creation of new DTO
        this.id = SqlSelect.getNextAvailablePrimaryKey("db_invoice", "ID");
        this.fiscalYear = fiscalYear;
        this.fieldName = "new entry";
        this.widgetType = "none";
        this.width = 65;
        this.order = new SimpleIntegerProperty(0);
        this.multiplied = false;
        this.price_editable = false;
        this.is_credit = false;
        this.maxQty = 0;
        this.autoPopulate = false;
        this.itemized = false;
    }

    public DbInvoiceDTO clone(DbInvoiceDTO dbInvoiceDTO) {
        System.out.println(dbInvoiceDTO.getFiscalYear());
        dbInvoiceDTO.setFieldName(this.getFieldName());
        dbInvoiceDTO.setWidgetType(this.getWidgetType());
        dbInvoiceDTO.setWidth(this.getWidth());
        dbInvoiceDTO.setOrder(this.getOrder());
        dbInvoiceDTO.setMultiplied(this.isMultiplied());
        dbInvoiceDTO.setPrice_editable(this.isPrice_editable());
        dbInvoiceDTO.setIsCredit(this.isCredit());
        dbInvoiceDTO.setMaxQty(this.getMaxQty());
        dbInvoiceDTO.setAutoPopulate(this.isAutoPopulate());
        dbInvoiceDTO.setItemized(this.isItemized());
        return dbInvoiceDTO;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
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

    public boolean isAutoPopulate() {
        return autoPopulate;
    }

    public void setAutoPopulate(boolean autoPopulate) {
        this.autoPopulate = autoPopulate;
    }

    public boolean isItemized() {
        return itemized;
    }

    public void setItemized(boolean itemized) {
        this.itemized = itemized;
    }

    @Override
    public String toString() {
        return "DbInvoiceDTO{" +
                "id=" + id +
                ", fiscalYear='" + fiscalYear + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", widgetType='" + widgetType + '\'' +
                ", width=" + width +
                ", order=" + order +
                ", multiplied=" + multiplied +
                ", price_editable=" + price_editable +
                ", is_credit=" + is_credit +
                ", maxQty=" + maxQty +
                ", autoPopulate=" + autoPopulate +
                ", itemized=" + itemized +
                ", fee=" + fee +
                '}';
    }
}
