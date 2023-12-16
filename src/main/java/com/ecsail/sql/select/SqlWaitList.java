package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.WaitListDTO;

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


}
