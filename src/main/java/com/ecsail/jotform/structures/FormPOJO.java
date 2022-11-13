package com.ecsail.jotform.structures;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FormPOJO {
    String duration;
    @JsonProperty(value = "limit-left")
    int limitLeft;
    private String message;
    List<ContentPOJO> content;
    ResultSetPOJO resultSet;
    int responseCode;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getLimitLeft() {
        return limitLeft;
    }

    public void setLimitLeft(int limitLeft) {
        this.limitLeft = limitLeft;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ContentPOJO> getContent() {
        return content;
    }

    public void setContent(List<ContentPOJO> content) {
        this.content = content;
    }

    public ResultSetPOJO getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSetPOJO resultSet) {
        this.resultSet = resultSet;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
