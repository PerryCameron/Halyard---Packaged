package com.ecsail.gui.tabs.fee;

import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LabeledSpinner extends HBox {

    private Text labelText = new Text();
    private Spinner<Integer> spinner;
    public LabeledSpinner(String label) {
        this.labelText.setText(label);
        this.spinner = new Spinner<>();
        spinner.setPrefWidth(65);
        VBox vBox1 = new VBox();
        vBox1.setPrefWidth(90);
        VBox vBox2 = new VBox();
        HBox.setHgrow(vBox2, Priority.ALWAYS);
        vBox1.setAlignment(Pos.CENTER_RIGHT);
        vBox2.setAlignment(Pos.CENTER_LEFT);
        vBox1.getChildren().add(labelText);
        vBox2.getChildren().add(spinner);
        setSpacing(5);
        getChildren().addAll(vBox1,vBox2);
    }

    public void setSpinner(int min, int max, int initialValue) {
            spinner.setValueFactory(new SpinnerValueFactory
                    .IntegerSpinnerValueFactory(min, max, initialValue));
    }
}
