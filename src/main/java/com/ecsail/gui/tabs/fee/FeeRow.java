package com.ecsail.gui.tabs.fee;

import com.ecsail.structures.DbInvoiceDTO;
import com.ecsail.structures.FeeDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class FeeRow extends HBox {
    private final TabFee parent;
    private final ArrayList<FeeDTO> fees = new ArrayList<>();
    private FeeDTO selectedFee;
    private RadioButton radioButton;
    private Text label;
    private final DbInvoiceDTO dbInvoiceDTO;

    private final boolean hasFee = false;
    private final IntegerProperty order;

    public FeeRow(TabFee parent, DbInvoiceDTO dbInvoiceDTO) {
        this.parent = parent;
        this.dbInvoiceDTO = dbInvoiceDTO;
        this.order = new SimpleIntegerProperty(0);
        this.order.bindBidirectional(dbInvoiceDTO.orderProperty());
        setSpacing(15);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(addRadioButton(), createLabel());
    }

    private RadioButton addRadioButton() {
        radioButton = new RadioButton();
        // puts this object into the hash map
        parent.getHboxHashMap().put(radioButton, this);
        radioButton.setToggleGroup(parent.getRadioGroup());
        setRadioButtonListener();
        return radioButton;
    }

    private void setRadioButtonListener() {
        radioButton.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if (isNowSelected) {
                System.out.println(dbInvoiceDTO);
                parent.okToWriteToDataBase = false;
                parent.feeEditControls.refreshData();
                parent.okToWriteToDataBase = true;
                this.selectedFee = deriveSelectedFee();
                System.out.println("selectedFee= " + selectedFee);
                System.out.println("fees.size()= " + fees.size());
            }
        });
    }

    private FeeDTO deriveSelectedFee() {
        if(fees.size() > 0) {
            parent.duesLineChart.refreshChart(fees.get(0).getDescription());
            return fees.get(0);
        }
        return null;
    }

    private Text createLabel() {
        Text newLabel = new Text(dbInvoiceDTO.getFieldName());
        newLabel.setId("invoice-text-number");
        return newLabel;
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

    public ArrayList<FeeDTO> getFees() {
        return fees;
    }

    public DbInvoiceDTO getDbInvoiceDTO() {
        return dbInvoiceDTO;
    }

    public void setSelectedFee(FeeDTO selectedFee) {
        this.selectedFee = selectedFee;
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
