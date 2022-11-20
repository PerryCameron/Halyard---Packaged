//package com.ecsail.structures;
//
//import com.ecsail.gui.boxes.invoice.HBoxInvoice;
//import com.ecsail.gui.boxes.invoice.HBoxInvoiceLine;
//import com.ecsail.sql.select.SqlInvoiceWidget;
//import com.ecsail.sql.select.SqlPayment;
//import javafx.geometry.Insets;
//import javafx.geometry.Orientation;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//public class InvoiceDTO {
//
//    Map<String, HBox> widgetMap = new HashMap<>();
//
////    Map<String, Spinner<Integer>> spinnerMap = new HashMap<>();
////
////    Map<String, Text> textMap = new HashMap<>();
//
//    ArrayList<InvoiceWidget> theseWidgets = SqlInvoiceWidget.getInvoiceWidgets();
//    private final MoneyDTO invoice;
//    private final GridPane gridPane;
//
//
//    private final Text duesText;
//    private final Text beachText;
//    private final Text kayakRackText;
//    private final Text kayakBeachRackText;
//    private final Text kayakShedText;
//    private final Text sailLoftText;
//    private final Text sailSchoolLoftText;
//    private final Text wetSlipText;
//    private final Text winterStorageText;
//    private final Text yspText;
//    private final Text initiationText;
//    private final Text otherFeeText;
//    private final Text workCreditsText;
//    private final Text gateKeyText;
//    private final Text sailLKeyText;
//    private final Text kayakSKeyText;
//    private final Text sailSSLKeyText;
//    private final Text otherCreditText;
//    private final Text positionCreditText;
//    private final Text wetslipTextFee;
//
//    private final Text totalFeesText;
//    private final Text totalCreditText;
//    private final Text totalPaymentText;
//    private final Text totalBalanceText;
//
//
//    // VBoxes for totals
//    private final VBox vboxDues;
//    private final VBox vboxBeach;
//    private final VBox vboxKayak;
//    private final VBox vboxBeachKayak;
//    private final VBox vboxKayakShed;
//    private final VBox vboxSailLoft;
//    private final VBox vboxSailSchoolLoft;
//    private final VBox vboxWetSlip;
//    private final VBox vboxWinterStorage;
//    private final VBox vboxGateKey;
//    private final VBox vboxSailLoftKey;
//    private final VBox vboxKayakShedKey;
//    private final VBox vboxSailSchoolLoftKey;
//    private final VBox vboxWorkCredits;
//    private final VBox vboxYSC;
//    private final VBox vboxInitiation;
//    private final VBox vboxOther;
//    private final VBox vboxOtherCredit;
//    private final VBox vboxPositionCredit;
//
//    // VBoxes for multipliers
//    private final VBox vboxBeachFee;
//    private final VBox vboxKayakFee;
//    private final VBox vboxBeachKayakFee;
//    private final VBox vboxKayakShedFee;
//    private final VBox vboxSailLoftFee;
//    private final VBox vboxSailSchoolLoftFee;
//    private final VBox vboxWetSlipFee;
//    private final VBox vboxWinterStorageFee;
//    private final VBox vboxGateKeyFee;
//    private final VBox vboxSailLoftKeyFee;
//    private final VBox vboxKayakShedKeyFee;
//    private final VBox vboxSailSchoolLoftKeyFee;
//    private final VBox vboxWorkCreditsFee;
//
//
//    private final VBox vboxTitlePrice;
//    private final VBox vboxTitleTotal;
//    private final VBox vboxTitleFee;
//    private final VBox vboxTitleQty;
//
//    private final VBox vboxTotalFee;
//    private final VBox vboxTotalCredit;
//    private final VBox vboxTotalPayment;
//    private final VBox vboxTotalBalance;
//
//    private final VBox vboxButtons;
//    private VBox vboxPink; // this creates a pink border around the table
//    private final VBox vboxCommitButton;
//
//    private final Button buttonAdd;
//    private final Button buttonDelete;
//    private final Button commitButton;
//    private final Button buttonAddNote;
//    private final CheckBox renewCheckBox;
//    private final ComboBox<Integer> comboBox;
//
////    private final DefinedFeeDTO definedFees;
//    Separator separator = new Separator(Orientation.HORIZONTAL);
//
//    public InvoiceDTO(MoneyDTO invoice, DefinedFeeDTO definedFees, TableView<PaymentDTO> paymentTableView) {
//
//        for(InvoiceWidget i: theseWidgets) {
//            widgetMap.put(i.getObjectName(), new HBoxInvoiceLine(i));
//        }
//
//        Text text1 = new Text("Fee");
//        Text text2 = new Text("Price");
//        Text text3 = new Text("Total");
//        Text text4 = new Text("Qty");
//        this.comboBox = new ComboBox<>();
//        this.invoice = invoice;
//        this.buttonAdd = new Button("Add");
//        this.buttonDelete = new Button("Delete");
//        this.commitButton = new Button("Commit");
//        this.buttonAddNote = new Button("Add Note");
//        this.renewCheckBox = new CheckBox("Renew");
////        this.definedFees = definedFees;
//        this.gridPane = new GridPane();
//
//

