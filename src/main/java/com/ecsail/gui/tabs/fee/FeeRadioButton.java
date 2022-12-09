package com.ecsail.gui.tabs.fee;

import javafx.scene.control.RadioButton;

public class FeeRadioButton extends RadioButton {

    private String value;

    public FeeRadioButton(String text) {
        super(text);
        this.value = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
