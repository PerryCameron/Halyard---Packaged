package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.MembershipDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlMembership {

    public static ObservableList<MembershipDTO> getMemberships() {  /// for SQL Script Maker
        ObservableList<MembershipDTO> memberships = FXCollections.observableArrayList();
        String query = "SELECT * FROM membership";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                memberships.add(new MembershipDTO(
                        rs.getInt("MS_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("JOIN_DATE"),
                        rs.getString("MEM_TYPE"),
                        rs.getString("ADDRESS"),
                        rs.getString("CITY"),
                        rs.getString("STATE"),
                        rs.getString("ZIP")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        return memberships;
    }
}
