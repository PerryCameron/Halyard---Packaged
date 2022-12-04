package com.ecsail.gui.tabs.fee;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

    HBoxFeeRow hfr;
    public HBoxEditControls(TabFee tf) {
        this.tf = tf;
        setPadding(new Insets(5,5,5,7));
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        VBox vBox3 = new VBox();
        HBox hBox4 = new HBox();
        VBox vBoxRadio = new VBox();
        HBox hBoxOrder = new HBox();
        VBox vBoxDescription = new VBox();

        vBox2.setPadding(new Insets(0,0,0,10));

        vBox1.setSpacing(10);
        vBox2.setSpacing(10);
        vBox3.setSpacing(10);
        hBox4.setSpacing(10);
        vBoxRadio.setSpacing(5);
        hBoxOrder.setSpacing(5);
        vBoxDescription.setSpacing(5);

        vBox1.setPrefWidth(300);
        orderSpinner.setPrefWidth(65);
        descriptionTextField.setPrefWidth(150);

        hBoxOrder.setAlignment(Pos.CENTER_LEFT);
        vBoxDescription.setAlignment(Pos.CENTER_LEFT);

        ToggleGroup tg = new ToggleGroup();
        rbSpinner.setToggleGroup(tg);
        rbTextField.setToggleGroup(tg);
        rbComboBox.setToggleGroup(tg);
        rbNone.setToggleGroup(tg);

        vBoxDescription.getChildren().addAll(new Text("Description"), descriptionTextField);
        hBoxOrder.getChildren().addAll(new Text("Order"), orderSpinner);
        hBox4.getChildren().addAll(addFeeButton,deleteButton);
        vBoxRadio.getChildren().addAll(rbSpinner,rbTextField,rbComboBox,rbNone);
        vBox1.getChildren().addAll(hBoxOrder,vBoxDescription,isMultipliedCheckBox,vBoxRadio);
        vBox2.getChildren().addAll(autoPopulate,isCredit,maxQty,priceIsEditable);
        vBox3.getChildren().add(hBox4);
        getChildren().addAll(vBox1,vBox2,vBox3);
    }

    public void refreshData(HBoxFeeRow hBoxFeeRow) {
        this.hfr = hBoxFeeRow;
        autoPopulate.setSelected(hfr.getDbInvoiceDTO().isAutoPopulate());
        isMultipliedCheckBox.setSelected(hfr.getDbInvoiceDTO().isMultiplied());
        isCredit.setSelected(hfr.getDbInvoiceDTO().isIs_credit());
        priceIsEditable.setSelected(hfr.getDbInvoiceDTO().isPrice_editable());
        descriptionTextField.setText(hfr.getDbInvoiceDTO().getFieldName());
        setRadioButtons();
        setOrderSpinner();
    }

    private void setOrderSpinner() {
        orderSpinner.setValueFactory(new SpinnerValueFactory
                .IntegerSpinnerValueFactory(1,tf.getInvoiceItems().size(), hfr.getOrder()));
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
    }
}
