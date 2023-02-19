package com.ecsail.gui.tabs.roster;

import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;

public class RadioHBox extends HBox {
    private MembershipListRadioDTO mrDTO;
    private RadioButton radioButton;
    private ControlBox parent;
    public RadioHBox(MembershipListRadioDTO r, ControlBox p) {
        this.parent = p;
        this.mrDTO = r;
        this.radioButton = new RadioButton(mrDTO.getLabel());
        setRadioButtonListener();
        radioButton.setSelected(mrDTO.isSelected());
        this.getChildren().add(radioButton);
    }

    private void setRadioButtonListener() {
        radioButton.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if (isNowSelected) {
            parent.selectedRadioBox = this;
            }
        });
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public String getQuery() {
        return mrDTO.getQuery();
    }
}
