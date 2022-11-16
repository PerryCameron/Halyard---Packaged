package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.AwardDTO;
import com.ecsail.structures.DbTableChangesDTO;
import com.ecsail.structures.DbUpdatesDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlDbTableChanges {

    public static DbTableChangesDTO getDbTableChangesById(int id, String tableChanged) { // overload but must be separate
        DbTableChangesDTO tableChangesDTO = null;
        String query = "select * from db_table_changes where table_changed='" + tableChanged + "' and db_updates_id=" + id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                tableChangesDTO = new DbTableChangesDTO(
                        rs.getInt("id"),
                        rs.getInt("db_updates_id"),
                        rs.getString("table_changed"),
                        rs.getInt("table_insert"),
                        rs.getInt("table_delete"),
                        rs.getInt("table_update"),
                        rs.getString("change_date"),
                        rs.getString("changed_by"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
//            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
            e.printStackTrace();
        }
        return tableChangesDTO;
    }

    public static ArrayList<DbTableChangesDTO> getDbtableChanges() {  //p_id
        ArrayList<DbTableChangesDTO> thisDbUpdates = new ArrayList<>();
        String query = "SELECT * FROM db_table_changes";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisDbUpdates.add(new DbTableChangesDTO(
                        rs.getInt("ID"),
                        rs.getInt("DB_UPDATES_ID"),
                        rs.getString("TABLE_CHANGED"),
                        rs.getInt("TABLE_INSERT"),
                        rs.getInt("TABLE_DELETE"),
                        rs.getInt("TABLE_UPDATE"),
                        rs.getString("CHANGE_DATE"),
                        rs.getString("CHANGED_BY")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return thisDbUpdates;
    }

    public static ArrayList<DbUpdatesDTO> getDbUpdates() {  //p_id
        ArrayList<DbUpdatesDTO> thisDbUpdates = new ArrayList<>();
        String query = "SELECT * FROM db_updates";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisDbUpdates.add(new DbUpdatesDTO(
                        rs.getInt("ID"),
                        rs.getString("SQL_CREATION_DATE"),
                        rs.getBoolean("IS_CLOSED"),
                        rs.getDouble("DB_SIZE")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return thisDbUpdates;
    }
}
