package com.ecsail.structures;

public class IdChangeDTO {
    int changeId;
    int idYear;
    boolean changed;

    public IdChangeDTO(int changeId, int idYear, boolean changed) {
        this.changeId = changeId;
        this.idYear = idYear;
        this.changed = changed;
    }

    public int getChangeId() {
        return changeId;
    }

    public void setChangeId(int changeId) {
        this.changeId = changeId;
    }

    public int getIdYear() {
        return idYear;
    }

    public void setIdYear(int idYear) {
        this.idYear = idYear;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
