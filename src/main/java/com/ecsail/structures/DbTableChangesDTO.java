package com.ecsail.structures;

public class DbTableChangesDTO {
        int id;
        int dbUpdatesId;
        String tableChanged;
        int tableInsert;
        int tableDelete;
        int tableUpdate;
        String changeDate;
        String changedBy;

    public DbTableChangesDTO(int id, int dbUpdatesId, String tableChanged, int tableInsert, int tableDelete, int tableUpdate, String changeDate, String changedBy) {
        this.id = id;
        this.dbUpdatesId = dbUpdatesId;
        this.tableChanged = tableChanged;
        this.tableInsert = tableInsert;
        this.tableDelete = tableDelete;
        this.tableUpdate = tableUpdate;
        this.changeDate = changeDate;
        this.changedBy = changedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDbUpdatesId() {
        return dbUpdatesId;
    }

    public void setDbUpdatesId(int dbUpdatesId) {
        this.dbUpdatesId = dbUpdatesId;
    }

    public String getTableChanged() {
        return tableChanged;
    }

    public void setTableChanged(String tableChanged) {
        this.tableChanged = tableChanged;
    }

    public int getTableInsert() {
        return tableInsert;
    }

    public void setTableInsert(int tableInsert) {
        this.tableInsert = tableInsert;
    }

    public int getTableDelete() {
        return tableDelete;
    }

    public void setTableDelete(int tableDelete) {
        this.tableDelete = tableDelete;
    }

    public int getTableUpdate() {
        return tableUpdate;
    }

    public void setTableUpdate(int tableUpdate) {
        this.tableUpdate = tableUpdate;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}

