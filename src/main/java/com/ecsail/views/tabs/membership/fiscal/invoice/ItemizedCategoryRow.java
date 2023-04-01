package com.ecsail.views.tabs.membership.fiscal.invoice;

import com.ecsail.dto.FeeDTO;
import com.ecsail.dto.InvoiceItemDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class ItemizedCategoryRow extends HBox {

    private ItemizedCategory parent;
    protected BigDecimal lineTotal = new BigDecimal("0.00");
    private InvoiceItemDTO categoryName;
    private InvoiceItemDTO invoiceItemDTO;

    private Text price;
    public ItemizedCategoryRow(ItemizedCategory itemizedCategory, FeeDTO feeDTO) {
        this.parent = itemizedCategory;
        this.invoiceItemDTO = getInvoiceItem(feeDTO);
        categoryName = parent.parent.invoiceItemDTO;
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        VBox vBox3 = new VBox();
        Text label = new Text(invoiceItemDTO.getFieldName());
        label.setId("invoice-text-light");
        price = new Text(feeDTO.getFieldValue());
        // calculate our total from database
        lineTotal = getLineTotal(feeDTO.getFieldValue(), invoiceItemDTO.getQty());
        Spinner spinner = new Spinner<>();
        spinner.setPrefWidth(65);
        vBox1.setAlignment(Pos.CENTER_LEFT);
        vBox2.setAlignment(Pos.CENTER_LEFT);
        vBox3.setAlignment(Pos.CENTER_RIGHT);
        vBox1.setPrefWidth(155);
        vBox2.setPrefWidth(65);
        vBox3.setPrefWidth(110);
        setSpinnerListener(spinner, feeDTO);
        vBox1.getChildren().add(label);
        vBox2.getChildren().add(spinner);
        vBox3.getChildren().add(price);
        getChildren().addAll(vBox1,vBox2,vBox3);
    }

    private InvoiceItemDTO getInvoiceItem(FeeDTO feeDTO) {
        for(InvoiceItemDTO invoiceItemDTO1: parent.parent.parent.items) {
            if(feeDTO.getDescription().equals(invoiceItemDTO1.getFieldName())) return invoiceItemDTO1;
        }
        return null;
    }

    private void setSpinnerListener(Spinner spinner, FeeDTO feeDTO) {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, parent.parent.dbInvoiceDTO.getMaxQty(), invoiceItemDTO.getQty());
        spinner.setValueFactory(spinnerValueFactory);
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            // calculates total for the line Qty * fee
            lineTotal = getLineTotal(feeDTO.getFieldValue(), (Integer) newValue);
            // saves line total as the value
            invoiceItemDTO.setValue(String.valueOf(lineTotal));
            System.out.println("Item1 " + invoiceItemDTO.getFieldName() + " Is set to " + invoiceItemDTO.getValue());
            // saves value of spinner as QTY
            invoiceItemDTO.setQty((Integer) newValue);
            // calculates lineTotal for all sub items and gives total
            parent.parent.invoiceItemDTO.setValue(parent.calculateAllLines());
            parent.parent.updateBalance();
        });

        spinner.focusedProperty().addListener((observable, oldValue, focused) -> {
            if(!focused)
                parent.parent.checkIfNotCommittedAndUpdateSql(invoiceItemDTO);
        });
    }

    private BigDecimal getLineTotal(String value, Integer multiple) {
        return new BigDecimal(value).multiply(BigDecimal.valueOf(multiple));
    }
}
