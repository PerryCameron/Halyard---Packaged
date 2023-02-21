package com.ecsail.gui.tabs.fee;

import com.ecsail.dto.FeeDTO;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MockItemizedCategory extends VBox {
    MockInvoiceItemRow parent;
    ArrayList<MockItemizedCategoryRow> itemizedCategoryRows = new ArrayList<>();
    public MockItemizedCategory(MockInvoiceItemRow mockInvoiceItemRow) {
        this.parent = mockInvoiceItemRow;
        this.setSpacing(5);
        this.setPadding(new Insets(5,0,0,0));
        for(FeeDTO fee: parent.parent.parent.selectedFeeRow.fees) {
            MockItemizedCategoryRow row = new MockItemizedCategoryRow(this,fee);
            itemizedCategoryRows.add(row);
            getChildren().add(row);
        }
        setId("box-background-light");
    }

    public String calculateAllLines() {
        BigDecimal finalTotal = new BigDecimal("0.00");
        for(MockItemizedCategoryRow row: itemizedCategoryRows) {
            finalTotal = finalTotal.add(row.lineTotal);
        }
        return String.valueOf(finalTotal);
    }
}
