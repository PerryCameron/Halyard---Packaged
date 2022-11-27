package com.ecsail.gui.tabs.deposits;

import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.sql.SqlCount;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlDbInvoice;
import com.ecsail.sql.select.SqlDeposit;
import com.ecsail.sql.select.SqlInvoice;
import com.ecsail.sql.select.SqlInvoiceItem;
import com.ecsail.structures.DepositDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class VboxControls extends VBox {
    private final TabDeposits tabParent;
    private final ArrayList<String> invoiceItemTypes; // a list of types of invoice items for a given year
    private final DepositDTO depositDTO = new DepositDTO();
    private final VBox vBoxSumItemsInner = new VBox();
    private final Text numberOfRecords = new Text();
    private final Text numberOfDeposits = new Text();
    private final ComboBox<String> comboBox;

    private final Spinner<Integer> batchSpinner = new Spinner<>();
    private int batch = 1;
    private int selectedYear;
    private DatePicker depositDatePicker = new DatePicker();

    public VboxControls(TabDeposits tabDeposits) {
        this.tabParent = tabDeposits;
        this.selectedYear = Integer.parseInt(BaseApplication.selectedYear);
        this.invoiceItemTypes = SqlDbInvoice.getInvoiceCategoriesByYear(selectedYear);
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
        var vboxDateCombo = new VBox();

        var remainingRenewalHBox = new HBox();
        var selectionHBox = new HBox();
        var vboxRecords = new VBox();
        var hboxInvoiceRecords = new HBox();
        var hboxDepositRecords = new HBox();
        var nonRenewed = new Text("0");
        var invoiceLabel = new Text("Invoices:");
        var depositLabel = new Text("Deposits:");
        ObservableList<String> options = FXCollections.observableArrayList("Show All", "Show One");
        this.comboBox = new ComboBox<>(options);
        var newInvoiceButton = new Button("New Deposit");
        var printPdfButton = new Button("Print PDF");
        final Spinner<Integer> batchSpinner = new Spinner<>();

        controlsHBox.setPadding(new Insets(5, 5, 5, 5));
        controlsVBox.setPadding(new Insets(15, 5, 5, 5));
        vboxBlue.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink fram around table
        selectionHBox.setPadding(new Insets(0, 0, 0, 37));
        this.setPadding(new Insets(15, 10, 5, 10));
        VBox vboxSumItems = new VBox();
        vboxSumItems.setPadding(new Insets(5,2,2,2));
        vBoxSumItemsInner.setPadding(new Insets(5,5,10,5));
        vboxSumItems.setSpacing(5);
        vBoxSumItemsInner.setSpacing(5);
        vBoxSumItemsInner.setId("box-background-light");
        numberOfRecords.setId("invoice-text-number");
        numberOfDeposits.setId("invoice-text-number");
        invoiceLabel.setId("invoice-text-label");
        depositLabel.setId("invoice-text-label");

        controlsVBox.setPrefWidth(342);
        depositDatePicker.setPrefWidth(123);
        comboBox.setPrefWidth(123);
        this.setPrefWidth(350);

        controlsVBox.setSpacing(10);
        mainHBox.setSpacing(5);
        batchNumberHBox.setSpacing(5);
        buttonHBox.setSpacing(10);
        vboxRecords.setSpacing(5);
        selectionHBox.setSpacing(30);
        yearBatchHBox.setSpacing(15);
        vboxRecords.setSpacing(10);
        hboxDepositRecords.setSpacing(7);
        hboxInvoiceRecords.setSpacing(7);
        this.setSpacing(20);
        vboxDateCombo.setSpacing(10);

        vboxRecords.setAlignment(Pos.CENTER);

        comboBox.setValue("Show All");

        final var yearSpinner = new Spinner<Integer>();
        var wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970,
                selectedYear, selectedYear);
        yearSpinner.setValueFactory(wetSlipValueFactory);
        yearSpinner.setPrefWidth(95);
        yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                selectedYear = Integer.parseInt(yearSpinner.getEditor().getText());
                refreshData(true); // gets all for 2022 //
            }
        });

        var batchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
                batch
        ); // 0 to batch, display batch
        batchSpinner.setValueFactory(batchValueFactory);
        batchSpinner.setPrefWidth(60);
        batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                batchSpinner.increment(0); // won't change value, but will commit editor
                batch = Integer.parseInt(batchSpinner.getEditor().getText());
                refreshData(false);
            }
        });

        var pickerEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                depositDTO.setDepositDate(String.valueOf(depositDatePicker.getValue()));
				SqlUpdate.updateDeposit(depositDTO);
            }
        };

        depositDatePicker.setOnAction(pickerEvent);

        comboBox.valueProperty().addListener((change) -> {
                System.out.println("Changing type");
                refreshData(false);
    }
        );

        VBox.setVgrow(vboxBlue, Priority.ALWAYS);
        VBox.setVgrow(vboxPink, Priority.ALWAYS);
        VBox.setVgrow(vboxGrey, Priority.ALWAYS);

        batchNumberHBox.setAlignment(Pos.CENTER);
        yearBatchHBox.setAlignment(Pos.CENTER);
        vboxSumItems.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.CENTER);
        remainingRenewalHBox.setAlignment(Pos.CENTER);

