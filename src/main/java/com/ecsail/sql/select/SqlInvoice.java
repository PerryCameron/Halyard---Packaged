package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.InvoiceDTO;
import com.ecsail.structures.MoneyDTO;
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
                        rs.getBoolean("SUPPLEMENTAL")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return invoices;
    }
}
