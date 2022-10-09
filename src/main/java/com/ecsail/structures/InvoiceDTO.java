package com.ecsail.structures;

import com.ecsail.sql.select.SqlPayment;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class InvoiceDTO {
    private final MoneyDTO invoice;
    private GridPane gridPane;
    private TextField yscTextField;
    private TextField duesTextField;
    private TextField otherTextField;
    private TextField initiationTextField;
    private TextField wetslipTextField;
    private TextField otherCreditTextField;

    private Spinner<Integer> beachSpinner;
    private Spinner<Integer> kayakRackSpinner;
    private Spinner<Integer> kayakBeachRackSpinner; // new
    private Spinner<Integer> kayakShedSpinner;
    private Spinner<Integer> sailLoftSpinner;
    private Spinner<Integer> sailSchoolLoftSpinner;
    private Spinner<Integer> winterStorageSpinner;
    private Spinner<Integer> wetSlipSpinner;
//    private Spinner<Integer> workCreditSpinner;
    private Spinner<Integer> gateKeySpinner;
    private Spinner<Integer> sailLKeySpinner;
    private Spinner<Integer> kayakSKeySpinner;
    private Spinner<Integer> sailSSLKeySpinner;

    private Text duesText;
    private Text beachText;
    private Text kayakRackText;
    private Text kayakBeachRackText;
    private Text kayakShedText;
    private Text sailLoftText;
    private Text sailSchoolLoftText;
    private Text wetSlipText;
    private Text winterStorageText;
    private Text yspText;
    private Text initiationText;
    private Text otherFeeText;
    private Text workCreditsText;
    private Text gateKeyText;
    private Text sailLKeyText;
    private Text kayakSKeyText;
    private Text sailSSLKeyText;
    private Text otherCreditText;
    private Text positionCreditText;
    private Text wetslipTextFee;

    private Text totalFeesText;
    private Text totalCreditText;
    private Text totalPaymentText;
    private Text totalBalanceText;

    // VBoxes for totals
    private VBox vboxDues;
    private VBox vboxBeach;
    private VBox vboxKayak;
    private VBox vboxBeachKayak;
    private VBox vboxKayakShed;
    private VBox vboxSailLoft;
    private VBox vboxSailSchoolLoft;
    private VBox vboxWetSlip;
    private VBox vboxWinterStorage;
    private VBox vboxGateKey;
    private VBox vboxSailLoftKey;
    private VBox vboxKayakShedKey;
    private VBox vboxSailSchoolLoftKey;
    private VBox vboxWorkCredits;
    private VBox vboxYSC;
    private VBox vboxInitiation;
    private VBox vboxOther;
    private VBox vboxOtherCredit;
    private VBox vboxPositionCredit;

    // VBoxes for multipliers
    private VBox vboxBeachFee;
    private VBox vboxKayakFee;
    private VBox vboxBeachKayakFee;
    private VBox vboxKayakShedFee;
    private VBox vboxSailLoftFee;
    private VBox vboxSailSchoolLoftFee;
    private VBox vboxWetSlipFee;
    private VBox vboxWinterStorageFee;
    private VBox vboxGateKeyFee;
    private VBox vboxSailLoftKeyFee;
    private VBox vboxKayakShedKeyFee;
    private VBox vboxSailSchoolLoftKeyFee;
    private VBox vboxWorkCreditsFee;

    private VBox vboxTitlePrice;
    private VBox vboxTitleTotal;
    private final VBox vboxTitleFee;
    private final VBox vboxTitleQty;

    private VBox vboxTotalFee;
    private VBox vboxTotalCredit;
    private VBox vboxTotalPayment;
    private VBox vboxTotalBalance;

    private VBox vboxButtons;
    private VBox vboxPink; // this creates a pink border around the table
    private VBox vboxCommitButton;
    private Button buttonAdd;
    private Button buttonDelete;
    private Button commitButton;
    private Button buttonAddNote;
    private CheckBox renewCheckBox;
    private ComboBox<Integer> comboBox;

    private DefinedFeeDTO definedFees;
    Separator separator = new Separator(Orientation.HORIZONTAL);

    public InvoiceDTO(MoneyDTO invoice, DefinedFeeDTO definedFees, TableView<PaymentDTO> paymentTableView) {
        Text text1 = new Text("Fee");
        Text text2 = new Text("Price");
        Text text3 = new Text("Total");
        Text text4 = new Text("Qty");
        this.comboBox = new ComboBox<>();
        this.invoice = invoice;
        this.buttonAdd = new Button("Add");
        this.buttonDelete = new Button("Delete");
        this.commitButton = new Button("Commit");
        this.buttonAddNote = new Button("Add Note");
        this.renewCheckBox = new CheckBox("Renew");
        this.definedFees = definedFees;
        this.gridPane = new GridPane();
        this.yscTextField = new TextField();
        this.duesTextField = new TextField();
        this.otherTextField = new TextField();
        this.initiationTextField = new TextField();
        this.wetslipTextField = new TextField();
        this.otherCreditTextField = new TextField();

        this.beachSpinner = new Spinner<>();
        this.kayakRackSpinner = new Spinner<>();
        this.kayakBeachRackSpinner = new Spinner<>();
        this.kayakShedSpinner = new Spinner<>();
        this.sailLoftSpinner = new Spinner<>();
        this.sailSchoolLoftSpinner = new Spinner<>();
        this.winterStorageSpinner = new Spinner<>();
        this.wetSlipSpinner = new Spinner<>();
//        this.workCreditSpinner = new Spinner<>();
        this.gateKeySpinner = new Spinner<>();
        this.sailLKeySpinner = new Spinner<>();
        this.kayakSKeySpinner = new Spinner<>();
        this.sailSSLKeySpinner = new Spinner<>();

        this.duesText = new Text();
        this.beachText = new Text();
        this.kayakRackText = new Text();
        this.kayakBeachRackText = new Text();
        this.kayakShedText = new Text();
        this.sailLoftText = new Text();
        this.sailSchoolLoftText = new Text();
        this.wetSlipText = new Text();
        this.winterStorageText = new Text();
        this.yspText = new Text();
        this.initiationText = new Text();
        this.otherFeeText = new Text();
        this.workCreditsText = new Text();
        this.gateKeyText = new Text();
        this.sailLKeyText = new Text();
        this.kayakSKeyText = new Text();
        this.sailSSLKeyText = new Text();
        this.otherCreditText = new Text();
        this.positionCreditText = new Text();
        this.wetslipTextFee = new Text();
        this.totalFeesText = new Text();
        this.totalCreditText = new Text();
        this.totalPaymentText = new Text();
        this.totalBalanceText = new Text();

        // VBoxes for totals
        this.vboxDues = new VBox();
        this.vboxBeach = new VBox();
        this.vboxKayak = new VBox();
        this.vboxBeachKayak = new VBox();
        this.vboxKayakShed = new VBox();
        this.vboxSailLoft = new VBox();
        this.vboxSailSchoolLoft = new VBox();
        this.vboxWetSlip = new VBox();
        this.vboxWinterStorage = new VBox();
        this.vboxGateKey = new VBox();
        this.vboxSailLoftKey = new VBox();
        this.vboxKayakShedKey = new VBox();
        this.vboxSailSchoolLoftKey = new VBox();
        this.vboxWorkCredits = new VBox();
        this.vboxYSC = new VBox();
        this.vboxInitiation = new VBox();
        this.vboxOther = new VBox();
        this.vboxOtherCredit = new VBox();
        this.vboxPositionCredit = new VBox();

        // VBoxes for multipliers
        this.vboxBeachFee = new VBox();
        this.vboxKayakFee = new VBox();
        this.vboxBeachKayakFee = new VBox();
        this.vboxKayakShedFee = new VBox();
        this.vboxSailLoftFee = new VBox();
        this.vboxSailSchoolLoftFee = new VBox();
        this.vboxWetSlipFee = new VBox();
        this.vboxWinterStorageFee = new VBox();
        this.vboxGateKeyFee = new VBox();
        this.vboxSailLoftKeyFee = new VBox();
        this.vboxKayakShedKeyFee = new VBox();
        this.vboxSailSchoolLoftKeyFee = new VBox();
        this.vboxWorkCreditsFee = new VBox();
        this.vboxTitlePrice = new VBox();
        this.vboxTitleTotal = new VBox();
        this.vboxTitleFee = new VBox();
        this.vboxTitleQty = new VBox();
        this.vboxTotalFee = new VBox();
        this.vboxTotalCredit = new VBox();
        this.vboxTotalPayment = new VBox();
        this.vboxTotalBalance = new VBox();
        this.vboxButtons = new VBox();
        this.vboxPink = new VBox(); // this creates a pink border around the table
        this.vboxCommitButton = new VBox();

        // fill comboBox
        for(int i = 0; i < 100; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().selectFirst();


        Font font = Font.font("Verdana", FontWeight.BOLD, 16);
        // ATTRIBUTES

        vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
        vboxPink.setId("box-pink");
        HBox.setHgrow(vboxPink, Priority.ALWAYS);
        vboxButtons.setSpacing(5);
        vboxCommitButton.setSpacing(10);

        HBox.setHgrow(gridPane,Priority.ALWAYS);

        gridPane.setVgap(5);

        text1.setFont(font);
        text2.setFont(font);
        text3.setFont(font);
        text4.setFont(font);

        this.yscTextField.setPrefWidth(65);
        this.otherTextField.setPrefWidth(65);
        this.otherCreditTextField.setPrefWidth(65);
        this.initiationTextField.setPrefWidth(65);
        this.wetslipTextField.setPrefWidth(65);
        this.duesTextField.setPrefWidth(65);
        this.beachSpinner.setPrefWidth(65);
        this.kayakShedSpinner.setPrefWidth(65);
        this.sailLoftSpinner.setPrefWidth(65);
        this.sailSchoolLoftSpinner.setPrefWidth(65);
        this.winterStorageSpinner.setPrefWidth(65);
        this.wetSlipSpinner.setPrefWidth(65);
        this.kayakRackSpinner.setPrefWidth(65);
        this.kayakBeachRackSpinner.setPrefWidth(65);
        this.comboBox.setPrefWidth(65);
        this.gateKeySpinner.setPrefWidth(65);
        this.sailLKeySpinner.setPrefWidth(65);
        this.sailSSLKeySpinner.setPrefWidth(65);
        this.kayakSKeySpinner.setPrefWidth(65);

        renewCheckBox.setSelected(true);
        buttonAdd.setPrefWidth(60);

        buttonDelete.setPrefWidth(60);

        vboxDues.getChildren().add(duesText);
        vboxDues.setAlignment(Pos.CENTER_RIGHT);
        vboxBeach.getChildren().add(beachText);
        vboxBeach.setAlignment(Pos.CENTER_RIGHT);
        vboxKayak.getChildren().add(kayakRackText);
        vboxKayak.setAlignment(Pos.CENTER_RIGHT);
        vboxBeachKayak.getChildren().add(kayakBeachRackText);
        vboxBeachKayak.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakShed.getChildren().add(kayakShedText);
        vboxKayakShed.setAlignment(Pos.CENTER_RIGHT);
        vboxSailLoft.getChildren().add(sailLoftText);
        vboxSailLoft.setAlignment(Pos.CENTER_RIGHT);
        vboxSailSchoolLoft.getChildren().add(sailSchoolLoftText);
        vboxSailSchoolLoft.setAlignment(Pos.CENTER_RIGHT);
        vboxWetSlip.getChildren().add(wetSlipText);
        vboxWetSlip.setAlignment(Pos.CENTER_RIGHT);
        vboxWinterStorage.getChildren().add(winterStorageText);
        vboxWinterStorage.setAlignment(Pos.CENTER_RIGHT);
        vboxGateKey.getChildren().add(gateKeyText);
        vboxGateKey.setAlignment(Pos.CENTER_RIGHT);
        vboxSailLoftKey.getChildren().add(sailLKeyText);
        vboxSailLoftKey.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakShedKey.getChildren().add(kayakSKeyText);
        vboxKayakShedKey.setAlignment(Pos.CENTER_RIGHT);
        vboxSailSchoolLoftKey.getChildren().add(sailSSLKeyText);
        vboxSailSchoolLoftKey.setAlignment(Pos.CENTER_RIGHT);
        vboxPositionCredit.getChildren().add(positionCreditText);
        vboxPositionCredit.setAlignment(Pos.CENTER_RIGHT);
        vboxWorkCredits.getChildren().add(workCreditsText);
        vboxWorkCredits.setAlignment(Pos.CENTER_RIGHT);

        vboxYSC.getChildren().add(yspText);
        vboxYSC.setAlignment(Pos.CENTER_RIGHT);
        vboxInitiation.getChildren().add(initiationText);
        vboxInitiation.setAlignment(Pos.CENTER_RIGHT);
        vboxOther.getChildren().add(otherFeeText);
        vboxOther.setAlignment(Pos.CENTER_RIGHT);
        vboxOtherCredit.getChildren().add(otherCreditText);
        vboxOtherCredit.setAlignment(Pos.CENTER_RIGHT);

        vboxBeachFee.getChildren().add(new Text(String.valueOf(definedFees.getBeach())));
        vboxBeachFee.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_rack())));
        vboxKayakFee.setAlignment(Pos.CENTER_RIGHT);
        vboxBeachKayakFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_beach_rack())));
        vboxBeachKayakFee.setAlignment(Pos.CENTER_RIGHT);

        vboxKayakShedFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_shed())));
        vboxKayakShedFee.setAlignment(Pos.CENTER_RIGHT);
        vboxSailLoftFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_loft())));
        vboxSailLoftFee.setAlignment(Pos.CENTER_RIGHT);
        vboxSailSchoolLoftFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_school_laser_loft())));
        vboxSailSchoolLoftFee.setAlignment(Pos.CENTER_RIGHT);
        vboxWinterStorageFee.getChildren().add(new Text(String.valueOf(definedFees.getWinter_storage())));
        vboxWinterStorageFee.setAlignment(Pos.CENTER_RIGHT);
        vboxGateKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getMain_gate_key())));
        vboxGateKeyFee.setAlignment(Pos.CENTER_RIGHT);
        vboxSailLoftKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_loft_key())));
        vboxSailLoftKeyFee.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakShedKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_shed_key())));
        vboxKayakShedKeyFee.setAlignment(Pos.CENTER_RIGHT);
        vboxSailSchoolLoftKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_school_loft_key())));
        vboxSailSchoolLoftKeyFee.setAlignment(Pos.CENTER_RIGHT);
        vboxWorkCreditsFee.getChildren().add(new Text(String.valueOf(definedFees.getWork_credit())));
        vboxWorkCreditsFee.setAlignment(Pos.CENTER_RIGHT);
        vboxTitlePrice.setAlignment(Pos.CENTER_RIGHT);
        vboxTitleTotal.setAlignment(Pos.CENTER_RIGHT);
        vboxWetSlipFee.getChildren().add(wetslipTextFee);
        vboxWetSlipFee.setAlignment(Pos.CENTER_RIGHT);

        vboxTotalFee.getChildren().add(totalFeesText);
        vboxTotalCredit.getChildren().add(totalCreditText);
        vboxTotalPayment.getChildren().add(totalPaymentText);
        vboxTotalBalance.getChildren().add(totalBalanceText);

        vboxTotalFee.setAlignment(Pos.CENTER_RIGHT);
        vboxTotalCredit.setAlignment(Pos.CENTER_RIGHT);
        vboxTotalPayment.setAlignment(Pos.CENTER_RIGHT);
        vboxTotalBalance.setAlignment(Pos.CENTER_RIGHT);

        vboxCommitButton.getChildren().addAll(renewCheckBox,commitButton);
        vboxPink.getChildren().add(paymentTableView);
        vboxButtons.getChildren().addAll(buttonAdd, buttonDelete);
        vboxTitleFee.getChildren().add(text1);
        vboxTitlePrice.getChildren().add(text2);
        vboxTitleTotal.getChildren().add(text3);
        vboxTitleQty.getChildren().add(text4);
    }

    public ComboBox<Integer> getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox<Integer> comboBox) {
        this.comboBox = comboBox;
    }

    public MoneyDTO getInvoice() {
        return invoice;
    }

    public VBox getVboxTitleQty() {
        return vboxTitleQty;
    }

    public VBox getVboxTotalFee() {
        return vboxTotalFee;
    }

    public void setVboxTotalFee(VBox vboxTotalFee) {
        this.vboxTotalFee = vboxTotalFee;
    }

    public VBox getVboxTotalCredit() {
        return vboxTotalCredit;
    }

    public void setVboxTotalCredit(VBox vboxTotalCredit) {
        this.vboxTotalCredit = vboxTotalCredit;
    }

    public VBox getVboxTotalPayment() {
        return vboxTotalPayment;
    }

    public void setVboxTotalPayment(VBox vboxTotalPayment) {
        this.vboxTotalPayment = vboxTotalPayment;
    }

    public VBox getVboxTotalBalance() {
        return vboxTotalBalance;
    }

    public void setVboxTotalBalance(VBox vboxTotalBalance) {
        this.vboxTotalBalance = vboxTotalBalance;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Button getButtonAdd() {
        return buttonAdd;
    }

    public void setButtonAdd(Button buttonAdd) {
        this.buttonAdd = buttonAdd;
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(Button buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public Button getCommitButton() {
        return commitButton;
    }

    public void setCommitButton(Button commitButton) {
        this.commitButton = commitButton;
    }

    public CheckBox getRenewCheckBox() {
        return renewCheckBox;
    }

    public void setRenewCheckBox(CheckBox renewCheckBox) {
        this.renewCheckBox = renewCheckBox;
    }

    public DefinedFeeDTO getDefinedFees() {
        return definedFees;
    }

    public void setDefinedFees(DefinedFeeDTO definedFees) {
        this.definedFees = definedFees;
    }

    public VBox getVboxDues() {
        return vboxDues;
    }

    public void setVboxDues(VBox vboxDues) {
        this.vboxDues = vboxDues;
    }

    public VBox getVboxBeach() {
        return vboxBeach;
    }

    public void setVboxBeach(VBox vboxBeach) {
        this.vboxBeach = vboxBeach;
    }

    public VBox getVboxKayak() {
        return vboxKayak;
    }

    public void setVboxKayak(VBox vboxKayak) {
        this.vboxKayak = vboxKayak;
    }

    public VBox getVboxKayakShed() {
        return vboxKayakShed;
    }

    public void setVboxKayakShed(VBox vboxKayakShed) {
        this.vboxKayakShed = vboxKayakShed;
    }

    public VBox getVboxSailLoft() {
        return vboxSailLoft;
    }

    public void setVboxSailLoft(VBox vboxSailLoft) {
        this.vboxSailLoft = vboxSailLoft;
    }

    public VBox getVboxSailSchoolLoft() {
        return vboxSailSchoolLoft;
    }

    public void setVboxSailSchoolLoft(VBox vboxSailSchoolLoft) {
        this.vboxSailSchoolLoft = vboxSailSchoolLoft;
    }

    public VBox getVboxWetSlip() {
        return vboxWetSlip;
    }

    public void setVboxWetSlip(VBox vboxWetSlip) {
        this.vboxWetSlip = vboxWetSlip;
    }

    public VBox getVboxWinterStorage() {
        return vboxWinterStorage;
    }

    public void setVboxWinterStorage(VBox vboxWinterStorage) {
        this.vboxWinterStorage = vboxWinterStorage;
    }

    public VBox getVboxGateKey() {
        return vboxGateKey;
    }

    public void setVboxGateKey(VBox vboxGateKey) {
        this.vboxGateKey = vboxGateKey;
    }

    public VBox getVboxSailLoftKey() {
        return vboxSailLoftKey;
    }

    public void setVboxSailLoftKey(VBox vboxSailLoftKey) {
        this.vboxSailLoftKey = vboxSailLoftKey;
    }

    public VBox getVboxKayakShedKey() {
        return vboxKayakShedKey;
    }

    public void setVboxKayakShedKey(VBox vboxKayakShedKey) {
        this.vboxKayakShedKey = vboxKayakShedKey;
    }

    public VBox getVboxSailSchoolLoftKey() {
        return vboxSailSchoolLoftKey;
    }

    public void setVboxSailSchoolLoftKey(VBox vboxSailSchoolLoftKey) {
        this.vboxSailSchoolLoftKey = vboxSailSchoolLoftKey;
    }

    public VBox getVboxWorkCredits() {
        return vboxWorkCredits;
    }

    public void setVboxWorkCredits(VBox vboxWorkCredits) {
        this.vboxWorkCredits = vboxWorkCredits;
    }

    public VBox getVboxYSC() {
        return vboxYSC;
    }

    public void setVboxYSC(VBox vboxYSC) {
        this.vboxYSC = vboxYSC;
    }

    public VBox getVboxInitiation() {
        return vboxInitiation;
    }

    public void setVboxInitiation(VBox vboxInitiation) {
        this.vboxInitiation = vboxInitiation;
    }

    public VBox getVboxOther() {
        return vboxOther;
    }

    public void setVboxOther(VBox vboxOther) {
        this.vboxOther = vboxOther;
    }

    public VBox getVboxOtherCredit() {
        return vboxOtherCredit;
    }

    public void setVboxOtherCredit(VBox vboxOtherCredit) {
        this.vboxOtherCredit = vboxOtherCredit;
    }

    public VBox getVboxPositionCredit() {
        return vboxPositionCredit;
    }

    public void setVboxPositionCredit(VBox vboxPositionCredit) {
        this.vboxPositionCredit = vboxPositionCredit;
    }

    public VBox getVboxBeachFee() {
        return vboxBeachFee;
    }

    public void setVboxBeachFee(VBox vboxBeachFee) {
        this.vboxBeachFee = vboxBeachFee;
    }

    public VBox getVboxKayakFee() {
        return vboxKayakFee;
    }

    public void setVboxKayakFee(VBox vboxKayakFee) {
        this.vboxKayakFee = vboxKayakFee;
    }

    public VBox getVboxKayakShedFee() {
        return vboxKayakShedFee;
    }

    public void setVboxKayakShedFee(VBox vboxKayakShedFee) {
        this.vboxKayakShedFee = vboxKayakShedFee;
    }

    public VBox getVboxSailLoftFee() {
        return vboxSailLoftFee;
    }

    public void setVboxSailLoftFee(VBox vboxSailLoftFee) {
        this.vboxSailLoftFee = vboxSailLoftFee;
    }

    public VBox getVboxSailSchoolLoftFee() {
        return vboxSailSchoolLoftFee;
    }

    public void setVboxSailSchoolLoftFee(VBox vboxSailSchoolLoftFee) {
        this.vboxSailSchoolLoftFee = vboxSailSchoolLoftFee;
    }

    public VBox getVboxWetSlipFee() {
        return vboxWetSlipFee;
    }

    public void setVboxWetSlipFee(VBox vboxWetSlipFee) {
        this.vboxWetSlipFee = vboxWetSlipFee;
    }

    public VBox getVboxWinterStorageFee() {
        return vboxWinterStorageFee;
    }

    public void setVboxWinterStorageFee(VBox vboxWinterStorageFee) {
        this.vboxWinterStorageFee = vboxWinterStorageFee;
    }

    public VBox getVboxGateKeyFee() {
        return vboxGateKeyFee;
    }

    public void setVboxGateKeyFee(VBox vboxGateKeyFee) {
        this.vboxGateKeyFee = vboxGateKeyFee;
    }

    public VBox getVboxSailLoftKeyFee() {
        return vboxSailLoftKeyFee;
    }

    public void setVboxSailLoftKeyFee(VBox vboxSailLoftKeyFee) {
        this.vboxSailLoftKeyFee = vboxSailLoftKeyFee;
    }

    public VBox getVboxKayakShedKeyFee() {
        return vboxKayakShedKeyFee;
    }

    public void setVboxKayakShedKeyFee(VBox vboxKayakShedKeyFee) {
        this.vboxKayakShedKeyFee = vboxKayakShedKeyFee;
    }

    public VBox getVboxSailSchoolLoftKeyFee() {
        return vboxSailSchoolLoftKeyFee;
    }

    public void setVboxSailSchoolLoftKeyFee(VBox vboxSailSchoolLoftKeyFee) {
        this.vboxSailSchoolLoftKeyFee = vboxSailSchoolLoftKeyFee;
    }

    public VBox getVboxWorkCreditsFee() {
        return vboxWorkCreditsFee;
    }

    public void setVboxWorkCreditsFee(VBox vboxWorkCreditsFee) {
        this.vboxWorkCreditsFee = vboxWorkCreditsFee;
    }

    public VBox getVboxTitlePrice() {
        return vboxTitlePrice;
    }

    public void setVboxTitlePrice(VBox vboxTitlePrice) {
        this.vboxTitlePrice = vboxTitlePrice;
    }

    public VBox getVboxTitleTotal() {
        return vboxTitleTotal;
    }

    public void setVboxTitleTotal(VBox vboxTitleTotal) {
        this.vboxTitleTotal = vboxTitleTotal;
    }

    public VBox getVboxButtons() {
        return vboxButtons;
    }

    public void setVboxButtons(VBox vboxButtons) {
        this.vboxButtons = vboxButtons;
    }

    public VBox getVboxPink() {
        return vboxPink;
    }

    public void setVboxPink(VBox vboxPink) {
        this.vboxPink = vboxPink;
    }

    public VBox getVboxCommitButton() {
        return vboxCommitButton;
    }

    public void setVboxCommitButton(VBox vboxCommitButton) {
        this.vboxCommitButton = vboxCommitButton;
    }

    public TextField getYscTextField() {
        return yscTextField;
    }

    public void setYscTextField(TextField yscTextField) {
        this.yscTextField = yscTextField;
    }

    public TextField getDuesTextField() {
        return duesTextField;
    }

    public void setDuesTextField(TextField duesTextField) {
        this.duesTextField = duesTextField;
    }

    public TextField getOtherTextField() {
        return otherTextField;
    }

    public void setOtherTextField(TextField otherTextField) {
        this.otherTextField = otherTextField;
    }

    public TextField getInitiationTextField() {
        return initiationTextField;
    }

    public void setInitiationTextField(TextField initiationTextField) {
        this.initiationTextField = initiationTextField;
    }

    public TextField getWetslipTextField() {
        return wetslipTextField;
    }

    public void setWetslipTextField(TextField wetslipTextField) {
        this.wetslipTextField = wetslipTextField;
    }

    public TextField getOtherCreditTextField() {
        return otherCreditTextField;
    }

    public void setOtherCreditTextField(TextField otherCreditTextField) {
        this.otherCreditTextField = otherCreditTextField;
    }

    public Spinner<Integer> getBeachSpinner() {
        return beachSpinner;
    }

    public void setBeachSpinner(Spinner<Integer> beachSpinner) {
        this.beachSpinner = beachSpinner;
    }

    public Spinner<Integer> getKayakRackSpinner() {
        return kayakRackSpinner;
    }

    public void setKayakRackSpinner(Spinner<Integer> kayakRackSpinner) {
        this.kayakRackSpinner = kayakRackSpinner;
    }

    public Spinner<Integer> getKayakShedSpinner() {
        return kayakShedSpinner;
    }

    public void setKayakShedSpinner(Spinner<Integer> kayakShedSpinner) {
        this.kayakShedSpinner = kayakShedSpinner;
    }

    public Spinner<Integer> getSailLoftSpinner() {
        return sailLoftSpinner;
    }

    public void setSailLoftSpinner(Spinner<Integer> sailLoftSpinner) {
        this.sailLoftSpinner = sailLoftSpinner;
    }

    public Spinner<Integer> getSailSchoolLoftSpinner() {
        return sailSchoolLoftSpinner;
    }

    public void setSailSchoolLoftSpinner(Spinner<Integer> sailSchoolLoftSpinner) {
        this.sailSchoolLoftSpinner = sailSchoolLoftSpinner;
    }

    public Spinner<Integer> getWinterStorageSpinner() {
        return winterStorageSpinner;
    }

    public void setWinterStorageSpinner(Spinner<Integer> winterStorageSpinner) {
        this.winterStorageSpinner = winterStorageSpinner;
    }

    public Spinner<Integer> getWetSlipSpinner() {
        return wetSlipSpinner;
    }

    public void setWetSlipSpinner(Spinner<Integer> wetSlipSpinner) {
        this.wetSlipSpinner = wetSlipSpinner;
    }

    public Spinner<Integer> getGateKeySpinner() {
        return gateKeySpinner;
    }

    public void setGateKeySpinner(Spinner<Integer> gateKeySpinner) {
        this.gateKeySpinner = gateKeySpinner;
    }

    public Spinner<Integer> getSailLKeySpinner() {
        return sailLKeySpinner;
    }

    public void setSailLKeySpinner(Spinner<Integer> sailLKeySpinner) {
        this.sailLKeySpinner = sailLKeySpinner;
    }

    public Spinner<Integer> getKayakSKeySpinner() {
        return kayakSKeySpinner;
    }

    public void setKayakSKeySpinner(Spinner<Integer> kayakSKeySpinner) {
        this.kayakSKeySpinner = kayakSKeySpinner;
    }

    public Spinner<Integer> getSailSSLKeySpinner() {
        return sailSSLKeySpinner;
    }

    public void setSailSSLKeySpinner(Spinner<Integer> sailSSLKeySpinner) {
        this.sailSSLKeySpinner = sailSSLKeySpinner;
    }

    public Text getDuesText() {
        return duesText;
    }

    public Text getBeachText() {
        return beachText;
    }

    public Text getKayakRackText() {
        return kayakRackText;
    }

    public Text getKayakShedText() {
        return kayakShedText;
    }

    public Text getSailLoftText() {
        return sailLoftText;
    }

    public Text getSailSchoolLoftText() {
        return sailSchoolLoftText;
    }

    public Text getWetSlipText() {
        return wetSlipText;
    }

    public Text getWinterStorageText() {
        return winterStorageText;
    }

    public Text getYspText() {
        return yspText;
    }

    public Text getInitiationText() {
        return initiationText;
    }

    public Text getOtherFeeText() {
        return otherFeeText;
    }

    public Text getWorkCreditsText() {
        return workCreditsText;
    }

    public Text getGateKeyText() {
        return gateKeyText;
    }

    public Text getSailLKeyText() {
        return sailLKeyText;
    }

    public Text getKayakSKeyText() {
        return kayakSKeyText;
    }

    public Text getSailSSLKeyText() {
        return sailSSLKeyText;
    }

    public Text getOtherCreditText() {
        return otherCreditText;
    }

    public Text getPositionCreditText() {
        return positionCreditText;
    }

    public Text getWetslipTextFee() {
        return wetslipTextFee;
    }

    public Text getTotalFeesText() {
        return totalFeesText;
    }

    public Text getTotalCreditText() {
        return totalCreditText;
    }

    public Text getTotalPaymentText() {
        return totalPaymentText;
    }

    public Text getTotalBalanceText() {
        return totalBalanceText;
    }

    public void clearGridPane() {
        gridPane.getChildren().clear();
    }

    public Spinner<Integer> getKayakBeachRackSpinner() {
        return kayakBeachRackSpinner;
    }

    public Text getKayakBeachRackText() {
        return kayakBeachRackText;
    }

    public Button getButtonAddNote() {
        return buttonAddNote;
    }


    public static <T, E, F> int addCommittedRow(int row, GridPane gridPane, T name, E quantity, F total ) {
        gridPane.add((Node) name, 0, row, 1, 1);
        gridPane.add((Node) quantity, 1, row, 1, 1);
        gridPane.add((Node) total, 2, row, 1, 1);
        row++;
        return row;
    }

    public void populateCommitted() {
        int row = 0;
        gridPane.setHgap(100);
        row = addCommittedRow(row,gridPane,vboxTitleFee, vboxTitleQty, vboxTitleTotal);
        if(!invoice.getDues().equals("0.00"))
            row = addCommittedRow(row,gridPane,new Label("Dues:"), new Text(""), vboxDues);
        if(invoice.getBeach() != 0)
            row = addCommittedRow(row,gridPane,new Label("Beach Spot:"), new Text(String.valueOf(invoice.getBeach())), vboxBeach);
        if(invoice.getKayac_rack() != 0)
            row = addCommittedRow(row,gridPane,new Label("Kayak Rack:"), new Text(String.valueOf(invoice.getKayac_rack())), vboxKayak);
        if(invoice.getKayak_beach_rack() != 0)
            row = addCommittedRow(row,gridPane,new Label("Kayak Beach Rack:"), new Text(String.valueOf(invoice.getKayak_beach_rack())), vboxBeachKayak);
        if(invoice.getKayac_shed() != 0)
            row = addCommittedRow(row,gridPane,new Label("Kayak Shed:"), new Text(String.valueOf(invoice.getKayac_shed())), vboxKayakShed);
        if(invoice.getSail_loft() != 0)
            row = addCommittedRow(row,gridPane,new Label("Sail Loft:"), new Text(String.valueOf(invoice.getSail_loft())), vboxSailLoft);
        if(invoice.getSail_school_laser_loft() != 0)
            row = addCommittedRow(row,gridPane,new Label("Sail School Loft:"), new Text(String.valueOf(invoice.getSail_school_laser_loft())), vboxSailSchoolLoft);
        if(!invoice.getWet_slip().equals("0.00"))
            row = addCommittedRow(row,gridPane,new Label("Wet Slip:"), new Text("1"), vboxWetSlip);
        if(invoice.getWinter_storage() != 0)
            row = addCommittedRow(row,gridPane,new Label("Winter Storage:"), new Text(String.valueOf(invoice.getWinter_storage())), vboxWinterStorage);
        if(invoice.getExtra_key() != 0)
            row = addCommittedRow(row,gridPane,new Label("Gate Key:"), new Text(String.valueOf(invoice.getExtra_key())), vboxGateKey);
        if(invoice.getSail_loft_key() != 0)
            row = addCommittedRow(row,gridPane,new Label("Sail Loft Key:"), new Text(String.valueOf(invoice.getSail_loft_key())), vboxSailLoftKey);
        if(invoice.getKayac_shed_key() != 0)
            row = addCommittedRow(row,gridPane,new Label("Kayak Shed Key:"), new Text(String.valueOf(invoice.getKayac_shed_key())), vboxKayakShedKey);
        if(invoice.getSail_loft_key() != 0)
            row = addCommittedRow(row,gridPane,new Label("Sail School Loft Key:"), new Text(String.valueOf(invoice.getSail_school_loft_key())), vboxSailSchoolLoftKey);
        if(!invoice.getYsc_donation().equals("0.00"))
            row = addCommittedRow(row,gridPane,new Label("YSP Donation:"), new Text(""), vboxYSC);
        if(!invoice.getInitiation().equals("0.00"))
            row = addCommittedRow(row,gridPane,new Label("Initiation:"), new Text(""), vboxInitiation);
        if(!invoice.getOther().equals("0.00"))
            // TODO when first introduced "other" does not show the amount, but does so when tab is reopened
            row = addCommittedRow(row,gridPane,new Label("Other Fee:"), new Text(""), vboxOther);
        if(invoice.getWork_credit() != 0)
            row = addCommittedRow(row,gridPane,new Label("Work Credits:"), new Text(String.valueOf(invoice.getWork_credit())), vboxWorkCredits);
        if(!invoice.getOther_credit().equals("0.00"))
            row = addCommittedRow(row,gridPane,new Label("Other Credit:"), new Text(""), vboxOtherCredit);
        if(!invoice.getOfficer_credit().equals("0.00"))
            row = addCommittedRow(row,gridPane,new Label("Position Credit:"), new Text(""), vboxPositionCredit);
        row++;
        gridPane.add(separator, 0, row, 3, 1);
        row++;
            row = addCommittedRow(row,gridPane,new Label("Total Fees:"), new Text(""), vboxTotalFee);
            row = addCommittedRow(row,gridPane,new Label("Total Credit:"), new Text(""), vboxTotalCredit);
            row = addCommittedRow(row,gridPane,new Label("Payment:"), new Text(""), vboxTotalPayment);
            row = addCommittedRow(row,gridPane,new Label("Balance:"), new Text(""), vboxTotalBalance);
        row++;
        // TODO This will only print the last payment, if there are 2 payments may want to add a list here
        PaymentDTO thisPayment = SqlPayment.getPayment(invoice.getMoney_id());
        gridPane.add(new Text("Payment Date: " + thisPayment.getPaymentDate()), 0,row,3,1);
        row++;
        // TODO Make it so if you click on the deposit number the deposit window will open up to that deposit
        gridPane.add(new Text("Deposit Number: " + invoice.getBatch()) , 0, row, 1, 2);
        gridPane.add(vboxCommitButton , 2, row, 1, 1);
    }

    public static <T, E, F, G, H> int addUnCommittedRow(int row, GridPane gridPane, T col1, E col2, F col3, G col4, H col5) {
        gridPane.add((Node) col1, 0, row, 1, 1);
        gridPane.add((Node) col2, 1, row, 1, 1);
        gridPane.add((Node) col3, 2, row, 1, 1);
        gridPane.add((Node) col4, 3, row, 1, 1);
        gridPane.add((Node) col5, 4, row, 1, 1);
        row++;
        return row;
    }

    public void populateUncommitted() {
        int row = 0;
        gridPane.setHgap(25);
        Region spacer = new Region();
        spacer.setPrefHeight(25);
        row = addUnCommittedRow(row, gridPane, vboxTitleFee, new Text(""), new Text(""), vboxTitlePrice, vboxTitleTotal);
        row = addUnCommittedRow(row, gridPane, new Label("Dues:"), duesTextField, new Text(""), new Label(""), vboxDues);
        row = addUnCommittedRow(row, gridPane, new Label("Beach Spot:"), beachSpinner, new Label("X"), vboxBeachFee, vboxBeach);
        row = addUnCommittedRow(row, gridPane, new Label("Kayak Rack:"), kayakRackSpinner, new Label("X"), vboxKayakFee, vboxKayak);

        row = addUnCommittedRow(row, gridPane, new Label("Kayak Beach Rack:"), kayakBeachRackSpinner, new Label("X"), vboxBeachKayakFee, vboxBeachKayak);

        row = addUnCommittedRow(row, gridPane, new Label("Kayak Shed:"), kayakShedSpinner, new Label("X"), vboxKayakShedFee, vboxKayakShed);
        row = addUnCommittedRow(row, gridPane, new Label("Sail Loft:"), sailLoftSpinner,new Label("X"), vboxSailLoftFee, vboxSailLoft);
        row = addUnCommittedRow(row, gridPane, new Label("Sail School Loft:"), sailSchoolLoftSpinner, new Label("X"), vboxSailSchoolLoftFee, vboxSailSchoolLoft);
        row = addUnCommittedRow(row, gridPane, new Label("Wet Slip:"), wetSlipSpinner, new Label("X"), vboxWetSlipFee, vboxWetSlip);
        row = addUnCommittedRow(row, gridPane, new Label("Winter Storage:"), winterStorageSpinner, new Label("X"), vboxWinterStorageFee, vboxWinterStorage);
        row = addUnCommittedRow(row, gridPane, new Label("Gate Key:"), gateKeySpinner, new Label("X"), vboxGateKeyFee, vboxGateKey);
        row = addUnCommittedRow(row, gridPane, new Label("Sail Loft Key:"), sailLKeySpinner, new Label("X"), vboxSailLoftKeyFee, vboxSailLoftKey);
        row = addUnCommittedRow(row, gridPane, new Label("Kayak Shed Key:"), kayakSKeySpinner, new Label("X"), vboxKayakShedKeyFee, vboxKayakShedKey);
        row = addUnCommittedRow(row, gridPane, new Label("Sail School Loft Key:"), sailSSLKeySpinner, new Label("X"), vboxSailSchoolLoftKeyFee, vboxSailSchoolLoftKey);
        row = addUnCommittedRow(row, gridPane, new Label("YSP Donation:"), yscTextField, new Label(""), new Label(""), vboxYSC);
        row = addUnCommittedRow(row, gridPane, new Label("Initiation:"), initiationTextField, new Label(""), new Label(""), vboxInitiation);
        row = addUnCommittedRow(row, gridPane, new Label("Other Fee:"), otherTextField, new Label(""), new Label(""), vboxOther);
        row = addUnCommittedRow(row, gridPane, new Label("Work Credits:"), comboBox, new Label("X"), vboxWorkCreditsFee, vboxWorkCredits);
        row = addUnCommittedRow(row, gridPane, new Label("Other Credit:"), otherCreditTextField, new Label(""), new Label(""), vboxOtherCredit);
        row = addUnCommittedRow(row, gridPane, new Label("Position Credit:"), spacer, new Label(""), new Label(""), vboxPositionCredit);

        row++;
        gridPane.add(new Label(""), 0, row, 5, 1);
        // Table
        row++;
        gridPane.add(vboxPink, 0, row, 4, 1);
        gridPane.add(vboxButtons, 4, row, 1, 1);
        // spacer
        row++;
        gridPane.add(new Label(""), 0, row, 5, 1);
        // final portion
        row++;
        gridPane.add(new Label("Total Fees:"), 0, row, 1, 1);
        gridPane.add(vboxTotalFee, 1, row, 3, 1);
        gridPane.add(vboxCommitButton, 4, row, 1, 4);
        row++;
        gridPane.add(new Label("Total Credit:"), 0, row, 3, 1);
        gridPane.add(vboxTotalCredit, 1, row, 3, 1);
        row++;
        gridPane.add(new Label("Payment:"), 0, row, 3, 1);
        gridPane.add(vboxTotalPayment, 1, row, 3, 1);
        row++;
        gridPane.add(new Label("Balance:"), 0, row, 3, 1);
        gridPane.add(vboxTotalBalance, 1, row, 3, 1);
        row++;
        gridPane.add(new Label(""), 0, row, 5, 1);
    }
}
