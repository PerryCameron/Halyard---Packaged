package com.ecsail.jotform.structures;

import java.sql.Date;

public class JotFormSettingsDTO {
    private long jotId;
    private long formNumber;
    private int answerNumber;
    private String answerText;
    private String answerType;
    private int answerOrder;
    private Date updated_At;

    public JotFormSettingsDTO(long jotId, long formNumber, int answerNumber, String answerText, String answerType, int answerOrder, Date updated_At) {
        this.jotId = jotId;
        this.formNumber = formNumber;
        this.answerNumber = answerNumber;
        this.answerText = answerText;
        this.answerType = answerType;
        this.answerOrder = answerOrder;
        this.updated_At = updated_At;
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

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }
}
