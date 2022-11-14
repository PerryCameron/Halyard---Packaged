package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.structures.DbTableChangesDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
