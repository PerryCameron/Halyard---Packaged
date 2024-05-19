package com.ecsail.dto;

public class SlipStructureDTO {
    int id;
    String dock;
    int dockSection;
    String slip1;
    String slip2;
    String slip3;
    String slip4;

    public SlipStructureDTO(int id, String dock, int dockSection, String slip1, String slip2, String slip3, String slip4) {
        this.id = id;
        this.dock = dock;
        this.dockSection = dockSection;
        this.slip1 = slip1;
        this.slip2 = slip2;
        this.slip3 = slip3;
        this.slip4 = slip4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDock() {
        return dock;
    }

    public void setDock(String dock) {
        this.dock = dock;
    }

    public int getDockSection() {
        return dockSection;
    }

    public void setDockSection(int dockSection) {
        this.dockSection = dockSection;
    }

    public String getSlip1() {
        return slip1;
    }

    public void setSlip1(String slip1) {
        this.slip1 = slip1;
    }

    public String getSlip2() {
        return slip2;
    }

    public void setSlip2(String slip2) {
        this.slip2 = slip2;
    }

    public String getSlip3() {
        return slip3;
    }

    public void setSlip3(String slip3) {
        this.slip3 = slip3;
    }

    public String getSlip4() {
        return slip4;
    }

    public void setSlip4(String slip4) {
        this.slip4 = slip4;
    }

    @Override
    public String toString() {
        return "SlipStructureDTO{" +
                "id=" + id +
                ", dock='" + dock + '\'' +
                ", dockSection=" + dockSection +
                ", slip1='" + slip1 + '\'' +
                ", slip2='" + slip2 + '\'' +
                ", slip3='" + slip3 + '\'' +
                ", slip4='" + slip4 + '\'' +
                '}';
    }
}
