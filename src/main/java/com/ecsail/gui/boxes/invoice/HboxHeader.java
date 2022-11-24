package com.ecsail.gui.boxes.invoice;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class HboxHeader extends HBox {
    VBox vBox1 = new VBox();
    VBox vBox2 = new VBox();
    VBox vBox3 = new VBox();
    Text priceText = new Text();


    public HboxHeader() {
        Font font = Font.font("Verdana", FontWeight.BOLD, 16);
        setSpacing(15);

        // column 1
        vBox1.setAlignment(Pos.CENTER_LEFT);
        Text feeText = new Text("Fee");
        feeText.setFont(font);
        vBox1.getChildren().add(feeText);

        // column 2

        vBox2.setAlignment(Pos.CENTER_RIGHT);
        priceText.setFont(font);
        vBox2.getChildren().add(priceText);

        // column 3

        vBox3.setAlignment(Pos.CENTER_RIGHT);
        Text totalText = new Text("Total");
        totalText.setFont(font);
        vBox3.getChildren().add(totalText);
        getChildren().addAll(vBox1,vBox2,vBox3);
    }

    public void setCommitMode(boolean setCommit) {
        if(setCommit)
            setCommit();
        else
            setEdit();
    }
    private void setEdit() {
        priceText.setText("Price");
        vBox1.setPrefWidth(265);
        vBox2.setPrefWidth(50);
        vBox3.setPrefWidth(70);
    }
    private void setCommit() {
        priceText.setText("Qty");
        vBox1.setPrefWidth(160);
        vBox2.setPrefWidth(50);
        vBox3.setPrefWidth(180);
    }
}
