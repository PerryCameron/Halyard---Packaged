package com.ecsail.widgetfx;
import javafx.geometry.Insets;


public class InsetWidget {

    public static Insets getCommonInsets(double value) {
        return new Insets(value, value, value, value);
    }

    public static Insets getTopInset(double value) {
        return new Insets(value, 0, 0, 0);
    }

    public static Insets getBottomInset(double value) {
        return new Insets(0, 0, value, 0);
    }

    public static Insets getRightInset(double value) {
        return new Insets(0, value, 0, 0);
    }

    public static Insets getLeftInset(double value) {
        return new Insets(0, 0, 0, value);
    }

}
