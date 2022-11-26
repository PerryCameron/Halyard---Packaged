package com.ecsail.gui.tabs.deposits;

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

import static com.ecsail.BaseApplication.selectedYear;

public class VboxControls extends VBox {

    public VboxControls() {
        setPrefWidth(350);

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
        ObservableList<String> options = FXCollections.observableArrayList("Show All", "Show Selected");
        final var comboBox = new ComboBox<>(options);
        var gridPane = new GridPane();
        var refreshButton = new Button("Refresh");
        var printPdfButton = new Button("Print PDF");
        var depositDatePicker = new DatePicker();
        Text numberOfRecords = new Text("0");
        final Spinner<Integer> batchSpinner = new Spinner<>();

        controlsHBox.setPadding(new Insets(5, 5, 5, 5));
        controlsVBox.setPadding(new Insets(15, 5, 5, 5));
        vboxBlue.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink fram around table
        selectionHBox.setPadding(new Insets(0, 0, 0, 37));
        comboBoxHBox.setPadding(new Insets(0, 0, 0, 37));
        this.setPadding(new Insets(15, 5, 5, 5));

        controlsVBox.setPrefWidth(342);
        depositDatePicker.setPrefWidth(123);

        //vboxGrey.setPrefHeight(688);

        controlsVBox.setSpacing(10);
        mainHBox.setSpacing(5);
        batchNumberHBox.setSpacing(5);
        buttonHBox.setSpacing(10);
        numberOfRecordsHBox.setSpacing(5);
        selectionHBox.setSpacing(30);
        yearBatchHBox.setSpacing(15);
        this.setSpacing(10);

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
        gridHBox.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.CENTER);
        remaindingRenewalHBox.setAlignment(Pos.CENTER);

        comboBoxHBox.getChildren().add(comboBox);
        numberOfRecordsHBox.getChildren().addAll(new Text("Records:"), numberOfRecords);
        selectionHBox.getChildren().addAll(depositDatePicker, numberOfRecordsHBox);
        remaindingRenewalHBox.getChildren().addAll(new Text("Memberships not renewed: "), nonRenewed);
        batchNumberHBox.getChildren().addAll(new Label("Deposit Number"), batchSpinner);
        yearBatchHBox.getChildren().addAll(yearSpinner, batchNumberHBox);
        buttonHBox.getChildren().addAll(refreshButton, printPdfButton);
        gridHBox.getChildren().add(gridPane);
        controlsHBox.getChildren().add(controlsVBox);
        getChildren().addAll(yearBatchHBox, selectionHBox, comboBoxHBox, gridHBox, buttonHBox,
                remaindingRenewalHBox);
    }



}
