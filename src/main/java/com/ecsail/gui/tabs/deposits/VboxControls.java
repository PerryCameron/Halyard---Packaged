package com.ecsail.gui.tabs.deposits;

import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.sql.SqlCount;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.*;
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
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class VboxControls extends VBox {
    private final TabDeposits tabParent;
    private final ArrayList<String> invoiceItemTypes; // a list of types of invoice items for a given year
    private final DepositDTO depositDTO;
    private final VBox vBoxSumItemsInner = new VBox();
    private final Text numberOfRecordsText = new Text();
    private final Text numberOfDepositsText = new Text();
    private final ComboBox<String> comboBox;
    private int selectedYear;
    private final DatePicker depositDatePicker = new DatePicker();
    private int numberOfDeposits = 100;
    Text nonRenewed = new Text("0");
    boolean isSafeToDisplay = true;
    final Spinner<Integer> batchSpinner;
//    private Integer batch = 1;

    public VboxControls(TabDeposits tabDeposits) {
        this.tabParent = tabDeposits;
        this.depositDTO = tabParent.getDepositDTO();
        this.selectedYear = Integer.parseInt(depositDTO.getFiscalYear());
        this.invoiceItemTypes = SqlDbInvoice.getInvoiceCategoriesByYear(selectedYear);
        this.numberOfRecordsText.setText(String.valueOf(tabDeposits.getInvoices().size()));

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
        var invoiceLabel = new Text("Invoices:");
        var depositLabel = new Text("Deposits:");
        var notRenewedLabel = new Text("Not Renewed:");
        ObservableList<String> options = FXCollections.observableArrayList("Show All", "Show Current"
                ,"Show Last");
        this.comboBox = new ComboBox<>(options);
        var newDepositButton = new Button("New Deposit");
        var printPdfButton = new Button("Print PDF");
        batchSpinner = new Spinner<>();

        controlsHBox.setPadding(new Insets(5, 5, 5, 5));
        controlsVBox.setPadding(new Insets(15, 5, 5, 5));
        vboxBlue.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setPadding(new Insets(3, 3, 3, 3));
        selectionHBox.setPadding(new Insets(0, 0, 0, 37));
        this.setPadding(new Insets(15, 10, 5, 10));
        VBox vboxSumItems = new VBox();
        vboxSumItems.setPadding(new Insets(5, 2, 2, 2));
        vBoxSumItemsInner.setPadding(new Insets(5, 5, 10, 5));
        vboxSumItems.setSpacing(5);
        vBoxSumItemsInner.setSpacing(5);
        vBoxSumItemsInner.setId("box-background-light");
        numberOfRecordsText.setId("invoice-text-number");
        numberOfDepositsText.setId("invoice-text-number");
        nonRenewed.setId("invoice-text-number");
        invoiceLabel.setId("invoice-text-label");
        depositLabel.setId("invoice-text-label");
        notRenewedLabel.setId("invoice-text-label");

        controlsVBox.setPrefWidth(342);
        depositDatePicker.setPrefWidth(150);
        comboBox.setPrefWidth(150);
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
                refreshAllData(true); // gets all for 2022 //
            }
        });

        // 0 to batch, display batch
        batchSpinner.setEditable(true);
        var batchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, depositDTO.getBatch());
        batchSpinner.setValueFactory(batchValueFactory);
        batchSpinner.setPrefWidth(60);
        batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                batchSpinner.increment(0); // won't change value, but will commit editor
                depositDTO.setBatch(Integer.parseInt(batchSpinner.getEditor().getText()));
                refreshSafeToDisplay();
                refreshAllData(false);
            }
        });
