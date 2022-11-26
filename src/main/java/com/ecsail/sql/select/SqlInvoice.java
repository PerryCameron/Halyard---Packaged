package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.gui.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.structures.InvoiceDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlInvoice {
    public static ObservableList<InvoiceDTO> getInvoicesByMsid(int ms_id) { // overload
        ObservableList<InvoiceDTO> invoices = FXCollections.observableArrayList();
        String query = "SELECT * FROM invoice WHERE ms_id=" + ms_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoices.add(new InvoiceDTO(
                        rs.getInt("ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("PAID"),
                        rs.getString("TOTAL"),
                        rs.getString("CREDIT"),
                        rs.getString("BALANCE"),
                        rs.getInt("BATCH"),
                        rs.getBoolean("COMMITTED"),
                        rs.getBoolean("CLOSED"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getString("MAX_CREDIT")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return invoices;
    }

    public static ObservableList<InvoiceDTO> getAllInvoices() { // overload
        ObservableList<InvoiceDTO> invoices = FXCollections.observableArrayList();
        String query = "SELECT * FROM invoice";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoices.add(new InvoiceDTO(
                        rs.getInt("ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("PAID"),
                        rs.getString("TOTAL"),
                        rs.getString("CREDIT"),
                        rs.getString("BALANCE"),
                        rs.getInt("BATCH"),
                        rs.getBoolean("COMMITTED"),
                        rs.getBoolean("CLOSED"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getString("MAX_CREDIT")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return invoices;
    }

    public static ObservableList<InvoiceDTO> getInvoicesByYear(String year) { // overload
        ObservableList<InvoiceDTO> invoices = FXCollections.observableArrayList();
        String query = "SELECT * FROM invoice where fiscal_year=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoices.add(new InvoiceDTO(
                        rs.getInt("ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("PAID"),
                        rs.getString("TOTAL"),
                        rs.getString("CREDIT"),
                        rs.getString("BALANCE"),
                        rs.getInt("BATCH"),
                        rs.getBoolean("COMMITTED"),
                        rs.getBoolean("CLOSED"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getString("MAX_CREDIT")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return invoices;
    }

    public static ObservableList<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByYear(String year) { // overload
        ObservableList<InvoiceWithMemberInfoDTO> invoices = FXCollections.observableArrayList();
        String query = "select mi.MEMBERSHIP_ID, p.L_NAME, p.F_NAME, i.* from invoice i left join person" +
                " p on i.MS_ID = p.MS_ID left join membership_id mi on i.MS_ID = mi.MS_ID where i.FISCAL_YEAR=" +year+
                " and mi.FISCAL_YEAR="+year+" and p.MEMBER_TYPE=1 and i.COMMITTED=true";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoices.add(new InvoiceWithMemberInfoDTO(
                        rs.getInt("ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("PAID"),
                        rs.getString("TOTAL"),
                        rs.getString("CREDIT"),
                        rs.getString("BALANCE"),
                        rs.getInt("BATCH"),
                        rs.getBoolean("COMMITTED"),
                        rs.getBoolean("CLOSED"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getString("MAX_CREDIT"),
                        rs.getInt("MEMBERSHIP_ID"),
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return invoices;
    }



}
