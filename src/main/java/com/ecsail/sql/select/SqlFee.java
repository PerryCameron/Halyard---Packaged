package com.ecsail.sql.select;



import com.ecsail.BaseApplication;
import com.ecsail.structures.FeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlFee {

        public static ArrayList<FeeDTO> getFeesFromYear(int year) {  //p_id
            ArrayList<FeeDTO> thisAwards = new ArrayList<>();
            String query = "SELECT * FROM fee WHERE fee_year=" + year;
            try {
                ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
                while (rs.next()) {
                    thisAwards.add(new FeeDTO(
                            rs.getInt("FEE_ID"),
                            rs.getString("FIELD_NAME"),
                            rs.getBigDecimal("FIELD_VALUE"),
                            rs.getInt("FIELD_QTY"),
                            rs.getInt("fee_year"),
                            rs.getString("DESCRIPTION"),
                            rs.getString("GROUP_NAME")
                    ));
                }
                BaseApplication.connect.closeResultSet(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return thisAwards;
        }

    public static ArrayList<FeeDTO> getAllFees() {  //p_id
        ArrayList<FeeDTO> thisAwards = new ArrayList<>();
        String query = "SELECT * FROM fee";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisAwards.add(new FeeDTO(
                        rs.getInt("FEE_ID"),
                        rs.getString("FIELD_NAME"),
                        rs.getBigDecimal("FIELD_VALUE"),
                        rs.getInt("FIELD_QTY"),
                        rs.getInt("fee_year"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("GROUP_NAME")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisAwards;
    }

    public static ObservableList<FeeDTO> getAllFeesByDescription(String description) {  //p_id
        ObservableList<FeeDTO> feeDTOS = FXCollections.observableArrayList();
        String query = "SELECT * FROM fee WHERE description='" + description + "'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                feeDTOS.add(new FeeDTO(
                        rs.getInt("FEE_ID"),
                        rs.getString("FIELD_NAME"),
                        rs.getBigDecimal("FIELD_VALUE"),
                        rs.getInt("FIELD_QTY"),
                        rs.getInt("fee_year"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("GROUP_NAME")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeDTOS;
    }
}