//    THIS is original DATE PICKER CODE SEE BELOW
//        var pickerEvent = new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent e) {
//                depositDTO.setDepositDate(String.valueOf(depositDatePicker.getValue()));
//                SqlUpdate.updateDeposit(depositDTO);
//            }
//        };
//
//        depositDatePicker.setOnAction(pickerEvent);

        // This is a hack I got from here
        // https://stackoverflow.com/questions/32346893/javafx-datepicker-not-updating-value
        // Apparently datepicker was broken after java 8 and then fixed in java 18
        // this is a work-around until I upgrade this to java 18+
        depositDatePicker.setConverter(new StringConverter<>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty())
                    return null;
                try {
                    return LocalDate.parse(dateString, dateTimeFormatter);
                } catch (Exception e) {
                    BaseApplication.logger.error("Bad date value entered");
                    return null;
                }
            }
        });

        //This deals with the bug located here where the datepicker value is not updated on focus lost
        //https://bugs.openjdk.java.net/browse/JDK-8092295?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel
        depositDatePicker.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (!isFocused){
                try {
                    depositDatePicker.setValue(depositDatePicker.getConverter().fromString(depositDatePicker.getEditor().getText()));
                } catch (DateTimeParseException e) {
                    depositDatePicker.getEditor().setText(depositDatePicker.getConverter().toString(depositDatePicker.getValue()));
                }
                depositDTO.setDepositDate(String.valueOf(depositDatePicker.getValue()));
                SqlUpdate.updateDeposit(depositDTO);
            }
        });

        comboBox.valueProperty().addListener((change) -> {
                    refreshAllData(false);
                }
        );

        newDepositButton.setOnAction(event -> {
            if(okToMakeNewDeposit()) { // checks to see that last deposit is not empty, so ok to make new one
                numberOfDeposits++; // must be first
                cleanDeposit(true);
                SqlInsert.addDeposit(depositDTO);
                refreshDepositCount();
                depositDTO.setBatch(numberOfDeposits);
                batchSpinner.getEditor().setText(String.valueOf(depositDTO.getBatch()));
            } else {
                // TODO add error dialogue here
                System.out.println("Cant make a new record because the last one is new");
            }
        });

