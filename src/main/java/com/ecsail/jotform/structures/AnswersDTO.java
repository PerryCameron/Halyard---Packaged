package com.ecsail.jotform.structures;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class AnswersDTO {

    private List<AnswerFieldsDTO> answerFieldsDTOLIST;

    public List<AnswerFieldsDTO> getAnswerFieldsDTOLIST() {
        return answerFieldsDTOLIST;
    }

    public void setAnswerFieldsDTOLIST(List<AnswerFieldsDTO> answerFieldsDTOLIST) {
        this.answerFieldsDTOLIST = answerFieldsDTOLIST;
    }
}
