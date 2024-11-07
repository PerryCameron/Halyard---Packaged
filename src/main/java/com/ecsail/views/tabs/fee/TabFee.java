package com.ecsail.views.tabs.fee;

import com.ecsail.dto.DbInvoiceDTO;
import com.ecsail.dto.FeeDTO;
import com.ecsail.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.repository.interfaces.InvoiceRepository;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class TabFee extends Tab {
    private String selectedYear;
    private ArrayList<FeeDTO> feeDTOS;
    private final ArrayList<FeeRow> rows = new ArrayList<>();
    private final ToggleGroup radioGroup;
    private final VBox vboxCategories;
    private final HBox hboxYearNewDeleteButtons;
    private final ComboBox<Integer> comboBox;
    protected FeesLineChartEx duesLineChart;
    protected FeeEditControls feeEditControls;
    protected boolean okToWriteToDataBase = true;
    protected FeeRow selectedFeeRow;
    private InvoiceRepository invoiceRepository;

    public TabFee(String text) {
        super(text);
        this.selectedYear = String.valueOf(Year.now().getValue());
        this.invoiceRepository = new InvoiceRepositoryImpl();
        this.feeDTOS = (ArrayList<FeeDTO>) invoiceRepository.getFeesFromYear(Integer.parseInt(selectedYear));
        this.radioGroup = new ToggleGroup();
        this.comboBox = addComboBox();
        this.vboxCategories = createControlsVBox();
        this.hboxYearNewDeleteButtons = new HBox();
        this.duesLineChart = new FeesLineChartEx(this);
        feeEditControls = new FeeEditControls(this);
        createFeeRows();
        addFeeRows();
        feeEditControls.setOrderSpinner();
        feeEditControls.setMaxQtySpinner();
        feeEditControls.setOrderSpinnerListener(); // might need to move this
        feeEditControls.setMaxQtySpinnerListener();

        // this is the vbox for organizing all the widgets
        VBox vboxCategoryList = new VBox();
        HBox hbox2 = new HBox();
        VBox vbox1 = new VBox();
        ScrollPane CategoriesScrollPane = new ScrollPane();

        ////////////////////// ADD PROPERTIES TO OBJECTS //////////////
        hboxYearNewDeleteButtons.setSpacing(10);
        vbox1.setId("box-blue");
        feeEditControls.setPrefHeight(400);

        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vboxCategoryList.setPadding(new Insets(10, 10, 10, 10));
        vboxCategoryList.setPrefHeight(688);
        vboxCategoryList.setSpacing(15);
        vboxCategoryList.setPrefWidth(250); //225
        HBox.setHgrow(vboxCategories,Priority.ALWAYS);
        //////////////// LISTENERS ///////////////////

        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> setNewYear(newValue));

        //////////// SETTING CONTENT /////////////
        TitledPane feeEditControlsTitlePane = new TitledPane();
        feeEditControlsTitlePane.setContent(feeEditControls);
        // adds buttons and year combobox
        addControlBox();
        HBox.setHgrow(hbox2, Priority.ALWAYS);
        HBox.setHgrow(duesLineChart, Priority.ALWAYS);
        CategoriesScrollPane.setContent(vboxCategories);
        vboxCategoryList.getChildren().addAll(hboxYearNewDeleteButtons, CategoriesScrollPane);
        hbox2.getChildren().addAll(vboxCategoryList, duesLineChart);
        vbox1.getChildren().addAll(hbox2, feeEditControlsTitlePane);
        setContent(vbox1);
    }

    // vboxOne first Box

    private void addControlBox() {
        hboxYearNewDeleteButtons.getChildren().clear();
        if (feeDTOS.size() > 0)
            hboxYearNewDeleteButtons.getChildren().addAll(comboBox, createAddButton(), createDeleteButton());
            // if we donn't have entries set buttons add, copy fees
        else
            hboxYearNewDeleteButtons.getChildren().addAll(comboBox, createAddButton(), createCopyFeeButton());
    }

    private Button createAddButton() {
        Button addButton = new Button("New");
        addButton.setOnAction(event -> {
            vboxCategories.getChildren().add(addNewRow());
            refreshFeeRows();
        });
        return addButton;
    }

    private FeeRow addNewRow() {
        DbInvoiceDTO dbInvoiceDTO = new DbInvoiceDTO(selectedYear, rows.size() + 1);
        invoiceRepository.insertDbInvoice(dbInvoiceDTO);
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

    private Button createCopyFeeButton() {  // TODO reintroduce functionality moving forward
        Button copyFeesBtn = new Button("Copy Fees");
        copyFeesBtn.setOnAction((event) -> copyPreviousYearsFees());
        return copyFeesBtn;
    }

    // this worked great for going back, I need to make it work going forward now
    private void copyPreviousYearsFees() {
        // previous years fees
        ArrayList<FeeDTO> previousYearsFees = (ArrayList<FeeDTO>) invoiceRepository.getFeesFromYear(Integer.parseInt(selectedYear) - 1);
        System.out.println("previousYearsFees: " + previousYearsFees.size());
        // previous years dbInvoices
        ArrayList<DbInvoiceDTO> dbInvoiceDTOS = (ArrayList<DbInvoiceDTO>) invoiceRepository.getDbInvoiceByYear(Integer.parseInt(selectedYear) - 1);
        System.out.println("dbInvoiceDTOS: " + dbInvoiceDTOS.size());

        previousYearsFees.forEach(feeDTO -> feeDTO.setFeeYear(Integer.parseInt(selectedYear)));
        feeDTOS.addAll(previousYearsFees);
        dbInvoiceDTOS.forEach(dbInvoiceDTO -> {
            dbInvoiceDTO.clone(new DbInvoiceDTO(selectedYear));
        });
        previousYearsFees.forEach(System.out::println);
        dbInvoiceDTOS.forEach(System.out::println);
    }

    private void setNewYear(Object newValue) {
        okToWriteToDataBase = false;
        this.selectedYear = newValue.toString();
        String fieldName = selectedFeeRow.dbInvoiceDTO.getFieldName();
        this.feeDTOS.clear();
        this.feeDTOS = (ArrayList<FeeDTO>) invoiceRepository.getFeesFromYear(Integer.parseInt(selectedYear));
        rows.clear();
        addControlBox();
        createFeeRows();
        addFeeRows();
        if(rows.size() != 0)  // is it a new year with no rows yet?
        selectDesiredFeeRow(fieldName);
        okToWriteToDataBase = true;
    }
    // when switching years this will keep it selected on correct one.
    private void selectDesiredFeeRow(String fieldName) {
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
            invoiceRepository.deleteFeesByDbInvoiceId(selectedFeeRow.dbInvoiceDTO);
            // remove dbInvoice
            invoiceRepository.deleteDbInvoice(selectedFeeRow.dbInvoiceDTO);
            // remove feeRow from list
            rows.remove(selectedFeeRow);
            // clear HBoxes from column
            vboxCategories.getChildren().remove(selectedFeeRow);
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
                invoiceRepository.updateDbInvoice(row.dbInvoiceDTO);
            }
        }
    }

    // year combo box at top
    private ComboBox<Integer> addComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        // creates a combo box with a list of years
        for (int i = Integer.parseInt(String.valueOf(Year.now().getValue())) + 1; i > 1969; i--) {
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
        ArrayList<DbInvoiceDTO> dbInvoiceDTOS = (ArrayList<DbInvoiceDTO>) invoiceRepository.getDbInvoiceByYear(Integer.parseInt(selectedYear));
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
        vboxCategories.getChildren().clear();
        rows.sort(Comparator.comparing(FeeRow::getOrder).reversed());
        int size = rows.size();
        for(FeeRow row: rows) {
            vboxCategories.getChildren().add(row);
            if(row.getOrder() == size) { // picks top entry
                row.getRadioButton().setSelected(true);
                if(row.selectedFee != null)
                    duesLineChart.refreshChart(row.selectedFee.getDescription());
                feeEditControls.refreshData(); // refreshed data for selected fee
            }
        }
    }

    public void refreshFeeRows() {
        vboxCategories.getChildren().clear();
        rows.sort(Comparator.comparing(FeeRow::getOrder).reversed());
        rows.forEach(row -> vboxCategories.getChildren().add(row));
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

    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }
}
