package com.ecsail.dto;

public class MembershipListRadioDTO {
    private int id;
    private String label;
    private String methodName;
    private int order;
    private int list;
    private boolean selected;


    public MembershipListRadioDTO(int id, String label, String methodName, int order, int list, boolean selected) {
        this.id = id;
        this.label = label;
        this.methodName = methodName;
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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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
