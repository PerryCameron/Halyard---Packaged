package com.ecsail.gui.tabs.deposits;

import com.ecsail.dto.InvoiceItemDTO;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HboxInvoiceSumItem extends HBox {

    private VBox vboxItemType = new VBox();
    private VBox vboxQty = new VBox();
    private VBox vboxValue = new VBox();
    private Text typeText = new Text();
    private Text qtyText = new Text();
    private Text valueText = new Text();
    private InvoiceItemDTO invoiceSummedItem;

    public HboxInvoiceSumItem(InvoiceItemDTO invoiceSummedItem) {
        this.invoiceSummedItem = invoiceSummedItem;
        vboxItemType.setPrefWidth(140);
        vboxQty.setPrefWidth(50);
        vboxValue.setPrefWidth(150);
        vboxItemType.setAlignment(Pos.CENTER_LEFT);
        vboxQty.setAlignment(Pos.CENTER_RIGHT);
        vboxValue.setAlignment(Pos.CENTER_RIGHT);

        typeText.setText(invoiceSummedItem.getFieldName());
        qtyText.setText(String.valueOf(invoiceSummedItem.getQty()));
        valueText.setText("$" + invoiceSummedItem.getValue());
        typeText.setId("invoice-text-light");
        qtyText.setId("invoice-text-black");
        if(invoiceSummedItem.isCredit())
            valueText.setId("invoice-text-credit");
        else
        valueText.setId("invoice-text-black");
        vboxItemType.getChildren().add(typeText);
        vboxQty.getChildren().add(qtyText);
        vboxValue.getChildren().add(valueText);
        getChildren().addAll(vboxItemType,vboxQty,vboxValue);
    }

    public InvoiceItemDTO getInvoiceSummedItem() {
        return invoiceSummedItem;
    }
}
