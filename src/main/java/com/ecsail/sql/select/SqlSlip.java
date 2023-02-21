package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.pdf.directory.Object_SlipInfo;
import com.ecsail.dto.SlipDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlSlip {
    public static ArrayList<SlipDTO> getSlips() {
        ArrayList<SlipDTO> slips = new ArrayList<>();
        String query = "SELECT * FROM slip";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                slips.add(new SlipDTO(rs.getInt("SLIP_ID")
                        , rs.getInt("ms_id")
                        , rs.getString("slip_num")
                        , rs.getInt("subleased_to")
                        , rs.getString("ALT_TEXT")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return slips;
    }

    public static SlipDTO getSlip(int ms_id) {
        SlipDTO thisSlip = null;
        String query = "SELECT * FROM slip WHERE ms_id=" + ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisSlip = new SlipDTO(rs.getInt("SLIP_ID")
                        , rs.getInt("ms_id")
                        , rs.getString("slip_num")
                        , rs.getInt("subleased_to")
                        , rs.getString("ALT_TEXT")
                );
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisSlip;
    }

    public static SlipDTO getSubleasedSlip(int ms_id) {
        SlipDTO thisSlip = null;
        String query = "SELECT * FROM slip WHERE subleased_to=" + ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);;
            while (rs.next()) {
                thisSlip = new SlipDTO(
                        rs.getInt("SLIP_ID"),
                        rs.getInt("ms_id"),
                        rs.getString("slip_num"),
                        rs.getInt("subleased_to"),
                        rs.getString("ALT_TEXT")
                );
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisSlip;
    }

    public static ArrayList<Object_SlipInfo> getSlipsForDock(String dock) {
        ArrayList<Object_SlipInfo> thisSlipInfo = new ArrayList<>();
        String query = "SELECT slip_num,subleased_to,f_name,l_name  FROM slip s \n"
                + "LEFT JOIN membership m ON s.ms_id=m.ms_id \n"
                + "LEFT JOIN person p ON m.p_id=p.p_id \n"
                + "WHERE slip_num LIKE '" +dock + "%'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisSlipInfo.add(new Object_SlipInfo(
                        rs.getString("slip_num"),
                        rs.getInt("subleased_to"),
                        rs.getString("f_name"),
                        rs.getString("l_name")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisSlipInfo;
    }
}
