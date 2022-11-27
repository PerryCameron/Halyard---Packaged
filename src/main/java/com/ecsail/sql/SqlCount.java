package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlCount {


    public static int getNumberOfDepositsForYear(int year) {
        int number = 0;
        String query = "select count(*) AS number_of_deposits from deposit where FISCAL_YEAR=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            number = rs.getInt("number_of_deposits");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

}
