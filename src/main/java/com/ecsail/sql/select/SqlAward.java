package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.AwardDTO;
import com.ecsail.dto.PersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlAward {
    public static ObservableList<AwardDTO> getAwards(PersonDTO p) {  //p_id
        ObservableList<AwardDTO> thisAwards = FXCollections.observableArrayList();
        String query = "SELECT * FROM awards WHERE p_id=" + p.getpId();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisAwards.add(new AwardDTO(
                        rs.getInt("AWARD_ID"),
                        rs.getInt("p_id"),
                        rs.getString("AWARD_YEAR"),
                        rs.getString("AWARD_TYPE")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisAwards;
    }

    public static ArrayList<AwardDTO> getAwards() {
        ArrayList<AwardDTO> theseAwards = new ArrayList<>();
        String query = "SELECT * FROM awards";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseAwards.add(new AwardDTO(
                        rs.getInt("AWARD_ID"),
                        rs.getInt("p_id"),
                        rs.getString("AWARD_YEAR"),
                        rs.getString("AWARD_TYPE")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseAwards;
    }
}
