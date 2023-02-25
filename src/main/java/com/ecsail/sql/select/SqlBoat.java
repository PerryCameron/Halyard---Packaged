package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.BoatOwnerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlBoat {
    // was a list
    public static ObservableList<BoatOwnerDTO> getBoatOwners() {
        ObservableList<BoatOwnerDTO> thisBoatOwner = FXCollections.observableArrayList();
        String query = "SELECT * FROM boat_owner";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisBoatOwner.add(new BoatOwnerDTO(
                        rs.getInt("ms_id"),
                        rs.getInt("boat_id")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoatOwner;
    }

    public static ObservableList<BoatDTO> getBoats() {
        ObservableList<BoatDTO> thisBoat = FXCollections.observableArrayList();
        String query = "SELECT * FROM boat";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisBoat.add(new BoatDTO(
                        rs.getInt("boat_id"), 0, // because Object_Boat has a ms-id variable but database does not
                        rs.getString("manufacturer"), // might be the best note I have ever left ^^ lol
                        rs.getString("manufacture_year"),
                        rs.getString("registration_num"),
                        rs.getString("model"),
                        rs.getString("boat_name"),
                        rs.getString("sail_number"),
                        rs.getBoolean("has_trailer"),
                        rs.getString("length"),
                        rs.getString("weight"),
                        rs.getString("keel"),
                        rs.getString("phrf"),
                        rs.getString("draft"),
                        rs.getString("beam"),
                        rs.getString("lwl"),
                        rs.getBoolean("aux")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }
    // gets all boats and kayak for all time



}
