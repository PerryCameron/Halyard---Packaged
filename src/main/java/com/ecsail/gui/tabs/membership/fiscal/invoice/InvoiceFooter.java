package com.ecsail.gui.tabs.membership.fiscal.invoice;

import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlPayment;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.InvoiceDTO;
import com.ecsail.structures.PaymentDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.ecsail.HalyardPaths.date;

public class InvoiceFooter extends VBox {

    private final TableView<PaymentDTO> paymentTableView;
    private final InvoiceDTO invoice;
    private Text totalFeesText = new Text("0.00");
    private Text totalCreditText = new Text("0.00");
    private Text totalPaymentText = new Text("0.00");
    private Text totalBalanceText = new Text("0.00");
    private final HBox hboxTop = new HBox();
    private final HBox hboxBottom = new HBox();
    private final ObservableList<PaymentDTO> payments;
    private final VBox vboxButtons = new VBox();
    private final VBox vboxTableView = new VBox();
    private final VBox vboxCommitButton = new VBox();
    private final VBox vboxTotalLabels = new VBox();
    private final VBox vboxTotalAmounts = new VBox();
    private final CheckBox renewCheckBox = new CheckBox("Renew");
    private final Button buttonCommit;
    private final Button buttonAddNote = new Button("Add Note");
    private final Invoice hBoxInvoice;

    public InvoiceFooter(Invoice hBoxInvoice) {
        this.hBoxInvoice = hBoxInvoice;
        this.invoice = hBoxInvoice.getInvoice();
        totalFeesText.setText(invoice.getTotal());
        totalCreditText.setText(invoice.getCredit());
        totalCreditText.setId("invoice-text-credit");
        totalBalanceText.setText(invoice.getBalance());
        totalPaymentText.setText(invoice.getPaid());
        this.payments = hBoxInvoice.getPayments();
        this.paymentTableView = new PaymentTableView(this);
        this.buttonCommit = hBoxInvoice.getButtonCommit();
        Button buttonAdd = new Button("Add");
        Button buttonDelete = new Button("Delete");

        setPadding(new Insets(15, 0, 15, 0));
        setSpacing(15);

        buttonAdd.setOnAction(e -> {
            int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment", "pay_id");
            payments.add(new PaymentDTO(pay_id, invoice.getId(), null, "CH", date, "0", 1)); // let's add it to our GUI
            SqlInsert.addPaymentRecord(payments.get(payments.size() - 1));
        });

		buttonDelete.setOnAction(e -> {
			int selectedIndex = paymentTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) // is something selected?
				SqlDelete.deletePayment(payments.get(selectedIndex));
			paymentTableView.getItems().remove(selectedIndex); // remove it from our GUI
			BigDecimal totalPaidAmount = new BigDecimal(SqlPayment.getTotalAmount(invoice.getId()));
			totalPaymentText.setText(String.valueOf(totalPaidAmount.setScale(2)));
			invoice.setPaid(String.valueOf(totalPaidAmount.setScale(2)));
			updateTotals();
		});

        buttonAddNote.setOnAction(e -> hBoxInvoice.getNote().addMemoAndReturnId("Invoice Note: ",date,invoice.getId(),"I"));

        buttonAdd.setPrefWidth(60);
        buttonDelete.setPrefWidth(60);
        buttonCommit.setPrefWidth(75);
        buttonAddNote.setPrefWidth(75);

        Text totalFeesLabelText = new Text("Total Fees:");
        Text totalCreditLabelText = new Text("Total Credit:");
        Text totalPaymentLabelText = new Text("Payment:");
        Text totalBalanceLabelText = new Text("Balance:");
        totalFeesLabelText.setId("invoice-text-light");
        totalCreditLabelText.setId("invoice-text-light");
        totalPaymentLabelText.setId("invoice-text-light");
        totalBalanceLabelText.setId("invoice-text-light");

        hboxTop.setSpacing(10);
        hboxBottom.setSpacing(25);
        vboxTotalAmounts.setSpacing(5);
        vboxTotalLabels.setSpacing(5);
        vboxCommitButton.setSpacing(10);
        vboxButtons.setSpacing(5);
        renewCheckBox.setSelected(true);

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

        hboxBottom.getChildren().addAll(vboxTotalLabels,vboxTotalAmounts,vboxCommitButton);
    }

    public void setCommitMode(boolean setCommit) {
        getChildren().clear();
        if(setCommit)
            setCommit();
        else
            setEdit();
    }
    private void setEdit() {
        hboxTop.getChildren().clear();
        hboxTop.getChildren().addAll(vboxTableView,vboxButtons);
        buttonCommit.setText("Commit");
        this.requestFocus(); // removes focus from button
        vboxCommitButton.getChildren().clear();
        vboxCommitButton.getChildren().addAll(renewCheckBox, buttonCommit);
        getChildren().addAll(hboxTop,hboxBottom);
    }

    private void setCommit() {
    Separator separator = new Separator(Orientation.HORIZONTAL);
    HBox.setHgrow(separator, Priority.ALWAYS);
    VBox vboxCommitDetails = new VBox();
    buttonCommit.setText("Edit");
    this.requestFocus(); // removes focus from button
    vboxCommitDetails.getChildren().addAll(
            new Text("Payment Date: " + payments.get(0).getPaymentDate()),
            new Text("Deposit Number: " + invoice.getBatch()));
    vboxCommitButton.getChildren().clear();
    vboxCommitButton.getChildren().addAll(buttonAddNote, buttonCommit);
    hboxTop.getChildren().clear();
    hboxTop.getChildren().add(separator);
        getChildren().addAll(hboxTop,hboxBottom,vboxCommitDetails);
    }

    public void updateTotals(BigDecimal fees, BigDecimal credit) {
        BigDecimal payment = new BigDecimal(invoice.getPaid());
        updateItemsAndSave(fees, credit, payment);
    }

    public void updateTotals() {
        BigDecimal payment = new BigDecimal(invoice.getPaid());
        BigDecimal fees = new BigDecimal(invoice.getTotal());
        BigDecimal credit = new BigDecimal(invoice.getCredit());
        updateItemsAndSave(fees, credit, payment);
    }

    private void updateItemsAndSave(BigDecimal fees, BigDecimal credit, BigDecimal payment) {
        BigDecimal balance = fees.subtract(credit).subtract(payment);
        String feesString = String.valueOf(fees);
        String creditString = String.valueOf(credit);
        String paymentString = String.valueOf(payment);
        String balanceString = String.valueOf(balance);
        totalFeesText.setText(feesString);
        totalCreditText.setText(creditString);
        totalBalanceText.setText(balanceString);
        totalPaymentText.setText(paymentString);
        invoice.setTotal(feesString);
        invoice.setCredit(creditString);
        invoice.setBalance(balanceString);
        invoice.setPaid(paymentString);
        SqlUpdate.updateInvoice(invoice);
    }

    public ObservableList<PaymentDTO> getPayments() {
        return payments;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }


    public Text getTotalPaymentText() {
        return totalPaymentText;
    }

    public CheckBox getRenewCheckBox() {
        return renewCheckBox;
    }

    public Invoice getBoxInvoice() {
        return hBoxInvoice;
    }
}
