package com.ecsail.gui.tabs.roster;

import javafx.scene.control.RadioButton;

public class MembershipListRadioDTO {
    private int id;
    private String label;
    private String query;
    private int order;
    private int list;
    private boolean selected;

    public MembershipListRadioDTO(int id, String label, String query, int order, int list, boolean selected) {
        this.id = id;
        this.label = label;
        this.query = query;
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
}
