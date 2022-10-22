package com.ecsail.gui.boxes;

import com.ecsail.BaseApplication;
import com.ecsail.EditCell;
import com.ecsail.FixInput;
import com.ecsail.Note;
import com.ecsail.enums.PaymentType;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlDefinedFee;
import com.ecsail.sql.select.SqlMoney;
import com.ecsail.sql.select.SqlPayment;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

public class HBoxInvoice extends HBox {
	private final ObservableList<MoneyDTO> fiscals;
	private ObservableList<PaymentDTO> payments;
	private final MoneyDTO invoice;
	private final InvoiceDTO invoiceDTO;
	MembershipDTO membership;
	DefinedFeeDTO definedFees;
	WorkCreditDTO selectedWorkCreditYear;
	final private ObservableList<PersonDTO> people;
	boolean hasOfficer;
	final private int rowIndex;
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	private final TableView<PaymentDTO> paymentTableView = new TableView<>();
	boolean isCommitted;
	Button addWetSlip = new Button();
	
	public HBoxInvoice(MembershipDTO m, ObservableList<PersonDTO> p, ObservableList<MoneyDTO> o, int r, Note note) {
		this.membership = m;
		this.people = p;
		this.rowIndex = r;
		this.fiscals = o;
		this.definedFees = SqlDefinedFee.getDefinedFeeByYear(String.valueOf(fiscals.get(rowIndex).getFiscal_year()));
		this.invoice = fiscals.get(rowIndex);
		this.invoiceDTO = new InvoiceDTO(invoice, definedFees, paymentTableView);
		this.selectedWorkCreditYear = SqlMoney.getWorkCredit(fiscals.get(rowIndex).getMoney_id());
		this.hasOfficer = membershipHasOfficer();
		this.isCommitted = fiscals.get(rowIndex).isCommitted();

		///////////// ACTION ///////////////
		getPayment();

		////////////// OBJECTS /////////////////////

		ScrollPane scrollPane = new ScrollPane();

		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox mainVbox = new VBox();
		HBox mainHbox = new HBox();
		VBox vboxTabPanes = new VBox();
		VBox vboxSpinners = new VBox();
		HBox hboxButtonCommit = new HBox();

		/////////////// TABLE ///////////////////
		TableColumn<PaymentDTO, String> col1 = createColumn("Amount", PaymentDTO::PaymentAmountProperty);
		col1.setPrefWidth(60);
		col1.setStyle( "-fx-alignment: CENTER-RIGHT;");
		col1.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setPaymentAmount(String.valueOf(new BigDecimal(t.getNewValue()).setScale(2, RoundingMode.CEILING)));
					int pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
					BigDecimal amount = new BigDecimal(t.getNewValue());
					SqlUpdate.updatePayment(pay_id, "amount", String.valueOf(amount.setScale(2, RoundingMode.HALF_UP)));
					BigDecimal totalPaidAmount = BigDecimal.valueOf(SqlMoney.getTotalAmount(fiscals.get(rowIndex).getMoney_id()));
					invoiceDTO.getTotalPaymentText().setText(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
					fiscals.get(rowIndex).setPaid(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
					updateBalance();
				}
		);

		// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
		ObservableList<PaymentType> paymentTypeList = FXCollections.observableArrayList(PaymentType.values());
		TableColumn<PaymentDTO, PaymentType> col2 = new TableColumn<>("Type");

		col2.setPrefWidth(55);
		col2.setStyle( "-fx-alignment: CENTER;");
		col2.setCellValueFactory(param -> {
			PaymentDTO thisPayment = param.getValue();
			String paymentCode = thisPayment.getPaymentType();
			PaymentType paymentType = PaymentType.getByCode(paymentCode);
			return new SimpleObjectProperty<>(paymentType);
		});

		col2.setCellFactory(ComboBoxTableCell.forTableColumn(paymentTypeList));

		col2.setOnEditCommit((TableColumn.CellEditEvent<PaymentDTO, PaymentType> event) -> {
			TablePosition<PaymentDTO, PaymentType> pos = event.getTablePosition();
			PaymentType newPaymentType = event.getNewValue();
			int row = pos.getRow();
			PaymentDTO thisPayment = event.getTableView().getItems().get(row);
			SqlUpdate.updatePayment(thisPayment.getPay_id(), "payment_type", newPaymentType.getCode());
			// need to update paid from here
			thisPayment.setPaymentType(newPaymentType.getCode());
		});

		TableColumn<PaymentDTO, String> col3 = createColumn("Check #", PaymentDTO::checkNumberProperty);
		col3.setPrefWidth(55);
		col3.setStyle( "-fx-alignment: CENTER-LEFT;");
		col3.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setCheckNumber(t.getNewValue());
					int pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
					SqlUpdate.updatePayment(pay_id, "CHECKNUMBER", t.getNewValue());
					//	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
				}
		);

		TableColumn<PaymentDTO, String> col4 = createColumn("Date", PaymentDTO::paymentDateProperty);
		col4.setPrefWidth(70);
		col4.setStyle( "-fx-alignment: CENTER-LEFT;");
		col4.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setPaymentDate(t.getNewValue());
					int pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
					SqlUpdate.updatePayment(pay_id, "payment_date", t.getNewValue());
					//	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
				}
		);

		col1.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Boat Name
		col2.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Manufacturer
		col3.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );   // Year
		col4.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Model

		//////////////// ATTRIBUTES ///////////////////

		paymentTableView.setItems(payments);
		paymentTableView.setPrefHeight(115);
		paymentTableView.setFixedCellSize(30);
		paymentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
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
		HBox.setHgrow(paymentTableView,Priority.ALWAYS);
		HBox.setHgrow(scrollPane,Priority.ALWAYS);
		VBox.setVgrow(scrollPane,Priority.ALWAYS);

		// not editable if record is committed
		paymentTableView.setEditable(!fiscals.get(rowIndex).isCommitted());

		//////////////// LISTENER //////////////////
		invoiceDTO.getButtonAddNote().setOnAction(e -> note.addMemoAndReturnId("Invoice Note: ",date,fiscals.get(rowIndex).getMoney_id(),"I"));

		invoiceDTO.getButtonAdd().setOnAction(e -> {
			int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment","pay_id");
			payments.add(new PaymentDTO(pay_id,fiscals.get(rowIndex).getMoney_id(),null,"CH",date, "0",1)); // let's add it to our GUI
			SqlInsert.addPaymentRecord(payments.get(payments.size() -1));
		});

		invoiceDTO.getButtonDelete().setOnAction(e -> {
			int selectedIndex = paymentTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) // is something selected?
				SqlDelete.deletePayment(payments.get(selectedIndex));
			paymentTableView.getItems().remove(selectedIndex); // remove it from our GUI
			BigDecimal totalPaidAmount = BigDecimal.valueOf(SqlMoney.getTotalAmount(fiscals.get(rowIndex).getMoney_id()));
			invoiceDTO.getTotalPaymentText().setText(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
			fiscals.get(rowIndex).setPaid(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
			updateBalance();
		});

		// this is only called if you change membership type or open a record or manually type in
		invoiceDTO.getDuesTextField().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!SqlMoney.isCommitted(fiscals.get(rowIndex).getMoney_id())) {
				invoiceDTO.getDuesText().setText(newValue);
				fiscals.get(rowIndex).setDues(newValue);
				updateBalance();
			} else {
				System.out.println("Record is committed, no changes made");
			}
		});
		
		invoiceDTO.getDuesTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!FixInput.isBigDecimal(invoiceDTO.getDuesTextField().getText())) {
						invoiceDTO.getDuesTextField().setText("0");
	            	}
	            	BigDecimal dues = new BigDecimal(invoiceDTO.getDuesTextField().getText());
	            	updateItem(dues,"dues");
					invoiceDTO.getDuesTextField().setText(String.valueOf(dues.setScale(2, RoundingMode.HALF_UP)));
	            	updateBalance();
	            }
	        });

		SpinnerValueFactory<Integer> beachValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getBeach());
		invoiceDTO.getBeachSpinner().setValueFactory(beachValueFactory);
		invoiceDTO.getBeachSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setBeach(newValue);
			invoiceDTO.getBeachText().setText(String.valueOf(definedFees.getBeach().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakRackValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_rack());
		invoiceDTO.getKayakRackSpinner().setValueFactory(kayakRackValueFactory);
		invoiceDTO.getKayakRackSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setKayac_rack(newValue);
			invoiceDTO.getKayakRackText().setText(String.valueOf(definedFees.getKayak_rack().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakBeachRackValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayak_beach_rack());
		invoiceDTO.getKayakBeachRackSpinner().setValueFactory(kayakBeachRackValueFactory);
		invoiceDTO.getKayakBeachRackSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println(fiscals.get(rowIndex));
			System.out.println("newValue= " + newValue);
			fiscals.get(rowIndex).setKayak_beach_rack(newValue);
			System.out.println("Set kayak beach rack to money object =" + fiscals.get(rowIndex).getKayak_beach_rack());
			invoiceDTO.getKayakBeachRackText().setText(String.valueOf(definedFees.getKayak_beach_rack().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakShedValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_shed());
		invoiceDTO.getKayakShedSpinner().setValueFactory(kayakShedValueFactory);
		invoiceDTO.getKayakShedSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setKayac_shed(newValue);
			invoiceDTO.getKayakShedText().setText(String.valueOf(definedFees.getKayak_shed().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_loft());
		invoiceDTO.getSailLoftSpinner().setValueFactory(sailLoftValueFactory);
		invoiceDTO.getSailLoftSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setSail_loft(newValue);
			invoiceDTO.getSailLoftText().setText(String.valueOf(definedFees.getSail_loft().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailSchoolLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_school_laser_loft());
		invoiceDTO.getSailSchoolLoftSpinner().setValueFactory(sailSchoolLoftValueFactory);
		invoiceDTO.getSailSchoolLoftSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setSail_school_laser_loft(newValue);
			invoiceDTO.getSailSchoolLoftText().setText(String.valueOf(definedFees.getSail_school_laser_loft().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, getInitialWetSlipValue(fiscals.get(rowIndex).getWet_slip()));
		invoiceDTO.getWetSlipSpinner().setValueFactory(wetSlipValueFactory);
		invoiceDTO.getWetSlipSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			String wetSlip = String.valueOf(new BigDecimal(invoiceDTO.getWetslipTextFee().getText()).multiply(BigDecimal.valueOf(newValue)));
			fiscals.get(rowIndex).setWet_slip(wetSlip);
			invoiceDTO.getWetSlipText().setText(wetSlip);
			updateBalance();
		});

		// this is for changing the value from default
		invoiceDTO.getWetslipTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!FixInput.isBigDecimal(invoiceDTO.getWetslipTextField().getText())) {
					invoiceDTO.getWetslipTextField().setText("0.00");
				}
				BigDecimal slip = new BigDecimal(invoiceDTO.getWetslipTextField().getText());
				invoiceDTO.getWetSlipText().setText(String.valueOf(slip.multiply(BigDecimal.valueOf(invoiceDTO.getWetSlipSpinner().getValue()))));
				invoiceDTO.getWetslipTextField().setText(String.valueOf(slip.setScale(2, RoundingMode.HALF_UP)));
				invoiceDTO.getWetslipTextFee().setText(String.valueOf(slip.setScale(2, RoundingMode.HALF_UP)));
				String wetSlip = String.valueOf(new BigDecimal(invoiceDTO.getWetslipTextFee().getText()).multiply(BigDecimal.valueOf(invoiceDTO.getWetSlipSpinner().getValue())));
				invoice.setWet_slip(wetSlip);
				updateBalance();
				invoiceDTO.getVboxWetSlipFee().getChildren().clear();
				invoiceDTO.getVboxWetSlipFee().getChildren().add(invoiceDTO.getWetslipTextFee());
			}
		});

		SpinnerValueFactory<Integer> winterStorageValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getWinter_storage());
		invoiceDTO.getWinterStorageSpinner().setValueFactory(winterStorageValueFactory);
		invoiceDTO.getWinterStorageSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setWinter_storage(newValue);
			invoiceDTO.getWinterStorageText().setText(String.valueOf(definedFees.getWinter_storage().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> gateKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getExtra_key());
		invoiceDTO.getGateKeySpinner().setValueFactory(gateKeyValueFactory);
		invoiceDTO.getGateKeySpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			invoiceDTO.getGateKeyText().setText(String.valueOf(definedFees.getMain_gate_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setExtra_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getSail_loft_key());
		invoiceDTO.getSailLKeySpinner().setValueFactory(sailLKeyValueFactory);
		invoiceDTO.getSailLKeySpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			invoiceDTO.getSailLKeyText().setText(String.valueOf(definedFees.getSail_loft_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setSail_loft_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakSKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getKayac_shed_key());
		invoiceDTO.getKayakSKeySpinner().setValueFactory(kayakSKeyValueFactory);
		invoiceDTO.getKayakSKeySpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			invoiceDTO.getKayakSKeyText().setText(String.valueOf(definedFees.getKayak_shed_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setKayac_shed_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailSSLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getSail_school_loft_key());
		invoiceDTO.getSailSSLKeySpinner().setValueFactory(sailSSLKeyValueFactory);
		invoiceDTO.getSailSSLKeySpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			invoiceDTO.getSailSSLKeyText().setText(String.valueOf(definedFees.getSail_school_loft_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setSail_school_loft_key(newValue);
			updateBalance();
		});

		invoiceDTO.getYscTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!FixInput.isBigDecimal(invoiceDTO.getYscTextField().getText())) {
					invoiceDTO.getYscTextField().setText("0.00");
				}
				BigDecimal ysc = new BigDecimal(invoiceDTO.getYscTextField().getText());
				invoiceDTO.getYspText().setText(String.valueOf(ysc.setScale(2, RoundingMode.HALF_UP)));
				updateItem(ysc.setScale(2, RoundingMode.HALF_UP), "ysc");
				invoiceDTO.getYscTextField().setText(String.valueOf(ysc.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
			}
		});

		invoiceDTO.getInitiationTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!FixInput.isBigDecimal(invoiceDTO.getInitiationTextField().getText())) {
					invoiceDTO.getInitiationTextField().setText("0.00");
				}
				BigDecimal initiation = new BigDecimal(invoiceDTO.getInitiationTextField().getText());
				updateItem(initiation.setScale(2, RoundingMode.HALF_UP), "initiation");
				invoiceDTO.getInitiationTextField().setText(String.valueOf(initiation.setScale(2, RoundingMode.HALF_UP)));
				invoiceDTO.getInitiationText().setText(String.valueOf(initiation.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
			}
		});

		invoiceDTO.getOtherTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!FixInput.isBigDecimal(invoiceDTO.getOtherTextField().getText())) {
					invoiceDTO.getOtherTextField().setText("0.00");
				}
				BigDecimal other = new BigDecimal(invoiceDTO.getOtherTextField().getText());
				invoiceDTO.getOtherTextField().setText(String.valueOf(other.setScale(2, RoundingMode.HALF_UP)));
				updateItem(other.setScale(2, RoundingMode.HALF_UP),"other");
				updateBalance();
			}
		});

		invoiceDTO.getComboBox().getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			fiscals.get(rowIndex).setWork_credit(newValue);
			String workCredits = String.valueOf(definedFees.getWork_credit().multiply(BigDecimal.valueOf(newValue)));
			invoiceDTO.getWorkCreditsText().setText(workCredits);
			updateBalance();
		});

		invoiceDTO.getOtherCreditTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!FixInput.isBigDecimal(invoiceDTO.getOtherCreditTextField().getText())) {
					invoiceDTO.getOtherCreditTextField().setText("0.00");
				}
				BigDecimal otherCredit = new BigDecimal(invoiceDTO.getOtherCreditTextField().getText());
				invoiceDTO.getOtherCreditTextField().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
				fiscals.get(rowIndex).setOther_credit(String.valueOf(otherCredit));
				invoiceDTO.getOtherCreditTextField().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
			}
		});

		invoiceDTO.getOtherCreditTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!FixInput.isBigDecimal(invoiceDTO.getOtherCreditTextField().getText())) {
					invoiceDTO.getOtherCreditTextField().setText("0.00");
				}
				BigDecimal otherCredit = new BigDecimal(invoiceDTO.getOtherCreditTextField().getText());
				invoiceDTO.getOtherCreditTextField().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
				invoiceDTO.getOtherCreditText().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
			}
		});

		invoiceDTO.getCommitButton().setOnAction((event) -> {
			if (!fiscals.get(rowIndex).isCommitted()) {
				if (!invoiceDTO.getTotalBalanceText().getText().equals("0.00")) {
					invoiceDTO.getTotalBalanceText().setStyle("-fx-background-color: #f23a50");
					note.addMemoAndReturnId("Non-Zero Balance: ",date,fiscals.get(rowIndex).getMoney_id(),"B");
				}
				SqlUpdate.commitFiscalRecord(fiscals.get(rowIndex).getMoney_id(), true);// this could be placed in line above
				SqlUpdate.updateMembershipId(fiscals.get(rowIndex).getMs_id(), fiscals.get(rowIndex).getFiscal_year(), invoiceDTO.getRenewCheckBox().isSelected());
				fiscals.get(rowIndex).setCommitted(true);

				// if we put an amount in other we need to make a note
				if(new BigDecimal(fiscals.get(rowIndex).getOther()).compareTo(BigDecimal.ZERO) != 0) {
					System.out.println("Found an other field");
					// make sure the memo doesn't already exist
					if(!SqlExists.memoExists(fiscals.get(rowIndex).getMoney_id(), "O"))
						note.addMemoAndReturnId("Other expense: ",date,fiscals.get(rowIndex).getMoney_id(),"O");
				}
				setEditable(false);
			} else {
				setEditable(true);
				fiscals.get(rowIndex).setCommitted(false);
				SqlUpdate.commitFiscalRecord(fiscals.get(rowIndex).getMoney_id(), false);
			}
		});

		invoiceDTO.getWetslipTextFee().setOnMouseClicked(e -> {
			invoiceDTO.getVboxWetSlipFee().getChildren().clear();
			invoiceDTO.getVboxWetSlipFee().getChildren().add(invoiceDTO.getWetslipTextField());
		});

		invoiceDTO.getWetslipTextFee().setFill(Color.BLUE);
		invoiceDTO.getWetslipTextFee().setOnMouseEntered(en -> invoiceDTO.getWetslipTextFee().setFill(Color.RED));
		invoiceDTO.getWetslipTextFee().setOnMouseExited(ex -> invoiceDTO.getWetslipTextFee().setFill(Color.BLUE));

		checkIfRecordHasOfficer();
		updateBalance(); // updates and saves
		//////////////// SETTING CONTENT //////////////
		invoiceDTO.getDuesText().setText(String.valueOf(fiscals.get(rowIndex).getDues()));
		invoiceDTO.getDuesTextField().setText(String.valueOf(fiscals.get(rowIndex).getDues()));
		invoiceDTO.getYscTextField().setText(String.valueOf(fiscals.get(rowIndex).getYsc_donation()));
		invoiceDTO.getOtherTextField().setText(String.valueOf(fiscals.get(rowIndex).getOther()));
		invoiceDTO.getInitiationTextField().setText(String.valueOf(fiscals.get(rowIndex).getInitiation()));
		invoiceDTO.getWetslipTextField().setText(String.valueOf(definedFees.getWet_slip()));
		invoiceDTO.getOtherCreditTextField().setText(String.valueOf(fiscals.get(rowIndex).getOther_credit()));
		invoiceDTO.getYspText().setText(fiscals.get(rowIndex).getYsc_donation());
		invoiceDTO.getInitiationText().setText(fiscals.get(rowIndex).getInitiation());
		invoiceDTO.getOtherFeeText().setText(fiscals.get(rowIndex).getOther());
		invoiceDTO.getWorkCreditsText().setText(String.valueOf(countWorkCredits()));
		invoiceDTO.getOtherCreditText().setText(fiscals.get(rowIndex).getOther_credit());
		invoiceDTO.getTotalBalanceText().setText(fiscals.get(rowIndex).getCredit());
		invoiceDTO.getTotalCreditText().setText(fiscals.get(rowIndex).getCredit());
		invoiceDTO.getTotalPaymentText().setText(fiscals.get(rowIndex).getPaid());
		invoiceDTO.getWetslipTextFee().setText(String.valueOf(definedFees.getWet_slip()));
		invoiceDTO.getBeachText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getBeach()).multiply(definedFees.getBeach())));
		invoiceDTO.getKayakRackText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_rack()).multiply(definedFees.getKayak_rack())));
		invoiceDTO.getKayakBeachRackText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayak_beach_rack()).multiply(definedFees.getKayak_beach_rack())));
		invoiceDTO.getKayakShedText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_shed()).multiply(definedFees.getKayak_shed())));
		invoiceDTO.getSailLoftText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_loft()).multiply(definedFees.getSail_loft())));
		invoiceDTO.getSailSchoolLoftText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_school_laser_loft()).multiply(definedFees.getSail_school_laser_loft())));
		invoiceDTO.getWetSlipText().setText(fiscals.get(rowIndex).getWet_slip());
		invoiceDTO.getWinterStorageText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getWinter_storage()).multiply(definedFees.getWinter_storage())));
		invoiceDTO.getGateKeyText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getExtra_key()).multiply(definedFees.getMain_gate_key())));
		invoiceDTO.getSailLKeyText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_loft_key()).multiply(definedFees.getSail_loft_key())));
		invoiceDTO.getKayakSKeyText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_shed_key()).multiply(definedFees.getKayak_shed_key())));
		invoiceDTO.getSailSSLKeyText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_school_loft_key()).multiply(definedFees.getSail_school_loft_key())));
		invoiceDTO.getPositionCreditText().setText(fiscals.get(rowIndex).getOfficer_credit());
		setEditable(!fiscals.get(rowIndex).isCommitted());
		updateBalance();
		paymentTableView.getColumns().addAll(Arrays.asList(col1,col2,col3,col4));
		scrollPane.setContent(invoiceDTO.getGridPane());
		mainVbox.getChildren().addAll(scrollPane);  // add error HBox in first
		vboxGrey.getChildren().addAll(mainVbox);
		getChildren().addAll(vboxGrey);
	}

	private void checkIfRecordHasOfficer() {
		if (fiscals.get(rowIndex).isSupplemental()) { // have we already created a record for this year?
			invoiceDTO.getDuesTextField().setEditable(true);
			//duesTextField.setText("0");
		} else { // this is the first invoice record created for this year
			if (hasOfficer) { // has officer and not
				fiscals.get(rowIndex).setOfficer_credit(String.valueOf(definedFees.getDues_regular()));
				if(!SqlMoney.isCommitted(fiscals.get(rowIndex).getMoney_id()))	{	// is not committed
					BaseApplication.logger.info("This record has not been committed");
				}
			} else {

				fiscals.get(rowIndex).setOfficer_credit("0.00");
			}
			BaseApplication.logger.info("Membership officer or chairman: " + hasOfficer);
		}
	}

	//////////////////////  CLASS METHODS ///////////////////////////
	private int getInitialWetSlipValue(String wet_slip) {
		int startPoint = 1;
		BigDecimal wetSlip = new BigDecimal(wet_slip);
		if(wetSlip.compareTo(BigDecimal.ZERO) == 0) startPoint = 0;
		return startPoint;
	}

	private void getPayment() {
		// check to see if invoice record exists
		if(SqlExists.paymentExists(fiscals.get(rowIndex).getMoney_id())) {
			// select existing invoice record from SQL
			this.payments = SqlPayment.getPayments(fiscals.get(rowIndex).getMoney_id());
			System.out.println("A record for money_id=" + fiscals.get(rowIndex).getMoney_id() + " exists. Opening Payment");
			// pull up payments from database
		} else {  // if not create one
			this.payments = FXCollections.observableArrayList();
			System.out.println("getPayment(): Creating a new payment entry");
			int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment","pay_id");
			payments.add(new PaymentDTO(pay_id,fiscals.get(rowIndex).getMoney_id(),"0","CH",date, "0",1));
			SqlInsert.addPaymentRecord(payments.get(payments.size() - 1));
		}
	}

	private void updateItem(BigDecimal newTotalValue, String type) {
		switch (type) {
			case "initiation" -> fiscals.get(rowIndex).setInitiation(String.valueOf(newTotalValue));
			case "other" -> fiscals.get(rowIndex).setOther(String.valueOf(newTotalValue));
			case "ysc" -> fiscals.get(rowIndex).setYsc_donation(String.valueOf(newTotalValue));
			case "dues" -> fiscals.get(rowIndex).setDues(String.valueOf(newTotalValue));
			case "wetslip" -> fiscals.get(rowIndex).setWet_slip(String.valueOf(newTotalValue));
			case "other_credit" -> fiscals.get(rowIndex).setOther_credit(String.valueOf(newTotalValue));
		}
		fiscals.get(rowIndex).setTotal(String.valueOf(updateTotalFeeField()));
	}
	
	private void setEditable(boolean isEditable) {
		invoiceDTO.clearGridPane();
		if(isEditable)  {
			invoiceDTO.populateUncommitted();
			invoiceDTO.getCommitButton().setText("Commit");
			invoiceDTO.getVboxCommitButton().getChildren().clear();
			invoiceDTO.getVboxCommitButton().getChildren().addAll(invoiceDTO.getRenewCheckBox(), invoiceDTO.getCommitButton());
		} else {
			invoiceDTO.populateCommitted();
			invoiceDTO.getCommitButton().setText("Edit");
			invoiceDTO.getVboxCommitButton().getChildren().clear();
			HBox hboxButtons = new HBox();
			hboxButtons.setSpacing(5);
			hboxButtons.getChildren().addAll(invoiceDTO.getCommitButton());
			invoiceDTO.getVboxCommitButton().getChildren().addAll(hboxButtons);
		}
	}
	
	private void updateBalance() {
		  // updates total to the selected money object
		  fiscals.get(rowIndex).setTotal(String.valueOf(updateTotalFeeField()));
		  // updates gui with the newly calculated total
		  invoiceDTO.getTotalFeesText().setText(String.valueOf(fiscals.get(rowIndex).getTotal()));
		  // updates credit to the selected money object
		  fiscals.get(rowIndex).setCredit(String.valueOf(countTotalCredit()));
		  // updates gui with the newly calculated credit
		  invoiceDTO.getTotalCreditText().setText(fiscals.get(rowIndex).getCredit());
		  // updates balance to the selected money object
		  fiscals.get(rowIndex).setBalance(String.valueOf(getBalance()));
		  // updates gui with the newly calculated balance
		  invoiceDTO.getTotalBalanceText().setText(fiscals.get(rowIndex).getBalance());
		  // updates sql using selected money object
		  SqlUpdate.updateMoney(fiscals.get(rowIndex));  // saves to database
	}
	
	private BigDecimal updateTotalFeeField() {
		// adds all values together to get total
		BigDecimal dues = new BigDecimal(fiscals.get(rowIndex).getDues());
		BigDecimal beachSpot = new BigDecimal(fiscals.get(rowIndex).getBeach()).multiply(definedFees.getBeach());
		BigDecimal kayakRack = new BigDecimal(fiscals.get(rowIndex).getKayac_rack()).multiply(definedFees.getKayak_rack());
		BigDecimal kayakBeachRack = new BigDecimal(fiscals.get(rowIndex).getKayak_beach_rack()).multiply(definedFees.getKayak_beach_rack());
		BigDecimal kayakShed = new BigDecimal(fiscals.get(rowIndex).getKayac_shed()).multiply(definedFees.getKayak_shed());
		BigDecimal sailLoft = new BigDecimal(fiscals.get(rowIndex).getSail_loft()).multiply(definedFees.getSail_loft());
		BigDecimal sailSchoolLoft = new BigDecimal(fiscals.get(rowIndex).getSail_school_laser_loft()).multiply(definedFees.getSail_school_laser_loft());
		BigDecimal wetSlip = new BigDecimal(fiscals.get(rowIndex).getWet_slip());
		BigDecimal winterStorage = new BigDecimal(fiscals.get(rowIndex).getWinter_storage()).multiply(definedFees.getWinter_storage());
		BigDecimal extraKey = new BigDecimal(fiscals.get(rowIndex).getExtra_key()).multiply(definedFees.getMain_gate_key());
		BigDecimal sailLoftKey = new BigDecimal(fiscals.get(rowIndex).getSail_loft_key()).multiply(definedFees.getSail_loft_key());
		BigDecimal kayakShedKey = new BigDecimal(fiscals.get(rowIndex).getKayac_shed_key()).multiply(definedFees.getKayak_shed_key());
		BigDecimal sailSchoolLoftKey = new BigDecimal(fiscals.get(rowIndex).getSail_school_loft_key()).multiply(definedFees.getSail_school_loft_key());
		BigDecimal yscDonation = new BigDecimal(fiscals.get(rowIndex).getYsc_donation());
		BigDecimal other = new BigDecimal(fiscals.get(rowIndex).getOther());
		BigDecimal initiation = new BigDecimal(fiscals.get(rowIndex).getInitiation());
		return extraKey.add(sailLoftKey).add(kayakShedKey).add(sailSchoolLoftKey).add(beachSpot).add(kayakRack).add(kayakBeachRack).add(kayakShed)
				.add(sailLoft).add(sailSchoolLoft).add(wetSlip).add(winterStorage).add(yscDonation).add(dues).add(other).add(initiation);
	}

	private BigDecimal getBalance() {
		// calculates new balance
		BigDecimal total = new BigDecimal(fiscals.get(rowIndex).getTotal());
		BigDecimal paid = new BigDecimal(fiscals.get(rowIndex).getPaid());
		BigDecimal credit = new BigDecimal(fiscals.get(rowIndex).getCredit());
		return total.subtract(paid).subtract(credit);
	}

	// decides if officer credit or work credit is counted
	private BigDecimal countCredit() {
		BigDecimal credit;
		// if an officer we are going to use this
		if(hasOfficer) credit = new BigDecimal(fiscals.get(rowIndex).getOfficer_credit());
		// else we are going to calculate work credits
		else credit = countWorkCredits();
		return credit;
	}

	private BigDecimal countWorkCredits() {
		// counts the dollar value of a credit by the number of credits earned
		return definedFees.getWork_credit().multiply(BigDecimal.valueOf(fiscals.get(rowIndex).getWork_credit()));
	}

	private BigDecimal countTotalCredit() {
		// calculates either officer credit or work credits
		BigDecimal normalCredit = countCredit();
		//  additional or "other credit"
		BigDecimal otherCredit = new BigDecimal(fiscals.get(rowIndex).getOther_credit());
		//  sum of both credits above
		return normalCredit.add(otherCredit);
	}

	private Boolean membershipHasOfficer() {
		return people.stream()
				.anyMatch(personDTO -> SqlExists.isOfficer(personDTO, fiscals.get(rowIndex).getFiscal_year()));
	}

	private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
		TableColumn<T, String> col = new TableColumn<>(title);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		col.setCellFactory(column -> EditCell.createStringEditCell());
		return col ;
	}
}
