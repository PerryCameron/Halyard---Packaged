package com.ecsail.gui.tabs.fee;

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

public class FeeEditControls extends HBox {
    private TabFee parent;
    private ObservableList<FeeDTO> fees = FXCollections.observableArrayList();
    private LabeledSpinner orderedSpinner = new LabeledSpinner("Order");
    private LabeledSpinner maxQtySpinner = new LabeledSpinner("Max Qty");
    private CheckBox isMultipliedCheckBox = new CheckBox("Multiplied By Qty.");
    private CheckBox autoPopulate = new CheckBox("Auto Populate");
    private CheckBox isCredit = new CheckBox("Credit");
    private CheckBox priceIsEditable = new CheckBox("Price Editable");
    private RadioButton rbSpinner = new RadioButton("Spinner");
    private RadioButton rbTextField = new RadioButton("Text Field");
    private RadioButton rbComboBox = new RadioButton("Drop Down");
    private RadioButton rbNone = new RadioButton("None");
    private LabeledTextField fieldNameText = new LabeledTextField("Field Name");
    private LabeledTextField groupNameText = new LabeledTextField("Group Name");
    private FeeRow selectedHBoxFreeRow;
    private MockHeader mockHeader = new MockHeader();
    private VBox vBoxMockItems = new VBox();

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
        Button addFeeButton = new Button("Add Fee");
        Button deleteButton = new Button("Delete");
        ToggleGroup tg = new ToggleGroup();

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
        addFeeButton.setPrefWidth(70);
        deleteButton.setPrefWidth(70);
        vBoxTableButtons.setPrefWidth(70);
        hBoxDisplay.setPrefHeight(100);
        HBox.setHgrow(vBoxTable, Priority.ALWAYS);

        vBoxTable.setAlignment(Pos.CENTER_RIGHT);
        vBoxMockItems.setAlignment(Pos.CENTER);
        vBoxMockItems.setId("box-background-light");

        rbSpinner.setToggleGroup(tg);
        rbTextField.setToggleGroup(tg);
        rbComboBox.setToggleGroup(tg);
        rbNone.setToggleGroup(tg);


        setMultipliedByCheckBoxListener();
        vBoxTableButtons.getChildren().addAll(addFeeButton,deleteButton);
        hBoxTableHeader.getChildren().addAll(fieldNameText,groupNameText);
        vBoxRadio.getChildren().addAll(rbSpinner,rbTextField,rbComboBox,rbNone);
        vBoxSpinner.getChildren().addAll(orderedSpinner,maxQtySpinner);
        vBoxCheckBox.getChildren().addAll(isMultipliedCheckBox, autoPopulate,isCredit,priceIsEditable,vBoxRadio);
        vBoxTable.getChildren().addAll(hBoxTableHeader, new FeeTableView(this));
        hBoxTableGroup.getChildren().addAll(vBoxTable,vBoxTableButtons);
        hBoxDisplay.getChildren().addAll(vBoxSpinner,vBoxCheckBox,vBoxRadio);
        vBoxDisplay.getChildren().addAll(hBoxDisplay,titledPane); // add displayed item to bottom
        getChildren().addAll(vBoxDisplay,hBoxTableGroup); // this is hbox
    }

    public void refreshMockBox() {
            vBoxMockItems.getChildren().clear();
            if(fees.size() > 0) // make sure items has associated fee
                vBoxMockItems.getChildren().addAll(mockHeader, new MockInvoiceItemRow(selectedHBoxFreeRow.getDbInvoiceDTO(), fees.get(0)));
    }

    public FeeRow getSelectedHBoxFeeRow() {
        return parent.getHboxHashMap().get(parent.getRadioGroup().getSelectedToggle());
    }

    public void refreshData() {
        System.out.println("RefreshData fire!");
        this.selectedHBoxFreeRow = getSelectedHBoxFeeRow();
        autoPopulate.setSelected(selectedHBoxFreeRow.getDbInvoiceDTO().isAutoPopulate());
        isMultipliedCheckBox.setSelected(selectedHBoxFreeRow.getDbInvoiceDTO().isMultiplied());
        isCredit.setSelected(selectedHBoxFreeRow.getDbInvoiceDTO().isIs_credit());
        priceIsEditable.setSelected(selectedHBoxFreeRow.getDbInvoiceDTO().isPrice_editable());
        fieldNameText.setText(selectedHBoxFreeRow.getDbInvoiceDTO().getFieldName());
        if(selectedHBoxFreeRow.getFees().size() > 0) { // we have at least one fee
            groupNameText.setText(selectedHBoxFreeRow.getFees().get(0).getGroupName());
        }
        setRadioButtons();
        setOrderSpinner();
        fees.clear();
        fees.addAll(selectedHBoxFreeRow.getFees());
        refreshMockBox();
    }

    public void setOrderSpinner() {
        orderedSpinner.setSpinner(1, parent.getRows().size(), selectedHBoxFreeRow.getOrder());
        maxQtySpinner.setSpinner(1,10000, selectedHBoxFreeRow.getDbInvoiceDTO().getMaxQty());
    }

    public void setOrderSpinnerListener() {
        orderedSpinner.getSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("orderedSpinner listener fire!");
            if (parent.isItemCanChange()) { // don't trigger order spinner if you select another radio button
                FeeRow displacedRow = parent.getRows().stream()
                        .filter(e -> e.getDbInvoiceDTO().getOrder() == newValue).findFirst().orElse(null);
                FeeRow changedRow = getSelectedHBoxFeeRow();
                displacedRow.setOrder(oldValue);
                changedRow.setOrder(newValue);
                SqlUpdate.updateDbInvoice(displacedRow.getDbInvoiceDTO());
                SqlUpdate.updateDbInvoice(changedRow.getDbInvoiceDTO());
                parent.refreshFeeRows();
            }
        });
    }

    private void setRadioButtons() {
        switch (selectedHBoxFreeRow.getDbInvoiceDTO().getWidgetType()) {
            case "text-field" -> rbTextField.setSelected(true);
            case "spinner" -> rbSpinner.setSelected(true);
            case "combo-box" -> rbComboBox.setSelected(true);
            case "none" -> rbNone.setSelected(true);
        }
        setVisibleRadioButtons(selectedHBoxFreeRow.getDbInvoiceDTO().isMultiplied());
    }

    private void setVisibleRadioButtons(boolean isMultiplied) {
        rbTextField.setVisible(!isMultiplied);
        rbTextField.setManaged(!isMultiplied);
        rbNone.setVisible(!isMultiplied);
        rbNone.setManaged(!isMultiplied);
        rbSpinner.setVisible(isMultiplied);
        rbSpinner.setManaged(isMultiplied);
        rbComboBox.setVisible(isMultiplied);
        rbComboBox.setManaged(isMultiplied);
        maxQtySpinner.setVisible(isMultiplied);
        maxQtySpinner.setManaged(isMultiplied);
    }

    private void setMultipliedByCheckBoxListener() {
        isMultipliedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> setVisibleRadioButtons(newValue));
    }

    public ObservableList<FeeDTO> getFees() {
        return fees;
    }

    public void setFees(ObservableList<FeeDTO> fees) {
        this.fees = fees;
    }
}
