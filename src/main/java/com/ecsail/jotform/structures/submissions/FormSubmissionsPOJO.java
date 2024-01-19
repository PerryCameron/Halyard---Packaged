package com.ecsail.jotform.structures.submissions;

import java.util.List;

public class FormSubmissionsPOJO {
    private int responseCode;
    private String message;
    private List<ContentPOJO> content;

    public FormSubmissionsPOJO(int responseCode, String message, List<ContentPOJO> content) {
        this.responseCode = responseCode;
        this.message = message;
        this.content = content;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
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
}
