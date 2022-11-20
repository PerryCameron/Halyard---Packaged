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
			i.setItem(insertItemIntoWidget(i));
            widgetMap.put(i.getObjectName(), new HBoxInvoiceRow(i, footer));
        }
//		fees.stream().forEach(System.out::println);
//		items.stream().forEach(System.out::println);


		///////////// ACTION ///////////////


		////////////// OBJECTS /////////////////////

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
//		// this is only called if you change membership type or open a record or manually type in
//		invoiceDTO.getTextFieldMap().get("Dues").textProperty().addListener((observable, oldValue, newValue) -> {
//			if (!SqlMoney.isCommitted(invoice.getMoney_id())) {
//				invoiceDTO.getDuesText().setText(newValue);
//				invoice.setDues(newValue);
//				updateBalance();
//			} else {
//				System.out.println("Record is committed, no changes made");
//			}
//		});
//
//		invoiceDTO.getTextFieldMap().get("Dues").focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//	            //focus out
//	            if (oldValue) {  // we have focused and unfocused
//	            	if(!FixInput.isBigDecimal(invoiceDTO.getTextFieldMap().get("Dues").getText())) {
//						invoiceDTO.getTextFieldMap().get("Dues").setText("0");
//	            	}
//	            	BigDecimal dues = new BigDecimal(invoiceDTO.getTextFieldMap().get("Dues").getText());
//	            	updateItem(dues,"Dues");
//					invoiceDTO.getTextFieldMap().get("Dues").setText(String.valueOf(dues.setScale(2, RoundingMode.HALF_UP)));
//	            	updateBalance();
//	            }
//	        });
//
//		SpinnerValueFactory<Integer> beachValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, invoice.getBeach());
//		invoiceDTO.getSpinnerMap().get("Beach Spot").setValueFactory(beachValueFactory);
//		invoiceDTO.getSpinnerMap().get("Beach Spot").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoice.setBeach(newValue);
//			invoiceDTO.getBeachText().setText(String.valueOf(definedFees.getBeach().multiply(BigDecimal.valueOf(newValue))));
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> kayakRackValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, invoice.getKayac_rack());
//		invoiceDTO.getSpinnerMap().get("Kayak Rack").setValueFactory(kayakRackValueFactory);
//		invoiceDTO.getSpinnerMap().get("Kayak Rack").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoice.setKayac_rack(newValue);
//			invoiceDTO.getKayakRackText().setText(String.valueOf(definedFees.getKayak_rack().multiply(BigDecimal.valueOf(newValue))));
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> kayakBeachRackValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, invoice.getKayak_beach_rack());
//		invoiceDTO.getSpinnerMap().get("Kayak Beach Rack").setValueFactory(kayakBeachRackValueFactory);
//		invoiceDTO.getSpinnerMap().get("Kayak Beach Rack").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoice.setKayak_beach_rack( newValue);
//			invoiceDTO.getKayakBeachRackText().setText(String.valueOf(definedFees.getKayak_beach_rack().multiply(BigDecimal.valueOf(newValue))));
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> kayakShedValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, invoice.getKayac_shed());
//		invoiceDTO.getSpinnerMap().get("Kayak Shed").setValueFactory(kayakShedValueFactory);
//		invoiceDTO.getSpinnerMap().get("Kayak Shed").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoice.setKayac_shed(newValue);
//			invoiceDTO.getKayakShedText().setText(String.valueOf(definedFees.getKayak_shed().multiply(BigDecimal.valueOf(newValue))));
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> sailLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, invoice.getSail_loft());
//		invoiceDTO.getSpinnerMap().get("Sail Loft").setValueFactory(sailLoftValueFactory);
//		invoiceDTO.getSpinnerMap().get("Sail Loft").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoice.setSail_loft(newValue);
//			invoiceDTO.getSailLoftText().setText(String.valueOf(definedFees.getSail_loft().multiply(BigDecimal.valueOf(newValue))));
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> sailSchoolLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, invoice.getSail_school_laser_loft());
//		invoiceDTO.getSpinnerMap().get("Sail School Loft").setValueFactory(sailSchoolLoftValueFactory);
//		invoiceDTO.getSpinnerMap().get("Sail School Loft").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoice.setSail_school_laser_loft(newValue);
//			invoiceDTO.getSailSchoolLoftText().setText(String.valueOf(definedFees.getSail_school_laser_loft().multiply(BigDecimal.valueOf(newValue))));
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, getInitialWetSlipValue(invoice.getWet_slip()));
//		invoiceDTO.getSpinnerMap().get("Wet Slip").setValueFactory(wetSlipValueFactory);
//		invoiceDTO.getSpinnerMap().get("Wet Slip").valueProperty().addListener((observable, oldValue, newValue) -> {
//			String wetSlip = String.valueOf(new BigDecimal(invoiceDTO.getWetslipTextFee().getText()).multiply(BigDecimal.valueOf(newValue)));
//			invoice.setWet_slip(wetSlip);
//			invoiceDTO.getWetSlipText().setText(wetSlip);
//			updateBalance();
//		});
//
//		// this is for changing the value from default
//		invoiceDTO.getTextFieldMap().get("Wet Slip-editable").focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//			//focus out
//			if (oldValue) {  // we have focused and unfocused
//				if(!FixInput.isBigDecimal(invoiceDTO.getTextFieldMap().get("Wet Slip-editable").getText())) {
//					invoiceDTO.getTextFieldMap().get("Wet Slip-editable").setText("0.00");
//				}
//				BigDecimal slip = new BigDecimal(invoiceDTO.getTextFieldMap().get("Wet Slip-editable").getText());
//				invoiceDTO.getWetSlipText().setText(String.valueOf(slip.multiply(BigDecimal.valueOf(invoiceDTO.getSpinnerMap().get("Wet Slip").getValue()))));
//				invoiceDTO.getTextFieldMap().get("Wet Slip-editable").setText(String.valueOf(slip.setScale(2, RoundingMode.HALF_UP)));
//				invoiceDTO.getWetslipTextFee().setText(String.valueOf(slip.setScale(2, RoundingMode.HALF_UP)));
//				String wetSlip = String.valueOf(new BigDecimal(invoiceDTO.getWetslipTextFee().getText()).multiply(BigDecimal.valueOf(invoiceDTO.getSpinnerMap().get("Wet Slip").getValue())));
//				invoice.setWet_slip(wetSlip);
//				updateBalance();
//				invoiceDTO.getVboxWetSlipFee().getChildren().clear();
//				invoiceDTO.getVboxWetSlipFee().getChildren().add(invoiceDTO.getWetslipTextFee());
//			}
//		});
//
//		SpinnerValueFactory<Integer> winterStorageValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, invoice.getWinter_storage());
//		invoiceDTO.getSpinnerMap().get("Winter Storage").setValueFactory(winterStorageValueFactory);
//		invoiceDTO.getSpinnerMap().get("Winter Storage").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoice.setWinter_storage(newValue);
//			invoiceDTO.getWinterStorageText().setText(String.valueOf(definedFees.getWinter_storage().multiply(BigDecimal.valueOf(newValue))));
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> gateKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, invoice.getExtra_key());
//		invoiceDTO.getSpinnerMap().get("Gate Key").setValueFactory(gateKeyValueFactory);
//		invoiceDTO.getSpinnerMap().get("Gate Key").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoiceDTO.getGateKeyText().setText(String.valueOf(definedFees.getMain_gate_key().multiply(BigDecimal.valueOf(newValue))));
//			invoice.setExtra_key(newValue);
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> sailLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, invoice.getSail_loft_key());
//		invoiceDTO.getSpinnerMap().get("Sail Loft Key").setValueFactory(sailLKeyValueFactory);
//		invoiceDTO.getSpinnerMap().get("Sail Loft Key").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoiceDTO.getSailLKeyText().setText(String.valueOf(definedFees.getSail_loft_key().multiply(BigDecimal.valueOf(newValue))));
//			invoice.setSail_loft_key(newValue);
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> kayakSKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, invoice.getKayac_shed_key());
//		invoiceDTO.getSpinnerMap().get("Kayak Shed Key").setValueFactory(kayakSKeyValueFactory);
//		invoiceDTO.getSpinnerMap().get("Kayak Shed Key").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoiceDTO.getKayakSKeyText().setText(String.valueOf(definedFees.getKayak_shed_key().multiply(BigDecimal.valueOf(newValue))));
//			invoice.setKayac_shed_key(newValue);
//			updateBalance();
//		});
//
//		SpinnerValueFactory<Integer> sailSSLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, invoice.getSail_school_loft_key());
//		invoiceDTO.getSpinnerMap().get("Sail School Key").setValueFactory(sailSSLKeyValueFactory);
//		invoiceDTO.getSpinnerMap().get("Sail School Key").valueProperty().addListener((observable, oldValue, newValue) -> {
//			invoiceDTO.getSailSSLKeyText().setText(String.valueOf(definedFees.getSail_school_loft_key().multiply(BigDecimal.valueOf(newValue))));
//			invoice.setSail_school_loft_key(newValue);
//			updateBalance();
//		});
//
//		invoiceDTO.getTextFieldMap().get("YSP Donation").focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//			//focus out
//			if (oldValue) {  // we have focused and unfocused
//				if(!FixInput.isBigDecimal(invoiceDTO.getTextFieldMap().get("YSP Donation").getText())) {
//					invoiceDTO.getTextFieldMap().get("YSP Donation").setText("0.00");
//				}
//				BigDecimal ysc = new BigDecimal(invoiceDTO.getTextFieldMap().get("YSP Donation").getText());
//				invoiceDTO.getYspText().setText(String.valueOf(ysc.setScale(2, RoundingMode.HALF_UP)));
//				updateItem(ysc.setScale(2, RoundingMode.HALF_UP), "YSP Donation");
//				invoiceDTO.getTextFieldMap().get("YSP Donation").setText(String.valueOf(ysc.setScale(2, RoundingMode.HALF_UP)));
//				updateBalance();
//			}
//		});
//
//		invoiceDTO.getTextFieldMap().get("Initiation").focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//			//focus out
//			if (oldValue) {  // we have focused and unfocused
//				if(!FixInput.isBigDecimal(invoiceDTO.getTextFieldMap().get("Initiation").getText())) {
//					invoiceDTO.getTextFieldMap().get("Initiation").setText("0.00");
//				}
//				BigDecimal initiation = new BigDecimal(invoiceDTO.getTextFieldMap().get("Initiation").getText());
//				updateItem(initiation.setScale(2, RoundingMode.HALF_UP), "Initiation");
//				invoiceDTO.getTextFieldMap().get("Initiation").setText(String.valueOf(initiation.setScale(2, RoundingMode.HALF_UP)));
//				invoiceDTO.getInitiationText().setText(String.valueOf(initiation.setScale(2, RoundingMode.HALF_UP)));
//				updateBalance();
//			}
//		});
//
//		invoiceDTO.getTextFieldMap().get("Other Fee").focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//			//focus out
//			if (oldValue) {  // we have focused and unfocused
//				if(!FixInput.isBigDecimal(invoiceDTO.getTextFieldMap().get("Other Fee").getText())) {
//					invoiceDTO.getTextFieldMap().get("Other Fee").setText("0.00");
//				}
//				BigDecimal other = new BigDecimal(invoiceDTO.getTextFieldMap().get("Other Fee").getText());
//				invoiceDTO.getTextFieldMap().get("Other Fee").setText(String.valueOf(other.setScale(2, RoundingMode.HALF_UP)));
//				updateItem(other.setScale(2, RoundingMode.HALF_UP),"Other Fee");
//				updateBalance();
//			}
//		});
//
//		widgetMap.get("combo-box").getComboBox().getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
//			invoice.setWork_credit(newValue);
//			String workCredits = String.valueOf(definedFees.getWork_credit().multiply(BigDecimal.valueOf(newValue)));
//			widgetMap.get("combo-box").getTotal().setText(workCredits);
//			updateBalance();
//		});
//
//		invoiceDTO.getTextFieldMap().get("Other Credit").focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//			//focus out
//			if (oldValue) {  // we have focused and unfocused
//				if(!FixInput.isBigDecimal(invoiceDTO.getTextFieldMap().get("Other Credit").getText())) {
//					invoiceDTO.getTextFieldMap().get("Other Credit").setText("0.00");
//				}
//				BigDecimal otherCredit = new BigDecimal(invoiceDTO.getTextFieldMap().get("Other Credit").getText());
//				invoiceDTO.getTextFieldMap().get("Other Credit").setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
//				invoice.setOther_credit(String.valueOf(otherCredit));
//				invoiceDTO.getTextFieldMap().get("Other Credit").setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
//				updateBalance();
//			}
//		});
//
//		invoiceDTO.getTextFieldMap().get("Other Credit").focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//			//focus out
//			if (oldValue) {  // we have focused and unfocused
//				if(!FixInput.isBigDecimal(invoiceDTO.getTextFieldMap().get("Other Credit").getText())) {
//					invoiceDTO.getTextFieldMap().get("Other Credit").setText("0.00");
//				}
//				BigDecimal otherCredit = new BigDecimal(invoiceDTO.getTextFieldMap().get("Other Credit").getText());
//				invoiceDTO.getTextFieldMap().get("Other Credit").setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
//				invoiceDTO.getOtherCreditText().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
//				updateBalance();
//			}
//		});
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
//		widgetMap.get("Dues").getTextField().setText(String.valueOf(invoice.getDues()));
//		widgetMap.get("YSP Donation").getTextField().setText(String.valueOf(invoice.getYsc_donation()));
//		widgetMap.get("Other Fee").getTextField().setText(String.valueOf(invoice.getOther()));
//		widgetMap.get("Initiation").getTextField().setText(String.valueOf(invoice.getInitiation()));
////		widgetMap.get("Wet Slip-editable").getTextField().setText(String.valueOf(definedFees.getWet_slip()));
//		widgetMap.get("Other Credit").getTextField().setText(String.valueOf(invoice.getOther_credit()));

