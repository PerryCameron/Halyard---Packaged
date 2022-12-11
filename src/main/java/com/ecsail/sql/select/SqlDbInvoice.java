package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.DbInvoiceDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlDbInvoice {

    public static ArrayList<DbInvoiceDTO> getInvoiceWidgets() {  //p_id
        ArrayList<DbInvoiceDTO> theseWidgets = new ArrayList<>();
        String query = "select * from db_invoice";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseWidgets.add(new DbInvoiceDTO(
                        rs.getInt("ID"),
                        rs.getString("FISCAL_YEAR"),
                        rs.getString("FIELD_NAME"),
                        rs.getString("widget_type"),
                        rs.getDouble("width"),
                        rs.getInt("invoice_order"),
                        rs.getBoolean("multiplied"),
                        rs.getBoolean("price_editable"),
                        rs.getBoolean("is_credit"),
                        rs.getInt("max_qty"),
                        rs.getBoolean("auto_populate")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseWidgets;
    }

    public static ArrayList<DbInvoiceDTO> getDbInvoiceByYear(int year) {  //p_id
        ArrayList<DbInvoiceDTO> dbInvoiceDTOS = new ArrayList<>();
        String query = "select * from db_invoice where FISCAL_YEAR=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                dbInvoiceDTOS.add(new DbInvoiceDTO(
                        rs.getInt("ID"),
                        rs.getString("FISCAL_YEAR"),
                        rs.getString("FIELD_NAME"),
                        rs.getString("widget_type"),
                        rs.getDouble("width"),
                        rs.getInt("invoice_order"),
                        rs.getBoolean("multiplied"),
                        rs.getBoolean("price_editable"),
                        rs.getBoolean("is_credit"),
                        rs.getInt("max_qty"),
                        rs.getBoolean("auto_populate")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return dbInvoiceDTOS;
    }

    public static ArrayList<String> getInvoiceCategoriesByYear(int year) {  //p_id
        ArrayList<String> categories = new ArrayList<>();
        String query = "select FIELD_NAME from db_invoice where FISCAL_YEAR=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                categories.add(rs.getString("FIELD_NAME"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve categories","See below for details");
        }
        return categories;
    }

    public static DbInvoiceDTO getInvoiceByYearAndFieldName(int year, String fieldName) {  //p_id
        DbInvoiceDTO dbInvoiceDTO = null;
        String query = "select * from db_invoice where FISCAL_YEAR="+year+" and FIELD_NAME='"+fieldName+"'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                dbInvoiceDTO = new DbInvoiceDTO(
                        rs.getInt("ID"),
                        rs.getString("FISCAL_YEAR"),
                        rs.getString("FIELD_NAME"),
                        rs.getString("widget_type"),
                        rs.getDouble("width"),
                        rs.getInt("invoice_order"),
                        rs.getBoolean("multiplied"),
                        rs.getBoolean("price_editable"),
                        rs.getBoolean("is_credit"),
                        rs.getInt("max_qty"),
                        rs.getBoolean("auto_populate")
                );
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve db_invoice","See below for details");
        }
        return dbInvoiceDTO;
    }

}
