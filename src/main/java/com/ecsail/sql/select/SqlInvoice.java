package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.gui.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.InvoiceDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlInvoice {


    public static ObservableList<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByDeposit(DepositDTO d) { // overload
        ObservableList<InvoiceWithMemberInfoDTO> invoices = FXCollections.observableArrayList();
        String query = "select mi.MEMBERSHIP_ID, p.L_NAME, p.F_NAME, i.* from invoice i left join person" +
                " p on i.MS_ID = p.MS_ID left join membership_id mi on i.MS_ID = mi.MS_ID where i.FISCAL_YEAR=" +d.getFiscalYear()+
                " and mi.FISCAL_YEAR="+d.getFiscalYear()+" and p.MEMBER_TYPE=1 and i.COMMITTED=true and i.batch=" +d.getBatch();
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

    public static int getBatchNumber(String year) {
        int number = 0;
        String query = "SELECT MAX(batch) FROM invoice WHERE committed=true and fiscal_year='"+year+"'";
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
