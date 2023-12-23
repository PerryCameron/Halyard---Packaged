package com.ecsail.views.tabs.membership.fiscal.invoice;

import com.ecsail.EditCell;
import com.ecsail.enums.PaymentType;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.dto.PaymentDTO;
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
import java.util.Arrays;
import java.util.function.Function;

public class PaymentTableView extends TableView<PaymentDTO> {

    InvoiceRepository invoiceRepository;
    InvoiceFooter parent;
    public PaymentTableView(InvoiceFooter invoiceFooter) {
        this.parent = invoiceFooter;
        this.invoiceRepository = parent.parent.getInvoiceRepository();
        TableColumn<PaymentDTO, String> col1 = createColumn("Amount", PaymentDTO::PaymentAmountProperty);
        col1.setPrefWidth(60);
        col1.setStyle("-fx-alignment: CENTER-RIGHT;");
        col1.setOnEditCommit(
                t -> {
                    PaymentDTO paymentDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    paymentDTO.setPaymentAmount(String.valueOf(new BigDecimal(t.getNewValue()).setScale(2)));
                    parent.parent.getInvoiceRepository().updatePayment(paymentDTO);
                    // This adds all the amounts together
                    BigDecimal totalPaidAmount = new BigDecimal(invoiceRepository.getTotalAmount(parent.getInvoice().getId())).setScale(2);
                    String totalAmountPaid = String.valueOf(totalPaidAmount.setScale(2));
                    parent.parent.totalPaymentText.setText(totalAmountPaid);
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
            PaymentType newPaymentType = event.getNewValue();
            PaymentDTO paymentDTO = event.getTableView().getItems().get(event.getTablePosition().getRow());
            paymentDTO.setPaymentType(newPaymentType.getCode());
            parent.parent.getInvoiceRepository().updatePayment(paymentDTO);
        });

        TableColumn<PaymentDTO, String> col3 = createColumn("Check #", PaymentDTO::checkNumberProperty);
        col3.setPrefWidth(55);
        col3.setStyle("-fx-alignment: CENTER-LEFT;");
        col3.setOnEditCommit(
                t -> {
                    PaymentDTO paymentDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    paymentDTO.setCheckNumber(t.getNewValue());
                    parent.parent.getInvoiceRepository().updatePayment(paymentDTO);
                }
        );

        TableColumn<PaymentDTO, String> col4 = createColumn("Date", PaymentDTO::paymentDateProperty);
        col4.setPrefWidth(70);
        col4.setStyle("-fx-alignment: CENTER-LEFT;");
        col4.setOnEditCommit(t -> {
            PaymentDTO paymentDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
            paymentDTO.setPaymentDate(t.getNewValue());
            parent.parent.getInvoiceRepository().updatePayment(paymentDTO);
                }
        );

        col1.setMaxWidth(1f * Integer.MAX_VALUE * 20);  // Boat Name
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 20);  // Manufacturer
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 35);   // Year
        col4.setMaxWidth(1f * Integer.MAX_VALUE * 25);  // Model

        //////////////// ATTRIBUTES ///////////////////
        HBox.setHgrow(this, Priority.ALWAYS);
        setEditable(!parent.getInvoice().isCommitted());
        setItems(parent.parent.payments);
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
