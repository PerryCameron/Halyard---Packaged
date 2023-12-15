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
    public static ObservableList<MembershipIdDTO> getIds() {
        ObservableList<MembershipIdDTO> ids = FXCollections.observableArrayList();
        String query = "SELECT * FROM membership_id";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                ids.add(new MembershipIdDTO(
                        rs.getInt("MID")
                        , rs.getString("fiscal_year")
                        , rs.getInt("ms_id")
                        , rs.getString("membership_id")
                        , rs.getBoolean("renew")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return ids;
    }

    public static ObservableList<MembershipIdDTO> getIds(int ms_id) {
        ObservableList<MembershipIdDTO> ids = FXCollections.observableArrayList();
        String query = "SELECT * FROM membership_id WHERE ms_id=" +ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                ids.add(new MembershipIdDTO(
                        rs.getInt("MID")
                        , rs.getString("fiscal_year")
                        , rs.getInt("ms_id")
                        , rs.getString("membership_id")
                        , rs.getBoolean("renew")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return ids;
    }

    public static String getId(int ms_id) {
        MembershipIdDTO id = null;
        String query = "SELECT * FROM membership_id WHERE ms_id=" +ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                id = new MembershipIdDTO(
                        rs.getInt("MID")
                        , rs.getString("fiscal_year")
                        , rs.getInt("ms_id")
                        , rs.getString("membership_id")
                        , rs.getBoolean("renew")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        assert id != null;
        return id.getMembership_id();
    }

    public static int getMembershipIDfromMsid(int msid)  {
        int result = 0;
        String query = "SELECT membership_id FROM membership_id WHERE ms_id=" + msid + " AND fiscal_year=" + HalyardPaths.getYear();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            result = rs.getInt("membership_id");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }
    /// now in a repo
    public static int getMsidFromMembershipID(int membership_id)  {
        int result = 0;
        String query = "SELECT ms_id FROM membership_id WHERE fiscal_year='" + HalyardPaths.getYear() + "' AND membership_id=" + membership_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            result = rs.getInt("ms_id");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }

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

    public static MembershipIdDTO getMembershipIdObject(int mid) {
        MembershipIdDTO id = null;
        String query = "SELECT * FROM membership_id WHERE mid="  + mid;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
                id = new MembershipIdDTO(
                        rs.getInt("MID")
                        , rs.getString("fiscal_year")
                        , rs.getInt("ms_id")
                        , rs.getString("membership_id")
                        , rs.getBoolean("renew")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW"));
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return id;
    }

    public static int getHighestMembershipId(String year) {  // example-> "email","email_id"
        int result = 0;
        String query = "SELECT Max(membership_id) FROM membership_id WHERE fiscal_year='" + year + "' AND membership_id < 500";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            result =  rs.getInt("Max(membership_id)");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
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


    //////////  FOR CHARTS /////////////

    public static ObservableList<MembershipIdDTO> getAllMembershipIdsByYear(String year) {
		ObservableList<MembershipIdDTO> theseIds = FXCollections.observableArrayList();
        String query = "SELECT * FROM membership_id WHERE fiscal_year=" + year + " ORDER BY membership_id";
		try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
		while (rs.next()) {
			theseIds.add(new MembershipIdDTO(
					rs.getInt("MID"),
					rs.getString("fiscal_year"),
					rs.getInt("ms_id"),
					rs.getString("membership_id"),
					rs.getBoolean("renew"),
					rs.getString("MEM_TYPE"),
					rs.getBoolean("SELECTED"),
				    rs.getBoolean("LATE_RENEW")));
		}
            BaseApplication.connect.closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to retrieve information membership_id info for " + year,"See below for details");
		}
		return theseIds;
	}

    public static ObservableList<MembershipIdDTO> getActiveMembershipIdsByYear(String year) {
        ObservableList<MembershipIdDTO> theseIds = FXCollections.observableArrayList();
        String query = "SELECT * FROM membership_id WHERE fiscal_year=" + year + " AND renew=true ORDER BY membership_id";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseIds.add(new MembershipIdDTO(
                        rs.getInt("MID"),
                        rs.getString("fiscal_year"),
                        rs.getInt("ms_id"),
                        rs.getString("membership_id"),
                        rs.getBoolean("renew"),
                        rs.getString("MEM_TYPE"),
                        rs.getBoolean("SELECTED"),
                        rs.getBoolean("LATE_RENEW")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseIds;
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
