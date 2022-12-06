package com.ecsail.gui.tabs.fee;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LabeledTextField extends HBox {

    private Text labelText = new Text();
    private TextField textField;
    public LabeledTextField(String label) {
        this.labelText.setText(label);
        this.textField = new TextField();
        VBox vBox1 = new VBox();
        vBox1.setPrefWidth(90);
        textField.setPrefWidth(80);
        VBox vBox2 = new VBox();
        HBox.setHgrow(vBox2, Priority.ALWAYS);
        vBox1.setAlignment(Pos.CENTER_RIGHT);
        vBox2.setAlignment(Pos.CENTER_LEFT);
        vBox1.getChildren().add(labelText);
        vBox2.getChildren().add(textField);

        setSpacing(5);
        getChildren().addAll(vBox1,vBox2);
    }

    public void setText(String text) {
        this.textField.setText(text);
    }

}
