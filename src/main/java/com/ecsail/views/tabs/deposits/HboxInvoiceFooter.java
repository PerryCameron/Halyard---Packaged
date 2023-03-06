package com.ecsail.views.tabs.deposits;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        valueText.setText("$" + filterText(value));
        typeText.setId("invoice-header");
        valueText.setId("invoice-text-label");

        vboxItemType.getChildren().add(typeText);
        vboxValue.getChildren().add(valueText);
        getChildren().addAll(vboxItemType,vboxValue);
    }

    private String filterText(String text) {
        if(text == null) return "0.00";
        return text;
    }
}
