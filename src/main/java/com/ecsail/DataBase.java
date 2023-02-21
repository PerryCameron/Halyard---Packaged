package com.ecsail;

import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlDbTableChanges;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.dto.DbTableChangesDTO;

import java.sql.Timestamp;
import java.time.Instant;

public class DataBase {
    /**
     * This records all changes made to the database, the table db_updates is a main record and the
     * table db_table_changes have a many-to-one relationship to db_updates. These tables record all
     * updates, inserts, and deletes. When the database is backed up The tables are closed and new ones are
     * created to start the process over again.
     * @param
     */

    public static Timestamp getTimeStamp() {
        Timestamp timeStamp = Timestamp.from(Instant.now());
        return timeStamp;
    }
    public static void recordChange(String query) {
        String tableName = getTableName(query);

        // need to skip updates from db_table_changes and db_updates
        if(tableToBeSkipped(tableName))
            return;
        // primary key for current update record
        int currentDBUpdates = SqlSelect.getLastPrimaryKey("db_updates","ID");
                // see if row for specific table exists, has to be available for db_updates_id
                if(!SqlExists.dbTableChangeRowExists(currentDBUpdates,tableName))
                    // if row does not exist create it
                    SqlInsert.addNewDbTableChanges(tableName,currentDBUpdates);
                // select the row
                DbTableChangesDTO row = SqlDbTableChanges.getDbTableChangesById(currentDBUpdates,tableName);
                // update the row DTO and or perform database action
                updateRow(row, query);
    }

    private static void updateRow(DbTableChangesDTO row, String query) {
        String action = query.substring(0, query.indexOf(' ')).toUpperCase();
        if(action.equals("UPDATE"))
            row.setTableUpdate(row.getTableUpdate() + 1);
        if(action.equals("DELETE"))
            row.setTableDelete((row.getTableDelete()) +1);
        if(action.equals("INSERT"))
            row.setTableInsert(row.getTableInsert() + 1);
        SqlUpdate.updateDbTableChanges(row);
    }


    // need to skip updates from db_table_changes and db_updates
    private static boolean tableToBeSkipped(String tableName) {
        return tableName.equals("DB_TABLE_CHANGES") || tableName.equals("DB_UPDATES");
    }

    private static String getTableName(String query) {
        String tableName = null;
        String[] splitQuery = query.toUpperCase().split(" ");
        switch (splitQuery[0]) {
            case "UPDATE" -> tableName = splitQuery[1];
            case "DELETE" -> tableName = splitQuery[2];
            case "INSERT" -> tableName = splitQuery[2];
        }
        return tableName;
    }
}
