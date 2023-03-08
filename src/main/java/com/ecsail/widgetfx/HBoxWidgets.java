package com.ecsail.widgetfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class HBoxWidgets {
    public static HBox createHBox(Pos alignment, Insets padding) {
        HBox box = new HBox();
        box.setAlignment(alignment);
        box.setPadding(padding);
        return box;
    }

    public static HBox createHBox(Pos alignment, double prefWidth) {
        HBox box = new HBox();
        box.setAlignment(alignment);
        box.setPrefWidth(prefWidth);
        return box;
    }

    public static HBox createHBox(Pos alignment, double prefWidth, Insets padding) {
        HBox box = new HBox();
        box.setAlignment(alignment);
        box.setPrefWidth(prefWidth);
        box.setPadding(padding);
        return box;
    }

    public static HBox createHBox(Pos alignment, double prefWidth, Insets padding, double spacing) {
        HBox box = new HBox();
        box.setAlignment(alignment);
        box.setPrefWidth(prefWidth);
        box.setPadding(padding);
        box.setSpacing(spacing);
        return box;
    }
    public static HBox createHBox(Insets padding) {
        HBox box = new HBox();
        box.setPadding(padding);
        return box;
    }

    public static HBox createHBox(Insets padding, double spacing) {
        HBox box = new HBox();
        box.setPadding(padding);
        box.setSpacing(spacing);
        return box;
    }

}
