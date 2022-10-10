package com.ecsail.gui.customwidgets;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RoundCheckBox extends HBox {
    StackPane stackPane = new StackPane();
    Circle circle2 = new Circle(10,135,8);
    BooleanProperty isSelected = new SimpleBooleanProperty(false);
    String text;
    String responseCode = "200";

    public RoundCheckBox() {
        makeCircles();
    }

    public RoundCheckBox(String text) {
        makeCircles();
    }

    private void makeCircles() {

        Circle circle0 = new Circle(20, 20, 9);
        circle0.setStroke(Color.GRAY);
        Circle circle1 = new Circle(20, 20, 8);
        circle1.setFill(Color.WHITE);
        circle1.setStroke(Color.BLACK);

        circle2.setFill(Color.YELLOW);
        circle2.setStroke(Color.BLACK);
        ObservableList list = stackPane.getChildren();

        //Adding all the nodes to the pane
        list.addAll(circle0,circle1);

        // listener

        stackPane.setOnMouseClicked((mouseEvent) -> {
                    if (stackPane.getChildren().contains(circle2)) {
                        setSelected(false);
                    } else {
                        setSelected(true);
                    }
                }
        );

        isSelectedProperty().addListener((observableValue, aBoolean, t1) -> {
            // makes sure it changed it at the server
            if(responseCode.equals("200")) {
                if (t1) {
                    stackPane.getChildren().add(circle2);
                }
                if (!t1) {
                    // this forces a redraw because of a bug that was never fixed.
                    circle2.toBack();
                    stackPane.getChildren().remove(circle2);
                }
            }
        });
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(stackPane);
    }

    public boolean isSelected() {
        return isSelected.get();
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
