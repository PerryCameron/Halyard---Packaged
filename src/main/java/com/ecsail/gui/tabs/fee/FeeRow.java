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
    private RadioButton radioButton;
    private final IntegerProperty order;
    protected final ArrayList<FeeDTO> fees = new ArrayList<>();
    protected FeeDTO selectedFee;
    protected Text label;
    protected final DbInvoiceDTO dbInvoiceDTO;


    public FeeRow(TabFee parent, DbInvoiceDTO dbInvoiceDTO) {
        this.parent = parent;
        this.dbInvoiceDTO = dbInvoiceDTO;
        this.order = new SimpleIntegerProperty(0);
        this.order.bindBidirectional(dbInvoiceDTO.orderProperty());
        this.label = createLabel();
        setSpacing(15);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(addRadioButton(), label);
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
                parent.selectedFeeRow = this;
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


    public RadioButton getRadioButton() {
        return radioButton;
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
