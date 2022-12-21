package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.DepositDTO;
import com.ecsail.structures.FeeDTO;
import com.ecsail.structures.InvoiceItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

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
                        rs.getString("FIELD_NAME"),
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

    public static ObservableList<InvoiceItemDTO> getAllInvoiceItemsByYearAndBatch(DepositDTO d) { // overload
        ObservableList<InvoiceItemDTO> invoiceItems = FXCollections.observableArrayList();
        String query = "select ii.* from invoice i left join invoice_item ii on i.ID = ii.INVOICE_ID " +
                "where i.FISCAL_YEAR="+d.getFiscalYear()+" and ii.FISCAL_YEAR="+d.getFiscalYear()+" " +
                "and i.BATCH="+d.getBatch();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoiceItems.add(new InvoiceItemDTO(
                        rs.getInt("ID"),
                        rs.getInt("INVOICE_ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("FIELD_NAME"),
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

    public static ObservableList<InvoiceItemDTO> getAllInvoiceItemSumsByDeposit(DepositDTO d) { // overload
        ObservableList<InvoiceItemDTO> invoiceItems = FXCollections.observableArrayList();
        String query = "select ii.* from invoice i left join invoice_item ii on i.ID = ii.INVOICE_ID " +
                "where i.FISCAL_YEAR="+d.getFiscalYear()+" and ii.FISCAL_YEAR="+d.getFiscalYear()+" " +
                "and i.BATCH="+d.getBatch();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoiceItems.add(new InvoiceItemDTO(
                        rs.getInt("ID"),
                        rs.getInt("INVOICE_ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("FIELD_NAME"),
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
                        rs.getString("FIELD_NAME"),
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

    public static InvoiceItemDTO getInvoiceItemSumByYearAndType(int year, String type, int batch) { // overload
        InvoiceItemDTO invoiceItem = null;
        String query = "select sum(ii.value) AS VALUE,sum(ii.QTY) AS QTY,IF(SUM(ii.IS_CREDIT) > 0,true,false) AS IS_CREDIT" +
                " from invoice_item  ii left join invoice i on ii.INVOICE_ID = i.ID where i.FISCAL_YEAR="+year+" " +
                " and ii.FISCAL_YEAR="+year+" and FIELD_NAME='"+type+"' and COMMITTED=true";
        if(batch > 0)
            query = "select sum(ii.value) AS VALUE,sum(ii.QTY) AS QTY, IF(SUM(ii.IS_CREDIT) > 0,true,false)" +
                    " AS IS_CREDIT from invoice_item ii left join invoice i on ii.INVOICE_ID = i.ID where" +
                    " i.FISCAL_YEAR="+year+" and ii.FISCAL_YEAR="+year+" and FIELD_NAME='"+type+"' and BATCH="+batch;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                invoiceItem = new InvoiceItemDTO(
                        0,
                        0,
                        0,
                        year,
                        type,
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

    public static InvoiceItemDTO getInvoiceItemByFeeDTO(FeeDTO feeDTO, int msId) { // overload
        InvoiceItemDTO invoiceItem = null;
        String query = "select * from invoice_item where ms_id="+msId+" and fiscal_year="+feeDTO.getFeeYear()+" " +
                "and field_name= '"+feeDTO.getDescription()+"'";
        System.out.println(query);
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) { 
                invoiceItem = new InvoiceItemDTO(
                        rs.getInt("ID"),
                        rs.getInt("INVOICE_ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getString("FIELD_NAME"),
                        rs.getBoolean("IS_CREDIT"),
                        rs.getString("VALUE"),
                        rs.getInt("QTY"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve invoice items", "See below for details");
        }
        return invoiceItem;
    }
}
