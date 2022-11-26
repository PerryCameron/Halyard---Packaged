package com.ecsail.gui.tabs.deposits;

import com.ecsail.structures.InvoiceItemDTO;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HboxInvoiceSumItem extends HBox {

    VBox vboxItemType = new VBox();
    VBox vboxQty = new VBox();
    VBox vboxValue = new VBox();
    Text typeText = new Text();
    Text qtyText = new Text();
    Text valueText = new Text();

    public HboxInvoiceSumItem(InvoiceItemDTO invoiceSummedItem) {
        vboxItemType.setPrefWidth(140);
        vboxQty.setPrefWidth(50);
        vboxValue.setPrefWidth(150);
        vboxItemType.setAlignment(Pos.CENTER_LEFT);
        vboxQty.setAlignment(Pos.CENTER_RIGHT);
        vboxValue.setAlignment(Pos.CENTER_RIGHT);

        typeText.setText(invoiceSummedItem.getItemType());
        qtyText.setText(String.valueOf(invoiceSummedItem.getQty()));
        valueText.setText("$" + invoiceSummedItem.getValue());
        typeText.setId("invoice-text-light");
        qtyText.setId("invoice-text-light");
        valueText.setId("invoice-text-light");

        vboxItemType.getChildren().add(typeText);
        vboxQty.getChildren().add(qtyText);
        vboxValue.getChildren().add(valueText);
        getChildren().addAll(vboxItemType,vboxQty,vboxValue);
    }
}
