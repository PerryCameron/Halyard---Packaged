package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DbBoatDTO {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty controlType;
    private StringProperty dataType;
    private StringProperty fieldName;
    private IntegerProperty order;

    public DbBoatDTO(Integer id, String name, String controlType, String dataType, String fieldName, Integer order) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.controlType = new SimpleStringProperty(controlType);
        this.dataType = new SimpleStringProperty(dataType);
        this.fieldName = new SimpleStringProperty(fieldName);
        this.order = new SimpleIntegerProperty(order);
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


    public final StringProperty nameProperty() {
        return this.name;
    }


    public final String getName() {
        return this.nameProperty().get();
    }


    public final void setName(final String name) {
        this.nameProperty().set(name);
    }


    public final StringProperty controlTypeProperty() {
        return this.controlType;
    }


    public final String getControlType() {
        return this.controlTypeProperty().get();
    }


    public final void setControlType(final String controlType) {
        this.controlTypeProperty().set(controlType);
    }


    public final StringProperty dataTypeProperty() {
        return this.dataType;
    }


    public final String getDataType() {
        return this.dataTypeProperty().get();
    }


    public final void setDataType(final String dataType) {
        this.dataTypeProperty().set(dataType);
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


    public final IntegerProperty orderProperty() {
        return this.order;
    }


    public final int getOrder() {
        return this.orderProperty().get();
    }


    public final void setOrder(final int order) {
        this.orderProperty().set(order);
    }
}
