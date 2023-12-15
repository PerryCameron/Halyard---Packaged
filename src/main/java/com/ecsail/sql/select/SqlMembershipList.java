package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
//import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
//import com.ecsail.main.Halyard;
//import com.ecsail.main.HalyardPaths;
import com.ecsail.HalyardPaths;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.MembershipListDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlMembershipList {

    public static ObservableList<MembershipListDTO> getRoster(String query) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayList(rosters, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            BaseApplication.logger.error(e.getMessage());
        }
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRosterOfKayakRackOwners(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip \n"
                + "FROM slip s \n"
                + "RIGHT JOIN membership m ON m.ms_id=s.ms_id \n"
                + "LEFT JOIN membership_id id ON m.ms_id=id.ms_id\n"
                + "LEFT JOIN money mo ON m.ms_id=mo.ms_id \n"
                + "LEFT JOIN person p ON p.ms_id=m.ms_id \n"
                + "WHERE mo.fiscal_year='"+year+"' AND id.fiscal_year='"+year+"' AND id.renew=1 AND kayak_rack=1 AND p.member_type=1 ORDER BY membership_id";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayList(rosters, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        BaseApplication.logger.info("Creating Roster list kayak rack spaces for " + year + "...");
        return rosters;
    }


    public static ObservableList<MembershipListDTO> getRosterOfKayakShedOwners(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        String query ="SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip "
                + "FROM slip s "
                + "RIGHT JOIN membership m ON m.ms_id=s.ms_id "
                + "LEFT JOIN membership_id id ON m.ms_id=id.ms_id "
                + "LEFT JOIN money mo ON m.ms_id=mo.ms_id "
                + "LEFT JOIN person p ON p.ms_id=m.ms_id "
                + "WHERE mo.fiscal_year='"+year+"' AND id.fiscal_year='"+year+"' AND id.renew=1 AND kayak_shed=1 AND p.member_type=1 ORDER BY membership_id" ;
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayList(rosters, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        BaseApplication.logger.info("Creating Roster list Kayak Shed Spaces for " + year + "...");
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRoster(String year, boolean isActive) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip "
                + "FROM slip s "
                + "RIGHT JOIN membership m ON m.ms_id=s.ms_id "
                + "LEFT JOIN membership_id id ON m.ms_id=id.ms_id "
                + "LEFT JOIN person p ON p.ms_id=m.ms_id "
                + "WHERE id.fiscal_year='" + year + "' AND p.member_type=1 AND id.renew=" + isActive + " ORDER BY membership_id";
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayList(rosters, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
//            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        BaseApplication.logger.info("Selecting Active Roster list for " + year + "...");
        return rosters;
    }


    public static ObservableList<MembershipListDTO> getRosterOfSlipOwners(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip "
                + "FROM slip s "
                + "INNER JOIN membership m ON s.ms_id=m.ms_id "
                + "LEFT JOIN membership_id id ON m.ms_id=id.ms_id "
                + "LEFT JOIN person p ON p.ms_id=m.ms_id "
                + "WHERE p.member_type=1 AND fiscal_year="+ HalyardPaths.getYear();
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayList(rosters, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        BaseApplication.logger.info("Creating Roster list of slip owners for " + year + "...");
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRosterOfSubleasedSlips() {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip FROM slip s "
                + "INNER JOIN membership m ON s.ms_id=m.ms_id "
                + "LEFT JOIN membership_id id ON m.ms_id=id.ms_id "
                + "LEFT JOIN person p ON p.ms_id=s.subleased_to "
                + "WHERE subleased_to IS NOT NULL AND p.member_type=1 AND fiscal_year="+ HalyardPaths.getYear();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayList(rosters, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }

        return rosters;
    }

    // I added the p.MEMBER_TYPE=1 into the query because it was causing subleases to have the wrong first name
    // if you are here looking for problems, keep this in mind. (this may be redundant to getRoster)
    public static MembershipListDTO getMembershipList(int ms_id, String year) {
        MembershipListDTO thisMembership = null;
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,"
                + "id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,"
                + "m.zip FROM slip s RIGHT JOIN membership m ON m.ms_id=s.ms_id LEFT JOIN membership_id "
                + "id ON m.ms_id=id.ms_id LEFT JOIN person p ON p.ms_id=m.ms_id WHERE id.fiscal_year='" + year + "' "
                + "AND p.member_type=1 AND m.ms_id=" + ms_id;
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisMembership = new MembershipListDTO(
                        rs.getInt("ms_id"),
                        rs.getInt("p_id"),
                        rs.getInt("membership_id"),
                        rs.getString("join_date"),
                        rs.getString("mem_type"),
                        rs.getString("SLIP_NUM"),
                        rs.getString("l_name"),
                        rs.getString("f_name"),
                        rs.getInt("subleased_to"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("fiscal_year"));
                }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        return thisMembership;
    }
    /// this exists in jdbcFormat now
    public static MembershipListDTO getMembershipFromList(int ms_id, String year) {
        MembershipListDTO thisMembership = null;
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,"
                + "id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,"
                + "m.zip FROM slip s RIGHT JOIN membership m ON m.ms_id=s.ms_id LEFT JOIN membership_id "
                + "id ON m.ms_id=id.ms_id LEFT JOIN person p ON p.ms_id=m.ms_id WHERE id.fiscal_year='" + year + "' "
                + "AND p.member_type=1 AND m.ms_id=" + ms_id;
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisMembership = new MembershipListDTO(
                        rs.getInt("ms_id"),
                        rs.getInt("p_id"),
                        rs.getInt("membership_id"),
                        rs.getString("join_date"),
                        rs.getString("mem_type"),
                        rs.getString("SLIP_NUM"),
                        rs.getString("l_name"),
                        rs.getString("f_name"),
                        rs.getInt("subleased_to"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("fiscal_year"));
                }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        return thisMembership;
    }

    public static MembershipListDTO getMembershipFromListWithoutMembershipId(int ms_id) {
        MembershipListDTO thisMembership = null;
        String query = "SELECT m.ms_id,m.p_id,m.join_date,s.SLIP_NUM,p.l_name,"
                + "p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip FROM slip s RIGHT JOIN "
                + "membership m ON m.ms_id=s.ms_id LEFT JOIN person p ON p.ms_id=m.ms_id WHERE m.ms_id=" + ms_id;
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisMembership = new MembershipListDTO(
                        rs.getInt("ms_id"),
                        rs.getInt("p_id"),
                        0,
                        rs.getString("join_date"),
                        null,
                        rs.getString("SLIP_NUM"),
                        rs.getString("l_name"),
                        rs.getString("f_name"),
                        rs.getInt("subleased_to"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        "No Year"
                        );
                }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        return thisMembership;
    }

    public static ObservableList<MembershipListDTO> getSlipRoster(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip "
                + "FROM slip s INNER JOIN membership m ON s.ms_id=m.ms_id INNER JOIN membership_id id ON id.ms_id=m.ms_id "
                + "INNER JOIN person p ON p.p_id=m.p_id WHERE id.fiscal_year="+year;
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayList(rosters, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        return rosters;
    }



    public static ObservableList<MembershipListDTO> getWaitListRoster(String waitlist) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip "
                + "FROM waitlist w "
                + "INNER JOIN membership m ON w.ms_id=m.ms_id "
                + "LEFT JOIN membership_id id ON m.ms_id=id.ms_id "
                + "LEFT JOIN person p ON p.ms_id=m.ms_id "
                + "LEFT JOIN slip s ON s.ms_id=m.ms_id "
                + "WHERE " + waitlist + "=true AND id.fiscal_year='" + HalyardPaths.getYear() + "' AND p.member_type=1";
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayList(rosters, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        BaseApplication.logger.info("Creating Roster list for slip wait list");
        return rosters;
    }


    public static MembershipListDTO getMembershipByMembershipId(String membership_id) {  /// for SQL Script Maker
        MembershipListDTO membership = null;
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,"
                + "id.mem_type,p.l_name,p.f_name,m.address,m.city,m.state,m.zip FROM "
                + "membership m LEFT JOIN person p ON m.p_id=p.p_id LEFT JOIN membership_id "
                + "id ON m.ms_id=id.ms_id WHERE id.fiscal_year='"+ HalyardPaths.getYear()+"' AND membership_id='" + membership_id + "'";
        System.out.println(query);
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                membership = new MembershipListDTO(
                        rs.getInt("ms_id"),
                        rs.getInt("p_id"),
                        rs.getInt("membership_id"),
                        rs.getString("join_date"),
                        rs.getString("mem_type"),
                        "",
                        rs.getString("l_name"),
                        rs.getString("f_name"),
                        0,
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("fiscal_year"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        return membership;
    }

    /// may be a duplicate from above
    public static ObservableList<MembershipListDTO> getBoatOwnerRoster(int boat_id) {
        ObservableList<MembershipListDTO> boatOwners = FXCollections.observableArrayList();
        String query = "SELECT * FROM boat_owner bo LEFT JOIN membership m ON "
                + "bo.ms_id=m.ms_id LEFT JOIN membership_id id ON m.ms_id=id.ms_id "
                + "LEFT JOIN person p ON m.p_id=p.p_id WHERE BOAT_ID="+boat_id+" AND id.fiscal_year='" + HalyardPaths.getYear() + "'";
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            queryToArrayListConstant(boatOwners, rs);
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT list of boat owners","See below for details");
        }
        return boatOwners;
    }

    /////////////////// OBJECT PACKERS /////////////////////////////

    private static void queryToArrayListConstant(ObservableList<MembershipListDTO> rosters, ResultSet rs) throws SQLException {
        while (rs.next()) {
            rosters.add(new MembershipListDTO(
                    rs.getInt("ms_id"),
                    rs.getInt("p_id"),
                    rs.getInt("membership_id"),
                    rs.getString("join_date"),
                    rs.getString("mem_type"),
                    "",
                    rs.getString("l_name"),
                    rs.getString("f_name"),
                    0,
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("zip"),
                    rs.getString("fiscal_year")));
        }
    }
//
    private static void queryToArrayList(ObservableList<MembershipListDTO> rosters, ResultSet rs) throws SQLException {
        while (rs.next()) {
            rosters.add(new MembershipListDTO(
                    rs.getInt("ms_id"),
                    rs.getInt("p_id"),
                    rs.getInt("membership_id"),
                    rs.getString("join_date"),
                    rs.getString("mem_type"),
                    rs.getString("SLIP_NUM"),
                    rs.getString("l_name"),
                    rs.getString("f_name"),
                    rs.getInt("subleased_to"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("zip"),
                    rs.getString("fiscal_year")));
        }
    }
}
