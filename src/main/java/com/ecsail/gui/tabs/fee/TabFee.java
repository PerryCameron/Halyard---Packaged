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

/// This is the new experimental version
public class TabFee extends Tab {
    private String selectedYear;
    private ArrayList<FeeDTO> feeDTOS;
    private final ArrayList<FeeRow> rows = new ArrayList<>();
    private final ToggleGroup radioGroup;
    private final VBox vboxFeeRow;
    private final HBox hboxControls;
    private final ComboBox<Integer> comboBox;
    protected FeesLineChartEx duesLineChart;
    protected FeeEditControls feeEditControls;
    protected boolean okToWriteToDataBase = true;
    protected FeeRow selectedFeeRow;

    public TabFee(String text) {
        super(text);
        this.selectedYear = BaseApplication.selectedYear;
        this.feeDTOS = SqlFee.getFeesFromYear(Integer.parseInt(selectedYear));
        this.radioGroup = new ToggleGroup();
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

    private Button createDeleteButton() {  // for deleting invoice items, and corresponding fees
        Button delButton = new Button("Delete");
        delButton.setOnAction((event) -> deleteFeeRow());
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
        addControlBox(); // do these really need updated?
        // update fees on gui
        createFeeRows();
    }

    private void setNewYear(Object newValue) {
        okToWriteToDataBase = false;
        this.selectedYear = newValue.toString();
        this.feeDTOS.clear();
        this.feeDTOS = SqlFee.getFeesFromYear(Integer.parseInt(selectedYear));
        rows.clear();
        addControlBox();
        createFeeRows();
        addFeeRows();
        okToWriteToDataBase = true;
    }

    private void deleteFeeRow() {
        if (radioGroup.getSelectedToggle() != null) {
            // remove fees for this db_invoice from database
            SqlDelete.deleteFeesByDbInvoiceId(selectedFeeRow.dbInvoiceDTO);
            // remove dbInvoice
            SqlDelete.deleteDbInvoice(selectedFeeRow.dbInvoiceDTO);
            // remove feeRow from list
            feeDTOS.remove(selectedFeeRow.selectedFee);
            // clear HBoxes from column
            vboxFeeRow.getChildren().remove(selectedFeeRow);
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
                    newRow.fees.add(fee); // add fee to row
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
                if(row.selectedFee != null)
                    duesLineChart.refreshChart(row.selectedFee.getDescription());
                feeEditControls.refreshData(); // refreshed data for selected fee
            }
        }
    }

    public void refreshFeeRows() {
        vboxFeeRow.getChildren().clear();
        rows.sort(Comparator.comparing(FeeRow::getOrder).reversed());
        rows.forEach(row -> vboxFeeRow.getChildren().add(row));
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
