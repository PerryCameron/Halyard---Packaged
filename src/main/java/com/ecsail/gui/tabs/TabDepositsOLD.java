package com.ecsail.gui.tabs;

import com.ecsail.Launcher;
import com.ecsail.gui.dialogues.Dialogue_DepositPDF;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.*;
import com.ecsail.structures.*;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class TabDepositsOLD extends Tab {
	// starts with all paid dues for a given year, then can change to dues for a selected deposit
	private final ObservableList<PaidDuesDTO> paidDues;
	// containes all the defined fees for a given year
	private DefinedFeeDTO currentDefinedFee;
	// contains deposit number, date, year for a selected deposit
	private DepositDTO currentDeposit;
	// object of text objects to simplify method parameters
	private final DepositSummaryTextDTO summaryText = new DepositSummaryTextDTO();
	// will hold the totals of all at first and then for a selected deposit
	private final DepositSummaryDTO summaryTotals = new DepositSummaryDTO();

	Text numberOfRecords = new Text("0");
	String currentDate;
	String selectedYear;
	final Spinner<Integer> batchSpinner = new Spinner<>();

	public TabDepositsOLD(String text) {
		super(text);
		this.paidDues = FXCollections.observableArrayList(param -> new Observable[] { param.closedProperty() });
		this.selectedYear = new SimpleDateFormat("yyyy").format(new Date()); // lets start at the current year
		this.paidDues.addAll(SqlDeposit.getPaidDues(selectedYear));
		summaryTotals.setDepositNumber(SqlMoney.getBatchNumber(selectedYear));
		this.currentDefinedFee = SqlDefinedFee.getDefinedFeeByYear(selectedYear);
		this.currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		////////////////////// OBJECT INSTANCE //////////////////////////

		ObservableList<String> options = FXCollections.observableArrayList("Show All", "Show Selected");

		var vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		var vboxBlue = new VBox();
		var vboxPink = new VBox(); // this creates a pink border around the table
		var mainHBox = new HBox(); // this separates table content from controls
		var controlsVBox = new VBox(); // inner grey box
		var controlsHBox = new HBox(); // outer blue box
		var batchNumberHBox = new HBox(); // holds spinner and label
		var buttonHBox = new HBox(); // holds buttons
		var yearBatchHBox = new HBox(); // holds spinner and batchNumberHBox
		var gridHBox = new HBox(); // holds gridPane
		var remaindingRenewalHBox = new HBox();
		var selectionHBox = new HBox();
		var numberOfRecordsHBox = new HBox();
		var comboBoxHBox = new HBox();
		var nonRenewed = new Text("0");
		var paidDuesTableView = new TableView<PaidDuesDTO>();
		
		final var comboBox = new ComboBox<>(options);
		var gridPane = new GridPane();
		var refreshButton = new Button("Refresh");
		var printPdfButton = new Button("Print PDF");
		var depositDatePicker = new DatePicker();

		//////////////////// OBJECT ATTRIBUTES ///////////////////////////
		var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		numberOfRecords.setStyle("-fx-font-weight: bold;");

		vboxBlue.setId("box-blue");
		controlsHBox.setId("box-blue");
		controlsVBox.setId("box-grey");
		vboxPink.setId("box-pink");
		
		controlsHBox.setPadding(new Insets(5, 5, 5, 5));
		controlsVBox.setPadding(new Insets(15, 5, 5, 5));
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink fram around table
		selectionHBox.setPadding(new Insets(0, 0, 0, 37));
		comboBoxHBox.setPadding(new Insets(0, 0, 0, 37));
		
		controlsVBox.setPrefWidth(342);
		depositDatePicker.setPrefWidth(123);
		paidDuesTableView.setPrefWidth(1500);
		
		//vboxGrey.setPrefHeight(688);
		paidDuesTableView.setPrefHeight(1200);
		
		controlsVBox.setSpacing(10);
		mainHBox.setSpacing(5);
		batchNumberHBox.setSpacing(5);
		buttonHBox.setSpacing(10);
		numberOfRecordsHBox.setSpacing(5);
		selectionHBox.setSpacing(30);
		yearBatchHBox.setSpacing(15);
		
		VBox.setVgrow(vboxBlue, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		
		batchNumberHBox.setAlignment(Pos.CENTER);
		yearBatchHBox.setAlignment(Pos.CENTER);
		gridHBox.setAlignment(Pos.CENTER);
		buttonHBox.setAlignment(Pos.CENTER);
		remaindingRenewalHBox.setAlignment(Pos.CENTER);
		
		gridPane.setVgap(5);
		gridPane.setHgap(50);
		paidDuesTableView.setItems(paidDues);
		paidDuesTableView.setFixedCellSize(30);
		paidDuesTableView.setEditable(true);
		paidDuesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
		comboBox.setValue("Show All");

		final var yearSpinner = new Spinner<Integer>();
		var wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970,
				Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
		yearSpinner.setValueFactory(wetSlipValueFactory);
		yearSpinner.setPrefWidth(95);
		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				selectedYear = yearSpinner.getEditor().getText();
				batchSpinner.getValueFactory().setValue(SqlMoney.getBatchNumber(selectedYear)); // set batch to last																					// year
				paidDues.clear();
				paidDues.addAll(SqlDeposit.getPaidDues(selectedYear));
				currentDefinedFee.clear();
				currentDefinedFee = SqlDefinedFee.getDefinedFeeByYear(selectedYear);
				summaryTotals.setDepositNumber(Integer.parseInt(batchSpinner.getEditor().getText()));
				refreshButton.fire();
			}
		});

		// final Spinner<Integer> batchSpinner = new Spinner<Integer>();
		var batchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
				summaryTotals.getDepositNumber()); // 0 to batch, display batch
		batchSpinner.setValueFactory(batchValueFactory);
		batchSpinner.setPrefWidth(60);
		batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				batchSpinner.increment(0); // won't change value, but will commit editor
				// create batch if it doesn't already exist
				summaryTotals.setDepositNumber(Integer.parseInt(batchSpinner.getEditor().getText()));
				checkForDepositAndCreateIfNotExist();
				refreshButton.fire();
			}
		});

		// example for this column found at
		// https://o7planning.org/en/11079/javafx-tableview-tutorial
		var Col1 = new TableColumn<PaidDuesDTO, Boolean>("Select");
		Col1.setPrefWidth(50);
		Col1.setCellValueFactory(param -> {
			PaidDuesDTO thisPaidDues = param.getValue();
			SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(thisPaidDues.isClosed());
			booleanProp.addListener((observable, oldValue, newValue) -> {
				thisPaidDues.setClosed(newValue); // sets checkbox value in table
				if (newValue) { // if checked
					setBatchAndClose(thisPaidDues, summaryTotals.getDepositNumber(), true);
					System.out.println("batch=" + thisPaidDues.getBatch());
					addDepositIdToPayment(thisPaidDues); // does lots of stuff
				} else { // if unchecked
					setBatchAndClose(thisPaidDues, 0, false);
				}
				summaryTotals.clear();
				updateSummaryTotals();
				// updateCurrentMoneyTotals(); // need error check if batch doesn't exist
				updateMoneyTotals();
				updateNonRenewed(nonRenewed);
			});
			return booleanProp;
		});

		//
		Col1.setCellFactory(p -> {
			CheckBoxTableCell<PaidDuesDTO, Boolean> cell = new CheckBoxTableCell<>();
			cell.setAlignment(Pos.CENTER);
			return cell;
		});

		var Col2 = new TableColumn<PaidDuesDTO, Integer>("Batch");
		Col2.setCellValueFactory(new PropertyValueFactory<>("batch"));

		var Col9 = new TableColumn<PaidDuesDTO, Integer>("Mem ID");
		Col9.setCellValueFactory(new PropertyValueFactory<>("membershipId"));

		var Col3 = new TableColumn<PaidDuesDTO, String>("Last Name");
		Col3.setCellValueFactory(new PropertyValueFactory<>("l_name"));
		Col3.setPrefWidth(80);

		var Col4 = new TableColumn<PaidDuesDTO, String>("First Name");
		Col4.setCellValueFactory(new PropertyValueFactory<>("f_name"));
		Col4.setPrefWidth(80);

		var Col10 = new TableColumn<PaidDuesDTO, String>("Slip");
		Col10.setCellValueFactory(new PropertyValueFactory<>("wet_slip"));
		Col10.setPrefWidth(50);

		var Col5 = new TableColumn<PaidDuesDTO, Integer>("Fees");
		Col5.setCellValueFactory(new PropertyValueFactory<>("total"));
		Col5.setPrefWidth(50);

		var Col6 = new TableColumn<PaidDuesDTO, Integer>("Credit");
		Col6.setCellValueFactory(new PropertyValueFactory<>("credit"));
		Col6.setPrefWidth(50);

		var Col7 = new TableColumn<PaidDuesDTO, Integer>("Paid");
		Col7.setCellValueFactory(new PropertyValueFactory<>("paid"));
		Col7.setPrefWidth(50);

		var Col8 = new TableColumn<PaidDuesDTO, Integer>("Balance");
		Col8.setCellValueFactory(new PropertyValueFactory<>("balance"));
		Col8.setPrefWidth(50);

		var Col11 = new TableColumn<PaidDuesDTO, Integer>("Cmit");
		Col11.setCellValueFactory(new PropertyValueFactory<>("committed"));
		Col11.setPrefWidth(50);

		Col6.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col7.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col8.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col5.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col10.setStyle( "-fx-alignment: CENTER-RIGHT;");

		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );    // check box
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );    // batch
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // Mem ID
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // last
		Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // first
		Col6.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // slip
		Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // fees
		Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // credit
		Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );    // paid
		Col10.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // balance
		Col11.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // cmit

		////////////////// LISTENERS //////////////////////

		paidDuesTableView.setRowFactory(tv -> {
			var row = new TableRow<PaidDuesDTO>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					PaidDuesDTO clickedRow = row.getItem();
//					System.out.println("TabDeposits: membership=" + clickedRow.toString());
					Launcher.createTabForDeposits(clickedRow.getMs_id(), selectedYear);
				}
			});
			return row;
		});  // different for accounts that are not active any more

		refreshButton.setOnAction((event) -> {
			paidDues.clear();
			if (comboBox.getValue().equals("Show All")) {
				paidDues.addAll(SqlDeposit.getPaidDues(selectedYear));
			} else {
				paidDues.addAll(SqlDeposit.getPaidDues(selectedYear, summaryTotals.getDepositNumber()));
			}
			summaryTotals.clear();
			updateSummaryTotals();
			updateMoneyTotals();
			updateNonRenewed(nonRenewed);
			numberOfRecords.setText(paidDues.size() + "");

			if (SqlExists.depositRecordExists(selectedYear + "", summaryTotals.getDepositNumber())) {
				currentDeposit = SqlDeposit.getDeposit(selectedYear + "", summaryTotals.getDepositNumber());
				LocalDate date = LocalDate.parse(currentDeposit.getDepositDate(), formatter);
				depositDatePicker.setValue(date);
			}
			// check if deposit exists here
		});

		printPdfButton.setOnAction((event) -> new Dialogue_DepositPDF(currentDeposit, currentDefinedFee, selectedYear));

		comboBox.valueProperty().addListener((change) -> refreshButton.fire());

		var pickerEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				LocalDate date = depositDatePicker.getValue();
				SqlUpdate.updateDeposit("DEPOSIT_DATE", currentDeposit.getDeposit_id(), date);
			}
		};

		depositDatePicker.setOnAction(pickerEvent);

		/////////////////// SET CONTENT ///////////////////////

		gridPane.add(new Text("Annual Dues:"), 0, 0);
		gridPane.add(summaryText.getDuesNumberText(), 1, 0);
		GridPane.setHalignment(summaryText.getDuesNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getDuesMoneyText(), 2, 0);
		GridPane.setHalignment(summaryText.getDuesMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Winter Storage:"), 0, 1);
		gridPane.add(summaryText.getWinterStorageNumberText(), 1, 1);
		GridPane.setHalignment(summaryText.getWinterStorageNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getWinterStorageMoneyText(), 2, 1);
		GridPane.setHalignment(summaryText.getWinterStorageMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Wet Slip:"), 0, 2);
		gridPane.add(summaryText.getWetSlipNumberText(), 1, 2);
		GridPane.setHalignment(summaryText.getWetSlipNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getWetSlipMoneyText(), 2, 2);
		GridPane.setHalignment(summaryText.getWetSlipMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Beach Spot:"), 0, 3);
		gridPane.add(summaryText.getBeachSpotNumberText(), 1, 3);
		GridPane.setHalignment(summaryText.getBeachSpotNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getBeachSpotMoneyText(), 2, 3);
		GridPane.setHalignment(summaryText.getBeachSpotMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Outside Kayac Storage:"), 0, 4);
		gridPane.add(summaryText.getKayakRackNumberText(), 1, 4);
		GridPane.setHalignment(summaryText.getKayakRackNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getKayakRackMoneyText(), 2, 4);
		GridPane.setHalignment(summaryText.getKayakRackMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Inside Kayac Storage:"), 0, 5);
		gridPane.add(summaryText.getKayakShedNumberText(), 1, 5);
		GridPane.setHalignment(summaryText.getKayakShedNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getKayakShedMoneyText(), 2, 5);
		GridPane.setHalignment(summaryText.getKayakShedMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Sail Loft Access:"), 0, 6);
		gridPane.add(summaryText.getSailLoftNumberText(), 1, 6);
		GridPane.setHalignment(summaryText.getSailLoftNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getSailLoftMoneyText(), 2, 6);
		GridPane.setHalignment(summaryText.getSailLoftMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Sail School Loft Access:"), 0, 7);
		gridPane.add(summaryText.getSailSchoolLoftNumberText(), 1, 7);
		GridPane.setHalignment(summaryText.getSailSchoolLoftNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getSailSchoolLoftMoneyText(), 2, 7);
		GridPane.setHalignment(summaryText.getSailSchoolLoftMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Extra Gate Key:"), 0, 8);
		gridPane.add(summaryText.getGateKeyNumberText(), 1, 8);
		GridPane.setHalignment(summaryText.getGateKeyNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getGateKeyMoneyText(), 2, 8);
		GridPane.setHalignment(summaryText.getGateKeyMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Extra Kayak Shed Key:"), 0, 9);
		gridPane.add(summaryText.getKayakShedKeyNumberText(), 1, 9);
		GridPane.setHalignment(summaryText.getKayakShedKeyNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getKayakShedKeyMoneyText(), 2, 9);
		GridPane.setHalignment(summaryText.getKayakShedKeyMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Extra Sail Loft Key:"), 0, 10);
		gridPane.add(summaryText.getSailLoftKeyNumberText(), 1, 10);
		GridPane.setHalignment(summaryText.getSailLoftKeyNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getSailLoftKeyMoneyText(), 2, 10);
		GridPane.setHalignment(summaryText.getSailLoftKeyMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Extra Sail School Loft Key:"), 0, 11);
		gridPane.add(summaryText.getSailSchoolLoftKeyNumberText(), 1, 11);
		GridPane.setHalignment(summaryText.getSailSchoolLoftKeyNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getSailSchoolLoftKeyMoneyText(), 2, 11);
		GridPane.setHalignment(summaryText.getSailSchoolLoftKeyMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Initiation fee:"), 0, 12);
		gridPane.add(summaryText.getInitiationNumberText(), 1, 12);
		GridPane.setHalignment(summaryText.getInitiationNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getInitiationMoneyText(), 2, 12);
		GridPane.setHalignment(summaryText.getInitiationMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("YSC Donation:"), 0, 13);
		gridPane.add(summaryText.getYspDonationNumberText(), 1, 13);
		GridPane.setHalignment(summaryText.getYspDonationNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getYspDonationMoneyText(), 2, 13);
		GridPane.setHalignment(summaryText.getYspDonationMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Credits:"), 0, 14);
		gridPane.add(summaryText.getCreditsNumberText(), 1, 14);
		GridPane.setHalignment(summaryText.getCreditsNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getCreditsMoneyText(), 2, 14);
		GridPane.setHalignment(summaryText.getCreditsMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Total:"), 0, 15);
		gridPane.add(summaryText.getTotalNumberText(), 1, 15);
		GridPane.setHalignment(summaryText.getTotalNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getTotalMoneyText(), 2, 15);
		GridPane.setHalignment(summaryText.getTotalMoneyText(), HPos.RIGHT);

		refreshButton.fire();
		comboBoxHBox.getChildren().add(comboBox);
		numberOfRecordsHBox.getChildren().addAll(new Text("Records:"), numberOfRecords);
		selectionHBox.getChildren().addAll(depositDatePicker, numberOfRecordsHBox);
		remaindingRenewalHBox.getChildren().addAll(new Text("Memberships not renewed: "), nonRenewed);
		batchNumberHBox.getChildren().addAll(new Label("Deposit Number"), batchSpinner);
		yearBatchHBox.getChildren().addAll(yearSpinner, batchNumberHBox);
		buttonHBox.getChildren().addAll(refreshButton, printPdfButton);
		gridHBox.getChildren().add(gridPane);
		controlsHBox.getChildren().add(controlsVBox);
		controlsVBox.getChildren().addAll(yearBatchHBox, selectionHBox, comboBoxHBox, gridHBox, buttonHBox,
				remaindingRenewalHBox);
		paidDuesTableView.getColumns()
				.addAll(Arrays.asList(Col1, Col2, Col9, Col3, Col4, Col10, Col5, Col6, Col7, Col8, Col11));
		mainHBox.getChildren().addAll(paidDuesTableView, controlsHBox);
		vboxGrey.getChildren().add(mainHBox);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}

	//////////////////////// CLASS METHODS //////////////////////////

	private void checkForDepositAndCreateIfNotExist() {
		// does a deposit exist for selected year and batch?
		if (SqlExists.depositRecordExists(selectedYear + "", summaryTotals.getDepositNumber())) {
//			System.out.println("deposit exists");
			SqlDeposit.getDeposit(selectedYear + "", summaryTotals.getDepositNumber()).getDeposit_id();
		} else { // record does not exist
//			System.out.println("deposit does not exist, creating record");
			createDepositRecord();
		}
	}

	private void addDepositIdToPayment(PaidDuesDTO thisPaidDues) {
		int pay_id = getPayId(thisPaidDues); // gets relevant object_payment
		int deposit_id = getDepositId(thisPaidDues);
//		System.out.println("Adding deposit id to payment tuple");
		SqlUpdate.updatePayment(pay_id, "deposit_id", deposit_id + ""); // add deposit_id to payment tuple
	}

	private int getDepositId(PaidDuesDTO thisPaidDues) {
		int deposit_id;
		if (SqlExists.depositRecordExists(thisPaidDues.getFiscal_year() + "", summaryTotals.getDepositNumber())) { // does
																														// a
																							// batch?
//			System.out.println("deposit exists");
			deposit_id = SqlDeposit.getDeposit(selectedYear + "", summaryTotals.getDepositNumber()).getDeposit_id();
		} else { // record does not exist
//			System.out.println("deposit does not exist, creating record");
			deposit_id = createDepositRecord();
		}
		return deposit_id;
	}

	private int getPayId(PaidDuesDTO thisPaidDues) {
		int pay_id;
		if (!SqlExists.paymentExists(thisPaidDues.getMoney_id())) { // No payment has been recorded, we need to create a
																	// blank payment record
			pay_id = createPaymentRecord(thisPaidDues);
		} else {
			pay_id = SqlPayment.getPayment(thisPaidDues.getMoney_id()).getPay_id(); // payment record exists, lets get
																					// it's ID
		}
		return pay_id;
	}

	private int createPaymentRecord(PaidDuesDTO thisPaidDues) {
		int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment","pay_id");
		PaymentDTO newPayment = new PaymentDTO(pay_id, thisPaidDues.getMoney_id(), "0", "CH", currentDate, "0",
				1);
		SqlInsert.addPaymentRecord(newPayment);
		return pay_id;
	}

	private int createDepositRecord() {
		int deposit_id = SqlSelect.getNextAvailablePrimaryKey("deposit","deposit_id");
		DepositDTO newDeposit = new DepositDTO(deposit_id, currentDate, selectedYear,
				summaryTotals.getDepositNumber());
		SqlInsert.addDeposit(newDeposit);
		return deposit_id;
	}

	private void setBatchAndClose(PaidDuesDTO thisPaidDues, int thisBatch, Boolean closed) {
		SqlUpdate.updateMoneyBatch(thisPaidDues.getMoney_id(), thisBatch);
		SqlUpdate.updateMoneyClosed(thisPaidDues.getMoney_id(), closed);
		thisPaidDues.setBatch(thisBatch);
	}

	private void updateNonRenewed(Text nonRenewed) {
		nonRenewed.setText(SqlMembership_Id.getNonRenewNumber(selectedYear) + "");
	}

	private void updateMoneyTotals() { // need to add defined fees object
		summaryText.getDuesNumberText().setText(summaryTotals.getDuesNumber() + "");
		summaryText.getDuesMoneyText().setText("$" + summaryTotals.getDues());

		summaryText.getWinterStorageNumberText().setText(summaryTotals.getWinter_storageNumber() + "");
		summaryText.getWinterStorageMoneyText().setText("$" + summaryTotals.getWinter_storage());

		summaryText.getWetSlipNumberText().setText(summaryTotals.getWet_slipNumber() + "");
		summaryText.getWetSlipMoneyText().setText("$" + summaryTotals.getWet_slip());

		summaryText.getBeachSpotNumberText().setText(summaryTotals.getBeachNumber() + "");
		summaryText.getBeachSpotMoneyText().setText("$" + summaryTotals.getBeach());

		summaryText.getKayakRackNumberText().setText(summaryTotals.getKayak_rackNumber() + "");
		summaryText.getKayakRackMoneyText().setText("$" + summaryTotals.getKayak_rack());

		summaryText.getKayakShedNumberText().setText(summaryTotals.getKayak_shedNumber() + "");
		summaryText.getKayakShedMoneyText().setText("$" + summaryTotals.getKayak_shed());

		summaryText.getSailLoftNumberText().setText(summaryTotals.getSail_loftNumber() + "");
		summaryText.getSailLoftMoneyText().setText("$" + summaryTotals.getSail_loft());

		summaryText.getSailSchoolLoftNumberText().setText(summaryTotals.getSail_school_laser_loftNumber() + "");
		summaryText.getSailSchoolLoftMoneyText().setText("$" + summaryTotals.getSail_school_laser_loft());

		summaryText.getGateKeyNumberText().setText(summaryTotals.getGate_keyNumber() + "");
		summaryText.getGateKeyMoneyText().setText("$" + summaryTotals.getGate_key());

		summaryText.getKayakShedKeyNumberText().setText(summaryTotals.getKayac_shed_keyNumber() + "");
		summaryText.getKayakShedKeyMoneyText().setText("$" + summaryTotals.getKayac_shed_key());

		summaryText.getSailLoftKeyNumberText().setText(summaryTotals.getSail_loft_keyNumber() + "");
		summaryText.getSailLoftKeyMoneyText().setText("$" + summaryTotals.getSail_loft_key());

		summaryText.getSailSchoolLoftKeyNumberText().setText(summaryTotals.getSail_school_loft_keyNumber() + "");
		summaryText.getSailSchoolLoftKeyMoneyText().setText("$" + summaryTotals.getSail_school_loft_key());

		summaryText.getInitiationNumberText().setText(summaryTotals.getInitiationNumber() + "");
		summaryText.getInitiationMoneyText().setText("$" + summaryTotals.getInitiation());

		summaryText.getYspDonationNumberText().setText(summaryTotals.getYsc_donationNumber() + "");
		summaryText.getYspDonationMoneyText().setText("$" + summaryTotals.getYsc_donation());

		summaryText.getCreditsNumberText().setText(summaryTotals.getCreditNumber() + "");
		summaryText.getCreditsMoneyText().setText("$" + summaryTotals.getCredit());

		summaryText.getTotalNumberText().setText(summaryTotals.getNumberOfRecords() + "");
		summaryText.getTotalMoneyText().setText("$" + summaryTotals.getPaid());
	}

	private void updateSummaryTotals() {
		int numberOfRecordsCounted = 0; // number of records counted

		for (PaidDuesDTO d : paidDues) {
			BigDecimal beach = new BigDecimal(d.getBeach());  // make d.getbeach into bigDecimal
			if (beach.compareTo(BigDecimal.ZERO) != 0) { ///////// BEACH
				summaryTotals.setBeachNumber(d.getBeach() + summaryTotals.getBeachNumber()); // Integer
				BigDecimal totalBeachDollars = currentDefinedFee.getBeach().multiply(beach); // BigDecimal
				summaryTotals.setBeach(totalBeachDollars.add(summaryTotals.getBeach())); // BigDecimal

			}
			BigDecimal credit = new BigDecimal(d.getCredit()); //
			if (credit.compareTo(BigDecimal.ZERO) != 0) { //////// CREDIT
				summaryTotals.setCreditNumber(1 + summaryTotals.getCreditNumber()); // Integer
				summaryTotals.setCredit(credit.add(summaryTotals.getCredit())); // BigDecimal
			}
			BigDecimal dues = new BigDecimal((d.getDues()));
			if (dues.compareTo(BigDecimal.ZERO) != 0) { //////// DUES
				summaryTotals.setDuesNumber(1 + summaryTotals.getDuesNumber()); // Integer
				summaryTotals.setDues(dues.add(summaryTotals.getDues()));  // BigDecimal
//				System.out.println("d.getDues=" + d.getDues() + " duesNumber=" + summaryTotals.getDuesNumber()
//						+ " dues=" + dues + " dues=" + summaryTotals.getDues() + "currentDefinedFee=" + currentDefinedFee.getDues_regular());
			}
			BigDecimal extraKey = new BigDecimal(d.getExtra_key());
			if (d.getExtra_key() != 0) { ///// EXTRA GATE KEY
				summaryTotals.setGate_keyNumber(d.getExtra_key() + summaryTotals.getGate_keyNumber());
				BigDecimal totalGateKeyDollars = currentDefinedFee.getMain_gate_key().multiply(extraKey); // Integer
				summaryTotals.setGate_key(summaryTotals.getGate_key().add(totalGateKeyDollars)); // BigDecimal
//				System.out.println("d.getExtra_key=" + d.getExtra_key() + " gateKeyNumber=" + summaryTotals.getGate_keyNumber()
//						+ " totalGateKeyDollars=" + totalGateKeyDollars + " gate_key=" + summaryTotals.getGate_key());
			}
			BigDecimal initiation = new BigDecimal(d.getInitiation());
			if (initiation.compareTo(BigDecimal.ZERO) != 0) { /////// INITIATION
				summaryTotals.setInitiationNumber(1 + summaryTotals.getInitiationNumber());
				summaryTotals.setInitiation(initiation.add(summaryTotals.getInitiation()));
			}
			BigDecimal kayakRack = new BigDecimal(d.getKayac_rack());
			if (d.getKayac_rack() != 0) { ///// KAYACK RACK FEE
				summaryTotals.setKayak_rackNumber(d.getKayac_rack() + summaryTotals.getKayak_rackNumber());
				BigDecimal totalKayakRackDollars = currentDefinedFee.getKayak_rack().multiply(kayakRack);
				summaryTotals.setKayak_rack(totalKayakRackDollars.add(summaryTotals.getKayak_rack()));
			}
			BigDecimal kayakShed = new BigDecimal(d.getKayac_shed());
			if (d.getKayac_shed() != 0) { //////// KAYAK SHED ACCESS
				summaryTotals.setKayak_shedNumber(d.getKayac_shed() + summaryTotals.getKayac_shed_keyNumber());
				BigDecimal totalKayakShedDollars = currentDefinedFee.getKayak_shed().multiply(kayakShed);
				summaryTotals.setKayak_shed(totalKayakShedDollars.add(summaryTotals.getKayak_shed()));
			}
			BigDecimal kayakShedKey = new BigDecimal(d.getKayac_shed_key());
			if (d.getKayac_shed_key() != 0) { ///// KAYAK SHED KEY
				summaryTotals.setKayac_shed_keyNumber(d.getKayac_shed_key() + summaryTotals.getKayac_shed_keyNumber());
				BigDecimal totalKayakShedKeyDollars = currentDefinedFee.getKayak_shed_key().multiply(kayakShedKey);
				summaryTotals.setKayac_shed_key(totalKayakShedKeyDollars.add(summaryTotals.getKayac_shed_key()));
			}

			BigDecimal other = new BigDecimal(d.getOther());
			if (other.compareTo(BigDecimal.ZERO) != 0) { ///////// OTHER FEE ///////// IN DOLLARS
				summaryTotals.setOtherNumber(1 + summaryTotals.getOtherNumber());
				summaryTotals.setOther(other.add(summaryTotals.getOther()));
			}
			BigDecimal sailLoft = new BigDecimal(d.getSail_loft());
			if (d.getSail_loft() != 0) { ////////// SAIL LOFT ACCESS ///////// IN NUMBER OF
				summaryTotals.setSail_loftNumber(1 + summaryTotals.getSail_loftNumber());
				summaryTotals.setSail_loft(currentDefinedFee.getSail_loft().add(summaryTotals.getSail_loft()));
			}
			BigDecimal sailLoftKey = new BigDecimal(d.getSail_loft_key());
			if (d.getSail_loft_key() != 0) { ///////// SAIL LOFT KEY ///////// IN NUMBER OF
				summaryTotals.setSail_loft_keyNumber(d.getSail_loft_key() + summaryTotals.getSail_loft_keyNumber());
				BigDecimal totalSailLoftKeyDollars = currentDefinedFee.getSail_loft_key().multiply(sailLoftKey);
				summaryTotals.setSail_loft_key(totalSailLoftKeyDollars.add(summaryTotals.getSail_loft_key()));
			}
			BigDecimal laserLoft = new BigDecimal(d.getSail_school_laser_loft());
			if (d.getSail_school_laser_loft() != 0) { ///////// SAIL SCHOOL LOFT ACCESS ///////// IN NUMBER OF
				summaryTotals.setSail_school_laser_loftNumber(
						d.getSail_school_laser_loft() + summaryTotals.getSail_school_laser_loftNumber());
				BigDecimal totalSailSchoolLoftDollars = currentDefinedFee.getSail_school_laser_loft().multiply(laserLoft);
				summaryTotals.setSail_school_laser_loft(totalSailSchoolLoftDollars.add(summaryTotals.getSail_school_laser_loft()));
			}
			BigDecimal loftKey = new BigDecimal(d.getSail_school_loft_key());
			if (d.getSail_school_loft_key() != 0) { ////////// SAIL SCHOOL LOFT KEY ///////// IN NUMBER OF
				summaryTotals.setSail_school_loft_keyNumber(d.getSail_school_loft_key() + summaryTotals.getSail_school_loft_keyNumber());
				BigDecimal totalSailSchoolLoftKeyDollars = currentDefinedFee.getSail_school_loft_key().multiply(loftKey);
				summaryTotals.setSail_school_loft_key(
						totalSailSchoolLoftKeyDollars.add(summaryTotals.getSail_school_loft_key()));
			}
			BigDecimal wetSlip = new BigDecimal(d.getWet_slip());
			if (wetSlip.compareTo(BigDecimal.ZERO) != 0) { ////////// WET SLIP FEE ///////// IN DOLLARS
				summaryTotals.setWet_slipNumber(1 + summaryTotals.getWet_slipNumber());
				summaryTotals.setWet_slip(wetSlip.add(summaryTotals.getWet_slip()));
			}

			BigDecimal winterStorage = new BigDecimal(d.getWinter_storage());
			if (d.getWinter_storage() != 0) { //////// WINTER STORAGE FEE ///////// IN NUMBER OF
//				System.out.print("Mem ID " + d.getMembershipId() + ": Adding " + d.getWinter_storage() + " total: ");
				summaryTotals.setWinter_storageNumber(d.getWinter_storage() + summaryTotals.getWinter_storageNumber());
//				System.out.print(summaryTotals.getWinter_storageNumber());
				BigDecimal totalWinterStorageDollars = currentDefinedFee.getWinter_storage().multiply(winterStorage);
//				System.out.print(" -> " +totalWinterStorageDollars);
				summaryTotals.setWinter_storage(totalWinterStorageDollars.add(summaryTotals.getWinter_storage()));
//				System.out.println(" -> " + summaryTotals.getWinter_storage());
			}

			BigDecimal ysc = new BigDecimal(d.getYsc_donation());
			if (ysc.compareTo(BigDecimal.ZERO) != 0) { //////// YSC DONATION ///////// IN DOLLARS
				summaryTotals.setYsc_donationNumber(1 + summaryTotals.getYsc_donationNumber());
				summaryTotals.setYsc_donation(ysc.add(summaryTotals.getYsc_donation()));
			}
			numberOfRecordsCounted++;

			summaryTotals.setPaid(new BigDecimal(d.getPaid()).add(summaryTotals.getPaid()));
		}
		summaryTotals.setNumberOfRecords(numberOfRecordsCounted);
	}
}
