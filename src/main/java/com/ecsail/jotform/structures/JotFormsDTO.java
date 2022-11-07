package com.ecsail.jotform.structures;

import javafx.beans.property.*;

public class JotFormsDTO {
    LongProperty id;
    StringProperty username;
    StringProperty title;
    IntegerProperty height;
    StringProperty status;
    StringProperty created_at;
    StringProperty updated_at;
    StringProperty last_submission;
    IntegerProperty _new;
    IntegerProperty count;
    StringProperty type;
    IntegerProperty favorite;
    IntegerProperty archived;
    StringProperty url;

    public JotFormsDTO(Long id, String username, String title, Integer height, String status, String created_at,
                       String updated_at, String last_submission, Integer _new, Integer count, String type,
                       Integer favorite, Integer archived, String url) {
        this.id = new SimpleLongProperty(id);
        this.username = new SimpleStringProperty(username);
        this.title = new SimpleStringProperty(title);
        this.height = new SimpleIntegerProperty(height);
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleStringProperty(created_at);
        this.updated_at = new SimpleStringProperty(updated_at);
        this.last_submission = new SimpleStringProperty(last_submission);
        this._new = new SimpleIntegerProperty(_new);
        this.count = new SimpleIntegerProperty(count);
        this.type = new SimpleStringProperty(type);
        this.favorite = new SimpleIntegerProperty(favorite);
        this.archived = new SimpleIntegerProperty(archived);
        this.url = new SimpleStringProperty(url);
    }

    public final LongProperty idProperty() {
        return this.id;
    }

    public final long getId() {
        return this.idProperty().get();
    }

    public final void setId(final long id) {
        this.idProperty().set(id);
    }

    public final StringProperty usernameProperty() {
        return this.username;
    }

    public final String getUsername() {
        return this.usernameProperty().get();
    }

    public final void setUsername(final String username) {
        this.usernameProperty().set(username);
    }

    public final StringProperty titleProperty() {
        return this.title;
    }

    public final String getTitle() {
        return this.titleProperty().get();
    }

    public final void setTitle(final String title) {
        this.titleProperty().set(title);
    }

    public final IntegerProperty heightProperty() {
        return this.height;
    }

    public final int getHeight() {
        return this.heightProperty().get();
    }

    public final void setHeight(final int height) {
        this.heightProperty().set(height);
    }

    public final StringProperty statusProperty() {
        return this.status;
    }

    public final String getStatus() {
        return this.statusProperty().get();
    }

    public final void setStatus(final String status) {
        this.statusProperty().set(status);
    }

    public final StringProperty created_atProperty() {
        return this.created_at;
    }

    public final String getCreated_at() {
        return this.created_atProperty().get();
    }

    public final void setCreated_at(final String created_at) {
        this.created_atProperty().set(created_at);
    }

    public final StringProperty updated_atProperty() {
        return this.updated_at;
    }

    public final String getUpdated_at() {
        return this.updated_atProperty().get();
    }

    public final void setUpdated_at(final String updated_at) {
        this.updated_atProperty().set(updated_at);
    }

    public final StringProperty last_submissionProperty() {
        return this.last_submission;
    }

    public final String getLast_submission() {
        return this.last_submissionProperty().get();
    }

    public final void setLast_submission(final String last_submission) {
        this.last_submissionProperty().set(last_submission);
    }

    public final IntegerProperty _newProperty() {
        return this._new;
    }

    public final int get_new() {
        return this._newProperty().get();
    }

    public final void set_new(final int _new) {
        this._newProperty().set(_new);
    }

    public final IntegerProperty countProperty() {
        return this.count;
    }

    public final int getCount() {
        return this.countProperty().get();
    }

    public final void setCount(final int count) {
        this.countProperty().set(count);
    }

    public final StringProperty typeProperty() {
        return this.type;
    }

    public final String getType() {
        return this.typeProperty().get();
    }

    public final void setType(final String type) {
        this.typeProperty().set(type);
    }

    public final IntegerProperty favoriteProperty() {
        return this.favorite;
    }

    public final int getFavorite() {
        return this.favoriteProperty().get();
    }

    public final void setFavorite(final int favorite) {
        this.favoriteProperty().set(favorite);
    }

    public final IntegerProperty archivedProperty() {
        return this.archived;
    }

    public final int getArchived() {
        return this.archivedProperty().get();
    }

    public final void setArchived(final int archived) {
        this.archivedProperty().set(archived);
    }

    public final StringProperty urlProperty() {
        return this.url;
    }

    public final String getUrl() {
        return this.urlProperty().get();
    }

    public final void setUrl(final String url) {
        this.urlProperty().set(url);
    }
}
