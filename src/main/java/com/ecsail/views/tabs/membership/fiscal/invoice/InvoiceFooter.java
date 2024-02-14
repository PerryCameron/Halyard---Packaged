package com.ecsail.views.tabs.membership.fiscal.invoice;

import com.ecsail.dto.InvoiceDTO;
import com.ecsail.dto.MemoDTO;
import com.ecsail.dto.PaymentDTO;
import com.ecsail.repository.interfaces.InvoiceRepository;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class InvoiceFooter extends VBox {

    private final TableView<PaymentDTO> paymentTableView;
    private final HBox hboxTop = new HBox();
    private final HBox hboxBottom = new HBox();
    private final VBox vboxButtons = new VBox();
    private final VBox vboxTableView = new VBox();
    private final VBox vboxCommitButton = new VBox();
    private final CheckBox renewCheckBox = new CheckBox("Renew");
    private final Button buttonCommit;
    private final Button buttonAddNote = new Button("Add Note");
    protected final Invoice parent;
    private final InvoiceRepository invoiceRepository;
    public static Logger logger = LoggerFactory.getLogger(InvoiceFooter.class);

    public InvoiceFooter(Invoice hboxInvoice) {
        this.parent = hboxInvoice;
        this.invoiceRepository = parent.getInvoiceRepository();
        parent.totalFeesText.setText(parent.invoice.getTotal());
        parent.totalCreditText.setText(parent.invoice.getCredit());
        parent.totalCreditText.setId("invoice-text-credit");
        parent.totalBalanceText.setText(parent.invoice.getBalance());
        parent.totalPaymentText.setText(parent.invoice.getPaid());
        this.paymentTableView = new PaymentTableView(this);
        this.buttonCommit = parent.getButtonCommit();
        Button buttonAdd = new Button("Add");
        Button buttonDelete = new Button("Delete");
        setPadding(new Insets(15, 0, 15, 0));
        setSpacing(15);

        buttonAdd.setOnAction(e -> {
            PaymentDTO paymentDTO = invoiceRepository.insertPayment(new PaymentDTO(parent.invoice.getId()));
            parent.payments.add(paymentDTO); // let's add it to our GUI
        });

		buttonDelete.setOnAction(e -> {
			int selectedIndex = paymentTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) // is something selected?
				invoiceRepository.deletePayment(parent.payments.get(selectedIndex));
			paymentTableView.getItems().remove(selectedIndex); // remove it from our GUI
			BigDecimal totalPaidAmount = new BigDecimal(invoiceRepository.getTotalAmount(parent.invoice.getId()));
            parent.totalPaymentText.setText(String.valueOf(totalPaidAmount.setScale(2)));
            parent.invoice.setPaid(String.valueOf(totalPaidAmount.setScale(2)));
			updateTotals();
		});

        buttonAddNote.setOnAction(e -> {
            parent.getNote().addMemoAndReturnId(new MemoDTO("", parent.invoice.getMsId(), parent.invoice.getId(), "I"));
            parent.getTabMembership().selectTab("Notes");
            parent.getTabMembership().editRow();
        });

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
        VBox vboxTotalAmounts = new VBox();
        vboxTotalAmounts.setSpacing(5);
        VBox vboxTotalLabels = new VBox();
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
        vboxTotalAmounts.getChildren().addAll(
                parent.totalFeesText,parent.totalCreditText,parent.totalPaymentText,parent.totalBalanceText);
        vboxCommitButton.getChildren().addAll(renewCheckBox, buttonCommit);
        vboxTableView.getChildren().add(paymentTableView);
        vboxButtons.getChildren().addAll(buttonAdd, buttonDelete);
        hboxBottom.getChildren().addAll(vboxTotalLabels, vboxTotalAmounts,vboxCommitButton);
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
        paymentTableView.setEditable(true);
        buttonCommit.setText("Commit");
        this.requestFocus(); // removes focus from button
        vboxCommitButton.getChildren().clear();
        vboxCommitButton.getChildren().addAll(renewCheckBox, buttonCommit,buttonAddNote);
        getChildren().addAll(hboxTop,hboxBottom);
    }

    private void setCommit() {
    Separator separator = new Separator(Orientation.HORIZONTAL);
    HBox.setHgrow(separator, Priority.ALWAYS);
    VBox vboxCommitDetails = new VBox();
    buttonCommit.setText("Edit");
    this.requestFocus(); // removes focus from button
    vboxCommitDetails.getChildren().addAll(
            new Text("Payment Date: " + parent.payments.get(0).getPaymentDate()),
            new Text("Deposit Number: " + parent.invoice.getBatch()));
    vboxCommitButton.getChildren().clear();
    vboxCommitButton.getChildren().addAll(buttonAddNote, buttonCommit);
    hboxTop.getChildren().clear();
    hboxTop.getChildren().add(separator);
        getChildren().addAll(hboxTop,hboxBottom,vboxCommitDetails);
    }

    public void updateTotals(BigDecimal fees, BigDecimal credit) {
        BigDecimal payment = new BigDecimal(parent.invoice.getPaid());
        updateItemsAndSave(fees, credit, payment);
    }

    public void updateTotals() {
        logger.info("Updating totals for MSID=" + parent.invoice.getMsId());
        BigDecimal payment = new BigDecimal(parent.invoice.getPaid());
        BigDecimal fees = new BigDecimal(parent.invoice.getTotal());
        BigDecimal credit = new BigDecimal(parent.invoice.getCredit());
        updateItemsAndSave(fees, credit, payment);
    }

    private void updateItemsAndSave(BigDecimal fees, BigDecimal credit, BigDecimal payment) {
        logger.info("Updating items and Saving");
        BigDecimal balance = fees.subtract(credit).subtract(payment);
        String feesString = String.valueOf(fees);
        String creditString = String.valueOf(credit);
        String paymentString = String.valueOf(payment);
        String balanceString = String.valueOf(balance);
        parent.totalFeesText.setText(feesString);
        parent.totalCreditText.setText(creditString);
        parent.totalBalanceText.setText(balanceString);
        parent.totalPaymentText.setText(paymentString);
        parent.invoice.setTotal(feesString);
        parent.invoice.setCredit(creditString);
        parent.invoice.setBalance(balanceString);
        parent.invoice.setPaid(paymentString);
    }

    public ObservableList<PaymentDTO> getPayments() {
        return parent.payments;
    }
    public InvoiceDTO getInvoice() {
        return parent.invoice;
    }
    public CheckBox getRenewCheckBox() {
        return renewCheckBox;
    }
    public Invoice getBoxInvoice() {
        return parent;
    }
}
