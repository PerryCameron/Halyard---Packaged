package com.ecsail.dto;

import java.sql.Timestamp;

public class AppSettingsDTO {

    private String key;
    private String value;
    private String description;
    private String dataType;
    private Timestamp updatedAt;

    public AppSettingsDTO(String key, String value, String description, String dataType, Timestamp updatedAt) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.dataType = dataType;
        this.updatedAt = updatedAt;
    }

    public AppSettingsDTO() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
