package com.ecsail.gui.tabs.fee;

import com.ecsail.datacheck.NumberCheck;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.DbInvoiceDTO;
import com.ecsail.structures.FeeDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class FeeRow extends HBox {
    private TabFee tab;
    private ArrayList<FeeDTO> fees = new ArrayList<>();
    private FeeDTO selectedFee;
    private RadioButton radioButton;
    private TextField feeTextField;
    private Text label;
    private DbInvoiceDTO dbInvoiceDTO;
    private boolean hasFee = false;
    private IntegerProperty order;

    public FeeRow(TabFee tab, DbInvoiceDTO dbInvoiceDTO) {
        this.tab = tab;
        this.dbInvoiceDTO = dbInvoiceDTO;
        this.order = new SimpleIntegerProperty(0);
        this.order.bindBidirectional(dbInvoiceDTO.orderProperty());
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
        feeTextField.setPrefWidth(70);
        feeTextField.setText("NONE");
        feeTextField.setEditable(false);
        addTextListener(feeTextField);
        return feeTextField;
    }

    private Text createLabel() {
        return label = new Text(dbInvoiceDTO.getFieldName());
    }

    private void addTextListener(TextField t) {
        t.focusedProperty().addListener((observable, exitField, enterField) -> {
            // changed textField value and left field
            if (exitField) {
                // checks value entered, saves it to sql, and updates field (as currency)
                BigDecimal fieldValue = NumberCheck.StringToBigDecimal(t.getText());
                selectedFee.setFieldValue(String.valueOf(fieldValue));
                SqlUpdate.updateFeeRecord(selectedFee);
                t.setText(String.valueOf(fieldValue.setScale(2, RoundingMode.HALF_UP)));
            }
        });
    }

    public FeeDTO getSelectedFee() {
        return selectedFee;
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

    public void setPriceEditable(boolean editable) {
        this.feeTextField.setEditable(editable);
    }

    public String getPrice() {
        return feeTextField.getText();
    }

    public ArrayList<FeeDTO> getFees() {
        return fees;
    }

    public DbInvoiceDTO getDbInvoiceDTO() {
        return dbInvoiceDTO;
    }

    public void setSelectedFee(FeeDTO selectedFee) {
        this.selectedFee = selectedFee;
    }

    public boolean isHasFee() {
        return hasFee;
    }

    public void setHasFee(boolean hasFee) {
        this.hasFee = hasFee;
    }

    public void setForFee() {
        setSelectedFee(fees.get(0));
        setHasFee(true);
        setLabelText(fees.get(0).getDescription());
        setPrice(String.valueOf(fees.get(0).getFieldValue()));
        setPriceEditable(true);
    }

    public final IntegerProperty orderProperty() {
        return this.order;
    }

    public final int getOrder() {
        return this.orderProperty().get();
    }

    public final void setOrder(final int order) {
        this.orderProperty().set(order);
    }

    public Text getLabel() {
        return label;
    }

    public void setLabel(Text label) {
        this.label = label;
    }
}
