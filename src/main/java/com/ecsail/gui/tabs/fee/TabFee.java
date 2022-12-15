package com.ecsail.gui.tabs.fee;

import com.ecsail.BaseApplication;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlDbInvoice;
import com.ecsail.sql.select.SqlFee;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.DbInvoiceDTO;
import com.ecsail.structures.FeeDTO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/// This is the new experimental version
public class TabFee extends Tab {
    private String selectedYear;
    private ArrayList<FeeDTO> feeDTOS;
    private final ArrayList<FeeRow> rows = new ArrayList<>();

    private final ToggleGroup radioGroup;
    private final HashMap<RadioButton, FeeRow> hboxHashMap;
    private final VBox vboxFeeRow;
    private final HBox hboxControls;
    private final ComboBox<Integer> comboBox;
    protected FeesLineChartEx duesLineChart;
    protected FeeEditControls feeEditControls;
    protected boolean okToWriteToDataBase = true;

    public TabFee(String text) {
        super(text);
        this.selectedYear = BaseApplication.selectedYear;
        this.feeDTOS = SqlFee.getFeesFromYear(Integer.parseInt(selectedYear));
        this.radioGroup = new ToggleGroup();
        this.hboxHashMap = new HashMap<>();
        this.comboBox = addComboBox();
        this.vboxFeeRow = createControlsVBox();
        this.hboxControls = new HBox();
        this.duesLineChart = new FeesLineChartEx();
        feeEditControls = new FeeEditControls(this);
        createFeeRows();
        addFeeRows();
        feeEditControls.setOrderSpinner();
        feeEditControls.setOrderSpinnerListener(); // might need to move this

        // this is the vbox for organizing all the widgets
        VBox vbox4 = new VBox();
        HBox hbox2 = new HBox();
        VBox vbox1 = new VBox();
        ScrollPane itemsScrollPane = new ScrollPane();

        ////////////////////// ADD PROPERTIES TO OBJECTS //////////////
        hboxControls.setSpacing(10);
        vbox1.setId("box-blue");
        feeEditControls.setPrefHeight(400);

        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox4.setPadding(new Insets(10, 10, 10, 10));
        vbox4.setPrefHeight(688);
        vbox4.setSpacing(15);
        vbox4.setPrefWidth(225);
        HBox.setHgrow(vboxFeeRow,Priority.ALWAYS);
        //////////////// LISTENERS ///////////////////

        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> setNewYear(newValue));

        //////////// SETTING CONTENT /////////////
        TitledPane feeEditControlsTitlePane = new TitledPane();
        feeEditControlsTitlePane.setContent(feeEditControls);
        // adds buttons and year combobox
        addControlBox();
        HBox.setHgrow(hbox2, Priority.ALWAYS);
        HBox.setHgrow(duesLineChart, Priority.ALWAYS);
        itemsScrollPane.setContent(vboxFeeRow);
        vbox4.getChildren().addAll(hboxControls, itemsScrollPane);
        hbox2.getChildren().addAll(vbox4, duesLineChart);
        vbox1.getChildren().addAll(hbox2, feeEditControlsTitlePane);
        setContent(vbox1);
    }

    // vboxOne first Box

    private void addControlBox() {
        hboxControls.getChildren().clear();
        if (feeDTOS.size() > 0)
            hboxControls.getChildren().addAll(comboBox, createAddButton(), createDeleteButton());
            // if we donn't have entries set buttons add, copy fees
        else
            hboxControls.getChildren().addAll(comboBox, createAddButton(), createCopyFeeButton());
    }

    private Button createAddButton() {
        Button addButton = new Button("New");
        addButton.setOnAction(event -> {
            vboxFeeRow.getChildren().add(addNewRow());
        });
        return addButton;
    }

    private FeeRow addNewRow() {
        DbInvoiceDTO dbInvoiceDTO = new DbInvoiceDTO(selectedYear, rows.size() + 1);
        SqlInsert.addNewDbInvoice(dbInvoiceDTO);
        return new FeeRow(this, dbInvoiceDTO);
    }

    private Button createDeleteButton() {
        Button delButton = new Button("Delete");
        delButton.setOnAction((event) -> deleteRowIn());
        return delButton;
    }

    private Button createCopyFeeButton() {
        Button copyFeesBtn = new Button("Copy Fees");
        copyFeesBtn.setOnAction((event) -> copyPreviousYearsFees());
        return copyFeesBtn;
    }

    private void copyPreviousYearsFees() {
        // clear the current list
        feeDTOS.clear();
        // get next available primary key
        int key = SqlSelect.getNextAvailablePrimaryKey("fee", "FEE_ID");
        // choose year to copy
        int copyYear = Integer.parseInt(selectedYear) + 1;
        // populate that list with objects from copy year
        feeDTOS = SqlFee.getFeesFromYear(copyYear);
        // change the year from old objets to current year and save it to SQL
        for (FeeDTO feeDTO : feeDTOS) {
            feeDTO.setFeeYear(Integer.parseInt(selectedYear));
            feeDTO.setFeeId(key);
            SqlInsert.addNewFee(feeDTO);
            key++;
        }
        // update buttons on gui
        addControlBox();
        // update fees on gui
        createFeeRows();
    }

    private void setNewYear(Object newValue) {
        okToWriteToDataBase = false;
        this.selectedYear = newValue.toString();
        this.feeDTOS.clear();
        this.feeDTOS = SqlFee.getFeesFromYear(Integer.parseInt(selectedYear));
        hboxHashMap.clear();
        rows.clear();
        addControlBox();
        createFeeRows();
        addFeeRows();
        okToWriteToDataBase = true;
    }

    // java fx controls for editing, no business logic
