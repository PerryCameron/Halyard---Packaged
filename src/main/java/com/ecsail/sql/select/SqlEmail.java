package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.EmailDTO;
import com.ecsail.structures.Email_InformationDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlEmail {
    public static ObservableList<Email_InformationDTO> getEmailInfo() {
        ObservableList<Email_InformationDTO> thisEmailInfo = FXCollections.observableArrayList();
        String query = "SELECT id.membership_id,m.join_date,p.l_name,p.f_name,email,primary_use "
                + "FROM email e "
                + "INNER JOIN person p ON p.p_id=e.p_id "
                + "INNER JOIN membership m ON m.ms_id=p.ms_id "
                + "INNER JOIN membership_id id ON id.ms_id=m.ms_id "
                + "WHERE id.fiscal_year='" + BaseApplication.selectedYear
                + "' AND id.renew=true"
                + " order by id.membership_id";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisEmailInfo.add(new Email_InformationDTO(rs.getInt("membership_id"), rs.getString("join_date"),
                        rs.getString("l_name"), rs.getString("f_name"), rs.getString("email"),
                        rs.getBoolean("primary_use")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisEmailInfo;
    }

    public static ObservableList<EmailDTO> getEmail(int p_id) {
        String query = "SELECT * FROM email";
        if(p_id != 0)
            query += " WHERE p_id=" + p_id;
        ObservableList<EmailDTO> email = FXCollections.observableArrayList();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                email.add(new EmailDTO(
                        rs.getInt("EMAIL_ID")
                        ,rs.getInt("p_id")
                        ,rs.getBoolean("primary_use")
                        ,rs.getString("email")
                        ,rs.getBoolean("EMAIL_LISTED")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return email;
    }

    public static String getEmail(PersonDTO person) {
        EmailDTO email = null;
        String returnEmail = "";
        String query = "SELECT * FROM email WHERE p_id=" + person.getP_id() + " AND primary_use=true";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
                email = new EmailDTO(
                        rs.getInt("EMAIL_ID")
                        ,rs.getInt("p_id")
                        ,rs.getBoolean("primary_use")
                        ,rs.getString("email")
                        ,rs.getBoolean("EMAIL_LISTED"));
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        if(email.getEmail() != null) {
            returnEmail = email.getEmail();
        }
        return returnEmail;
    }
}
