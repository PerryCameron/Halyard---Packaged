package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.InvoiceWidget;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlInvoiceWidget {

    public static ArrayList<InvoiceWidget> getInvoiceWidgets() {  //p_id
        ArrayList<InvoiceWidget> theseWidgets = new ArrayList<>();
        String query = "SELECT * FROM db_invoice";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseWidgets.add(new InvoiceWidget(
                        rs.getInt("ID"),
                        rs.getString("effective"),
                        rs.getString("objectName"),
                        rs.getString("widget_type"),
                        rs.getBoolean("multiplied"),
                        rs.getBoolean("price_editable"),
                        rs.getBoolean("is_credit"),
                        rs.getString("listener_type")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseWidgets;
    }

}
