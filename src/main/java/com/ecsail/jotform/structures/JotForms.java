package com.ecsail.jotform.structures;

import javafx.beans.property.*;

public class JotForms {
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

    public JotForms(Long id, String username, String title, Integer height, String status, String created_at,
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


}
