package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.HalyardPaths;
import com.ecsail.dto.MembershipIdDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlMembership_Id {


    public static String getMembershipId(String year, int ms_id) {
        String id = "";
        String query = "SELECT membership_id FROM membership_id WHERE fiscal_year='" + year + "' AND ms_id=" + ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            if (!rs.next()) {
                id = "none";
            } else {
                do {
                    id = rs.getString("membership_id");
                } while (rs.next());
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return id;
    }

    public static boolean isRenewedByMsidAndYear(int ms_id, String year)
    {
        boolean renew = false;
        String query = "SELECT renew FROM membership_id WHERE fiscal_year=" + year + " AND ms_id=" + ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            renew = rs.getBoolean("renew");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"membership id record does not exist for ms_id " + ms_id + " for year " + year,"See below for details");
        }
        return renew;
    }

    public static int getNonRenewNumber(int year) {
        int number = 0;
        String query = "SELECT COUNT(*) FROM membership_id WHERE fiscal_year='" + year + "' AND renew=false";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            number = rs.getInt("COUNT(*)");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

    public static int getMsidFromYearAndMembershipId(int year, String membershipId) {
        int number = 0;
        String query = "SELECT ms_id FROM membership_id WHERE fiscal_year=" + year
                   + " AND membership_id=" + membershipId;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            number = rs.getInt("ms_id");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
