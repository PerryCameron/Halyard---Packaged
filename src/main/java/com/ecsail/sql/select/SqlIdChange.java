package com.ecsail.sql.select;


import com.ecsail.BaseApplication;
import com.ecsail.structures.IdChangeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlIdChange {
    public static ArrayList<IdChangeDTO> getAllChangedIds() {  //p_id
        ArrayList<IdChangeDTO> thisAwards = new ArrayList<>();
        String query = "SELECT * FROM id_change";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisAwards.add(new IdChangeDTO(
                        rs.getInt("CHANGE_ID"),
                        rs.getInt("ID_YEAR"),
                        rs.getBoolean("CHANGED")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisAwards;
    }
}
