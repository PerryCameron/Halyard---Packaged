package com.ecsail.views.tabs.deposits;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HboxInvoiceSectionHead extends HBox {

    private VBox vboxItemType = new VBox();
    private Text typeText = new Text();

    public HboxInvoiceSectionHead(String label) {
        vboxItemType.setPrefWidth(340);
        vboxItemType.setAlignment(Pos.CENTER);
        typeText.setText(label);
        setId("box-frame-dark");
        typeText.setId("invoice-header");
        vboxItemType.getChildren().add(typeText);
        getChildren().add(vboxItemType);
    }
}
