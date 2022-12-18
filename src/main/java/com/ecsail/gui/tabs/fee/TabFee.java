package com.ecsail.gui.tabs.fee;

import com.ecsail.BaseApplication;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
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
import java.util.Optional;

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
        feeEditControls.setMaxQtySpinner();
        feeEditControls.setOrderSpinnerListener(); // might need to move this
        feeEditControls.setMaxQtySpinnerListener();

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
        vbox4.setPrefWidth(250); //225
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
            refreshFeeRows();
        });
        return addButton;
    }

    private FeeRow addNewRow() {
        DbInvoiceDTO dbInvoiceDTO = new DbInvoiceDTO(selectedYear, rows.size() + 1);
        SqlInsert.addNewDbInvoice(dbInvoiceDTO);
        FeeRow feeRow = new FeeRow(this, dbInvoiceDTO);
        rows.add(feeRow);
        feeEditControls.refreshData();
        return feeRow;
    }

    private Button createDeleteButton() {  // for deleting invoice items, and corresponding fees
        Button delButton = new Button("Delete");
            delButton.setOnAction((event) -> checkForDelete());
        return delButton;
    }

    private void checkForDelete() {
        Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
        conformation.setTitle("Delete Invoice Category");
        conformation.setHeaderText(selectedFeeRow.label.getText());
        conformation.setContentText("Are sure you want to delete this Category?");
        DialogPane dialogPane = conformation.getDialogPane();
        dialogPane.getStylesheets().add("css/dark/dialogue.css");
        dialogPane.getStyleClass().add("dialog");
        Optional<ButtonType> result = conformation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteFeeRow();
        }
    }

    private Button createCopyFeeButton() {
        Button copyFeesBtn = new Button("Copy Fees");
//        copyFeesBtn.setOnAction((event) -> copyPreviousYearsFees());
        return copyFeesBtn;
    }

    // this worked great for going back, I need to make it work going forward now
    private void copyPreviousYearsFees() {
        for(FeeRow row: rows) {
            String year = String.valueOf(Integer.parseInt(selectedYear) - 1);
            DbInvoiceDTO dbInvoiceDTO = new DbInvoiceDTO(year, row.dbInvoiceDTO.getOrder());
            dbInvoiceDTO.setFieldName(row.dbInvoiceDTO.getFieldName());
            dbInvoiceDTO.setWidgetType(row.dbInvoiceDTO.getWidgetType());
            dbInvoiceDTO.setMultiplied(row.dbInvoiceDTO.isMultiplied());
            dbInvoiceDTO.setPrice_editable(row.dbInvoiceDTO.isPrice_editable());
            dbInvoiceDTO.setIsCredit(row.dbInvoiceDTO.isCredit());
            dbInvoiceDTO.setMaxQty(row.dbInvoiceDTO.getMaxQty());
            dbInvoiceDTO.setAutoPopulate(row.dbInvoiceDTO.isAutoPopulate());
//            System.out.println(dbInvoiceDTO);
            SqlInsert.addNewDbInvoice(dbInvoiceDTO);
            if(row.fees.size() > 0)
                for(FeeDTO fee: row.fees) {
                    FeeDTO newFee = new FeeDTO(fee.getFieldName(),fee.getFieldValue(),dbInvoiceDTO.getId(),Integer.parseInt(year),fee.getDescription());
                    newFee.setFeeId(SqlSelect.getNextAvailablePrimaryKey("fee", "Fee_ID"));
//                    System.out.println("    " + newFee);
                    SqlInsert.addNewFee(newFee);
                }
        }
    }

    private void setNewYear(Object newValue) {
        okToWriteToDataBase = false;
        this.selectedYear = newValue.toString();
        String fieldName = selectedFeeRow.dbInvoiceDTO.getFieldName();
        this.feeDTOS.clear();
        this.feeDTOS = SqlFee.getFeesFromYear(Integer.parseInt(selectedYear));
        rows.clear();
        addControlBox();
        createFeeRows();
        addFeeRows();
        selectDesiredFeeRow(fieldName);
        okToWriteToDataBase = true;
    }
    // when switching years this will keep it selected on correct one.
    private void selectDesiredFeeRow(String fieldName) {
        System.out.println(fieldName);
        boolean rowSelected = false;
        for(FeeRow row: rows) {
            if(row.label.getText().equals(fieldName)) {
                row.getRadioButton().setSelected(true);
                rowSelected = true;
            }
        }
        if(!rowSelected)
            rows.get(0).getRadioButton().setSelected(true);
    }

    private void deleteFeeRow() {
        if (radioGroup.getSelectedToggle() != null) {
            // gets the number we are removing
            int order = selectedFeeRow.dbInvoiceDTO.getOrder();
            // remove fees for this db_invoice from database
            SqlDelete.deleteFeesByDbInvoiceId(selectedFeeRow.dbInvoiceDTO);
            // remove dbInvoice
            SqlDelete.deleteDbInvoice(selectedFeeRow.dbInvoiceDTO);
            // remove feeRow from list
            rows.remove(selectedFeeRow);
            // clear HBoxes from column
            vboxFeeRow.getChildren().remove(selectedFeeRow);
            // need to reorder the order
            setNewOrderForFeeRows(order);
            // select fist item in row
            rows.get(0).getRadioButton().setSelected(true);
        }
    }

    private void setNewOrderForFeeRows(int order) {
        for(FeeRow row: rows) {
            int currentOrder = row.dbInvoiceDTO.getOrder();
            if(currentOrder > order) {
                row.dbInvoiceDTO.setOrder(currentOrder -1);
                row.setOrder(currentOrder -1);
                SqlUpdate.updateDbInvoice(row.dbInvoiceDTO);
            }
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
