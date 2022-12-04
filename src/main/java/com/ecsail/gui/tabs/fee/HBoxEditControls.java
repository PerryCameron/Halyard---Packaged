package com.ecsail.gui.tabs.fee;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.formula.atp.Switch;

public class HBoxEditControls extends HBox {
    TabFee tf;
    Spinner<Integer> orderSpinner = new Spinner<>();
    Spinner<Integer> maxQty = new Spinner<>();
    TextField descriptionTextField = new TextField();
    CheckBox isMultipliedCheckBox = new CheckBox("Multiplied By Qty.");
    CheckBox autoPopulate = new CheckBox("Auto Populate");
    CheckBox isCredit = new CheckBox("Credit");
    CheckBox priceIsEditable = new CheckBox("Price Editable");
    RadioButton rbSpinner = new RadioButton("Spinner");
    RadioButton rbTextField = new RadioButton("Text Field");
    RadioButton rbComboBox = new RadioButton("Drop Down");
    RadioButton rbNone = new RadioButton("None");
    Button addFeeButton = new Button("Add Fee");
    Button deleteButton = new Button("Delete");
    public HBoxEditControls(TabFee tf) {
        this.tf = tf;
        setPadding(new Insets(5,5,5,5));
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        VBox vBox3 = new VBox();
        HBox hBox4 = new HBox();
        VBox vBoxRadio = new VBox();

        vBox1.setSpacing(10);
        vBox2.setSpacing(10);
        vBox3.setSpacing(10);
        hBox4.setSpacing(10);
        vBoxRadio.setSpacing(5);

        ToggleGroup tg = new ToggleGroup();
        rbSpinner.setToggleGroup(tg);
        rbTextField.setToggleGroup(tg);
        rbComboBox.setToggleGroup(tg);
        rbNone.setToggleGroup(tg);

        hBox4.getChildren().addAll(addFeeButton,deleteButton);
        vBoxRadio.getChildren().addAll(rbSpinner,rbTextField,rbComboBox,rbNone);
        vBox1.getChildren().addAll(orderSpinner,descriptionTextField,isMultipliedCheckBox,vBoxRadio);
        vBox2.getChildren().addAll(autoPopulate,isCredit,maxQty,priceIsEditable);
        vBox3.getChildren().add(hBox4);
        getChildren().addAll(vBox1,vBox2,vBox3);
    }

    public void refreshData(HBoxFeeRow hBoxFeeRow) {
        autoPopulate.setSelected(hBoxFeeRow.getDbInvoiceDTO().isAutoPopulate());
        isMultipliedCheckBox.setSelected(hBoxFeeRow.getDbInvoiceDTO().isMultiplied());
        isCredit.setSelected(hBoxFeeRow.getDbInvoiceDTO().isIs_credit());
        priceIsEditable.setSelected(hBoxFeeRow.getDbInvoiceDTO().isPrice_editable());
        descriptionTextField.setText(hBoxFeeRow.getDbInvoiceDTO().getFieldName());
        setRadio(hBoxFeeRow.getDbInvoiceDTO().getWidgetType());
    }

    private void setRadio(String radio) {
        switch(radio) {
            case "text-field":
                rbTextField.setSelected(true);
                break;
            case "spinner":
                rbSpinner.setSelected(true);
                break;
            case "combo-box":
                rbComboBox.setSelected(true);
                break;
            case "none":
                rbNone.setSelected(true);
                break;
        }
    }
}
