package com.ecsail.gui.tabs.membership.fiscal.invoice;

import com.ecsail.gui.tabs.fee.MockItemizedCategoryRow;
import com.ecsail.sql.select.SqlFee;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.FeeDTO;
import com.ecsail.structures.InvoiceItemDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

public class ItemizedCategory extends VBox {
    InvoiceItemRow parent;
    ArrayList<ItemizedCategoryRow> itemizedCategoryRows = new ArrayList<>();
    ObservableList<FeeDTO> feeDTOS;

    public ItemizedCategory(InvoiceItemRow invoiceItemRow) {
        this.parent = invoiceItemRow;
        this.feeDTOS = SqlFee.getAllFeesByFieldNameAndYear(parent.fee);
        this.setSpacing(5);
        this.setPadding(new Insets(5,0,0,0));
        for(FeeDTO fee: feeDTOS) { // not correct fees, need for dbincoive only
            ItemizedCategoryRow row = new ItemizedCategoryRow(this,fee);
            itemizedCategoryRows.add(row);
            getChildren().add(row);

        }
        parent.rowTotal.setText(calculateAllLines());
        parent.updateBalance();
        setId("box-background-light");
    }

    public String calculateAllLines() {
        BigDecimal finalTotal = new BigDecimal("0.00");
        for(ItemizedCategoryRow row: itemizedCategoryRows) {
            finalTotal = finalTotal.add(row.lineTotal);
            System.out.println(row.lineTotal);
        }
        return String.valueOf(finalTotal);
    }
}
