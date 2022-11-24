package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.InvoiceWidgetDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlInvoiceWidget {

    public static ArrayList<InvoiceWidgetDTO> getInvoiceWidgets() {  //p_id
        ArrayList<InvoiceWidgetDTO> theseWidgets = new ArrayList<>();
        String query = "select * from db_invoice";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseWidgets.add(new InvoiceWidgetDTO(
                        rs.getInt("ID"),
                        rs.getString("year"),
                        rs.getString("objectName"),
                        rs.getString("widget_type"),
                        rs.getDouble("width"),
                        rs.getInt("order"),
                        rs.getBoolean("multiplied"),
                        rs.getBoolean("price_editable"),
                        rs.getBoolean("is_credit"),
                        rs.getInt("max_qty")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseWidgets;
    }

    public static ArrayList<InvoiceWidgetDTO> getInvoiceWidgetsByYear(int year) {  //p_id
        ArrayList<InvoiceWidgetDTO> theseWidgets = new ArrayList<>();
        String query = "select * from db_invoice where year=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseWidgets.add(new InvoiceWidgetDTO(
                        rs.getInt("ID"),
                        rs.getString("year"),
                        rs.getString("objectName"),
                        rs.getString("widget_type"),
                        rs.getDouble("width"),
                        rs.getInt("order"),
                        rs.getBoolean("multiplied"),
                        rs.getBoolean("price_editable"),
                        rs.getBoolean("is_credit"),
                        rs.getInt("max_qty")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseWidgets;
    }

    public static ArrayList<String> getInvoiceCategoriesByYear(int year) {  //p_id
        ArrayList<String> categories = null;
        String query = "select * from db_invoice";
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
