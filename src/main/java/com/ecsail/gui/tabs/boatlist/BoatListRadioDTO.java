package com.ecsail.gui.tabs.boatlist;

import javafx.scene.control.RadioButton;

public class BoatListRadioDTO {

    private int id;
    private String label;
    private String query;
    private int order;
    private RadioButton radioButton;

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

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }
}