//        hboxInvoiceRecords.setStyle("-fx-background-color: #c5c7c1;");  // gray
//        hboxDepositRecords.setStyle("-fx-background-color: #4d6955;");  //green
//        selectionHBox.setStyle("-fx-background-color: #feffab;");  // yellow
//        remainingRenewalHBox.setStyle("-fx-background-color: #e83115;");  // red
//        batchNumberHBox.setStyle("-fx-background-color: #201ac9;");  // blue
//        yearBatchHBox.setStyle("-fx-background-color: #e83115;");  // purple
//        this.setStyle("-fx-background-color: #15e8e4;");  // light blue
        vboxSumItems.setStyle("-fx-background-color: #0a0a0a;");  // orange
//        buttonHBox.setStyle("-fx-background-color: #a37226;");  // orange

        // gets all invoice items
        refreshData(false); // gets all for 2022
        refreshDepositCount();
        vboxDateCombo.getChildren().addAll(depositDatePicker,comboBox);
        hboxInvoiceRecords.getChildren().addAll(invoiceLabel, numberOfRecords);
        hboxDepositRecords.getChildren().addAll(depositLabel, numberOfDeposits);
        vboxRecords.getChildren().addAll(hboxDepositRecords, hboxInvoiceRecords);
        selectionHBox.getChildren().addAll(vboxDateCombo, vboxRecords);
        remainingRenewalHBox.getChildren().addAll(new Text("Memberships not renewed: "), nonRenewed);
        batchNumberHBox.getChildren().addAll(new Label("Deposit Number"), batchSpinner);
        yearBatchHBox.getChildren().addAll(yearSpinner, batchNumberHBox);
        buttonHBox.getChildren().addAll(newInvoiceButton, printPdfButton);
        controlsHBox.getChildren().add(controlsVBox);
        vboxSumItems.getChildren().addAll(new HboxInvoiceHeader(),vBoxSumItemsInner);
        getChildren().addAll(yearBatchHBox, selectionHBox, vboxSumItems, buttonHBox,
                remainingRenewalHBox);
    }

    private void refreshData(boolean refreshInvoiceTypes) {
        System.out.println("Refreshing Data");
        refreshCurrentDeposit();
        if(refreshInvoiceTypes) {  // if a new year was selected
            refreshInvoiceTypes();
            refreshDepositCount();
        }
        getInvoiceItemRows();
        refreshDate();
        refreshInvoices();
    }

    private void refreshInvoices() {
        tabParent.getInvoices().clear();
        if(comboBox.getValue().equals("Show All"))
            tabParent.getInvoices().addAll(SqlInvoice.getInvoicesWithMembershipInfoByYear(depositDTO.getFiscalYear()));
        else tabParent.getInvoices().addAll(SqlInvoice.getInvoicesWithMembershipInfoByDeposit(depositDTO));
    }

    private void refreshInvoiceTypes() {
        invoiceItemTypes.clear();
        invoiceItemTypes.addAll(SqlDbInvoice.getInvoiceCategoriesByYear(selectedYear));
    }

    private void refreshCurrentDeposit() {
        if(batch > 0) { // don't need this anymore
            depositDTO.setFiscalYear(String.valueOf(selectedYear));
            depositDTO.setBatch(batch);
            SqlDeposit.getDeposit(depositDTO);
            System.out.println("Current Deposit is " + depositDTO);
        }
    }

    private void refreshDate() {
        LocalDate date;
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // if no deposit selected put in today's date
        if(batch == 0) date = LocalDate.parse(HalyardPaths.getDate(), formatter);
        else date = LocalDate.parse(depositDTO.getDepositDate(), formatter);
        depositDatePicker.setValue(date);
    }

    private int getBatch() {
        if(comboBox.getValue().equals("Show All")) return 0;
        return batch;
    }

    private void refreshDepositCount() {
        numberOfDeposits.setText(String.valueOf(SqlCount.getNumberOfDepositsForYear(selectedYear)));
    }

    private void getInvoiceItemRows() {
        vBoxSumItemsInner.getChildren().clear();  // start with empty box
        int qty = 0; // for calculating totals
        BigDecimal value = new BigDecimal("0.00"); // for calculating totals
        for(String e: invoiceItemTypes) {
            /// take string and get with SQL
            HboxInvoiceSumItem item = new HboxInvoiceSumItem(
                    SqlInvoiceItem.getInvoiceItemSumByYearAndType(selectedYear, e, getBatch()));
            if(!item.getInvoiceSummedItem().getValue().equals("0.00")) { // let's not bother with 0 value line items
                qty = qty + item.getInvoiceSummedItem().getQty();
                if (item.getInvoiceSummedItem().isCredit())
                    value = value.subtract(new BigDecimal(item.getInvoiceSummedItem().getValue()));
                else
                    value = value.add(new BigDecimal(item.getInvoiceSummedItem().getValue()));
                vBoxSumItemsInner.getChildren().add(item);  // adds an invoice item row
            }
        }
        vBoxSumItemsInner.getChildren().add(new HboxInvoiceFooter(value,qty)); // adds the footer with totals
    }

    public Text getNumberOfRecords() {
        return numberOfRecords;
    }
}
