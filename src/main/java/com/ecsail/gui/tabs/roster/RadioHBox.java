package com.ecsail.gui.tabs.roster;

import com.ecsail.dto.MembershipListRadioDTO;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;

public class RadioHBox extends HBox {
    private MembershipListRadioDTO mrDTO;
    private RadioButton radioButton;
    private ControlBox parent;
    private int numberOfParameters;

    public RadioHBox(MembershipListRadioDTO r, ControlBox p) {
        this.parent = p;
        this.mrDTO = r;
        this.radioButton = new RadioButton(mrDTO.getLabel());
        this.numberOfParameters = countQuestionMarksUsingStream(mrDTO.getMethodName());
        setRadioButtonListener();
        radioButton.setSelected(mrDTO.isSelected());
        this.getChildren().add(radioButton);
    }

    public static int countQuestionMarksUsingStream(String str) {
        return (int) str.chars().filter(ch -> ch == '?').count();
    }

    private void setRadioButtonListener() {
        radioButton.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if (isNowSelected) {
            parent.selectedRadioBox = this;
                    parent.makeListByRadioButtonChoice();
            }
        });
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public String getMethod() {
        return mrDTO.getMethodName();
    }

    public int getNumberOfParameters() { // number of parameters
        return numberOfParameters;
    }
}
