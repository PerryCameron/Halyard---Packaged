package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.DbBoatDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlDbBoat {

    public static ObservableList<DbBoatDTO> getDbBoat() {  //p_id
        ObservableList<DbBoatDTO> dbBoatDTOS = FXCollections.observableArrayList();
        String query = "SELECT * from db_boat";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                dbBoatDTOS.add(new DbBoatDTO(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("control_type"),
                        rs.getString("data_type"),
                        rs.getString("field_name"),
                        rs.getInt("order")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return dbBoatDTOS;
    }
}
