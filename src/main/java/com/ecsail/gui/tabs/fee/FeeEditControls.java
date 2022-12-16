package com.ecsail.gui.tabs.fee;

import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.FeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class FeeEditControls extends HBox {
    protected TabFee parent;
    private ObservableList<FeeDTO> fees = FXCollections.observableArrayList();
    private final LabeledSpinner orderedSpinner = new LabeledSpinner("Order");
    private final LabeledSpinner maxQtySpinner = new LabeledSpinner("Max Qty");
    private final CheckBox autoPopulate = new CheckBox("Auto Populate");
    private final CheckBox isCredit = new CheckBox("Credit");
    private final CheckBox priceIsEditable = new CheckBox("Price Editable");
    private HashMap<String,RadioButton> rbHash = new HashMap<>();
    private String[] radioButtonTitles = {"Spinner","TextField","Drop Down","None"};
    private final LabeledTextField fieldNameText = new LabeledTextField("Field Name");
    private final MockHeader mockHeader = new MockHeader();
    private final VBox vBoxMockItems = new VBox();
    private ToggleGroup tg = new ToggleGroup();
    private FeeTableView feeTableView = new FeeTableView(this);
    private boolean okToWriteToDatabase;

    public FeeEditControls(TabFee parent) {
        this.parent = parent;
        setPadding(new Insets(15,5,5,7));
        VBox vBoxSpinner = new VBox();
        VBox vBoxCheckBox = new VBox();
        HBox hBoxTableGroup = new HBox();
        VBox vBoxTable = new VBox();
        HBox hBoxTableHeader = new HBox();
        VBox vBoxTableButtons = new VBox();
        VBox vBoxRadio = new VBox();
        VBox vBoxDisplay = new VBox();
        HBox hBoxDisplay = new HBox();
        Arrays.stream(radioButtonTitles).forEach(title -> {
            rbHash.put(title, new RadioButton(title));
            rbHash.get(title).setToggleGroup(tg);
        });
        vBoxCheckBox.setPadding(new Insets(0,0,0,30));
        vBoxTableButtons.setPadding(new Insets(46,0,0,0));
        vBoxDisplay.setPadding(new Insets(0,20,0,0));
        TitledPane titledPane = new TitledPane("Display Area", vBoxMockItems);

        vBoxSpinner.setSpacing(10);
        vBoxCheckBox.setSpacing(10);
        vBoxTable.setSpacing(10);
        hBoxTableHeader.setSpacing(10);
        vBoxRadio.setSpacing(5);
        hBoxTableGroup.setSpacing(5);
        vBoxTableButtons.setSpacing(5);
        vBoxDisplay.setSpacing(10);

        vBoxSpinner.setPrefWidth(160);
        vBoxCheckBox.setPrefWidth(200);
        vBoxRadio.setPrefWidth(150);

        vBoxTableButtons.setPrefWidth(70);
        hBoxDisplay.setPrefHeight(100);
        HBox.setHgrow(vBoxTable, Priority.ALWAYS);

        vBoxTable.setAlignment(Pos.CENTER_RIGHT);
        vBoxMockItems.setAlignment(Pos.CENTER);
        vBoxMockItems.setId("box-background-light");
        okToWriteToDatabase = false;
        setRadioGroupListener();
        setAutoPopulateCheckBoxListener();
        setIsCreditCheckBoxListener();
        setPriceIsEditableCheckBoxListener();
        setFieldNameTextListener();
        vBoxTableButtons.getChildren().addAll(setAddButton(),setDeleteButton());
        hBoxTableHeader.getChildren().add(fieldNameText);
        vBoxRadio.getChildren()
                .addAll(rbHash.get("Spinner"),rbHash.get("TextField"),rbHash.get("Drop Down"),rbHash.get("None"));
        vBoxSpinner.getChildren().addAll(orderedSpinner,maxQtySpinner);
        vBoxCheckBox.getChildren().addAll(autoPopulate,isCredit,priceIsEditable,vBoxRadio);
        vBoxTable.getChildren().addAll(hBoxTableHeader, feeTableView);
        hBoxTableGroup.getChildren().addAll(vBoxTable,vBoxTableButtons);
        hBoxDisplay.getChildren().addAll(vBoxSpinner,vBoxCheckBox,vBoxRadio);
        vBoxDisplay.getChildren().addAll(hBoxDisplay,titledPane); // add displayed item to bottom
        getChildren().addAll(vBoxDisplay,hBoxTableGroup); // this is hbox
    }

    private Button setDeleteButton() {
        Button deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(70);
        deleteButton.setOnAction(event -> {
            int selectedIndex = feeTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {// make sure something is selected
                FeeDTO feeDTO = fees.get(selectedIndex);
                Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
                conformation.setTitle("Delete Email Entry");
                conformation.setHeaderText(feeDTO.getDescription());
                conformation.setContentText("Are sure you want to delete this fee entry?");
                DialogPane dialogPane = conformation.getDialogPane();
                dialogPane.getStylesheets().add("css/dark/dialogue.css");
                dialogPane.getStyleClass().add("dialog");
                Optional<ButtonType> result = conformation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    SqlDelete.deleteFee(feeDTO);  // if deleted in database
                    feeTableView.getItems().remove(selectedIndex); // remove from GUI
                }
            }
        });
        return deleteButton;
    }

    private Button setAddButton() {
        Button addFeeButton = new Button("Add Fee");
        addFeeButton.setPrefWidth(70);
        addFeeButton.setOnAction(event -> {
            FeeDTO feeDTO = new FeeDTO(parent.selectedFeeRow.dbInvoiceDTO.getFieldName(),
                    "0.00", parent.selectedFeeRow.dbInvoiceDTO.getId(),
                    Integer.parseInt(parent.selectedFeeRow.dbInvoiceDTO.getFiscalYear()), "Description");
            fees.add(feeDTO);
            SqlInsert.addNewFee(feeDTO);
        });
        return addFeeButton;
    }

    public void refreshMockBox() {
            vBoxMockItems.getChildren().clear();
            if(fees.size() > 0) // make sure items has associated fee
                vBoxMockItems.getChildren().addAll(mockHeader, new MockInvoiceItemRow(parent.selectedFeeRow.dbInvoiceDTO, fees.get(0)));
            else {
                FeeDTO feeDTO = new FeeDTO(0,
                        parent.selectedFeeRow.dbInvoiceDTO.getFieldName(),
                        "0.00",0,0,"",true);
                vBoxMockItems.getChildren().addAll(mockHeader, new MockInvoiceItemRow(parent.selectedFeeRow.dbInvoiceDTO, feeDTO));
            }
    }

    public void refreshData() {
        okToWriteToDatabase = false;
        // sets various controls in edit box
        autoPopulate.setSelected(parent.selectedFeeRow.dbInvoiceDTO.isAutoPopulate());
        isCredit.setSelected(parent.selectedFeeRow.dbInvoiceDTO.isCredit());
        priceIsEditable.setSelected(parent.selectedFeeRow.dbInvoiceDTO.isPrice_editable());
        fieldNameText.setText(parent.selectedFeeRow.dbInvoiceDTO.getFieldName());
        // sets spinners in edit box
        setOrderSpinner();
        fees.clear();
        fees.addAll(parent.selectedFeeRow.fees);
        refreshMockBox();
        // sets correct radio button for widget in edit box
        setRadioToMatchDBInvoice();
        okToWriteToDatabase = true;
    }

    private void setFieldNameTextListener() {
        fieldNameText.getTextField().focusedProperty().addListener((observable, oldValue, newValue) -> {
            // changes label to match entered text
            parent.selectedFeeRow.label.setText(fieldNameText.getTextField().getText());
            // must change both db_invoice and matching fees
            updateFees();
            parent.selectedFeeRow.dbInvoiceDTO.setFieldName(fieldNameText.getTextField().getText());
            SqlUpdate.updateDbInvoice(parent.selectedFeeRow.dbInvoiceDTO);
        });
    }

    private void updateFees() {
        if(fees.size() > 0)
            fees.stream().forEach(feeDTO -> {
                feeDTO.setFieldName(fieldNameText.getTextField().getText());
                SqlUpdate.updateFeeRecord(feeDTO);
            });
    }

    public void setOrderSpinner() {
        orderedSpinner.setSpinner(1, parent.getRows().size(), parent.selectedFeeRow.getOrder());
        maxQtySpinner.setSpinner(1,10000, parent.selectedFeeRow.dbInvoiceDTO.getMaxQty());
    }

    public void setOrderSpinnerListener() {
        orderedSpinner.getSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (parent.isOkToWriteToDataBase()) { // don't trigger order spinner if you select another radio button
                FeeRow displacedRow = parent.getRows().stream()
                        .filter(e -> e.dbInvoiceDTO.getOrder() == newValue).findFirst().orElse(null);
                FeeRow changedRow = parent.selectedFeeRow;
                displacedRow.setOrder(oldValue);
                changedRow.setOrder(newValue);
                SqlUpdate.updateDbInvoice(displacedRow.dbInvoiceDTO);
                SqlUpdate.updateDbInvoice(changedRow.dbInvoiceDTO);
                parent.refreshFeeRows();
            }
        });
    }

    private void setRadioToMatchDBInvoice() {  // this sets the radio buttons to the invoice
        if(parent.selectedFeeRow.dbInvoiceDTO.getWidgetType().equals("text-field"))
            rbHash.get("TextField").setSelected(true);
        else if(parent.selectedFeeRow.dbInvoiceDTO.getWidgetType().equals("spinner"))
            rbHash.get("Spinner").setSelected(true);
        else if(parent.selectedFeeRow.dbInvoiceDTO.getWidgetType().equals("combo-box"))
            rbHash.get("Drop Down").setSelected(true);
        else if(parent.selectedFeeRow.dbInvoiceDTO.getWidgetType().equals("none"))
            rbHash.get("None").setSelected(true);
    }

    private void setRadioGroupListener() { // this detects which radio is selected then does something
        tg.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) ->
        {
            RadioButton rb = (RadioButton) new_toggle;
            if(rb.getText().equals("TextField")) {
                parent.selectedFeeRow.dbInvoiceDTO.setWidgetType("text-field");
                setMultipliedWidgets(false);
            }
            else if(rb.getText().equals("Spinner")) {
                parent.selectedFeeRow.dbInvoiceDTO.setWidgetType("spinner");
                setMultipliedWidgets(true);
            }
            else if(rb.getText().equals("Drop Down")) {
                parent.selectedFeeRow.dbInvoiceDTO.setWidgetType("combo-box");
                setMultipliedWidgets(true);
            }
            else if(rb.getText().equals("None")) {
                parent.selectedFeeRow.dbInvoiceDTO.setWidgetType("none");
                setMultipliedWidgets(false);
            }
            refreshMockBox();
            if(okToWriteToDatabase)
            SqlUpdate.updateDbInvoice(parent.selectedFeeRow.dbInvoiceDTO);
            okToWriteToDatabase = true;
        });
    }

    private void setMultipliedWidgets(boolean value) {
        parent.selectedFeeRow.dbInvoiceDTO.setMultiplied(value);
        maxQtySpinner.setVisible(value);
        priceIsEditable.setVisible(value);
    }

    public void setAutoPopulateCheckBoxListener() {
        autoPopulate.selectedProperty()
                .addListener((observable, oldValue, newValue) -> {
                    parent.selectedFeeRow.dbInvoiceDTO.setAutoPopulate(newValue);
                    if(parent.isOkToWriteToDataBase())
                    SqlUpdate.updateDbInvoice(parent.selectedFeeRow.dbInvoiceDTO);
                });
    }

    public void setIsCreditCheckBoxListener() {
        isCredit.selectedProperty().addListener((observable, oldValue, newValue) -> {
            parent.selectedFeeRow.dbInvoiceDTO.setIsCredit(newValue);
            refreshMockBox();
            if(parent.isOkToWriteToDataBase())
            SqlUpdate.updateDbInvoice(parent.selectedFeeRow.dbInvoiceDTO);
        });
    }

    public void setPriceIsEditableCheckBoxListener() {
        priceIsEditable.selectedProperty().addListener((observable, oldValue, newValue) -> {
            parent.selectedFeeRow.dbInvoiceDTO.setPrice_editable(newValue);
            refreshMockBox();
            if(parent.isOkToWriteToDataBase())
            SqlUpdate.updateDbInvoice(parent.selectedFeeRow.dbInvoiceDTO);
        });
    }

    public ObservableList<FeeDTO> getFees() {
        return fees;
    }

    public void setFees(ObservableList<FeeDTO> fees) {
        this.fees = fees;
    }

    public TabFee getTabFee() {
        return parent;
    }
}
