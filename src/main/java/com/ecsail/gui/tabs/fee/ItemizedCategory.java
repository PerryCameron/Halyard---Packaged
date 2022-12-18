package com.ecsail.gui.tabs.fee;

import com.ecsail.structures.FeeDTO;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ItemizedCategory extends VBox {
    MockInvoiceItemRow parent;
    ArrayList<ItemizedCategoryRow> itemizedCategoryRows = new ArrayList<>();
    public ItemizedCategory(MockInvoiceItemRow mockInvoiceItemRow) {
        this.parent = mockInvoiceItemRow;
        this.setSpacing(5);
        for(FeeDTO fee: parent.parent.parent.selectedFeeRow.fees) {
            ItemizedCategoryRow row = new ItemizedCategoryRow(this,fee);
            itemizedCategoryRows.add(row);
            getChildren().add(row);
        }
    }

    public String calculateAllLines() {
        BigDecimal finalTotal = new BigDecimal("0.00");
        for(ItemizedCategoryRow row: itemizedCategoryRows) {
            finalTotal = finalTotal.add(row.lineTotal);
        }
        return String.valueOf(finalTotal);
    }
}
