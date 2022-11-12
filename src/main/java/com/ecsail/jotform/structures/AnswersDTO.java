package com.ecsail.jotform.structures;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *
 */

public class AnswersDTO {
    /**
     * this is the name of a jotform widget for a specific piece of data, this is unique to each widget
     */
    String name;


    /**
     * this is the order the jotform widget is displayed in the form
     */
    Integer order;


    /**
     * This is the label on the form describing the widget purpose to the user
     */
    String text;



    /**
     * This is a unique name jotform gave to each type of widget
     */
    String type;


    /**
     * Whether 1 answer or many this is a way to make this object common to all jotform widgets
     */
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
