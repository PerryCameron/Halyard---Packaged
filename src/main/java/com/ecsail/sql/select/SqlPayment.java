package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.PaymentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlPayment {
    public static ObservableList<PaymentDTO> getPayments() {
        ObservableList<PaymentDTO> thisPayments = FXCollections.observableArrayList();
        String query = "SELECT * FROM payment";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisPayments.add(new PaymentDTO(
                        rs.getInt("pay_id"),
                        rs.getInt("INVOICE_ID"),
                        rs.getString("CHECK_NUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayments;
    }

    public static ObservableList<PaymentDTO> getPayments(int invoice_id) {
        ObservableList<PaymentDTO> thisPayments = FXCollections.observableArrayList();
        String query = "SELECT * FROM payment WHERE invoice_id=" + invoice_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisPayments.add(new PaymentDTO(
                        rs.getInt("pay_id"),
                        rs.getInt("INVOICE_ID"),
                        rs.getString("CHECK_NUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayments;
    }

    public static PaymentDTO getPayment(int invoice_id) {
        PaymentDTO thisPayment = null;
        String query = "SELECT * FROM payment WHERE invoice_id=" + invoice_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisPayment = new PaymentDTO(
                        rs.getInt("pay_id"),
                        rs.getInt("INVOICE_ID"),
                        rs.getString("CHECK_NUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        );
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayment;
    }

    public static String getTotalAmount(int invoice_id) {
        String number = "0.00";
        String query = "SELECT SUM(amount) FROM payment WHERE invoice_id=" + invoice_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            number = rs.getString("SUM(amount)");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

}
