package com.ecsail.views.tabs.membership.fiscal.invoice;

import com.ecsail.dto.*;
import com.ecsail.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.repository.implementations.MembershipIdRepositoryImpl;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import com.ecsail.views.common.Note;
import com.ecsail.views.tabs.membership.fiscal.HBoxInvoiceList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Invoice extends HBox {
    
    public static Logger logger = LoggerFactory.getLogger(Invoice.class);
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
    private InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();
    private MembershipIdRepository membershipIdRepository = new MembershipIdRepositoryImpl();

    public Invoice(HBoxInvoiceList parent, int index) {
        this.invoice = parent.getTabMembership().getModel().getInvoices().get(index);
        this.membership = parent.getTabMembership().getModel().getMembership();
        this.note = parent.getTabMembership().getModel().getNote();
        ArrayList<DbInvoiceDTO> dbInvoiceDTOs =
                (ArrayList<DbInvoiceDTO>) invoiceRepository.getDbInvoiceByYear(invoice.getYear());
        this.items = FXCollections.observableArrayList(invoiceRepository.getInvoiceItemsByInvoiceId(invoice.getId()));
        this.fees = (ArrayList<FeeDTO>) invoiceRepository.getFeesFromYear(invoice.getYear());
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
            membershipIdRepository.updateMembershipId(membership.getMsId(),invoice.getYear(),footer.getRenewCheckBox().isSelected());
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
            if(!invoiceRepository.invoiceItemPositionCreditExistsWithValue(invoice.getYear(),invoice.getMsId())) {
                invoiceItemMap.get("Position Credit").getRowTotal().setText(invoiceItemMap.get("Dues").getRowTotal().getText());
                // TODO this needs to be tested ( added so that deposit reports will show qty)
                invoiceItemMap.get("Position Credit").invoiceItemDTO.setQty(1);
                updateInvoiceItem(invoiceItemMap.get("Position Credit").invoiceItemDTO);
            }
        } else { // has no officer
            // has no officer but was once set as officer, will remove position credit // TODO need to test
            if(invoiceRepository.invoiceItemPositionCreditExistsWithValue(invoice.getYear(),invoice.getMsId())) {
                invoiceItemMap.get("Position Credit").invoiceItemDTO.setQty(0);
                invoiceItemMap.get("Position Credit").invoiceItemDTO.setValue("0.00");
                updateInvoiceItem(invoiceItemMap.get("Position Credit").invoiceItemDTO);
            }
        }
    }

    private boolean getOfficerCredit() {
        boolean hasOfficer = invoiceRepository.membershipHasOfficerForYear(invoice.getMsId(), invoice.getYear());
        logger.info("Membership has officer: " + hasOfficer);
        return hasOfficer && !invoice.isSupplemental();
    }

    public void updateInvoiceItem(InvoiceItemDTO invoiceItemDTO) {
        if(updateAllowed)
            invoiceRepository.updateInvoiceItem(invoiceItemDTO);
    }
    public void updateInvoice(InvoiceDTO invoice) {
        if(updateAllowed)
            invoiceRepository.updateInvoice(invoice);
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
        if (invoiceRepository.paymentExists(invoice.getId())) {
            return FXCollections.observableArrayList(invoiceRepository.getPaymentsWithInvoiceId(invoice.getId()));
        } else {  // if not create one
            logger.info("getPayment(): Creating a new payment entry");
            PaymentDTO paymentDTO = invoiceRepository.insertPayment(new PaymentDTO(invoice.getId()));
            // saves to database and updates object with correct pay_id
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

    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }
}
