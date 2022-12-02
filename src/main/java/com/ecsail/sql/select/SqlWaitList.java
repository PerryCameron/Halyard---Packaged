package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.WaitListDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlWaitList {
    public static WaitListDTO getWaitList(int ms_id) {
        WaitListDTO thisWaitList = null;
        String query = "SELECT * FROM wait_list WHERE ms_id=" + ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisWaitList = new WaitListDTO(
                        rs.getInt("MS_ID"),
                        rs.getBoolean("SLIP_WAIT"),
                        rs.getBoolean("KAYAK_RACK_WAIT"),
                        rs.getBoolean("SHED_WAIT"),
                        rs.getBoolean("WANT_SUBLEASE"),
                        rs.getBoolean("WANT_RELEASE"),
                        rs.getBoolean("WANT_SLIP_CHANGE")
                        );
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisWaitList;
    }

    public static ArrayList<WaitListDTO> getWaitLists() {
        ArrayList<WaitListDTO> thisWaitList = new ArrayList<>();
        String query = "SELECT * FROM wait_list";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisWaitList.add(new WaitListDTO(
                        rs.getInt("MS_ID"),
                        rs.getBoolean("SLIP_WAIT"),
                        rs.getBoolean("KAYAK_RACK_WAIT"),
                        rs.getBoolean("SHED_WAIT"),
                        rs.getBoolean("WANT_SUBLEASE"),
                        rs.getBoolean("WANT_RELEASE"),
                        rs.getBoolean("WANT_SLIP_CHANGE")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisWaitList;
    }
}
