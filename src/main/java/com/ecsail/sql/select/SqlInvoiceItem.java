package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.FeeDTO;
import com.ecsail.dto.InvoiceItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlInvoiceItem {

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

    public static InvoiceItemDTO getInvoiceItemSumByYearAndType(int year, String type, int batch) { // overload
        InvoiceItemDTO invoiceItem = null; // gets total
        String query = "select sum(ii.value) AS VALUE,sum(ii.QTY) AS QTY,IF(SUM(ii.IS_CREDIT) > 0,true,false) AS IS_CREDIT" +
                " from invoice_item  ii left join invoice i on ii.INVOICE_ID = i.ID where i.FISCAL_YEAR="+year+" " +
                " and ii.FISCAL_YEAR="+year+" and FIELD_NAME='"+type+"' and COMMITTED=true";
        if(batch > 0) // gets for single batch
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


}
