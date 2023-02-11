package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.tabs.boatlist.BoatListRadioDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlBoatListRadio {

    public static ArrayList<BoatListRadioDTO> getBoatListRadioDTOs() {
        ArrayList<BoatListRadioDTO> boatListRadioDTOS = new ArrayList<>();
        String query = "select * from boat_radio_selection";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                boatListRadioDTOS.add(new BoatListRadioDTO(
                        rs.getInt("ID"),
                        rs.getString("LABEL"),
                        rs.getString("SQL_QUERY"),
                        rs.getInt("LIST_ORDER")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boatListRadioDTOS;
    }
}