//    private void createEditHBox(FeeRow hbox) {
//        hbox.getChildren().clear();
//        Button saveButton = new Button("Save");
//        Label description = new Label("Description:");
//        TextField descriptionText = new TextField(hbox.getSelectedFee().getDescription());
//        CheckBox checkMultiply = new CheckBox("Multiplied by QTY");
//        Label maxQty = new Label("Max Qty");
//        TextField qtyText = new TextField("0");
//        CheckBox price_editable = new CheckBox("Price Editable");
//        CheckBox checkCredit = new CheckBox("Is Credit");
//        CheckBox autoPopulate = new CheckBox("Auto-populate");
//        VBox vboxEditBox = new VBox();
//        HBox hboxRow1 = new HBox();
//        HBox hboxRow2 = new HBox();
//        HBox hboxRow3 = new HBox();
//        HBox hboxRow4 = new HBox();
//        HBox hboxRow5 = new HBox();
//        HBox hboxRow6 = new HBox();
//        vboxEditBox.setSpacing(5);
//        hboxRow1.setSpacing(5);
//        hboxRow3.setSpacing(5);
//        hboxRow1.getChildren().addAll(descriptionText, description);
//        hboxRow2.getChildren().addAll(checkMultiply);
//        hboxRow3.getChildren().addAll(maxQty, qtyText);
//        hboxRow4.getChildren().addAll(price_editable);
//        hboxRow5.getChildren().addAll(checkCredit);
//        hboxRow6.getChildren().addAll(autoPopulate);
//        vboxEditBox.getChildren().addAll(hboxRow1, hboxRow2, hboxRow3, hboxRow4, hboxRow5, hboxRow6, saveButton);
//        hbox.getChildren().add(vboxEditBox);
//        addButtonListener(saveButton, descriptionText);
//    }

//    private void addButtonListener(Button saveButton, TextField fieldNameText) {
//        saveButton.setOnAction((event) -> {
//            // update selected object
//            hboxHashMap.get(radioGroup.getSelectedToggle()).getSelectedFee().setFieldName(fieldNameText.getText());
//            // write object to sql
//            SqlUpdate.updateFeeRecord(hboxHashMap.get(radioGroup.getSelectedToggle()).getSelectedFee());
//            // update contents of hbox
//        });
//    }

    private void deleteRowIn() {
        if (radioGroup.getSelectedToggle() != null) {
            // remove from database
            SqlDelete.deleteFee(hboxHashMap.get(radioGroup.getSelectedToggle()).getSelectedFee());
            // remove from list
            feeDTOS.remove(hboxHashMap.get(radioGroup.getSelectedToggle()).getSelectedFee());
            // clear HBoxes from column
            vboxFeeRow.getChildren().remove(hboxHashMap.get(radioGroup.getSelectedToggle()));
        }
    }

    // year combo box at top
    private ComboBox<Integer> addComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        // creates a combo box with a list of years
        for (int i = Integer.parseInt(BaseApplication.selectedYear) + 1; i > 1969; i--) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(1);
        return comboBox;
    }

    // create our controls vbox and add hbox rows to it
    private VBox createControlsVBox() {
        VBox feeBox = new VBox();
        feeBox.setSpacing(5);
        return feeBox;
    }

    // used to initially place hbox rows into vbox for a given year
    private void createFeeRows() {
        ArrayList<DbInvoiceDTO> dbInvoiceDTOS = SqlDbInvoice.getDbInvoiceByYear(Integer.parseInt(selectedYear));
        FeeRow newRow;
        for(DbInvoiceDTO item: dbInvoiceDTOS) {  // make each item type
            newRow = new FeeRow( this, item); // add db_invoice to each row
            rows.add(newRow);
            for (FeeDTO fee : feeDTOS) {
                if(fee.getDbInvoiceID() == item.getId()) {
                    newRow.getFees().add(fee); // add fee to row
                }
            }
        }
    }

    public void addFeeRows() {
        vboxFeeRow.getChildren().clear();
        rows.sort(Comparator.comparing(FeeRow::getOrder).reversed());
        int size = rows.size();
        for(FeeRow row: rows) {
            vboxFeeRow.getChildren().add(row);
            if(row.getOrder() == size) { // picks top entry
                row.getRadioButton().setSelected(true);
                if(row.getSelectedFee() != null)
                    duesLineChart.refreshChart(row.getSelectedFee().getDescription());
                feeEditControls.refreshData(); // refreshed data for selected fee
            }
        }
    }

    public void refreshFeeRows() {
        vboxFeeRow.getChildren().clear();
        rows.sort(Comparator.comparing(FeeRow::getOrder).reversed());
        rows.forEach(row -> vboxFeeRow.getChildren().add(row));
    }

    public HashMap<RadioButton, FeeRow> getHboxHashMap() {
        return hboxHashMap;
    }

    public ToggleGroup getRadioGroup() {
        return radioGroup;
    }

    public boolean isOkToWriteToDataBase() {
        return okToWriteToDataBase;
    }

    public ArrayList<FeeRow> getRows() {
        return rows;
    }

    public FeesLineChartEx getDuesLineChart() {
        return duesLineChart;
    }
}