//

//
//        // VBoxes for multipliers
//        this.vboxBeachFee = new VBox();
//        this.vboxKayakFee = new VBox();
//        this.vboxBeachKayakFee = new VBox();
//        this.vboxKayakShedFee = new VBox();
//        this.vboxSailLoftFee = new VBox();
//        this.vboxSailSchoolLoftFee = new VBox();
//        this.vboxWetSlipFee = new VBox();
//        this.vboxWinterStorageFee = new VBox();
//        this.vboxGateKeyFee = new VBox();
//        this.vboxSailLoftKeyFee = new VBox();
//        this.vboxKayakShedKeyFee = new VBox();
//        this.vboxSailSchoolLoftKeyFee = new VBox();
//        this.vboxWorkCreditsFee = new VBox();
//        this.vboxTitlePrice = new VBox();
//        this.vboxTitleTotal = new VBox();
//        this.vboxTitleFee = new VBox();
//        this.vboxTitleQty = new VBox();
//        this.vboxTotalFee = new VBox();
//        this.vboxTotalCredit = new VBox();
//        this.vboxTotalPayment = new VBox();
//        this.vboxTotalBalance = new VBox();
//        this.vboxButtons = new VBox();
//        this.vboxPink = new VBox(); // this creates a pink border around the table
//        this.vboxCommitButton = new VBox();
//
//        // fill comboBox
//        for(int i = 0; i < 100; i++) {
//            comboBox.getItems().add(i);
//        }
//        comboBox.getSelectionModel().selectFirst();
//
//
//        Font font = Font.font("Verdana", FontWeight.BOLD, 16);
//        // ATTRIBUTES
//
//        vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
//        vboxPink.setId("box-pink");
//        HBox.setHgrow(vboxPink, Priority.ALWAYS);
//        vboxButtons.setSpacing(5);
//        vboxCommitButton.setSpacing(10);
//
//        HBox.setHgrow(gridPane,Priority.ALWAYS);
//
//        gridPane.setVgap(5);