//        printPdfButton.setOnAction((event) -> new Dialogue_DepositPDF(depositDTO, currentDefinedFee, selectedYear));

        VBox.setVgrow(vboxBlue, Priority.ALWAYS);
        VBox.setVgrow(vboxPink, Priority.ALWAYS);
        VBox.setVgrow(vboxGrey, Priority.ALWAYS);

        batchNumberHBox.setAlignment(Pos.CENTER);
        yearBatchHBox.setAlignment(Pos.CENTER);
        vboxSumItems.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.CENTER);
        remainingRenewalHBox.setAlignment(Pos.CENTER);

        vboxSumItems.setStyle("-fx-background-color: #0a0a0a;");  // orange

        // gets all invoice items
        refreshAllData(false); // gets all for 2022
        refreshDepositCount();
        refreshNonRenewed();
        vboxDateCombo.getChildren().addAll(depositDatePicker, comboBox);
        hboxInvoiceRecords.getChildren().addAll(invoiceLabel, numberOfRecordsText);
        hboxDepositRecords.getChildren().addAll(depositLabel, numberOfDepositsText);
        vboxRecords.getChildren().addAll(hboxDepositRecords, hboxInvoiceRecords);
        selectionHBox.getChildren().addAll(vboxDateCombo, vboxRecords);
        remainingRenewalHBox.getChildren().addAll(new Text("Memberships not renewed: "), nonRenewed);
        batchNumberHBox.getChildren().addAll(new Label("Deposit Number"), batchSpinner);
        yearBatchHBox.getChildren().addAll(yearSpinner, batchNumberHBox);
        buttonHBox.getChildren().addAll(newDepositButton, printPdfButton);
        controlsHBox.getChildren().add(controlsVBox);
        vboxSumItems.getChildren().addAll(new HboxInvoiceHeader(), vBoxSumItemsInner);
        getChildren().addAll(yearBatchHBox, selectionHBox, vboxSumItems, buttonHBox,
                remainingRenewalHBox);
    }

    public boolean okToMakeNewDeposit() {
        return SqlExists.depositIsUsed(selectedYear,numberOfDeposits);
    }

    public boolean depositIsUsed() {
        return SqlExists.depositIsUsed(selectedYear,depositDTO.getBatch());
    }

    private void refreshAllData(boolean refreshInvoiceTypes) {
        refreshCurrentDeposit();
        refreshNewYearData(refreshInvoiceTypes);
        refreshRows();
        refreshDate();
        refreshInvoices();
        refreshNonRenewed();
    }

    private void refreshDate() {
        System.out.println("Refresh Date Called");
        LocalDate date;
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // if no deposit selected put in today's date
        if (!isSafeToDisplay) { // spinner is higher than number of deposits
            date = LocalDate.parse(HalyardPaths.getDate(), formatter);
            depositDTO.setDepositDate(HalyardPaths.getDate());
        }
        else date = LocalDate.parse(depositDTO.getDepositDate(), formatter);
        depositDatePicker.setValue(date);
    }

    private void refreshNewYearData(boolean refreshInvoiceTypes) {
        if (refreshInvoiceTypes) {  // if a new year was selected
            refreshInvoiceTypes();
            refreshDepositCount();
        }
    }

    private void refreshRows() {
        vBoxSumItemsInner.getChildren().clear();  // start with empty box
        if(!depositIsUsed()) createNewDepositNotificationRow();
        getInvoiceItemRows();
    }

    private void createNewDepositNotificationRow() { // if it is a new deposit, this will show "New Deposit Report"
        if (comboBox.getValue().equals("Show Current")) {
            HBox hboxNewDeposit = new HBox();
            hboxNewDeposit.setAlignment(Pos.CENTER);
            Text newText = new Text("New Deposit Report");
            newText.setId("invoice-warning-text-label");
            hboxNewDeposit.getChildren().add(newText);
            vBoxSumItemsInner.getChildren().add(hboxNewDeposit);
        }
    }

    private void refreshInvoices() {
        tabParent.getInvoices().clear();
        if (comboBox.getValue().equals("Show All"))
            tabParent.getInvoices().addAll(SqlInvoice.getInvoicesWithMembershipInfoByYear(depositDTO.getFiscalYear()));
        else tabParent.getInvoices().addAll(SqlInvoice.getInvoicesWithMembershipInfoByDeposit(depositDTO));
    }

    private void refreshInvoiceTypes() {
        invoiceItemTypes.clear();
        invoiceItemTypes.addAll(SqlDbInvoice.getInvoiceCategoriesByYear(selectedYear));
    }

    private void refreshCurrentDeposit() {
            depositDTO.setFiscalYear(String.valueOf(selectedYear));
            if(comboBox.getValue().equals("Show Last")) {
                depositDTO.setBatch(numberOfDeposits);
                batchSpinner.getEditor().setText(String.valueOf(depositDTO.getBatch()));
                comboBox.setValue("Show Current");
                // TODO move comboBox selector as well
            }
            depositDTO.setBatch(depositDTO.getBatch());
            SqlDeposit.getDeposit(depositDTO);
            System.out.println("Current Deposit is " + depositDTO);
    }

    private void cleanDeposit(boolean isNewDeposit) {
        if(isNewDeposit) {
            depositDTO.setDeposit_id(SqlSelect.getNextAvailablePrimaryKey("deposit","DEPOSIT_ID"));
            depositDTO.setBatch(numberOfDeposits);
        }
        else {
            depositDTO.setDeposit_id(0);
            depositDTO.setBatch(0);
        }
        depositDTO.setFiscalYear(String.valueOf(selectedYear));
        LocalDate date;
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(HalyardPaths.getDate(), formatter);
        depositDTO.setDepositDate(HalyardPaths.getDate());
        depositDatePicker.setValue(date);
    }



    private int getCorrectBatch() {
        if (comboBox.getValue().equals("Show All")) return 0;
        return depositDTO.getBatch();
    }

    private void refreshDepositCount() {
        numberOfDeposits = SqlCount.getNumberOfDepositsForYear(selectedYear);
        numberOfDepositsText.setText(String.valueOf(numberOfDeposits));
    }

    private void refreshSafeToDisplay() {
        isSafeToDisplay = getCorrectBatch() <= numberOfDeposits;
    }

    private boolean itemHasAValue(HboxInvoiceSumItem item) {
        if(item.getInvoiceSummedItem().getValue() != null)
        return !item.getInvoiceSummedItem().getValue().equals("0.00");
        else return false;
    }

    private static boolean itemIsACredit(HboxInvoiceSumItem item) {
        return item.getInvoiceSummedItem().isCredit();
    }

    private void getInvoiceItemRows() {
        int qty = 0; // for calculating totals
        BigDecimal value = new BigDecimal("0.00"); // for calculating totals
        if (isSafeToDisplay) { // prevent an exception if we go too high on selector
            for (String e : invoiceItemTypes) {
                HboxInvoiceSumItem item = new HboxInvoiceSumItem(
                        SqlInvoiceItem.getInvoiceItemSumByYearAndType(selectedYear, e, getCorrectBatch()));
                if (itemHasAValue(item)) { // let's not bother with 0 value line items
                    qty = qty + item.getInvoiceSummedItem().getQty();
                    if (itemIsACredit(item))
                        value = value.subtract(new BigDecimal(item.getInvoiceSummedItem().getValue()));
                    else
                        value = value.add(new BigDecimal(item.getInvoiceSummedItem().getValue()));
                    vBoxSumItemsInner.getChildren().add(item);  // adds an invoice item row
                }
            }
            vBoxSumItemsInner.getChildren().add(new HboxInvoiceFooter(value, qty)); // adds the footer with totals
            System.out.println("Batch=" + depositDTO.getBatch());
        } else
            BaseApplication.logger.info("Invoice selector set too high to display information");
    }

    private void refreshNonRenewed() {
        nonRenewed.setText(SqlMembership_Id.getNonRenewNumber(selectedYear) + "");
    }

    public int getSelectedYear() {
        return selectedYear;
    }

}
