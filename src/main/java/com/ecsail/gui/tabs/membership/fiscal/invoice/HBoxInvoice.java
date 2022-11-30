package com.ecsail.gui.tabs.membership.fiscal.invoice;

import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.Note;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.*;
import com.ecsail.structures.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class HBoxInvoice extends HBox {
    private final ObservableList<PaymentDTO> payments;
    private final InvoiceDTO invoice;
    private final ArrayList<FeeDTO> fees;
    MembershipDTO membership;
    VboxFooter footer;
    boolean isCommitted;
    Button addWetSlip = new Button();
    Map<String, HboxRow> invoiceItemMap = new LinkedHashMap<>();
    Button buttonCommit = new Button("Commit");
    Note note;

    public HBoxInvoice(MembershipDTO m, InvoiceDTO invoice, Note note) {
        this.membership = m;
        this.invoice = invoice;
        ArrayList<DbInvoiceDTO> theseWidgets = SqlDbInvoice.getInvoiceWidgetsByYear(invoice.getYear());
        ObservableList<InvoiceItemDTO> items = SqlInvoiceItem.getInvoiceItemsByInvoiceId(invoice.getId());
        this.fees = SqlFee.getFeesFromYear(invoice.getYear());
        this.isCommitted = invoice.isCommitted();
        this.payments = getPayment();
        this.footer = new VboxFooter(this);
        this.note = note;
        HboxHeader header = new HboxHeader();
        ScrollPane scrollPane = new ScrollPane();
        var vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        var mainVbox = new VBox();
        var hboxButtonCommit = new HBox();
        VBox vboxMain = new VBox();
        vboxMain.setSpacing(5);
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

        System.out.println("Commit is initially set at " + invoice.isCommitted());

        //////////////// LISTENER //////////////////

        buttonCommit.setOnAction((event) -> {
			invoice.setCommitted(!invoice.isCommitted()); // change to opposite of what it currently is
            System.out.println("committed = " + invoice.isCommitted());
            header.setCommitMode(invoice.isCommitted());
            invoiceItemMap.values().forEach(e -> e.setCommitMode(invoice.isCommitted()));
            footer.setCommitMode(invoice.isCommitted());
            SqlUpdate.updateInvoice(invoice);
        });

		// take list of invoiceWidgets, insert appropriate fee into widget, insert reference to invoice items
		// the put an HBOX with all this attached into a hash map
		for (DbInvoiceDTO i : theseWidgets) {
                i.setFee(insertFeeIntoWidget(i));

                i.setItems(items); // allows calculations to be made
                invoiceItemMap.put(i.getObjectName(), new HboxRow(i, footer));
		}
        //////////////// SETTING CONTENT //////////////

        // add table head
        header.setCommitMode(invoice.isCommitted());
        invoiceItemMap.values().forEach(e -> e.setCommitMode(invoice.isCommitted()));
        vboxMain.getChildren().add(header);
        // add rows in the correct order
        for (int i = 0; i < invoiceItemMap.size() + 1; i++) {
            for (String key : invoiceItemMap.keySet()) {
                if (invoiceItemMap.get(key).getInvoiceWidget().getOrder() == i)
                    vboxMain.getChildren().add(invoiceItemMap.get(key));
            }
        }
        // add footer

        footer.setCommitMode(invoice.isCommitted());
        vboxMain.getChildren().add(footer);
        scrollPane.setContent(vboxMain);
        mainVbox.getChildren().addAll(scrollPane);  // add error HBox in first
        vboxGrey.getChildren().addAll(mainVbox);
        getChildren().addAll(vboxGrey);
    }

    private FeeDTO insertFeeIntoWidget(DbInvoiceDTO i) {
        FeeDTO selectedFee = null;
        for (FeeDTO f : fees) {
            if (i.getObjectName().equals("Dues") && f.getFieldName().equals("Dues " + membership.getMemType()))
                selectedFee = f;
            if (i.getObjectName().equals(f.getFieldName()))
                selectedFee = f;
        }
        if(selectedFee == null) System.out.println("Fee for " + i.getObjectName() + " is null" );
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
            int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment", "pay_id");
            payments.add(new PaymentDTO(pay_id, invoice.getId(), "0", "CH", HalyardPaths.date, "0", 1));
            SqlInsert.addPaymentRecord(payments.get(payments.size() - 1));
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
}
