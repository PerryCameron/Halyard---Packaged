package com.ecsail.gui.tabs.boatlist;

import javafx.scene.control.RadioButton;

public class BoatListRadioDTO {

    private int id;
    private String label;
    private String method;
    private int order;
    private int list;
    private boolean selected;

    private RadioButton radioButton;

    public BoatListRadioDTO(int id, String label, String method, int order, int list, boolean selected) {
        this.id = id;
        this.label = label;
        this.method = method;
        this.order = order;
        this.list = list;
        this.selected = selected;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getList() {
        return list;
    }

    public void setList(int list) {
        this.list = list;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }
}
