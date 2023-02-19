package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.gui.tabs.roster.MembershipListRadioDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlMembershipListRadio {
    public static ArrayList<MembershipListRadioDTO> getRadioChoices() {  //p_id
        ArrayList<MembershipListRadioDTO> thisApiKey = new ArrayList<>();
        String query = "SELECT * FROM db_membership_list_selection";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisApiKey.add(new MembershipListRadioDTO(
                        rs.getInt("ID"),
                        rs.getString("LABEL"),
                        rs.getString("SQL_QUERY"),
                        rs.getInt("LIST_ORDER"),
                        rs.getInt("LIST"),
                        rs.getBoolean("selected")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisApiKey;
    }

}
