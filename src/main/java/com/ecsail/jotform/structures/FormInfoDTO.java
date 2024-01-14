package com.ecsail.jotform.structures;

public class FormInfoDTO {

    Long formId;
    String info1;
    String info2;

    public FormInfoDTO(Long formId, String info1, String info2) {
        this.formId = formId;
        this.info1 = info1;
        this.info2 = info2;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }
}
