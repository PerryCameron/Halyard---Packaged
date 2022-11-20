package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.InvoiceItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlInvoiceItem {

    public static ObservableList<InvoiceItemDTO> getInvoiceItemsByInvoiceId(int id) { // overload
        ObservableList<InvoiceItemDTO> invoiceItems = FXCollections.observableArrayList();
        String query = "SELECT * FROM invoice_item WHERE invoice_id=" + id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoiceItems.add(new InvoiceItemDTO(
                        rs.getInt("ID"),
                        rs.getInt("INVOICE_ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("ITEM_TYPE"),
                        rs.getBoolean("MULTIPLIED"),
                        rs.getBoolean("IS_CREDIT"),
                        rs.getString("VALUE"),
                        rs.getInt("QTY")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve invoice items", "See below for details");
        }
        return invoiceItems;
    }

    public static ObservableList<InvoiceItemDTO> getAllInvoiceItems() { // overload
        ObservableList<InvoiceItemDTO> invoiceItems = FXCollections.observableArrayList();
        String query = "SELECT * FROM invoice_item";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoiceItems.add(new InvoiceItemDTO(
                        rs.getInt("ID"),
                        rs.getInt("INVOICE_ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("ITEM_TYPE"),
                        rs.getBoolean("MULTIPLIED"),
                        rs.getBoolean("IS_CREDIT"),
                        rs.getString("VALUE"),
                        rs.getInt("QTY")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve invoice items", "See below for details");
        }
        return invoiceItems;
    }
}
