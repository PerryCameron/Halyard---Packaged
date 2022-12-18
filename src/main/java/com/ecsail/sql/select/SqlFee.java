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
                            rs.getString("FIELD_VALUE"),
                            rs.getInt("DB_INVOICE_ID"),
                            rs.getInt("fee_year"),
                            rs.getString("DESCRIPTION"),
                            rs.getBoolean("DEFAULT_FEE")
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
                        rs.getString("FIELD_VALUE"),
                        rs.getInt("DB_INVOICE_ID"),
                        rs.getInt("fee_year"),
                        rs.getString("DESCRIPTION"),
                        rs.getBoolean("DEFAULT_FEE")
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
                        rs.getString("FIELD_VALUE"),
                        rs.getInt("DB_INVOICE_ID"),
                        rs.getInt("fee_year"),
                        rs.getString("DESCRIPTION"),
                        rs.getBoolean("DEFAULT_FEE")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeDTOS;
    }

    public static ObservableList<FeeDTO> getAllFeesByFieldNameAndYear(FeeDTO feeDTO) {  //p_id
        ObservableList<FeeDTO> feeDTOS = FXCollections.observableArrayList();
        String query = "SELECT * FROM fee WHERE field_name='" + feeDTO.getFieldName()
                + "' and fee_year=" + feeDTO.getFeeYear();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                feeDTOS.add(new FeeDTO(
                        rs.getInt("FEE_ID"),
                        rs.getString("FIELD_NAME"),
                        rs.getString("FIELD_VALUE"),
                        rs.getInt("DB_INVOICE_ID"),
                        rs.getInt("fee_year"),
                        rs.getString("DESCRIPTION"),
                        rs.getBoolean("DEFAULT_FEE")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeDTOS;
    }

    public static FeeDTO getFeeByMembershipTypeForFiscalYear(int year, int msId) {  //p_id
        FeeDTO thisFee = null;
        String query = "select f.* from fee f where f.Description=(select\n" +
                "CASE\n" +
                "    WHEN (select MEM_TYPE from membership_id where FISCAL_YEAR="+year+" and MS_ID="+msId+") = 'FM' THEN 'Family'\n" +
                "    WHEN (select MEM_TYPE from membership_id where FISCAL_YEAR="+year+" and MS_ID="+msId+") = 'RM' THEN 'Regular'\n" +
                "    WHEN (select MEM_TYPE from membership_id where FISCAL_YEAR="+year+" and MS_ID="+msId+") = 'LA' THEN 'Lake Associate'\n" +
                "    WHEN (select MEM_TYPE from membership_id where FISCAL_YEAR="+year+" and MS_ID="+msId+") = 'SO' THEN 'Social'\n" +
                "    ELSE 'None'\n" +
                "END AS TYPE\n" +
                "from membership_id where FISCAL_YEAR="+year+" and MS_ID="+msId+"\n" +
                ") and FEE_YEAR="+year+";";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisFee = new FeeDTO(
                        rs.getInt("FEE_ID"),
                        rs.getString("FIELD_NAME"),
                        rs.getString("FIELD_VALUE"),
                        rs.getInt("DB_INVOICE_ID"),
                        rs.getInt("fee_year"),
                        rs.getString("DESCRIPTION"),
                        rs.getBoolean("DEFAULT_FEE")
                );
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisFee;
    }
}
