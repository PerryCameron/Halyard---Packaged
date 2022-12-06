package com.ecsail.gui.tabs.fee;

import com.ecsail.structures.FeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HBoxEditControls extends HBox {
    TabFee parent;
    ObservableList<FeeDTO> fees = FXCollections.observableArrayList();
    LabeledSpinner labeledSpinner = new LabeledSpinner("Order");
    LabeledSpinner maxQtySpinner = new LabeledSpinner("Max Qty");
    CheckBox isMultipliedCheckBox = new CheckBox("Multiplied By Qty.");
    CheckBox autoPopulate = new CheckBox("Auto Populate");
    CheckBox isCredit = new CheckBox("Credit");
    CheckBox priceIsEditable = new CheckBox("Price Editable");
    RadioButton rbSpinner = new RadioButton("Spinner");
    RadioButton rbTextField = new RadioButton("Text Field");
    RadioButton rbComboBox = new RadioButton("Drop Down");
    RadioButton rbNone = new RadioButton("None");

    LabeledTextField fieldNameText = new LabeledTextField("Field Name");
    LabeledTextField groupNameText = new LabeledTextField("Group Name");

    HBoxFeeRow hfr;
    public HBoxEditControls(TabFee parent) {
        this.parent = parent;
        setPadding(new Insets(15,5,5,7));
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        HBox hBoxTableGroup = new HBox();
        VBox vBoxTable = new VBox();
        HBox hBoxTableHeader = new HBox();
        VBox vBoxTableButtons = new VBox();
        VBox vBoxRadio = new VBox();
        HBox hBoxOrder = new HBox();
        Button addFeeButton = new Button("Add Fee");
        Button deleteButton = new Button("Delete");
        vBox2.setPadding(new Insets(0,0,0,30));
        vBox1.setSpacing(10);
        vBox2.setSpacing(10);
        vBoxTable.setSpacing(10);
        hBoxTableHeader.setSpacing(10);
        vBoxRadio.setSpacing(5);
        hBoxOrder.setSpacing(5);
        hBoxTableGroup.setSpacing(5);
        vBoxTableButtons.setSpacing(5);
        vBoxTableButtons.setPadding(new Insets(45,0,0,0));

        vBox1.setPrefWidth(250);
        vBox2.setPrefWidth(250);
        addFeeButton.setPrefWidth(70);
        deleteButton.setPrefWidth(70);
        HBox.setHgrow(vBoxTable, Priority.ALWAYS);
        vBoxTableButtons.setPrefWidth(70);
        hBoxOrder.setAlignment(Pos.CENTER_LEFT);
        vBoxTable.setAlignment(Pos.CENTER_RIGHT);

        ToggleGroup tg = new ToggleGroup();
        rbSpinner.setToggleGroup(tg);
        rbTextField.setToggleGroup(tg);
        rbComboBox.setToggleGroup(tg);
        rbNone.setToggleGroup(tg);

        setMultipliedByCheckBoxListener();

        hBoxOrder.getChildren().add(labeledSpinner);
        vBoxTableButtons.getChildren().addAll(addFeeButton,deleteButton);
        hBoxTableHeader.getChildren().addAll(fieldNameText,groupNameText);
        vBoxRadio.getChildren().addAll(rbSpinner,rbTextField,rbComboBox,rbNone);
        vBox1.getChildren().addAll(hBoxOrder,maxQtySpinner);
        vBox2.getChildren().addAll(isMultipliedCheckBox, autoPopulate,isCredit,priceIsEditable,vBoxRadio);

        vBoxTable.getChildren().addAll(hBoxTableHeader, new FeeTableView(this));
        hBoxTableGroup.getChildren().addAll(vBoxTableButtons,vBoxTable);
        getChildren().addAll(vBox1,vBox2,hBoxTableGroup);
    }

    public void refreshData(HBoxFeeRow hBoxFeeRow) {
        this.hfr = hBoxFeeRow;
        autoPopulate.setSelected(hfr.getDbInvoiceDTO().isAutoPopulate());
        isMultipliedCheckBox.setSelected(hfr.getDbInvoiceDTO().isMultiplied());
        isCredit.setSelected(hfr.getDbInvoiceDTO().isIs_credit());
        priceIsEditable.setSelected(hfr.getDbInvoiceDTO().isPrice_editable());
        fieldNameText.setText(hfr.getDbInvoiceDTO().getFieldName());
        if(hfr.getFees().size() > 0) { // we have at least one fee
            groupNameText.setText(hfr.getFees().get(0).getGroupName());
        }
        setRadioButtons();
        setOrderSpinner();
        fees.clear();
        fees.addAll(hfr.getFees());
    }

    private void setOrderSpinner() {
        labeledSpinner.setSpinner(1, parent.getInvoiceItems().size(), hfr.getOrder());
        maxQtySpinner.setSpinner(1,10000,hfr.getDbInvoiceDTO().getMaxQty());
    }

    private void setRadioButtons() {
        switch (hfr.getDbInvoiceDTO().getWidgetType()) {
            case "text-field" -> rbTextField.setSelected(true);
            case "spinner" -> rbSpinner.setSelected(true);
            case "combo-box" -> rbComboBox.setSelected(true);
            case "none" -> rbNone.setSelected(true);
        }
        setVisibleRadioButtons(hfr.getDbInvoiceDTO().isMultiplied());
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
