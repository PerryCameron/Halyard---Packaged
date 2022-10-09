package com.ecsail.structures;

public class MembershipPersonCountDTO {
    private int primary;
    private int secondary;
    private int dependant;

    public MembershipPersonCountDTO(int primary, int secondary, int dependant) {
        this.primary = primary;
        this.secondary = secondary;
        this.dependant = dependant;
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getSecondary() {
        return secondary;
    }

    public void setSecondary(int secondary) {
        this.secondary = secondary;
    }

    public int getDependant() {
        return dependant;
    }

    public void setDependant(int dependant) {
        this.dependant = dependant;
    }
}