//
//
//        renewCheckBox.setSelected(true);
//        buttonAdd.setPrefWidth(60);
//
//        buttonDelete.setPrefWidth(60);
//
//        vboxDues.getChildren().add(duesText);
//        vboxDues.setAlignment(Pos.CENTER_RIGHT);
//        vboxBeach.getChildren().add(beachText);
//        vboxBeach.setAlignment(Pos.CENTER_RIGHT);
//        vboxKayak.getChildren().add(kayakRackText);
//        vboxKayak.setAlignment(Pos.CENTER_RIGHT);
//        vboxBeachKayak.getChildren().add(kayakBeachRackText);
//        vboxBeachKayak.setAlignment(Pos.CENTER_RIGHT);
//        vboxKayakShed.getChildren().add(kayakShedText);
//        vboxKayakShed.setAlignment(Pos.CENTER_RIGHT);
//        vboxSailLoft.getChildren().add(sailLoftText);
//        vboxSailLoft.setAlignment(Pos.CENTER_RIGHT);
//        vboxSailSchoolLoft.getChildren().add(sailSchoolLoftText);
//        vboxSailSchoolLoft.setAlignment(Pos.CENTER_RIGHT);
//        vboxWetSlip.getChildren().add(wetSlipText);
//        vboxWetSlip.setAlignment(Pos.CENTER_RIGHT);
//        vboxWinterStorage.getChildren().add(winterStorageText);
//        vboxWinterStorage.setAlignment(Pos.CENTER_RIGHT);
//        vboxGateKey.getChildren().add(gateKeyText);
//        vboxGateKey.setAlignment(Pos.CENTER_RIGHT);
//        vboxSailLoftKey.getChildren().add(sailLKeyText);
//        vboxSailLoftKey.setAlignment(Pos.CENTER_RIGHT);
//        vboxKayakShedKey.getChildren().add(kayakSKeyText);
//        vboxKayakShedKey.setAlignment(Pos.CENTER_RIGHT);
//        vboxSailSchoolLoftKey.getChildren().add(sailSSLKeyText);
//        vboxSailSchoolLoftKey.setAlignment(Pos.CENTER_RIGHT);
//        vboxPositionCredit.getChildren().add(positionCreditText);
//        vboxPositionCredit.setAlignment(Pos.CENTER_RIGHT);
//        vboxWorkCredits.getChildren().add(workCreditsText);
//        vboxWorkCredits.setAlignment(Pos.CENTER_RIGHT);
//
//        vboxYSC.getChildren().add(yspText);
//        vboxYSC.setAlignment(Pos.CENTER_RIGHT);
//        vboxInitiation.getChildren().add(initiationText);
//        vboxInitiation.setAlignment(Pos.CENTER_RIGHT);
//        vboxOther.getChildren().add(otherFeeText);
//        vboxOther.setAlignment(Pos.CENTER_RIGHT);
//        vboxOtherCredit.getChildren().add(otherCreditText);
//        vboxOtherCredit.setAlignment(Pos.CENTER_RIGHT);
//
//        vboxBeachFee.getChildren().add(new Text(String.valueOf(definedFees.getBeach())));
//        vboxBeachFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxKayakFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_rack())));
//        vboxKayakFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxBeachKayakFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_beach_rack())));
//        vboxBeachKayakFee.setAlignment(Pos.CENTER_RIGHT);
//
//        vboxKayakShedFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_shed())));
//        vboxKayakShedFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxSailLoftFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_loft())));
//        vboxSailLoftFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxSailSchoolLoftFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_school_laser_loft())));
//        vboxSailSchoolLoftFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxWinterStorageFee.getChildren().add(new Text(String.valueOf(definedFees.getWinter_storage())));
//        vboxWinterStorageFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxGateKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getMain_gate_key())));
//        vboxGateKeyFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxSailLoftKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_loft_key())));
//        vboxSailLoftKeyFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxKayakShedKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_shed_key())));
//        vboxKayakShedKeyFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxSailSchoolLoftKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_school_loft_key())));
//        vboxSailSchoolLoftKeyFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxWorkCreditsFee.getChildren().add(new Text(String.valueOf(definedFees.getWork_credit())));
//        vboxWorkCreditsFee.setAlignment(Pos.CENTER_RIGHT);
//        vboxTitlePrice.setAlignment(Pos.CENTER_RIGHT);
//        vboxTitleTotal.setAlignment(Pos.CENTER_RIGHT);
//        vboxWetSlipFee.getChildren().add(wetslipTextFee);
//        vboxWetSlipFee.setAlignment(Pos.CENTER_RIGHT);
//

//    }
//

