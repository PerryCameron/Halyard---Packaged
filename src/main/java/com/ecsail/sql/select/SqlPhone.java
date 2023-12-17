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






}
