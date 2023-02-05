package com.ecsail.structures;

public class BoatPhotosDTO {
    int id;
    int boat_id;
    String upload_date;
    String filename;
    int fileNumber;
    boolean isDefault;

    public BoatPhotosDTO(int id, int boat_id, String upload_date, String filename, int fileNumber, boolean isDefault) {
        this.id = id;
        this.boat_id = boat_id;
        this.upload_date = upload_date;
        this.filename = filename;
        this.fileNumber = fileNumber;
        this.isDefault = isDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoat_id() {
        return boat_id;
    }

    public void setBoat_id(int boat_id) {
        this.boat_id = boat_id;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
