package com.ecsail.gui.tabs.deposits;

import com.ecsail.sql.select.SqlDbInvoice;
import com.ecsail.sql.select.SqlInvoiceItem;
import com.ecsail.structures.InvoiceItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.ecsail.BaseApplication.selectedYear;

public class VboxControls extends VBox {
    private final TabDeposits tabDeposits;
    private final ArrayList<String> invoiceItemTypes;
    private final VBox vboxSumItems = new VBox();
    private final VBox vBoxSumItemsInner = new VBox();

    private final Text numberOfRecords = new Text();

    public VboxControls(TabDeposits tabDeposits) {
        this.tabDeposits = tabDeposits;
        this.invoiceItemTypes = SqlDbInvoice.getInvoiceCategoriesByYear(tabDeposits.getSelectedYear());
        this.numberOfRecords.setText(String.valueOf(tabDeposits.getInvoices().size()));

        var vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
        var vboxBlue = new VBox();
        var vboxPink = new VBox(); // this creates a pink border around the table
        var mainHBox = new HBox(); // this separates table content from controls
        var controlsVBox = new VBox(); // inner grey box
        var controlsHBox = new HBox(); // outer blue box
        var batchNumberHBox = new HBox(); // holds spinner and label
        var buttonHBox = new HBox(); // holds buttons
        var yearBatchHBox = new HBox(); // holds spinner and batchNumberHBox

        var remaindingRenewalHBox = new HBox();
        var selectionHBox = new HBox();
        var numberOfRecordsHBox = new HBox();
        var comboBoxHBox = new HBox();
        var nonRenewed = new Text("0");
        var recordsLabel = new Text("Records:");
        ObservableList<String> options = FXCollections.observableArrayList("Show All", "Show Selected");
        final var comboBox = new ComboBox<>(options);
        var gridPane = new GridPane();
        var refreshButton = new Button("Refresh");
        var printPdfButton = new Button("Print PDF");
        var depositDatePicker = new DatePicker();
        final Spinner<Integer> batchSpinner = new Spinner<>();

        controlsHBox.setPadding(new Insets(5, 5, 5, 5));
        controlsVBox.setPadding(new Insets(15, 5, 5, 5));
        vboxBlue.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink fram around table
        selectionHBox.setPadding(new Insets(0, 0, 0, 37));
        comboBoxHBox.setPadding(new Insets(0, 0, 0, 37));
        this.setPadding(new Insets(15, 5, 5, 5));
        vboxSumItems.setPadding(new Insets(15,0,20,10));
        vBoxSumItemsInner.setPadding(new Insets(5,5,10,5));
        vboxSumItems.setSpacing(5);
        vBoxSumItemsInner.setSpacing(5);
        vBoxSumItemsInner.setId("box-background-light");
        numberOfRecords.setId("invoice-text-number");
        recordsLabel.setId("invoice-text-label");


        controlsVBox.setPrefWidth(342);
        depositDatePicker.setPrefWidth(123);
        this.setPrefWidth(350);
        //vboxGrey.setPrefHeight(688);

        controlsVBox.setSpacing(10);
        mainHBox.setSpacing(5);
        batchNumberHBox.setSpacing(5);
        buttonHBox.setSpacing(10);
        numberOfRecordsHBox.setSpacing(5);
        selectionHBox.setSpacing(30);
        yearBatchHBox.setSpacing(15);
        this.setSpacing(10);

        numberOfRecordsHBox.setAlignment(Pos.CENTER);


        comboBox.setValue("Show All");

        final var yearSpinner = new Spinner<Integer>();
        var wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970,
                Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
        yearSpinner.setValueFactory(wetSlipValueFactory);
        yearSpinner.setPrefWidth(95);
        yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
//                selectedYear = yearSpinner.getEditor().getText();
//                batchSpinner.getValueFactory().setValue(SqlMoney.getBatchNumber(selectedYear)); // set batch to last																					// year
//                paidDues.clear();
//                paidDues.addAll(SqlDeposit.getPaidDues(selectedYear));
//                currentDefinedFee.clear();
//                currentDefinedFee = SqlDefinedFee.getDefinedFeeByYear(selectedYear);
//                summaryTotals.setDepositNumber(Integer.parseInt(batchSpinner.getEditor().getText()));
//                refreshButton.fire();
            }
        });

        var batchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
//                summaryTotals.getDepositNumber()
                1
        ); // 0 to batch, display batch
        batchSpinner.setValueFactory(batchValueFactory);
        batchSpinner.setPrefWidth(60);
        batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                batchSpinner.increment(0); // won't change value, but will commit editor
//                // create batch if it doesn't already exist
//                summaryTotals.setDepositNumber(Integer.parseInt(batchSpinner.getEditor().getText()));
//                checkForDepositAndCreateIfNotExist();
//                refreshButton.fire();
            }
        });

        VBox.setVgrow(vboxBlue, Priority.ALWAYS);
        VBox.setVgrow(vboxPink, Priority.ALWAYS);
        VBox.setVgrow(vboxGrey, Priority.ALWAYS);

        batchNumberHBox.setAlignment(Pos.CENTER);
        yearBatchHBox.setAlignment(Pos.CENTER);
        vboxSumItems.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.CENTER);
        remaindingRenewalHBox.setAlignment(Pos.CENTER);

        // gets all invoice items
        getInvoiceItemRows(tabDeposits.getSelectedYear());

        comboBoxHBox.getChildren().add(comboBox);
        numberOfRecordsHBox.getChildren().addAll(new Text("Records:"), numberOfRecords);
        selectionHBox.getChildren().addAll(depositDatePicker, numberOfRecordsHBox);
        remaindingRenewalHBox.getChildren().addAll(new Text("Memberships not renewed: "), nonRenewed);
        batchNumberHBox.getChildren().addAll(new Label("Deposit Number"), batchSpinner);
        yearBatchHBox.getChildren().addAll(yearSpinner, batchNumberHBox);
        buttonHBox.getChildren().addAll(refreshButton, printPdfButton);
        controlsHBox.getChildren().add(controlsVBox);
        vboxSumItems.getChildren().addAll(new HboxInvoiceHeader(),vBoxSumItemsInner);
        getChildren().addAll(yearBatchHBox, selectionHBox, comboBoxHBox, vboxSumItems, buttonHBox,
                remaindingRenewalHBox);
    }

    private void getInvoiceItemRows(String year) {
        int qty = 0;
        BigDecimal value = new BigDecimal(0.00);
        for(String e: invoiceItemTypes) {
            /// take string and get with SQL
            HboxInvoiceSumItem item = new HboxInvoiceSumItem(
                    SqlInvoiceItem.getInvoiceItemSumByYearAndType(year, e));
            qty = qty + item.getInvoiceSummedItem().getQty();
            if(item.getInvoiceSummedItem().isCredit())
                value = value.subtract(new BigDecimal(item.getInvoiceSummedItem().getValue()));
            else
                value = value.add(new BigDecimal(item.getInvoiceSummedItem().getValue()));
            vBoxSumItemsInner.getChildren().add(item);
        }
        vBoxSumItemsInner.getChildren().add(new HboxInvoiceFooter(value,qty)); // adds the footer with totals
    }

    public Text getNumberOfRecords() {
        return numberOfRecords;
    }
}
