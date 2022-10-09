package com.ecsail.structures;

public class ApiKeyDTO {
    int id;
    String name;
    String key;
    String date;

    public ApiKeyDTO(int id, String name, String key, String date) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.date = date;
    }

    public ApiKeyDTO() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
