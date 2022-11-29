package com.ecsail.gui.tabs.deposits;

import com.ecsail.structures.DepositDTO;
import com.ecsail.structures.InvoiceItemDTO;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class HboxInvoiceFooter extends HBox {

    private VBox vboxItemType = new VBox();
    private VBox vboxValue = new VBox();
    private Text typeText = new Text();
    private Text valueText = new Text();

    public HboxInvoiceFooter(String label, String value) {
        vboxItemType.setPrefWidth(190);
        vboxValue.setPrefWidth(150);
        vboxItemType.setAlignment(Pos.CENTER_LEFT);
        vboxValue.setAlignment(Pos.CENTER_RIGHT);

        typeText.setText(label);
        valueText.setText("$" + value);
        typeText.setId("invoice-header");
        valueText.setId("invoice-text-label");

        vboxItemType.getChildren().add(typeText);
        vboxValue.getChildren().add(valueText);
        getChildren().addAll(vboxItemType,vboxValue);
    }
}
