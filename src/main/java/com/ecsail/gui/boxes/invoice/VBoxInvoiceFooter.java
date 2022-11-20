package com.ecsail.gui.boxes.invoice;

import com.ecsail.EditCell;
import com.ecsail.enums.PaymentType;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlMoney;
import com.ecsail.structures.InvoiceDTO;
import com.ecsail.structures.MoneyDTO;
import com.ecsail.structures.PaymentDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.function.Function;

public class VBoxInvoiceFooter extends VBox {

    private final TableView<PaymentDTO> paymentTableView = new TableView<>();
    private final InvoiceDTO invoice;
    private Text totalFeesText = new Text("0.00");
    private Text totalCreditText = new Text("0.00");
    private Text totalPaymentText = new Text("0.00");
    private Text totalBalanceText = new Text("0.00");

    public VBoxInvoiceFooter(InvoiceDTO invoice, ObservableList<PaymentDTO> payments) {
        this.invoice = invoice;


        TableColumn<PaymentDTO, String> col1 = createColumn("Amount", PaymentDTO::PaymentAmountProperty);
        col1.setPrefWidth(60);
        col1.setStyle("-fx-alignment: CENTER-RIGHT;");
        col1.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setPaymentAmount(String.valueOf(new BigDecimal(t.getNewValue()).setScale(2, RoundingMode.CEILING)));
                    var pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
                    BigDecimal amount = new BigDecimal(t.getNewValue());
                    SqlUpdate.updatePayment(pay_id, "amount", String.valueOf(amount.setScale(2, RoundingMode.HALF_UP)));
                    BigDecimal totalPaidAmount = BigDecimal.valueOf(SqlMoney.getTotalAmount(invoice.getId()));
//					invoiceDTO.getTotalPaymentText().setText(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
                    invoice.setPaid(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
//					updateBalance();
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
                    SqlUpdate.updatePayment(pay_id, "CHECKNUMBER", t.getNewValue());
                    //	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
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
        HBox.setHgrow(paymentTableView, Priority.ALWAYS);
        paymentTableView.setEditable(!invoice.isCommitted());

        paymentTableView.setItems(payments);
        paymentTableView.setPrefHeight(115);
        paymentTableView.setFixedCellSize(30);
        paymentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        paymentTableView.getColumns().addAll(Arrays.asList(col1, col2, col3, col4));

        setPadding(new Insets(15, 0, 15, 0));
        setSpacing(15);

        Button buttonAdd = new Button("Add");
        Button buttonDelete = new Button("Delete");
        Button buttonCommit = new Button("Commit");
        Button buttonAddNote = new Button("Add Note");
        CheckBox renewCheckBox = new CheckBox("Renew");

        buttonAdd.setPrefWidth(60);
        buttonDelete.setPrefWidth(60);
        buttonCommit.setPrefWidth(70);

        HBox hboxTop = new HBox();
        HBox hboxBottom = new HBox();

        VBox vboxTableView = new VBox();
        VBox vboxCommitButton = new VBox();
        VBox vboxTotalLabels = new VBox();
        VBox vboxTotalAmounts = new VBox();

        VBox vboxButtons = new VBox();

        Text totalFeesLabelText = new Text("Total Fees:");
        Text totalCreditLabelText = new Text("Total Credit:");
        Text totalPaymentLabelText = new Text("Payment:");
        Text totalBalanceLabelText = new Text("Balance:");
        totalFeesLabelText.setId("invoice-text-light");
        totalCreditLabelText.setId("invoice-text-light");
        totalPaymentLabelText.setId("invoice-text-light");
        totalBalanceLabelText.setId("invoice-text-light");

        vboxTableView.setStyle("-fx-background-color: #e83115;");  // red
        hboxTop.setSpacing(10);
        hboxBottom.setSpacing(25);
        vboxTotalAmounts.setSpacing(5);
        vboxTotalLabels.setSpacing(5);
        vboxCommitButton.setSpacing(10);
        vboxButtons.setSpacing(5);

        vboxTableView.setPrefWidth(360);
        vboxTotalLabels.setPrefWidth(260);
        vboxTotalAmounts.setPrefWidth(50);

        vboxTotalLabels.setAlignment(Pos.CENTER_LEFT);
        vboxTotalAmounts.setAlignment(Pos.CENTER_RIGHT);
        vboxButtons.setAlignment(Pos.TOP_LEFT);
        vboxCommitButton.setAlignment(Pos.BASELINE_LEFT);

        vboxTotalLabels.getChildren().addAll(totalFeesLabelText,totalCreditLabelText,totalPaymentLabelText,totalBalanceLabelText);
        vboxTotalAmounts.getChildren().addAll(totalFeesText,totalCreditText,totalPaymentText,totalBalanceText);

        vboxCommitButton.getChildren().addAll(renewCheckBox, buttonCommit);
        vboxTableView.getChildren().add(paymentTableView);
        vboxButtons.getChildren().addAll(buttonAdd, buttonDelete);

        hboxTop.getChildren().addAll(vboxTableView,vboxButtons);
        hboxBottom.getChildren().addAll(vboxTotalLabels,vboxTotalAmounts,vboxCommitButton);
        getChildren().addAll(hboxTop,hboxBottom);
    }

    public void updateBalance() {
        // updates total to the selected money object
//        invoice.setTotal(String.valueOf(updateTotalFeeField()));
        // updates gui with the newly calculated total
		totalFeesText.setText(String.valueOf(invoice.getTotal()));
        // updates credit to the selected money object
//        invoice.setCredit(String.valueOf(countTotalCredit()));
        // updates gui with the newly calculated credit
        totalCreditText.setText(invoice.getCredit());
        // updates balance to the selected money object
//        invoice.setBalance(String.valueOf(getBalance()));
        // updates gui with the newly calculated balance
        totalBalanceText.setText(invoice.getBalance());
        // updates sql using selected money object
        // TODO fix below
//        SqlUpdate.updateMoney(invoice);  // saves to database
    }

    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public Text getTotalFeesText() {
        return totalFeesText;
    }

    public void setTotalFeesText(Text totalFeesText) {
        this.totalFeesText = totalFeesText;
    }

    public Text getTotalCreditText() {
        return totalCreditText;
    }

    public void setTotalCreditText(Text totalCreditText) {
        this.totalCreditText = totalCreditText;
    }

    public Text getTotalPaymentText() {
        return totalPaymentText;
    }

    public void setTotalPaymentText(Text totalPaymentText) {
        this.totalPaymentText = totalPaymentText;
    }

    public Text getTotalBalanceText() {
        return totalBalanceText;
    }

    public void setTotalBalanceText(Text totalBalanceText) {
        this.totalBalanceText = totalBalanceText;
    }
}
