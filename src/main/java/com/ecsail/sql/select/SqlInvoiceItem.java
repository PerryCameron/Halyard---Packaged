package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.InvoiceDTO;
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

    public static InvoiceItemDTO getInvoiceItemSumByYearAndType(String year, String type) { // overload
        InvoiceItemDTO invoiceItem = null;
        String query = "select sum(value) AS VALUE,sum(QTY) AS QTY,IF(SUM(IS_CREDIT) > 0,true,false) AS IS_CREDIT" +
                " from invoice_item where FISCAL_YEAR="+year+" " +
                "and ITEM_TYPE='"+type+"'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoiceItem = new InvoiceItemDTO(
                        0,
                        0,
                        0,
                        Integer.parseInt(year),
                        type,
                        false,
                        rs.getBoolean("IS_CREDIT"),
                        rs.getString("VALUE"),
                        rs.getInt("QTY"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return invoiceItem;
    }

    public static InvoiceItemDTO getInvoiceItemSumByYearAndTypeAndBatch(String year, String type, int batch) { // overload
        InvoiceItemDTO invoiceItem = null;
        String query = "select sum(value) AS VALUE,sum(QTY) AS QTY from invoice_item where FISCAL_YEAR="+year+" " +
                "and ITEM_TYPE='"+type+"' and BATCH";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoiceItem = new InvoiceItemDTO(
                        0,
                        0,
                        0,
                        Integer.parseInt(year),
                        type,
                        false,
                        false,
                        rs.getString("VALUE"),
                        rs.getInt("QTY"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return invoiceItem;
    }
}
