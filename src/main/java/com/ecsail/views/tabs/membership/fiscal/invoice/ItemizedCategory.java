package com.ecsail.views.tabs.membership.fiscal.invoice;

import com.ecsail.dto.FeeDTO;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemizedCategory extends VBox {
    InvoiceItemRow parent;
    ArrayList<ItemizedCategoryRow> itemizedCategoryRows = new ArrayList<>();
    List<FeeDTO> feeDTOS;

    public ItemizedCategory(InvoiceItemRow invoiceItemRow) {
        this.parent = invoiceItemRow;
        this.feeDTOS = parent.parent.getInvoiceRepository().getAllFeesByFieldNameAndYear(parent.fee);
        this.setSpacing(5);
        this.setPadding(new Insets(5,0,0,0));
        for(FeeDTO fee: feeDTOS) { // not correct fees, need for db_invoice only
            ItemizedCategoryRow row = new ItemizedCategoryRow(this,fee);
            itemizedCategoryRows.add(row);
            getChildren().add(row);
        }
        parent.rowTotal.setText(calculateAllLines());
//        parent.updateBalance("Itemized categories created ");
        setId("box-background-light");
    }

    public String calculateAllLines() {
        BigDecimal finalTotal = new BigDecimal("0.00");
        for(ItemizedCategoryRow row: itemizedCategoryRows) {
            finalTotal = finalTotal.add(row.lineTotal);
        }
        return String.valueOf(finalTotal);
    }
}
