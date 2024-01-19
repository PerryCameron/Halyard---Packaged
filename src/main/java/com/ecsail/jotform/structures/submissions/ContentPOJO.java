package com.ecsail.jotform.structures.submissions;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Map;

public class ContentPOJO {
    long id;
    long formId;
    String ip;
    LocalDateTime createdAt;
    String status;
    boolean newForm;
    boolean flag;
    String notes;
    LocalDateTime updatedAt;
    private Map<String, AnswersDetailPOJO> answers;

    public ContentPOJO(long id, long formId, String ip, LocalDateTime createdAt, String status, boolean newForm, boolean flag, String notes, LocalDateTime updatedAt, Map<String, AnswersDetailPOJO> answers) {
        this.id = id;
        this.formId = formId;
        this.ip = ip;
        this.createdAt = createdAt;
        this.status = status;
        this.newForm = newForm;
        this.flag = flag;
        this.notes = notes;
        this.updatedAt = updatedAt;
        this.answers = answers;
    }

    public ContentPOJO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNewForm() {
        return newForm;
    }

    public void setNewForm(boolean newForm) {
        this.newForm = newForm;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<String, AnswersDetailPOJO> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, AnswersDetailPOJO> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "ContentPOJO{" +
                "id=" + id +
                ", formId=" + formId +
                ", ip='" + ip + '\'' +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                ", newForm=" + newForm +
                ", flag=" + flag +
                ", notes='" + notes + '\'' +
                ", updatedAt=" + updatedAt +
                ", answers=" + answers +
                '}';
    }
}
