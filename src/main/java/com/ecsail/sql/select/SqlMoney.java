package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.DepositSummaryDTO;
import com.ecsail.structures.MoneyDTO;
import com.ecsail.structures.WorkCreditDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlMoney {
    public static ObservableList<MoneyDTO> getMoniesByMsid(int ms_id) { // overload
        ObservableList<MoneyDTO> theseFiscals = FXCollections.observableArrayList();
        String query = "SELECT * FROM money WHERE ms_id=" + ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseFiscals.add(new MoneyDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getInt("BATCH"),
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
                        rs.getBoolean("COMMITED"),
                        rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"),
                        rs.getString("INITIATION"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseFiscals;
    }

    public static ObservableList<MoneyDTO> getAllMonies() { // overload
        ObservableList<MoneyDTO> theseFiscals = FXCollections.observableArrayList();
        String query = "SELECT * FROM money";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseFiscals.add(new MoneyDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getInt("BATCH"),
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
                        rs.getBoolean("COMMITED"),
                        rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"),
                        rs.getString("INITIATION"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseFiscals;
    }

    public static MoneyDTO getMoneyRecordByMsidAndYear(int ms_id, String fiscalYear) { // overload
        String query = "SELECT * FROM money WHERE ms_id=" + ms_id + " and fiscal_year=" + fiscalYear;
        MoneyDTO thisFiscal = null;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisFiscal = new MoneyDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getString("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_BEACH_RACK"),rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"),rs.getString("PAID"),rs.getString("TOTAL"),rs.getString("CREDIT"),
                        rs.getString("BALANCE"), rs.getString("DUES"),rs.getBoolean("COMMITED"),rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"),rs.getString("INITIATION"),rs.getBoolean("SUPPLEMENTAL"), rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisFiscal;
    }

    public static DepositSummaryDTO getSumTotalsFromYearAndBatch(String year, int batch) { // overload
        String query = "SELECT\n" +
                "SUM(OFFICER_CREDIT),\n" +
                "SUM(EXTRA_KEY),\n" +
                "SUM(KAYAK_SHED_KEY),\n" +
                "SUM(SAIL_LOFT_KEY),\n" +
                "SUM(SAIL_SCHOOL_LOFT_KEY),\n" +
                "SUM(BEACH),\n" +
                "SUM(WET_SLIP),\n" +
                "SUM(KAYAK_RACK),\n" +
                "SUM(KAYAK_SHED),\n" +
                "SUM(SAIL_LOFT),\n" +
                "SUM(SAIL_SCHOOL_LASER_LOFT),\n" +
                "SUM(WINTER_STORAGE),\n" +
                "SUM(YSC_DONATION),\n" +
                "SUM(PAID),\n" +
                "SUM(TOTAL),\n" +
                "SUM(CREDIT),\n" +
                "SUM(BALANCE),\n" +
                "SUM(DUES),\n" +
                "SUM(OTHER,\n" +
                "SUM(INITIATION),\n" +
                "SUM(WORK_CREDIT),\n" +
                "SUM(OTHER_CREDIT),\n" +
                "SUM(KAYAK_BEACH_RACK)" +
                "FROM money WHERE FISCAL_YEAR=" + year + " and BATCH=" + batch;
        DepositSummaryDTO thisFiscal = null;
        try {
           ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisFiscal = new DepositSummaryDTO(
                        rs.getBigDecimal("SUM(TOTAL)"),
                        rs.getBigDecimal("SUM(PAID)"),
                        rs.getBigDecimal("SUM(BALANCE)"),
                        rs.getBigDecimal("SUM(OFFICER_CREDIT)"),
                        rs.getBigDecimal("SUM(WET_SLIP)"),
                        rs.getBigDecimal("SUM(YSC_DONATION)"),
                        rs.getBigDecimal("SUM(CREDIT)"),
                        rs.getBigDecimal("SUM(DUES)"),
                        rs.getBigDecimal("SUM(OTHER)"),
                        rs.getBigDecimal("SUM(INITIATION)"),
                        rs.getInt("SUM(EXTRA_KEY)"),
                        rs.getInt("SUM(KAYAK_SHED_KEY)"),
                        rs.getInt("SUM(SAIL_LOFT_KEY)"),
                        rs.getInt("SUM(SAIL_SCHOOL_LOFT_KEY)"),
                        rs.getInt("SUM(BEACH)"),
                        rs.getInt("SUM(KAYAK_RACK)"),
                        rs.getInt("SUM(KAYAK_BEACH_RACK)"),
                        rs.getInt("SUM(KAYAK_SHED)"),
                        rs.getInt("SUM(SAIL_LOFT)"),
                        rs.getInt("SUM(SAIL_SCHOOL_LASER_LOFT)"),
                        rs.getInt("SUM(WINTER_STORAGE)"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            //            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisFiscal;
    }



    public static WorkCreditDTO getWorkCredit(int moneyID) {
        WorkCreditDTO workCredits = null;
        String query = "SELECT * FROM work_credit WHERE money_id=" + moneyID;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            workCredits = new WorkCreditDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),rs.getInt("RACING"), rs.getInt("HARBOR"),
                        rs.getInt("SOCIAL"), rs.getInt("OTHER"));
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return workCredits;
    }

    public static boolean isCommitted(int money_id) {
        boolean committed = false;
        String query = "SELECT commited FROM money WHERE money_id=" + money_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            committed = rs.getBoolean("commited");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return committed;
    }

    public static int getTotalAmount(int money_id) {
        int number = 0;
        String query = "SELECT SUM(amount) FROM payment WHERE money_id=" + money_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            number = rs.getInt("SUM(amount)");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

    public static int getNumberOfMemberDues(String year, String batch) {
        int number = 0;
        String query = "SELECT COUNT(*) FROM money WHERE FISCAL_YEAR="+year+" and BATCH="+batch+" and DUES > 0";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            number = rs.getInt("COUNT(*)");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

    public static int getBatchNumber(String year) {
        int number = 0;
        String query = "SELECT MAX(batch) FROM money WHERE commited=true and fiscal_year='"+year+"'";
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
