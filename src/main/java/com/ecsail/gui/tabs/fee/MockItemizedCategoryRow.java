package com.ecsail.gui.tabs.fee;

import com.ecsail.structures.FeeDTO;
import com.ecsail.structures.InvoiceItemDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class MockItemizedCategoryRow extends HBox {

    MockItemizedCategory parent;
    BigDecimal lineTotal = new BigDecimal("0.00");
    private Text price;
    public MockItemizedCategoryRow(MockItemizedCategory itemizedCategory, FeeDTO feeDTO) {
        this.parent = itemizedCategory;
        InvoiceItemDTO invoiceItemDTO = parent.parent.feeToMockInvoiceItem(feeDTO);
        invoiceItemDTO.setFieldName(feeDTO.getDescription());
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        VBox vBox3 = new VBox();
        Text label = new Text(invoiceItemDTO.getFieldName());
        label.setId("invoice-text-light");
        price = new Text(invoiceItemDTO.getValue());
        Spinner spinner = new Spinner<>();
        spinner.setPrefWidth(65);
        vBox1.setAlignment(Pos.CENTER_LEFT);
        vBox2.setAlignment(Pos.CENTER_LEFT);
        vBox3.setAlignment(Pos.CENTER_RIGHT);
        vBox1.setPrefWidth(155);
        vBox2.setPrefWidth(65);
        vBox3.setPrefWidth(110);
        setSpinnerListener(spinner, invoiceItemDTO, feeDTO);
        vBox1.getChildren().add(label);
        vBox2.getChildren().add(spinner);
        vBox3.getChildren().add(price);
        getChildren().addAll(vBox1,vBox2,vBox3);
    }
    private void setSpinnerListener(Spinner spinner, InvoiceItemDTO invoiceItemDTO, FeeDTO feeDTO) {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, parent.parent.parent.parent.selectedFeeRow.dbInvoiceDTO.getMaxQty(), invoiceItemDTO.getQty());
        spinner.setValueFactory(spinnerValueFactory);
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            lineTotal = new BigDecimal(feeDTO.getFieldValue()).multiply(BigDecimal.valueOf((Integer) newValue));
            price.setText(String.valueOf(lineTotal));
            parent.parent.getTotal().setText(parent.calculateAllLines());
        });
    }
}
