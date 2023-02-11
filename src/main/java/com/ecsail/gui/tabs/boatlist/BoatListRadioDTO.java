package com.ecsail.gui.tabs.boatlist;

public class BoatListRadioDTO {

    int id;
    String label;
    String query;
    int order;

    public BoatListRadioDTO(int id, String label, String query, int order) {
        this.id = id;
        this.label = label;
        this.query = query;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
