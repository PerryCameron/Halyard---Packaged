package com.ecsail.dto;

public class DbMembershipListDTO {

    private int id;
    private String name;
    private String pojo_name;
    private String data_type;
    private String field_name;
    private int list_order;
    private boolean searchable;
    private boolean exportable;

    public DbMembershipListDTO(int id, String name, String pojo_name, String data_type, String field_name, int list_order, boolean searchable, boolean exportable) {
        this.id = id;
        this.name = name;
        this.pojo_name = pojo_name;
        this.data_type = data_type;
        this.field_name = field_name;
        this.list_order = list_order;
        this.searchable = searchable;
        this.exportable = exportable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPojo_name() {
        return pojo_name;
    }

    public void setPojo_name(String pojo_name) {
        this.pojo_name = pojo_name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public int getList_order() {
        return list_order;
    }

    public void setList_order(int list_order) {
        this.list_order = list_order;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean isExportable() {
        return exportable;
    }

    public void setExportable(boolean exportable) {
        this.exportable = exportable;
    }
}
