package com.ecsail.gui.tabs.deposits;

import com.ecsail.structures.InvoiceItemDTO;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HboxInvoiceHeader extends HBox {

    VBox vboxItemType = new VBox();
    VBox vboxQty = new VBox();
    VBox vboxValue = new VBox();
    Text typeText = new Text();
    Text qtyText = new Text();
    Text valueText = new Text();

    public HboxInvoiceHeader() {
        vboxItemType.setPrefWidth(140);
        vboxQty.setPrefWidth(50);
        vboxValue.setPrefWidth(150);
        vboxItemType.setAlignment(Pos.CENTER_LEFT);
        vboxQty.setAlignment(Pos.CENTER_RIGHT);
        vboxValue.setAlignment(Pos.CENTER_RIGHT);

        typeText.setText("Fees/Credit");
        qtyText.setText("Qty");
        valueText.setText("Total");
        typeText.setId("invoice-header");
        qtyText.setId("invoice-header");
        valueText.setId("invoice-header");
//        this.setId("box-frame-dark");

        vboxItemType.getChildren().add(typeText);
        vboxQty.getChildren().add(qtyText);
        vboxValue.getChildren().add(valueText);
        getChildren().addAll(vboxItemType,vboxQty,vboxValue);
    }
}
