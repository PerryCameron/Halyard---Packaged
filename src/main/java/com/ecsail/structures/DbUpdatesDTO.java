package com.ecsail.structures;

public class DbUpdatesDTO {

    int id;
    String creationDate;
    boolean isClosed;

    public DbUpdatesDTO(int id, String creationDate, boolean isClosed) {
        this.id = id;
        this.creationDate = creationDate;
        this.isClosed = isClosed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