//
//    public static <T, E, F> int addCommittedRow(int row, GridPane gridPane, T name, E quantity, F total ) {
//        gridPane.add((Node) name, 0, row, 1, 1);
//        gridPane.add((Node) quantity, 1, row, 1, 1);
//        gridPane.add((Node) total, 2, row, 1, 1);
//        row++;
//        return row;
//    }
//
//    public void populateCommitted() {
//        int row = 0;
//        gridPane.setHgap(100);
//        row = addCommittedRow(row,gridPane,vboxTitleFee, vboxTitleQty, vboxTitleTotal);
//        if(!invoice.getDues().equals("0.00"))
//            row = addCommittedRow(row,gridPane,new Label("Dues:"), new Text(""), vboxDues);
//        if(invoice.getBeach() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Beach Spot:"), new Text(String.valueOf(invoice.getBeach())), vboxBeach);
//        if(invoice.getKayac_rack() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Kayak Rack:"), new Text(String.valueOf(invoice.getKayac_rack())), vboxKayak);
//        if(invoice.getKayak_beach_rack() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Kayak Beach Rack:"), new Text(String.valueOf(invoice.getKayak_beach_rack())), vboxBeachKayak);
//        if(invoice.getKayac_shed() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Kayak Shed:"), new Text(String.valueOf(invoice.getKayac_shed())), vboxKayakShed);
//        if(invoice.getSail_loft() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Sail Loft:"), new Text(String.valueOf(invoice.getSail_loft())), vboxSailLoft);
//        if(invoice.getSail_school_laser_loft() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Sail School Loft:"), new Text(String.valueOf(invoice.getSail_school_laser_loft())), vboxSailSchoolLoft);
//        if(!invoice.getWet_slip().equals("0.00"))
//            row = addCommittedRow(row,gridPane,new Label("Wet Slip:"), new Text("1"), vboxWetSlip);
//        if(invoice.getWinter_storage() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Winter Storage:"), new Text(String.valueOf(invoice.getWinter_storage())), vboxWinterStorage);
//        if(invoice.getExtra_key() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Gate Key:"), new Text(String.valueOf(invoice.getExtra_key())), vboxGateKey);
//        if(invoice.getSail_loft_key() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Sail Loft Key:"), new Text(String.valueOf(invoice.getSail_loft_key())), vboxSailLoftKey);
//        if(invoice.getKayac_shed_key() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Kayak Shed Key:"), new Text(String.valueOf(invoice.getKayac_shed_key())), vboxKayakShedKey);
//        if(invoice.getSail_loft_key() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Sail School Loft Key:"), new Text(String.valueOf(invoice.getSail_school_loft_key())), vboxSailSchoolLoftKey);
//        if(!invoice.getYsc_donation().equals("0.00"))
//            row = addCommittedRow(row,gridPane,new Label("YSP Donation:"), new Text(""), vboxYSC);
//        if(!invoice.getInitiation().equals("0.00"))
//            row = addCommittedRow(row,gridPane,new Label("Initiation:"), new Text(""), vboxInitiation);
//        if(!invoice.getOther().equals("0.00"))
//            // TODO when first introduced "other" does not show the amount, but does so when tab is reopened
//            row = addCommittedRow(row,gridPane,new Label("Other Fee:"), new Text(""), vboxOther);
//        if(invoice.getWork_credit() != 0)
//            row = addCommittedRow(row,gridPane,new Label("Work Credits:"), new Text(String.valueOf(invoice.getWork_credit())), vboxWorkCredits);
//        if(!invoice.getOther_credit().equals("0.00"))
//            row = addCommittedRow(row,gridPane,new Label("Other Credit:"), new Text(""), vboxOtherCredit);
//        if(!invoice.getOfficer_credit().equals("0.00"))
//            row = addCommittedRow(row,gridPane,new Label("Position Credit:"), new Text(""), vboxPositionCredit);
//        row++;
//        gridPane.add(separator, 0, row, 3, 1);
//        row++;
//            row = addCommittedRow(row,gridPane,new Label("Total Fees:"), new Text(""), vboxTotalFee);
//            row = addCommittedRow(row,gridPane,new Label("Total Credit:"), new Text(""), vboxTotalCredit);
//            row = addCommittedRow(row,gridPane,new Label("Payment:"), new Text(""), vboxTotalPayment);
//            row = addCommittedRow(row,gridPane,new Label("Balance:"), new Text(""), vboxTotalBalance);
//        row++;
//        // TODO This will only print the last payment, if there are 2 payments may want to add a list here
//        PaymentDTO thisPayment = SqlPayment.getPayment(invoice.getMoney_id());
//        gridPane.add(new Text("Payment Date: " + thisPayment.getPaymentDate()), 0,row,3,1);
//        row++;
//        // TODO Make it so if you click on the deposit number the deposit window will open up to that deposit
//        gridPane.add(new Text("Deposit Number: " + invoice.getBatch()) , 0, row, 1, 1);
//        gridPane.add(buttonAddNote, 1, row,1, 1);
//        gridPane.add(vboxCommitButton , 2, row, 1, 1);
//    }
//
//    public static <T, E, F, G, H> int addUnCommittedRow(int row, GridPane gridPane, T col1, E col2, F col3, G col4, H col5) {
//        gridPane.add((Node) col1, 0, row, 1, 1);
//        gridPane.add((Node) col2, 1, row, 1, 1);
//        gridPane.add((Node) col3, 2, row, 1, 1);
//        gridPane.add((Node) col4, 3, row, 1, 1);
//        gridPane.add((Node) col5, 4, row, 1, 1);
//        row++;
//        return row;
//    }
//
//    public void populateUncommitted() {
//        int row = 0;
//        gridPane.setHgap(25);
//        Region spacer = new Region();
//        spacer.setPrefHeight(25);
//        row = addUnCommittedRow(row, gridPane, vboxTitleFee, new Text(""), new Text(""), vboxTitlePrice, vboxTitleTotal);
//        row = addUnCommittedRow(row, gridPane, new Label("Dues:"), textFieldMap.get("Dues"), new Text(""), new Label(""), vboxDues);
//        row = addUnCommittedRow(row, gridPane, new Label("Beach Spot:"), spinnerMap.get("Beach Spot"), new Label("X"), vboxBeachFee, vboxBeach);
//        row = addTitledPane(row, gridPane);
//        row = addUnCommittedRow(row, gridPane, new Label("Kayak Rack:"), spinnerMap.get("Kayak Rack"), new Label("X"), vboxKayakFee, vboxKayak);
//        row = addUnCommittedRow(row, gridPane, new Label("Kayak Beach Rack:"), spinnerMap.get("Kayak Beach Rack"), new Label("X"), vboxBeachKayakFee, vboxBeachKayak);
//        row = addUnCommittedRow(row, gridPane, new Label("Kayak Shed:"), spinnerMap.get("Kayak Shed"), new Label("X"), vboxKayakShedFee, vboxKayakShed);
//        row = addUnCommittedRow(row, gridPane, new Label("Sail Loft:"), spinnerMap.get("Sail Loft"),new Label("X"), vboxSailLoftFee, vboxSailLoft);
//        row = addUnCommittedRow(row, gridPane, new Label("Sail School Loft:"), spinnerMap.get("Sail School Loft"), new Label("X"), vboxSailSchoolLoftFee, vboxSailSchoolLoft);
//        row = addUnCommittedRow(row, gridPane, new Label("Wet Slip:"), spinnerMap.get("Wet Slip"), new Label("X"), vboxWetSlipFee, vboxWetSlip);
//        row = addUnCommittedRow(row, gridPane, new Label("Winter Storage:"), spinnerMap.get("Winter Storage"), new Label("X"), vboxWinterStorageFee, vboxWinterStorage);
//        row = addUnCommittedRow(row, gridPane, new Label("Gate Key:"), spinnerMap.get("Gate Key"), new Label("X"), vboxGateKeyFee, vboxGateKey);
//        row = addUnCommittedRow(row, gridPane, new Label("Sail Loft Key:"), spinnerMap.get("Sail Loft Key"), new Label("X"), vboxSailLoftKeyFee, vboxSailLoftKey);
//        row = addUnCommittedRow(row, gridPane, new Label("Kayak Shed Key:"), spinnerMap.get("Kayak Shed Key"), new Label("X"), vboxKayakShedKeyFee, vboxKayakShedKey);
//        row = addUnCommittedRow(row, gridPane, new Label("Sail School Key:"), spinnerMap.get("Sail School Key"), new Label("X"), vboxSailSchoolLoftKeyFee, vboxSailSchoolLoftKey);
//        row = addUnCommittedRow(row, gridPane, new Label("YSP Donation:"), textFieldMap.get("YSP Donation"), new Label(""), new Label(""), vboxYSC);
//        row = addUnCommittedRow(row, gridPane, new Label("Initiation:"), textFieldMap.get("Initiation"), new Label(""), new Label(""), vboxInitiation);
//        row = addUnCommittedRow(row, gridPane, new Label("Other Fee:"), textFieldMap.get("Other Fee"), new Label(""), new Label(""), vboxOther);
//        row = addUnCommittedRow(row, gridPane, new Label("Work Credits:"), comboBox, new Label("X"), vboxWorkCreditsFee, vboxWorkCredits);
//        row = addUnCommittedRow(row, gridPane, new Label("Other Credit:"), textFieldMap.get("Other Credit"), new Label(""), new Label(""), vboxOtherCredit);
//        row = addUnCommittedRow(row, gridPane, new Label("Position Credit:"), spacer, new Label(""), new Label(""), vboxPositionCredit);
//
//        row++;
//        gridPane.add(new Label(""), 0, row, 5, 1);
//        // Table
//        row++;
//        gridPane.add(vboxPink, 0, row, 4, 1);
//        gridPane.add(vboxButtons, 4, row, 1, 1);
//        // spacer
//        row++;
//        gridPane.add(new Label(""), 0, row, 5, 1);
//        // final portion
//        row++;
//        gridPane.add(new Label("Total Fees:"), 0, row, 1, 1);
//        gridPane.add(vboxTotalFee, 1, row, 3, 1);
//        gridPane.add(vboxCommitButton, 4, row, 1, 4);
//        row++;
//        gridPane.add(new Label("Total Credit:"), 0, row, 3, 1);
//        gridPane.add(vboxTotalCredit, 1, row, 3, 1);
//        row++;
//        gridPane.add(new Label("Payment:"), 0, row, 3, 1);
//        gridPane.add(vboxTotalPayment, 1, row, 3, 1);
//        row++;
//        gridPane.add(new Label("Balance:"), 0, row, 3, 1);
//        gridPane.add(vboxTotalBalance, 1, row, 3, 1);
//        row++;
//        gridPane.add(new Label(""), 0, row, 5, 1);
//    }
//
//    private int addTitledPane(int row, GridPane gridPane) {
//        TitledPane titledPane = new TitledPane();
//        titledPane.setText("Kayak");
//        gridPane.add(titledPane, 0, row, 5, 1);
//        row++;
//        return row;
//    }
//}
