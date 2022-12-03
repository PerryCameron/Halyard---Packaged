package com.ecsail.gui.tabs.fee;

import com.ecsail.datacheck.NumberCheck;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.FeeDTO;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HBoxFeeRow extends HBox {
    private TabFee tab;
    private FeeDTO fee;
    private RadioButton radioButton;
    private TextField feeTextField;

    private Text label;
    public HBoxFeeRow(FeeDTO fee, TabFee tab) {
        this.tab=tab;
        this.fee=fee;
        setSpacing(15);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(addRadioButton(), addTextField(), createLabel());
    }

    private RadioButton addRadioButton() {
        radioButton = new RadioButton();
        // puts this object into the hash map
        tab.getHboxHashMap().put(radioButton, this);
        radioButton.setToggleGroup(tab.getRadioGroup());
        radioButton.setSelected(true);
        return radioButton;
    }

    private TextField addTextField() {
        feeTextField = new TextField();
        feeTextField.setPrefWidth(60);
        feeTextField.setText(String.valueOf(fee.getFieldValue()));
        addTextListener(feeTextField);
        return feeTextField;
    }

    private Text createLabel() {
        return label = new Text(fee.getDescription());
    }

    private void addTextListener(TextField t) {
        t.focusedProperty().addListener((observable, exitField, enterField) -> {
            // changed textField value and left field
            if (exitField) {
                // checks value entered, saves it to sql, and updates field (as currency)
                BigDecimal fieldValue = NumberCheck.StringToBigDecimal(t.getText());
                fee.setFieldValue(fieldValue);
                SqlUpdate.updateFeeRecord(fee);
                t.setText(String.valueOf(fieldValue.setScale(2, RoundingMode.HALF_UP)));
            }
        });
    }

    public FeeDTO getFee() {
        return fee;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setLabelText(String newLabel) {
        this.label.setText(newLabel);
    }

    public void setPrice(String newPrice) {
        this.feeTextField.setText(newPrice);
    }
}
