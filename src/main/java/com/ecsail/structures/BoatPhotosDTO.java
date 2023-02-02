package com.ecsail.structures;

public class BoatPhotosDTO {
    int id;
    int boat_id;
    String upload_date;
    String filename;
    String path;
    boolean isDefault;

    public BoatPhotosDTO(int id, int boat_id, String upload_date, String filename, String path, boolean isDefault) {
        this.id = id;
        this.boat_id = boat_id;
        this.upload_date = upload_date;
        this.filename = filename;
        this.path = path;
        this.isDefault = isDefault;
    }

    public String getFullPath() {
        return path + filename;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
