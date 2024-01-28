package com.ecsail.jotform.structures;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContentDTO {
        private long id;
        @JsonProperty(value = "form_id")
        String formId;
        String ip;
        @JsonProperty(value = "created_at")
        String createdAt;
        String status;
        @JsonProperty(value = "new")
        String newSubmission;
        String flag;
        String notes;
        @JsonProperty(value = "updated_at")
        String updatedAt;
        AnswersDTO answers;

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public AnswersDTO getAnswers() {
                return answers;
        }

        public void setAnswers(AnswersDTO answers) {
                this.answers = answers;
        }
}
