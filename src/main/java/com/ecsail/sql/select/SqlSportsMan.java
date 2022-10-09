package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlSportsMan {
//    public static ArrayList<Object_Sportsmen> getSportsManAwardNames() {
//        ArrayList<Object_Sportsmen> theseOfficers = new ArrayList<>();
//        String query = "SELECT award_year,f_name,l_name FROM awards a LEFT JOIN person p ON a.p_id=p.p_id";
//        try {
//            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
//            while (rs.next()) {
//                theseOfficers.add(new Object_Sportsmen(
//                        rs.getString("award_year"),
//                        rs.getString("f_name"),
//                        rs.getString("L_NAME")
//                ));
//            }
//            Halyard.getConnect().closeResultSet(rs);
//        } catch (SQLException e) {
//            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
//        }
//        return theseOfficers;
//    }
}
