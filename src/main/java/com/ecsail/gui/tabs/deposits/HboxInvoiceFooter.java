package com.ecsail.gui.tabs.deposits;

import com.ecsail.structures.InvoiceItemDTO;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class HboxInvoiceFooter extends HBox {

    private VBox vboxItemType = new VBox();
    private VBox vboxQty = new VBox();
    private VBox vboxValue = new VBox();
    private Text typeText = new Text();
    private Text qtyText = new Text();
    private Text valueText = new Text();

    public HboxInvoiceFooter(BigDecimal value, int qty) {
        vboxItemType.setPrefWidth(140);
        vboxQty.setPrefWidth(50);
        vboxValue.setPrefWidth(150);
        vboxItemType.setAlignment(Pos.CENTER_LEFT);
        vboxQty.setAlignment(Pos.CENTER_RIGHT);
        vboxValue.setAlignment(Pos.CENTER_RIGHT);

        typeText.setText("Total");
        qtyText.setText(String.valueOf(qty));
        valueText.setText("$" + value);
        typeText.setId("invoice-header");
        qtyText.setId("invoice-text-label");
        valueText.setId("invoice-text-label");

        vboxItemType.getChildren().add(typeText);
        vboxQty.getChildren().add(qtyText);
        vboxValue.getChildren().add(valueText);
        getChildren().addAll(vboxItemType,vboxQty,vboxValue);
    }
}
