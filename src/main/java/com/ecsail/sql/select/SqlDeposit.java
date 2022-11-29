package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.DepositDTO;
import com.ecsail.structures.DepositTotal;
import com.ecsail.structures.PaidDuesDTO;
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

    public static ObservableList<PaidDuesDTO> getPaidDues(DepositDTO currentDeposit) {
        ObservableList<PaidDuesDTO> theseFiscals = FXCollections.observableArrayList();
        String query = "SELECT id.membership_id, mo.*, p.l_name, p.f_name FROM money mo "
                + "INNER JOIN membership_id id on mo.ms_id=id.ms_id AND mo.fiscal_year=id.fiscal_year "
                + "INNER JOIN membership me on mo.ms_id=me.ms_id "
                + "INNER JOIN person p ON me.P_ID=p.P_ID  WHERE mo.fiscal_year='" + currentDeposit.getFiscalYear()
                + "' AND mo.commited=true AND mo.batch=" + currentDeposit.getBatch() + " "
                + "ORDER BY id.membership_id";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseFiscals.add(new PaidDuesDTO(rs.getInt("MONEY_ID"), rs.getInt("ms_id"),
                        rs.getInt("fiscal_year"), rs.getInt("batch"), rs.getString("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_BEACH_RACK"), rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"), rs.getString("PAID"), rs.getString("TOTAL"), rs.getString("CREDIT"),
                        rs.getString("BALANCE"), rs.getString("DUES"), rs.getBoolean("commited"), rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"), rs.getString("INITIATION"), rs.getBoolean("SUPPLEMENTAL"), rs.getInt("WORK_CREDIT"), rs.getString("OTHER_CREDIT"), rs.getString("F_NAME"),
                        rs.getString("L_NAME"), rs.getInt("membership_id")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return theseFiscals;
    }

    public static ObservableList<PaidDuesDTO> getPaidDues(String selectedYear) {
        String query = "SELECT id.membership_id, mo.*, p.l_name, p.f_name FROM money mo "
                + "INNER JOIN membership_id id on mo.ms_id=id.ms_id AND mo.fiscal_year=id.fiscal_year "
                + "INNER JOIN membership me on mo.ms_id=me.ms_id "
                + "INNER JOIN person p ON me.P_ID=p.P_ID WHERE mo.fiscal_year=" + selectedYear + " AND mo.commited=true ORDER BY id.membership_id";
        ObservableList<PaidDuesDTO> theseFiscals = FXCollections.observableArrayList();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseFiscals.add(new PaidDuesDTO(
                        rs.getInt("MONEY_ID"),
                        rs.getInt("ms_id"),
                        rs.getInt("fiscal_year"),
                        rs.getInt("batch"),
                        rs.getString("OFFICER_CREDIT"),
                        rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"),
                        rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"),
                        rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"),
                        rs.getInt("KAYAK_RACK"),
                        rs.getInt("KAYAK_BEACH_RACK"),
                        rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"),
                        rs.getInt("SAIL_SCHOOL_LASER_LOFT"),
                        rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"),
                        rs.getString("PAID"),
                        rs.getString("TOTAL"),
                        rs.getString("CREDIT"),
                        rs.getString("BALANCE"),
                        rs.getString("DUES"),
                        rs.getBoolean("commited"),
                        rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"),
                        rs.getString("INITIATION"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT"),
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME"),
                        rs.getInt("membership_id")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return theseFiscals;
    }

    public static ObservableList<PaidDuesDTO> getPaidDues(String selectedYear, int batch) { // overload
        String query = "SELECT mo.*, id.membership_id, p.l_name, p.f_name FROM membership_id id INNER JOIN membership m ON m.ms_id=id.ms_id LEFT JOIN person p ON m.P_ID=p.P_ID INNER JOIN money mo ON mo.ms_id=m.ms_id WHERE id.fiscal_year=" + selectedYear + " AND mo.batch=" + batch + " AND mo.fiscal_year=" + selectedYear;
        ObservableList<PaidDuesDTO> theseFiscals = FXCollections.observableArrayList();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseFiscals.add(new PaidDuesDTO(rs.getInt("MONEY_ID"), rs.getInt("ms_id"),
                        rs.getInt("fiscal_year"), rs.getInt("batch"), rs.getString("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_BEACH_RACK"), rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"), rs.getString("PAID"), rs.getString("TOTAL"), rs.getString("CREDIT"),
                        rs.getString("BALANCE"), rs.getString("DUES"), rs.getBoolean("commited"), rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"), rs.getString("INITIATION"), rs.getBoolean("SUPPLEMENTAL"), rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT"), rs.getString("F_NAME"),
                        rs.getString("L_NAME"), rs.getInt("membership_id")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }

        return theseFiscals;
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

    // can do this with a regular object but not properties for some reason
    public static void  updateDeposit(String year, int batch, DepositDTO thisDeposit) {
        String query = "SELECT * FROM deposit WHERE fiscal_year=" + year + " AND batch=" + batch;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
            thisDeposit.setDeposit_id(rs.getInt("DEPOSIT_ID"));
            thisDeposit.setDepositDate(rs.getString("DEPOSIT_DATE"));
            thisDeposit.setFiscalYear(rs.getString("fiscal_year"));
            thisDeposit.setBatch(rs.getInt("batch"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
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
