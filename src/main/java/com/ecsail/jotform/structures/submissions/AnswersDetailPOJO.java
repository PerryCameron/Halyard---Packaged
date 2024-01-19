package com.ecsail.jotform.structures.submissions;

public class AnswersDetailPOJO {
    private String name;
    private int order;
    private String text;
    private String type;
    private String answer;
    private String prettyFormat;

    public AnswersDetailPOJO(String name, int order, String text, String type, String answer, String prettyFormat) {
        this.name = name;
        this.order = order;
        this.text = text;
        this.type = type;
        this.answer = answer;
        this.prettyFormat = prettyFormat;
    }

    public AnswersDetailPOJO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPrettyFormat() {
        return prettyFormat;
    }

    public void setPrettyFormat(String prettyFormat) {
        this.prettyFormat = prettyFormat;
    }

    @Override
    public String toString() {
        return "AnswersDetailPOJO{" +
                "prettyFormat='" + prettyFormat + '\'' +
                '}';
    }
}
