package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.DepositDTO;
import com.ecsail.structures.DepositTotal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlDeposit {
    public static DepositDTO getDeposit(int year, int batch) {
        DepositDTO thisDeposit = null;
        String query = "SELECT * FROM deposit WHERE fiscal_year=" + year + " AND batch=" + batch;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisDeposit = new DepositDTO(
                        rs.getInt("DEPOSIT_ID"),
                        rs.getString("DEPOSIT_DATE"),
                        rs.getString("fiscal_year"),
                        rs.getInt("batch")
                );
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return thisDeposit;
    }

    public static void getDeposit(DepositDTO depositDTO) {
        String query = "SELECT * FROM deposit WHERE fiscal_year=" + depositDTO.getFiscalYear()
                + " AND batch=" + depositDTO.getBatch();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                depositDTO.setDeposit_id(rs.getInt("DEPOSIT_ID"));
                depositDTO.setDepositDate(rs.getString("DEPOSIT_DATE"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
    }

    public static ObservableList<DepositDTO> getDeposits() {
        ObservableList<DepositDTO> thisDeposits = FXCollections.observableArrayList();
        String query = "SELECT * FROM deposit";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisDeposits.add(new DepositDTO(
                        rs.getInt("DEPOSIT_ID"),
                        rs.getString("DEPOSIT_DATE"),
                        rs.getString("fiscal_year"),
                        rs.getInt("batch")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisDeposits;
    }

    public static DepositTotal getTotals(DepositDTO d) {
        DepositTotal depositTotal = null;
        String query = "select sum(TOTAL) AS TOTAL, sum(CREDIT) AS CREDIT,sum(PAID) AS PAID from invoice where " +
                "FISCAL_YEAR="+d.getFiscalYear()+" and BATCH="+d.getBatch()+" and COMMITTED=true";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                depositTotal = new DepositTotal(
                        rs.getString("TOTAL"),
                        rs.getString("CREDIT"),
                        rs.getString("PAID")
                );
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return depositTotal;
    }

    public static int getNumberOfDepositBatches(String year) {
        int number = 0;
        String query = "SELECT MAX(batch) FROM deposit WHERE fiscal_year=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            number = rs.getInt("MAX(batch)");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