//		invoiceDTO.getInitiationText().setText(invoice.getInitiation());
//		invoiceDTO.getOtherFeeText().setText(invoice.getOther());
//		invoiceDTO.getWorkCreditsText().setText(String.valueOf(countWorkCredits()));
//		invoiceDTO.getOtherCreditText().setText(invoice.getOther_credit());
//		invoiceDTO.getTotalBalanceText().setText(invoice.getCredit());
//		invoiceDTO.getTotalCreditText().setText(invoice.getCredit());
//		invoiceDTO.getTotalPaymentText().setText(invoice.getPaid());
//		invoiceDTO.getWetslipTextFee().setText(String.valueOf(definedFees.getWet_slip()));
//		invoiceDTO.getBeachText().setText(String.valueOf(BigDecimal.valueOf(invoice.getBeach()).multiply(definedFees.getBeach())));
//		invoiceDTO.getKayakRackText().setText(String.valueOf(BigDecimal.valueOf(invoice.getKayac_rack()).multiply(definedFees.getKayak_rack())));
//		invoiceDTO.getKayakBeachRackText().setText(String.valueOf(BigDecimal.valueOf(invoice.getKayak_beach_rack()).multiply(definedFees.getKayak_beach_rack())));
//		invoiceDTO.getKayakShedText().setText(String.valueOf(BigDecimal.valueOf(invoice.getKayac_shed()).multiply(definedFees.getKayak_shed())));
//		invoiceDTO.getSailLoftText().setText(String.valueOf(BigDecimal.valueOf(invoice.getSail_loft()).multiply(definedFees.getSail_loft())));
//		invoiceDTO.getSailSchoolLoftText().setText(String.valueOf(BigDecimal.valueOf(invoice.getSail_school_laser_loft()).multiply(definedFees.getSail_school_laser_loft())));
//		invoiceDTO.getWetSlipText().setText(invoice.getWet_slip());
//		invoiceDTO.getWinterStorageText().setText(String.valueOf(BigDecimal.valueOf(invoice.getWinter_storage()).multiply(definedFees.getWinter_storage())));
//		invoiceDTO.getGateKeyText().setText(String.valueOf(BigDecimal.valueOf(invoice.getExtra_key()).multiply(definedFees.getMain_gate_key())));
//		invoiceDTO.getSailLKeyText().setText(String.valueOf(BigDecimal.valueOf(invoice.getSail_loft_key()).multiply(definedFees.getSail_loft_key())));
//		invoiceDTO.getKayakSKeyText().setText(String.valueOf(BigDecimal.valueOf(invoice.getKayac_shed_key()).multiply(definedFees.getKayak_shed_key())));
//		invoiceDTO.getSailSSLKeyText().setText(String.valueOf(BigDecimal.valueOf(invoice.getSail_school_loft_key()).multiply(definedFees.getSail_school_loft_key())));
//		invoiceDTO.getPositionCreditText().setText(invoice.getOfficer_credit());
//		setEditable(!invoice.isCommitted());
//		updateBalance();

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

	private InvoiceItemDTO insertItemIntoWidget(InvoiceWidgetDTO i) {
		InvoiceItemDTO item = null;
		for(InvoiceItemDTO it: items) {
			if(i.getObjectName().equals(it.getItemType())) {
				System.out.println("Selected " + i.getObjectName() + " -> " + it.getItemType());
				return it;
			}
		}
		return null;
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
	

	
//	private BigDecimal updateTotalFeeField() {
//		// adds all values together to get total
//		BigDecimal dues = new BigDecimal(invoice.getDues());
//		BigDecimal beachSpot = new BigDecimal(invoice.getBeach()).multiply(definedFees.getBeach());
//		BigDecimal kayakRack = new BigDecimal(invoice.getKayac_rack()).multiply(definedFees.getKayak_rack());
//		BigDecimal kayakBeachRack = new BigDecimal(invoice.getKayak_beach_rack()).multiply(definedFees.getKayak_beach_rack());
//		BigDecimal kayakShed = new BigDecimal(invoice.getKayac_shed()).multiply(definedFees.getKayak_shed());
//		BigDecimal sailLoft = new BigDecimal(invoice.getSail_loft()).multiply(definedFees.getSail_loft());
//		BigDecimal sailSchoolLoft = new BigDecimal(invoice.getSail_school_laser_loft()).multiply(definedFees.getSail_school_laser_loft());
//		BigDecimal wetSlip = new BigDecimal(invoice.getWet_slip());
//		BigDecimal winterStorage = new BigDecimal(invoice.getWinter_storage()).multiply(definedFees.getWinter_storage());
//		BigDecimal extraKey = new BigDecimal(invoice.getExtra_key()).multiply(definedFees.getMain_gate_key());
//		BigDecimal sailLoftKey = new BigDecimal(invoice.getSail_loft_key()).multiply(definedFees.getSail_loft_key());
//		BigDecimal kayakShedKey = new BigDecimal(invoice.getKayac_shed_key()).multiply(definedFees.getKayak_shed_key());
//		BigDecimal sailSchoolLoftKey = new BigDecimal(invoice.getSail_school_loft_key()).multiply(definedFees.getSail_school_loft_key());
//		BigDecimal yscDonation = new BigDecimal(invoice.getYsc_donation());
//		BigDecimal other = new BigDecimal(invoice.getOther());
//		BigDecimal initiation = new BigDecimal(invoice.getInitiation());
//		return extraKey.add(sailLoftKey).add(kayakShedKey).add(sailSchoolLoftKey).add(beachSpot).add(kayakRack).add(kayakBeachRack).add(kayakShed)
//				.add(sailLoft).add(sailSchoolLoft).add(wetSlip).add(winterStorage).add(yscDonation).add(dues).add(other).add(initiation);
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
