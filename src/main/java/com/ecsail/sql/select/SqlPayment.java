package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.PaymentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
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

    public static ObservableList<PaymentDTO> getPayments(int money_id) {
        ObservableList<PaymentDTO> thisPayments = FXCollections.observableArrayList();
        String query = "SELECT * FROM payment WHERE money_id=" + money_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisPayments.add(new PaymentDTO(
                        rs.getInt("pay_id"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
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

    public static PaymentDTO getPayment(int money_id) {
        PaymentDTO thisPayment = null;
        String query = "SELECT * FROM payment WHERE money_id=" + money_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisPayment = new PaymentDTO(
                        rs.getInt("pay_id"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
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

}
