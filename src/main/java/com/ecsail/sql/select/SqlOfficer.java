package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.pdf.directory.PDF_Object_Officer;
import com.ecsail.dto.OfficerDTO;
import com.ecsail.dto.OfficerWithNameDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlOfficer {

    public static ObservableList<OfficerDTO> getOfficers() {
        ObservableList<OfficerDTO> thisOfficer = FXCollections.observableArrayList();
        String query = "SELECT * FROM officer";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisOfficer.add(new OfficerDTO(
                        rs.getInt("O_ID"),
                        rs.getInt("p_id"),
                        rs.getString("BOARD_YEAR"), // beginning of board term
                        rs.getString("off_type"),
                        rs.getString("off_year")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisOfficer;
    }

    public static ArrayList<PDF_Object_Officer> getOfficersByYear(String selectedYear) {
        ArrayList<PDF_Object_Officer> officers = new ArrayList<>();
        String query = "SELECT * FROM officer o LEFT JOIN person p ON o.p_id=p.p_id WHERE off_year=" + selectedYear;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                officers.add(new PDF_Object_Officer(
                        rs.getString("f_name"),
                        rs.getString("L_NAME"),
                        rs.getString("off_type"),
                        rs.getString("BOARD_YEAR"), // beginning of board term
                        rs.getString("off_year")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return officers;
    }

    public static ObservableList<OfficerDTO> getOfficer(String field, int attribute) {  //p_id
        ObservableList<OfficerDTO> thisOfficer = FXCollections.observableArrayList();
        String query = "SELECT * FROM officer WHERE " + field + "='" + attribute + "'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisOfficer.add(new OfficerDTO(
                        rs.getInt("O_ID"),
                        rs.getInt("p_id"),
                        rs.getString("BOARD_YEAR"),
                        rs.getString("off_type"),
                        rs.getString("off_year")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisOfficer;
    }

    public static ArrayList<OfficerWithNameDTO> getOfficersWithNames(String type) {
        ArrayList<OfficerWithNameDTO> theseOfficers = new ArrayList<>();
        String query = "SELECT f_name,L_NAME,off_year FROM officer o LEFT JOIN person p ON o.p_id=p.p_id WHERE off_type='"+type+"'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseOfficers.add(new OfficerWithNameDTO(
                        rs.getString("L_NAME"),
                        rs.getString("f_name"),
                        rs.getString("off_year")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseOfficers;
    }
}
