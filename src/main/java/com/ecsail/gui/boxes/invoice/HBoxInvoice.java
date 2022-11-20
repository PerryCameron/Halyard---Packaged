package com.ecsail.gui.boxes.invoice;

import com.ecsail.BaseApplication;
import com.ecsail.Note;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.*;
import com.ecsail.structures.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class HBoxInvoice extends HBox {
	private ObservableList<PaymentDTO> payments;
	private final InvoiceDTO invoice;
	private ObservableList<InvoiceItemDTO> items;
	private ArrayList<FeeDTO> fees;
	private ArrayList<InvoiceWidgetDTO> theseWidgets;
	MembershipDTO membership;
	VBoxInvoiceFooter footer;
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	boolean isCommitted;
	Button addWetSlip = new Button();


	Map<String, HBoxInvoiceRow> widgetMap = new LinkedHashMap<>();
	
	public HBoxInvoice(MembershipDTO m, InvoiceDTO invoice, Note note) {
		this.membership = m;
		this.invoice = invoice;
		this.theseWidgets = SqlInvoiceWidget.getInvoiceWidgets();
		this.items = SqlInvoiceItem.getInvoiceItemsByInvoiceId(invoice.getId());
		this.fees = SqlFee.getFeesFromYear(invoice.getYear());
//		this.hasOfficer = membershipHasOfficer();
		this.isCommitted = invoice.isCommitted();
		this.payments = getPayment();
		this.footer = new VBoxInvoiceFooter(invoice, payments);
		// TODO if not committed
        for(InvoiceWidgetDTO i: theseWidgets) {
			i.setFee(insertFeeIntoWidget(i));
			i.setItems(items); // allows calculations to be made
            widgetMap.put(i.getObjectName(), new HBoxInvoiceRow(i, footer));
        }

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
//		invoiceDTO.getButtonAdd().setOnAction(e -> {
//			int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment","pay_id");
//			payments.add(new PaymentDTO(pay_id,invoice.getMoney_id(),null,"CH",date, "0",1)); // let's add it to our GUI
//			SqlInsert.addPaymentRecord(payments.get(payments.size() -1));
//		});
//
//		invoiceDTO.getButtonDelete().setOnAction(e -> {
//			int selectedIndex = paymentTableView.getSelectionModel().getSelectedIndex();
//			if (selectedIndex >= 0) // is something selected?
//				SqlDelete.deletePayment(payments.get(selectedIndex));
//			paymentTableView.getItems().remove(selectedIndex); // remove it from our GUI
//			BigDecimal totalPaidAmount = BigDecimal.valueOf(SqlMoney.getTotalAmount(invoice.getMoney_id()));
//			invoiceDTO.getTotalPaymentText().setText(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
//			invoice.setPaid(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
//			updateBalance();
//		});
//

//
//		invoiceDTO.getCommitButton().setOnAction((event) -> {
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
//		});
//
//		invoiceDTO.getWetslipTextFee().setOnMouseClicked(e -> {
//			invoiceDTO.getVboxWetSlipFee().getChildren().clear();
//			invoiceDTO.getVboxWetSlipFee().getChildren().add(invoiceDTO.getTextFieldMap().get("Wet Slip-editable"));
//		});
//
//		invoiceDTO.getWetslipTextFee().setFill(Color.BLUE);
//		invoiceDTO.getWetslipTextFee().setOnMouseEntered(en -> invoiceDTO.getWetslipTextFee().setFill(Color.RED));
//		invoiceDTO.getWetslipTextFee().setOnMouseExited(ex -> invoiceDTO.getWetslipTextFee().setFill(Color.BLUE));

//		checkIfRecordHasOfficer();
//		updateBalance(); // updates and saves
		//////////////// SETTING CONTENT //////////////

		VBox vBox = new VBox();
		vBox.setSpacing(5);

		// add table head
		vBox.getChildren().add(new HBoxInvoiceTableHead());
		// add rows in the correct order
		for(int i = 0; i < widgetMap.size() + 1; i++) {
			for(String key: widgetMap.keySet()) {
				if(widgetMap.get(key).getInvoiceWidget().getOrder() == i)
					vBox.getChildren().add(widgetMap.get(key));
			}
		}
		// add footer

		vBox.getChildren().add(footer);


		scrollPane.setContent(vBox);
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

//	private void checkIfRecordHasOfficer() {
//		if (invoice.isSupplemental()) { // have we already created a record for this year?
//			invoiceDTO.getTextFieldMap().get("Dues").setEditable(true);
//			//duesTextField.setText("0");
//		} else { // this is the first invoice record created for this year
//			if (hasOfficer) { // has officer and not
//				invoice.setOfficer_credit(String.valueOf(definedFees.getDues_regular()));
//				if(!SqlMoney.isCommitted(invoice.getMoney_id()))	{	// is not committed
//					BaseApplication.logger.info("This record has not been committed");
//				}
//			} else {
//
//				invoice.setOfficer_credit("0.00");
//			}
//			BaseApplication.logger.info("Membership officer or chairman: " + hasOfficer);
//		}
//	}

	//////////////////////  CLASS METHODS ///////////////////////////
	private int getInitialWetSlipValue(String wet_slip) {
		int startPoint = 1;
		BigDecimal wetSlip = new BigDecimal(wet_slip);
		if(wetSlip.compareTo(BigDecimal.ZERO) == 0) startPoint = 0;
		return startPoint;
	}

	private ObservableList<PaymentDTO> getPayment() {
		// check to see if invoice record exists
		ObservableList<PaymentDTO> payments = FXCollections.observableArrayList();
		if(SqlExists.paymentExists(invoice.getId())) {
			return SqlPayment.getPayments(invoice.getId());
		} else {  // if not create one
			BaseApplication.logger.info("getPayment(): Creating a new payment entry");
			int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment","pay_id");
			payments.add(new PaymentDTO(pay_id,invoice.getId(),"0","CH",date, "0",1));
			SqlInsert.addPaymentRecord(payments.get(payments.size() - 1));
		}
		return payments;
	}

//	private void updateItem(BigDecimal newTotalValue, String type) {
//		switch (type) {
//			case "Initiation" -> invoice.setInitiation(String.valueOf(newTotalValue));
//			case "Other Fee" -> invoice.setOther(String.valueOf(newTotalValue));
//			case "YSP Donation" -> invoice.setYsc_donation(String.valueOf(newTotalValue));
//			case "Dues" -> invoice.setDues(String.valueOf(newTotalValue));
//			case "Wet Slip" -> invoice.setWet_slip(String.valueOf(newTotalValue));
//			case "other_credit" -> invoice.setOther_credit(String.valueOf(newTotalValue));
//		}
//		invoice.setTotal(String.valueOf(updateTotalFeeField()));
//	}
	
//	private void setEditable(boolean isEditable) {
//		invoiceDTO.clearGridPane();
//		if(isEditable)  {
//			invoiceDTO.populateUncommitted();
//			invoiceDTO.getCommitButton().setText("Commit");
//			invoiceDTO.getVboxCommitButton().getChildren().clear();
//			invoiceDTO.getVboxCommitButton().getChildren().addAll(invoiceDTO.getRenewCheckBox(), invoiceDTO.getCommitButton());
//		} else {
//			invoiceDTO.populateCommitted();
//			invoiceDTO.getCommitButton().setText("Edit");
//			invoiceDTO.getVboxCommitButton().getChildren().clear();
//			HBox hboxButtons = new HBox();
//			hboxButtons.setSpacing(5);
//			hboxButtons.getChildren().addAll(invoiceDTO.getCommitButton());
//			invoiceDTO.getVboxCommitButton().getChildren().addAll(hboxButtons);
//		}
//	}

	private BigDecimal getBalance() {
		// calculates new balance
		BigDecimal total = new BigDecimal(invoice.getTotal());
		BigDecimal paid = new BigDecimal(invoice.getPaid());
		BigDecimal credit = new BigDecimal(invoice.getCredit());
		return total.subtract(paid).subtract(credit);
	}

	// decides if officer credit or work credit is counted
//	private BigDecimal countCredit() {
//		BigDecimal credit;
//		// if an officer we are going to use this
//		if(SqlExists.membershipHasOfficerForYear(invoice.getMs_id(), invoice.getFiscal_year()))
//			credit = new BigDecimal(invoice.getOfficer_credit());
//		// else we are going to calculate work credits
//		else credit = countWorkCredits();
//		return credit;
//	}

//	private BigDecimal countWorkCredits() {
//		// counts the dollar value of a credit by the number of credits earned
//		return definedFees.getWork_credit().multiply(BigDecimal.valueOf(invoice.getWork_credit()));
//	}

//	private BigDecimal countTotalCredit() {
//		// calculates either officer credit or work credits
//		BigDecimal normalCredit = countCredit();
//		//  additional or "other credit"
//		BigDecimal otherCredit = new BigDecimal(invoice.getOther_credit());
//		//  sum of both credits above
//		return normalCredit.add(otherCredit);
//	}

	public VBoxInvoiceFooter getFooter() {
		return footer;
	}
}
