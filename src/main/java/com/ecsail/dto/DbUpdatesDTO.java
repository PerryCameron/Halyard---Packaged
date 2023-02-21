package com.ecsail.dto;

public class DbUpdatesDTO {

    int id;
    String creationDate;
    boolean isClosed;

    double dbSize;

    public DbUpdatesDTO(int id, String creationDate, boolean isClosed, double dbSize) {
        this.id = id;
        this.creationDate = creationDate;
        this.isClosed = isClosed;
        this.dbSize = dbSize;
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

    public double getDbSize() {
        return dbSize;
    }

    public void setDbSize(double dbSize) {
        this.dbSize = dbSize;
    }

    @Override
    public String toString() {
        return "DbUpdatesDTO{" +
                "id=" + id +
                ", creationDate='" + creationDate + '\'' +
                ", isClosed=" + isClosed +
                ", dbSize=" + dbSize +
                '}';
    }
}
