package com.ecsail.gui.tabs.membership.fiscal.invoice;

import com.ecsail.EditCell;
import com.ecsail.enums.PaymentType;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlPayment;
import com.ecsail.structures.PaymentDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.function.Function;

public class PaymentTableView extends TableView<PaymentDTO> {
    InvoiceFooter parent;
    public PaymentTableView(InvoiceFooter invoiceFooter) {
        this.parent = invoiceFooter;
        TableColumn<PaymentDTO, String> col1 = createColumn("Amount", PaymentDTO::PaymentAmountProperty);
        col1.setPrefWidth(60);
        col1.setStyle("-fx-alignment: CENTER-RIGHT;");
        col1.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setPaymentAmount(String.valueOf(new BigDecimal(t.getNewValue()).setScale(2)));
                    var pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
                    System.out.println("pay_id= " + pay_id);
                    BigDecimal amount = new BigDecimal(t.getNewValue());
                    SqlUpdate.updatePayment(pay_id, "amount", String.valueOf(amount.setScale(2)));
                    System.out.println("Updating payment= " + String.valueOf(amount.setScale(2)));
                    // This adds all the amounts together
                    BigDecimal totalPaidAmount = new BigDecimal(SqlPayment.getTotalAmount(parent.getInvoice().getId())).setScale(2);
                    System.out.println("All payments added= " + totalPaidAmount);
                    String totalAmountPaid = String.valueOf(totalPaidAmount.setScale(2));
                    parent.getTotalPaymentText().setText(totalAmountPaid);
                    parent.getInvoice().setPaid(totalAmountPaid);
                    parent.updateTotals();
                }
        );

        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        ObservableList<PaymentType> paymentTypeList = FXCollections.observableArrayList(PaymentType.values());
        TableColumn<PaymentDTO, PaymentType> col2 = new TableColumn<>("Type");

        col2.setPrefWidth(55);
        col2.setStyle("-fx-alignment: CENTER;");
        col2.setCellValueFactory(param -> {
            var thisPayment = param.getValue();
            var paymentCode = thisPayment.getPaymentType();
            var paymentType = PaymentType.getByCode(paymentCode);
            return new SimpleObjectProperty<>(paymentType);
        });

        col2.setCellFactory(ComboBoxTableCell.forTableColumn(paymentTypeList));

        col2.setOnEditCommit((TableColumn.CellEditEvent<PaymentDTO, PaymentType> event) -> {
            var pos = event.getTablePosition();
            var newPaymentType = event.getNewValue();
            var row = pos.getRow();
            var thisPayment = event.getTableView().getItems().get(row);
            SqlUpdate.updatePayment(thisPayment.getPay_id(), "payment_type", newPaymentType.getCode());
            // need to update paid from here
            thisPayment.setPaymentType(newPaymentType.getCode());
        });

        TableColumn<PaymentDTO, String> col3 = createColumn("Check #", PaymentDTO::checkNumberProperty);
        col3.setPrefWidth(55);
        col3.setStyle("-fx-alignment: CENTER-LEFT;");
        col3.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setCheckNumber(t.getNewValue());
                    var pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
                    SqlUpdate.updatePayment(pay_id, "CHECK_NUMBER", t.getNewValue());
                }
        );

        TableColumn<PaymentDTO, String> col4 = createColumn("Date", PaymentDTO::paymentDateProperty);
        col4.setPrefWidth(70);
        col4.setStyle("-fx-alignment: CENTER-LEFT;");
        col4.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setPaymentDate(t.getNewValue());
                    var pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
                    SqlUpdate.updatePayment(pay_id, "payment_date", t.getNewValue());
                    //	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
                }
        );

        col1.setMaxWidth(1f * Integer.MAX_VALUE * 20);  // Boat Name
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 20);  // Manufacturer
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 35);   // Year
        col4.setMaxWidth(1f * Integer.MAX_VALUE * 25);  // Model

        //////////////// ATTRIBUTES ///////////////////
        HBox.setHgrow(this, Priority.ALWAYS);
        setEditable(!parent.getInvoice().isCommitted());
        setItems(parent.getPayments());
        setPrefHeight(115);
        setFixedCellSize(30);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(Arrays.asList(col1, col2, col3, col4));
    }

    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }
}
