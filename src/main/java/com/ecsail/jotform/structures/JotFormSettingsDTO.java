package com.ecsail.jotform.structures;

import java.sql.Date;

public class JotFormSettingsDTO {
    private long jotId;
    private long formNumber;
    private int answerNumber;
    private String answerLocation;
    private String answerText;
    private String answerType;
    private String answerName;
    private int answerOrder;
    private int groupOrder;
    private String subLabels;
    private String dataType;
    private Date updated_At;

    public JotFormSettingsDTO(long jotId, long formNumber, int answerNumber, String answerLocation, String answerText, String answerType, String answerName, int answerOrder, int groupOrder, String subLabels, String dataType, Date updated_At) {
        this.jotId = jotId;
        this.formNumber = formNumber;
        this.answerNumber = answerNumber;
        this.answerLocation = answerLocation;
        this.answerText = answerText;
        this.answerType = answerType;
        this.answerName = answerName;
        this.answerOrder = answerOrder;
        this.groupOrder = groupOrder;
        this.subLabels = subLabels;
        this.dataType = dataType;
        this.updated_At = updated_At;
    }

    public boolean isGroup() {
        if(groupOrder > 0) return true;
        else return false;
    }

    public long getJotId() {
        return jotId;
    }

    public void setJotId(long jotId) {
        this.jotId = jotId;
    }

    public long getFormNumber() {
        return formNumber;
    }

    public void setFormNumber(long formNumber) {
        this.formNumber = formNumber;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    public String getAnswerLocation() {
        return answerLocation;
    }

    public void setAnswerLocation(String answerLocation) {
        this.answerLocation = answerLocation;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }

    public int getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(int groupOrder) {
        this.groupOrder = groupOrder;
    }

    public String getsubLabels() {
        return subLabels;
    }

    public void setsubLabels(String subLabels) {
        this.subLabels = subLabels;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }
}
