package com.ecsail.gui.boxes.invoice;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class HBoxInvoiceTableHead extends HBox {

    public HBoxInvoiceTableHead() {
                Font font = Font.font("Verdana", FontWeight.BOLD, 16);

        setSpacing(15);
        // column 1
        VBox vBox1 = new VBox();
        vBox1.setPrefWidth(140);
        vBox1.setAlignment(Pos.CENTER_LEFT);
        Text feeText = new Text("Fee");
        feeText.setFont(font);
        vBox1.getChildren().add(feeText);

        // column 2
        VBox vBox2 = new VBox();
        vBox2.setPrefWidth(110);
//        vBox2.setStyle("-fx-background-color: #e83115;");  // red

        // column 4
        VBox vBox4 = new VBox();
        vBox4.setPrefWidth(50);
        vBox4.setAlignment(Pos.CENTER_RIGHT);
        Text priceText = new Text("Price");
        priceText.setFont(font);
        vBox4.getChildren().add(priceText);

        // column 5
        VBox vBox5 = new VBox();
        vBox5.setPrefWidth(70);
        vBox5.setAlignment(Pos.CENTER_RIGHT);
        Text totalText = new Text("Total");
        totalText.setFont(font);
        vBox5.getChildren().add(totalText);

        getChildren().addAll(vBox1,vBox2,vBox4,vBox5);
    }
}
