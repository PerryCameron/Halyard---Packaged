package com.ecsail.views.tabs.boatlist;

import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;

public class RadioHBox extends HBox {
    private BoatListRadioDTO mrDTO;
    private RadioButton radioButton;
    private ControlBox parent;

    public RadioHBox(BoatListRadioDTO r, ControlBox p) {
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
            parent.parent.selectedRadioBox = this;
                    parent.makeListByRadioButtonChoice();
            }
        });
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public String getMethod() {
        return mrDTO.getMethod();
    }

    public String getRadioLabel() { return mrDTO.getLabel(); }

}
