package com.ecsail.jotform.structures;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *
 */

public class AnswersDTO {
    String name;
    Integer order;
    String text;
    String type;
    Map<String,String> answer = new HashMap<>();

    public AnswersDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
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

    public Map<String, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, String> answer) {
        this.answer = answer;
    }
}
