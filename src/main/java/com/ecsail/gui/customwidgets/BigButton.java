package com.ecsail.gui.customwidgets;

import javafx.scene.control.Button;

public class BigButton extends Button {

    public BigButton(String text) {
        super(text);
        setMaxWidth(Double.MAX_VALUE);
        setPrefHeight(70);
        setId("bigbuttontext");
    }
}
