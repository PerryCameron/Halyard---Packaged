package com.ecsail.gui.tabs.membership.fiscal.invoice;

import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.gui.common.Note;
import com.ecsail.gui.tabs.membership.fiscal.HBoxInvoiceList;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.*;
import com.ecsail.dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Invoice extends HBox {
    protected ObservableList<PaymentDTO> payments;

    protected InvoiceDTO invoice;
    protected Text totalCreditText = new Text("0.00");
    protected Text totalPaymentText = new Text("0.00");
    protected Text totalBalanceText = new Text("0.00");
    protected Text totalFeesText = new Text("0.00");

    protected ArrayList<FeeDTO> fees;
    private final MembershipDTO membership;
    private final InvoiceFooter footer;
    protected final Map<String, InvoiceItemRow> invoiceItemMap = new LinkedHashMap<>();
    private final Button buttonCommit = new Button("Commit");
    private final Note note;
    private boolean updateAllowed; // prevents any writing to database on load

    protected ObservableList<InvoiceItemDTO> items;

    public Invoice(HBoxInvoiceList parent, int index) {
        this.invoice = parent.getTabMembership().getInvoices().get(index);
        this.membership = parent.getTabMembership().getMembership();
        this.note = parent.getTabMembership().getNote();
        ArrayList<DbInvoiceDTO> dbInvoiceDTOs = SqlDbInvoice.getDbInvoiceByYear(invoice.getYear());
        this.items = SqlInvoiceItem.getInvoiceItemsByInvoiceId(invoice.getId());
        this.fees = SqlFee.getFeesFromYear(invoice.getYear());
        this.payments = getPayment();
        this.footer = new InvoiceFooter(this);
        InvoiceHeader header = new InvoiceHeader();
        ScrollPane scrollPane = new ScrollPane();
        var vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        var mainVbox = new VBox();
        var hboxButtonCommit = new HBox();
        VBox vboxMain = new VBox();
        vboxMain.setSpacing(5);
        Button addWetSlip = new Button();
        addWetSlip.setPrefWidth(25);
        addWetSlip.setPrefHeight(25);
        this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
        vboxGrey.setPadding(new Insets(8, 5, 0, 15));
        hboxButtonCommit.setPadding(new Insets(5, 0, 5, 170));

        setId("custom-tap-pane-frame");
        vboxGrey.setId("box-background-light");
        mainVbox.setId("box-background-light");
        VBox.setVgrow(mainVbox, Priority.ALWAYS);
        HBox.setHgrow(vboxGrey, Priority.ALWAYS);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        //////////////// LISTENER //////////////////

        buttonCommit.setOnAction((event) -> {
            invoice.setCommitted(!invoice.isCommitted()); // change to opposite of what it currently is
            header.setCommitMode(invoice.isCommitted());
            invoiceItemMap.values().forEach(e -> e.setCommitMode(invoice.isCommitted()));
            footer.setCommitMode(invoice.isCommitted());
            // need to set membership_id as active
            updateInvoice(invoice);
            SqlUpdate.updateMembershipId(membership.getMsId(),invoice.getYear(),footer.getRenewCheckBox().isSelected());
        });

		// take list of DBInvoiceDTOs, insert appropriate fee into widget, insert reference to invoice items
		// the put an HBOX with all this attached into a hash map
		for (DbInvoiceDTO dbInvoiceDTO : dbInvoiceDTOs) {
                dbInvoiceDTO.setFee(insertFeeIntoWidget(dbInvoiceDTO));
                new InvoiceItemRow(this, dbInvoiceDTO, footer);
		}
        //////////////// SETTING CONTENT //////////////

        // add table head
        header.setCommitMode(invoice.isCommitted());
        // add the rows
        invoiceItemMap.values().forEach(e -> e.setCommitMode(invoice.isCommitted()));
        vboxMain.getChildren().add(header);
        // add rows in the correct order
        for (int i = invoiceItemMap.size(); i > 0; i--) {  // iterate through hashmap
            for (String key : invoiceItemMap.keySet()) {
                if (invoiceItemMap.get(key).getDbInvoiceDTO().getOrder() == i) {
                    vboxMain.getChildren().add(invoiceItemMap.get(key));
//                    System.out.println("item " + i + " " + invoiceItemMap.get(key).itemName);
                }
            }
        }
        // add footer
        footer.setCommitMode(invoice.isCommitted());

        vboxMain.getChildren().add(footer);
        scrollPane.setContent(vboxMain);
        mainVbox.getChildren().addAll(scrollPane);  // add error HBox in first
        vboxGrey.getChildren().addAll(mainVbox);
        getChildren().addAll(vboxGrey);
        updateAllowed = true; // may write to database

        if (getOfficerCredit()) { // has an officer
            //if position doesn't already exist then add it
            if(!SqlExists.invoiceItemPositionCreditExistsWithValue(invoice.getYear(),invoice.getMsId())) {
                invoiceItemMap.get("Position Credit").getRowTotal().setText(invoiceItemMap.get("Dues").getRowTotal().getText());
                // TODO this needs to be tested ( added so that deposit reports witll show qty)
                invoiceItemMap.get("Position Credit").invoiceItemDTO.setQty(1);
                updateInvoiceItem(invoiceItemMap.get("Position Credit").invoiceItemDTO);
            }
        } else { // has no officer
            // has no officer but was once set as officer, will remove position credit // TODO need to test
            if(SqlExists.invoiceItemPositionCreditExistsWithValue(invoice.getYear(),invoice.getMsId())) {
                invoiceItemMap.get("Position Credit").invoiceItemDTO.setQty(0);
                invoiceItemMap.get("Position Credit").invoiceItemDTO.setValue("0.00");
                updateInvoiceItem(invoiceItemMap.get("Position Credit").invoiceItemDTO);
            }
        }
    }

    private boolean getOfficerCredit() {
        boolean hasOfficer = SqlExists.membershipHasOfficerForYear(invoice.getMsId(), invoice.getYear());
        BaseApplication.logger.info("Membership has officer: " + hasOfficer);
        return hasOfficer && !invoice.isSupplemental();
    }

    public void updateInvoiceItem(InvoiceItemDTO invoiceItemDTO) {
        if(updateAllowed)
            SqlUpdate.updateInvoiceItem(invoiceItemDTO);
    }
    public void updateInvoice(InvoiceDTO invoice) {
        if(updateAllowed)
            SqlUpdate.updateInvoice(invoice);
    }

    private FeeDTO insertFeeIntoWidget(DbInvoiceDTO i) {
        FeeDTO selectedFee = null;
        for (FeeDTO f : fees) {
            if (i.getFieldName().equals(f.getFieldName()))
                selectedFee = f;
        }
        return selectedFee;
    }

    //////////////////////  CLASS METHODS ///////////////////////////

    private ObservableList<PaymentDTO> getPayment() {
        // check to see if invoice record exists
        ObservableList<PaymentDTO> payments = FXCollections.observableArrayList();
        if (SqlExists.paymentExists(invoice.getId())) {
            return SqlPayment.getPayments(invoice.getId());
        } else {  // if not create one
            BaseApplication.logger.info("getPayment(): Creating a new payment entry");
            PaymentDTO paymentDTO = new PaymentDTO(0, invoice.getId(), "0", "CH",
                    HalyardPaths.date, "0", 1);
            // saves to database and updates object with correct pay_id
            paymentDTO.setPay_id(SqlInsert.addPaymentRecord(paymentDTO));
            payments.add(paymentDTO);
        }
        return payments;
    }

    public ObservableList<PaymentDTO> getPayments() {
        return payments;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public Button getButtonCommit() {
        return buttonCommit;
    }

    public Note getNote() {
        return note;
    }

    public boolean isUpdateAllowed() {
        return updateAllowed;
    }
}
