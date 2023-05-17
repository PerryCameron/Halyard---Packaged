package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.PersonDTO;
import com.ecsail.dto.PhoneDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlPhone {
    public static ObservableList<PhoneDTO> getPhoneByPid(int p_id) {  // if p_id = 0 then select all
        String query = "SELECT * FROM phone";
        if(p_id != 0)
            query += " WHERE p_id=" + p_id;
        ObservableList<PhoneDTO> thisPhone = FXCollections.observableArrayList();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisPhone.add(new PhoneDTO(
                        rs.getInt("PHONE_ID"),
                        rs.getInt("p_id"),
                        rs.getBoolean("phone_listed"),
                        rs.getString("PHONE"),
                        rs.getString("phone_type")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPhone;
    }

    public static ArrayList<PhoneDTO> getPhoneByPerson(PersonDTO p) {  // if p_id = 0 then select all
        ArrayList<PhoneDTO> thisPhone = new ArrayList<>();
        String query = "SELECT * FROM phone WHERE p_id=" + p.getP_id();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisPhone.add(new PhoneDTO(rs.getInt("PHONE_ID"), rs.getInt("p_id"), rs.getBoolean("phone_listed"),
                        rs.getString("PHONE"), rs.getString("phone_type")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPhone;
    }

    public static String getListedPhoneByType(PersonDTO p, String type) {  // if p_id = 0 then select all
        String phone = "";
        String query = "SELECT * FROM phone WHERE p_id=" + p.getP_id() + " AND phone_listed=true AND phone_type='" + type + "'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            phone = rs.getString("PHONE");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return phone;
    }

    public static String getPhoneByType(String pid, String type) {  // if p_id = 0 then select all
        String phone = "";
        String query = "SELECT * FROM phone WHERE p_id=" + pid + " AND phone_type='" + type + "'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            phone = rs.getString("PHONE");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return phone;
    }
}
