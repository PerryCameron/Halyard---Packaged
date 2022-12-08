package com.ecsail.gui.tabs.fee;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MockHeader extends HBox {
    VBox vBox1 = new VBox();
    VBox vBox2 = new VBox();
    VBox vBox3 = new VBox();
    Text priceText = new Text();


    public MockHeader() {
        Font font = Font.font("Verdana", FontWeight.BOLD, 16);
        setSpacing(15);

        // column 1
        vBox1.setAlignment(Pos.CENTER_LEFT);
        Text feeText = new Text("Fee");
        feeText.setFont(font);
        vBox1.getChildren().add(feeText);
        vBox1.setPrefWidth(265);
        // column 2

        vBox2.setAlignment(Pos.CENTER_RIGHT);
        priceText.setFont(font);
        vBox2.getChildren().add(priceText);
        vBox2.setPrefWidth(50);
        // column 3

        vBox3.setAlignment(Pos.CENTER_RIGHT);
        Text totalText = new Text("Total");
        totalText.setFont(font);
        priceText.setText("Price");
        vBox3.setPrefWidth(70);
        vBox3.getChildren().add(totalText);
        getChildren().addAll(vBox1,vBox2,vBox3);
    }

}
