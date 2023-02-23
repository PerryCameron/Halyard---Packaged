package com.ecsail.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DbBoatSettingsDTO {
    private int id;
    private String name;
    private String controlType;
    private String dataType;
    private String fieldName;
    private String getter;
    private boolean searchable ;
    private boolean exportable;

    public DbBoatSettingsDTO(int id, String name, String controlType, String dataType, String fieldName, String getter, boolean searchable, boolean exportable) {
        this.id = id;
        this.name = name;
        this.controlType = controlType;
        this.dataType = dataType;
        this.fieldName = fieldName;
        this.getter = getter;
        this.searchable = searchable;
        this.exportable = exportable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean isExportable() {
        return exportable;
    }

    public void setExportable(boolean exportable) {
        this.exportable = exportable;
    }
}
