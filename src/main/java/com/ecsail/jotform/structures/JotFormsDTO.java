package com.ecsail.jotform.structures;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;

public class JotFormsDTO {
    private Long id;
    private String username;
    private String title;
    private Integer height;
    private String status;
    private String created_at;
    private String updated_at;
    private String last_submission;
    @JsonProperty(value = "new")
    Integer newSubmission;
    Integer count;
    String type;
    boolean favorite;
    Integer archived;
    String url;

    public JotFormsDTO(Long id, String username, String title, Integer height, String status, String created_at, String updated_at, String last_submission, Integer newSubmission, Integer count, String type, boolean favorite, Integer archived, String url) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.height = height;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.last_submission = last_submission;
        this.newSubmission = newSubmission;
        this.count = count;
        this.type = type;
        this.favorite = favorite;
        this.archived = archived;
        this.url = url;
    }

    public JotFormsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLast_submission() {
        return last_submission;
    }

    public void setLast_submission(String last_submission) {
        this.last_submission = last_submission;
    }

    public Integer getNewSubmission() {
        return newSubmission;
    }

    public void setNewSubmission(Integer newSubmission) {
        this.newSubmission = newSubmission;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JotFormsDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", height=" + height +
                ", status='" + status + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", last_submission='" + last_submission + '\'' +
                ", newSubmission=" + newSubmission +
                ", count=" + count +
                ", type='" + type + '\'' +
                ", favorite=" + favorite +
                ", archived=" + archived +
                ", url='" + url + '\'' +
                '}';
    }
}