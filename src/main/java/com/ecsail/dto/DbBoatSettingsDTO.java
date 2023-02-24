package com.ecsail.dto;

public class DbBoatSettingsDTO {
    private int id;
    private String name;
    private String pojoName;
    private String controlType;
    private String fieldName;
    private String getter;
    private boolean searchable ;
    private boolean exportable;
    private boolean visible;

    public DbBoatSettingsDTO(int id, String name, String pojoName, String controlType, String fieldName, String getter, boolean searchable, boolean exportable, boolean visible) {
        this.id = id;
        this.name = name;
        this.pojoName = pojoName;
        this.controlType = controlType;
        this.fieldName = fieldName;
        this.getter = getter;
        this.searchable = searchable;
        this.exportable = exportable;
        this.visible = visible;
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

    public String getPojoName() {
        return pojoName;
    }

    public void setPojoName(String pojoName) {
        this.pojoName = pojoName;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
