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
                        rs.getString("year"),
                        rs.getString("objectName"),
                        rs.getString("widget_type"),
                        rs.getDouble("width"),
                        rs.getInt("order"),
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

    public static ArrayList<DbInvoiceDTO> getInvoiceWidgetsByYear(int year) {  //p_id
        ArrayList<DbInvoiceDTO> theseWidgets = new ArrayList<>();
        String query = "select * from db_invoice where year=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseWidgets.add(new DbInvoiceDTO(
                        rs.getInt("ID"),
                        rs.getString("year"),
                        rs.getString("objectName"),
                        rs.getString("widget_type"),
                        rs.getDouble("width"),
                        rs.getInt("order"),
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

    public static ArrayList<String> getInvoiceCategoriesByYear(int year) {  //p_id
        ArrayList<String> categories = new ArrayList<>();
        String query = "select objectName from db_invoice where year=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                categories.add(rs.getString("objectName"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve categories","See below for details");
        }
        return categories;
    }

}