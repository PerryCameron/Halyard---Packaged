package com.ecsail.gui.boxes.invoice;

import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.Note;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.*;
import com.ecsail.structures.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class HBoxInvoice extends HBox {
	private ObservableList<PaymentDTO> payments;
	private final InvoiceDTO invoice;
	private ObservableList<InvoiceItemDTO> items;
	private ArrayList<FeeDTO> fees;
	private ArrayList<InvoiceWidgetDTO> theseWidgets;
	MembershipDTO membership;
	VBoxInvoiceFooter footer;
	boolean isCommitted;
	Button addWetSlip = new Button();
	Map<String, InvoiceRowEdit> invoiceItemMap = new LinkedHashMap<>();
	
	public HBoxInvoice(MembershipDTO m, InvoiceDTO invoice, Note note) {
		this.membership = m;
		this.invoice = invoice;
		this.theseWidgets = SqlInvoiceWidget.getInvoiceWidgets();
		this.items = SqlInvoiceItem.getInvoiceItemsByInvoiceId(invoice.getId());
		this.fees = SqlFee.getFeesFromYear(invoice.getYear());
//		this.hasOfficer = membershipHasOfficer();
		this.isCommitted = invoice.isCommitted();
		this.payments = getPayment();
		Button buttonCommit = new Button("Commit");
		this.footer = new VBoxInvoiceFooter(invoice, payments, buttonCommit);
		HBoxInvoiceTableHead header = new HBoxInvoiceTableHead();


		ScrollPane scrollPane = new ScrollPane();
		var vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		var mainVbox = new VBox();
		var mainHbox = new HBox();
		var vboxTabPanes = new VBox();
		var vboxSpinners = new VBox();
		var hboxButtonCommit = new HBox();

		addWetSlip.setPrefWidth(25);
		addWetSlip.setPrefHeight(25);

		vboxTabPanes.setAlignment(Pos.CENTER);
		vboxSpinners.setAlignment(Pos.CENTER);

		vboxSpinners.setSpacing(5);
		mainHbox.setSpacing(10);

		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		vboxGrey.setPadding(new Insets(8, 5, 0, 15));
		hboxButtonCommit.setPadding(new Insets(5, 0, 5, 170));
		
		setId("custom-tap-pane-frame");
		vboxGrey.setId("box-background-light");
		mainVbox.setId("box-background-light");
		VBox.setVgrow(mainVbox,Priority.ALWAYS);
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);

		HBox.setHgrow(scrollPane,Priority.ALWAYS);
		VBox.setVgrow(scrollPane,Priority.ALWAYS);

		// not editable if record is committed

		//////////////// LISTENER //////////////////
//		invoiceDTO.getButtonAddNote().setOnAction(e -> note.addMemoAndReturnId("Invoice Note: ",date,invoice.getMoney_id(),"I"));
//


//
		buttonCommit.setOnAction((event) -> {
			if(invoice.isCommitted()) {
				invoice.setCommitted(false);
			}
			else
				invoice.setCommitted(true);

			header.setEditableMode(invoice.isCommitted());
			invoiceItemMap.values().forEach(e -> e.setEditableMode(invoice.isCommitted()));

//			if (!invoice.isCommitted()) {
//				if (!invoiceDTO.getTotalBalanceText().getText().equals("0.00")) {
//					invoiceDTO.getTotalBalanceText().setStyle("-fx-background-color: #f23a50");
//					note.addMemoAndReturnId("Non-Zero Balance: ",date,invoice.getMoney_id(),"B");
//				}
//				SqlUpdate.commitFiscalRecord(invoice.getMoney_id(), true);// this could be placed in line above
//				SqlUpdate.updateMembershipId(invoice.getMs_id(), invoice.getFiscal_year(), invoiceDTO.getRenewCheckBox().isSelected());
//				invoice.setCommitted(true);
//
//				// if we put an amount in other we need to make a note
//				if(new BigDecimal(invoice.getOther()).compareTo(BigDecimal.ZERO) != 0) {
//					System.out.println("Found an other field");
//					// make sure the memo doesn't already exist
//					if(!SqlExists.memoExists(invoice.getMoney_id(), "O"))
//						note.addMemoAndReturnId("Other expense: ",date,invoice.getMoney_id(),"O");
//				}
//				setEditable(false);
//			} else {
//				setEditable(true);
//				invoice.setCommitted(false);
//				SqlUpdate.commitFiscalRecord(invoice.getMoney_id(), false);
//			}
		});


		//////////////// SETTING CONTENT //////////////

		VBox vboxMain = new VBox();
		vboxMain.setSpacing(5);

		// Sets up Editable Rows

			// take list of invoiceWidgets, insert appropriate fee into widget, insert reference to invoice items
			// the put an HBOX with all this attached into a hash map
			for (InvoiceWidgetDTO i : theseWidgets) {
				i.setFee(insertFeeIntoWidget(i));
				i.setItems(items); // allows calculations to be made
				invoiceItemMap.put(i.getObjectName(), new InvoiceRowEdit(i, footer));
			}
			// add table head


			header.setEditableMode(invoice.isCommitted());
			invoiceItemMap.values().forEach(e -> e.setEditableMode(invoice.isCommitted()));
			vboxMain.getChildren().add(header);
			// add rows in the correct order
			for(int i = 0; i < invoiceItemMap.size() + 1; i++) {
				for(String key: invoiceItemMap.keySet()) {
					if(invoiceItemMap.get(key).getInvoiceWidget().getOrder() == i)
						vboxMain.getChildren().add(invoiceItemMap.get(key));
				}
			}
			// add footer
			vboxMain.getChildren().add(footer);


		scrollPane.setContent(vboxMain);
		mainVbox.getChildren().addAll(scrollPane);  // add error HBox in first
		vboxGrey.getChildren().addAll(mainVbox);
		getChildren().addAll(vboxGrey);
	}

	private FeeDTO insertFeeIntoWidget(InvoiceWidgetDTO i) {
		FeeDTO selectedFee = null;
		for (FeeDTO f : fees) {
			if (i.getObjectName().equals("Dues") && f.getFieldName().equals("Dues " + membership.getMemType()))
				selectedFee = f;
			if (i.getObjectName().equals(f.getFieldName()))
				selectedFee = f;
		}
		return selectedFee;
	}

	//////////////////////  CLASS METHODS ///////////////////////////


	private ObservableList<PaymentDTO> getPayment() {
		// check to see if invoice record exists
		ObservableList<PaymentDTO> payments = FXCollections.observableArrayList();
		if(SqlExists.paymentExists(invoice.getId())) {
			return SqlPayment.getPayments(invoice.getId());
		} else {  // if not create one
			BaseApplication.logger.info("getPayment(): Creating a new payment entry");
			int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment","pay_id");
			payments.add(new PaymentDTO(pay_id,invoice.getId(),"0","CH", HalyardPaths.date, "0",1));
			SqlInsert.addPaymentRecord(payments.get(payments.size() - 1));
		}
		return payments;
	}

	public VBoxInvoiceFooter getFooter() {
		return footer;
	}
}
