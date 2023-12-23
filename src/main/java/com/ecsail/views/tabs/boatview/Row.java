package com.ecsail.views.tabs.boatview;

import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.DbBoatSettingsDTO;
import com.ecsail.enums.KeelType;
import com.ecsail.repository.interfaces.BoatRepository;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Row extends HBox {
    TabBoatView parent;
    DbBoatSettingsDTO dbBoatSettingsDTO;
    private final BoatRepository boatRepository;
    private final BoatDTO boatDTO;
    public Row(TabBoatView tabBoatView, DbBoatSettingsDTO dbBoatSettingsDTO) {
        this.parent = tabBoatView;
        this.dbBoatSettingsDTO = dbBoatSettingsDTO;
        this.boatRepository = parent.getBoatRepository();
        this.boatDTO = parent.getBoatDTO();

        if(dbBoatSettingsDTO.isVisible()) {
            VBox labelBox = new VBox();
            VBox dataBox = new VBox();
            labelBox.setPrefWidth(90);
            labelBox.setAlignment(Pos.CENTER_LEFT);
            labelBox.getChildren().add(new Text(dbBoatSettingsDTO.getName()));
            dataBox.getChildren().add(getDataBoxContent(dbBoatSettingsDTO));
            setPadding(new Insets(0, 5, 5, 15));
            getChildren().addAll(labelBox, dataBox);
        }
    }

    private void setTextFieldListener(TextField textField) {
        textField.focusedProperty()
                .addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    // focus out
                    if (oldValue) { // we have focused and unfocused
                        setPojo(dbBoatSettingsDTO.getFieldName(), textField.getText());
                        boatRepository.updateBoat(boatDTO);
                    }
                });
    }

    private void setCheckBoxListener(CheckBox checkBox) {
        checkBox.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
                    switch (dbBoatSettingsDTO.getFieldName()) {
                        case "HAS_TRAILER":
                            boatDTO.setHasTrailer(isNowSelected);
                            break;
                        case "AUX":
                            boatDTO.setAux(isNowSelected);
                            break;
                        // You can add more cases as needed
                    }
                    boatRepository.updateBoat(boatDTO);
        });
    }

    private void setComboBoxListener(ComboBox<KeelType> comboBox) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                setPojo(dbBoatSettingsDTO.getFieldName(), newValue.getCode());
            boatRepository.updateBoat(boatDTO);
        });
    }

    private Node getDataBoxContent(DbBoatSettingsDTO dbBoatDTO) {
        switch (dbBoatDTO.getControlType()) {
            case "Text" -> {
                return new Text(setStringValue(dbBoatDTO.getFieldName()));
            }
            case "TextField" -> {
                TextField textField = new TextField(setStringValue(dbBoatDTO.getFieldName()));
                textField.setPrefSize(150, 10);
                setTextFieldListener(textField);
                return textField;
            }
            case "CheckBox" -> {
                CheckBox checkBox = new CheckBox();
                checkBox.setPrefHeight(10);
                checkBox.setSelected(setBooleanValue(dbBoatDTO.getFieldName()));
                setCheckBoxListener(checkBox);
                return checkBox;
            }
            case "ComboBox" -> {
                ComboBox<KeelType> comboBox = new ComboBox<>();
                comboBox.getItems().setAll(KeelType.values());
                comboBox.setValue(KeelType.getByCode(parent.boatDTO.getKeel()));
                comboBox.setPrefSize(150, 10);
                setComboBoxListener(comboBox);
                return comboBox;
            }
        }
        return new Text("undefined");
    }

    private boolean setBooleanValue(String fieldName) {
        if(fieldName.equals("HAS_TRAILER")) return parent.boatDTO.hasTrailer();
        else if (fieldName.equals("AUX")) return parent.boatDTO.isAux();
        return false;
    }

    private String setStringValue(String fieldName) {
        return switch (fieldName) {
            case "BOAT_ID" -> String.valueOf(parent.boatDTO.getBoatId());
            case "BOAT_NAME" -> parent.boatDTO.getBoatName();
            case "MANUFACTURER" -> parent.boatDTO.getManufacturer();
            case "MANUFACTURE_YEAR" -> parent.boatDTO.getManufactureYear();
            case "REGISTRATION_NUM" -> parent.boatDTO.getRegistrationNum();
            case "MODEL" -> parent.boatDTO.getModel();
            case "PHRF" -> parent.boatDTO.getPhrf();
            case "SAIL_NUMBER" -> parent.boatDTO.getSailNumber();
            case "LENGTH" -> parent.boatDTO.getLoa();
            case "WEIGHT" -> parent.boatDTO.getDisplacement();
            case "KEEL" -> parent.boatDTO.getKeel();
            case "DRAFT" -> parent.boatDTO.getDraft();
            case "BEAM" -> parent.boatDTO.getBeam();
            case "LWL" -> parent.boatDTO.getLwl();
            default -> "";
        };
    }

    private void setPojo(String fieldName, String value) {
            switch (fieldName) {
                case "BOAT_ID" -> boatDTO.setBoatId(Integer.parseInt(value));
                case "BOAT_NAME" -> boatDTO.setBoatName(value);
                case "MANUFACTURER" -> boatDTO.setManufacturer(value);
                case "MANUFACTURE_YEAR" -> boatDTO.setManufactureYear(value);
                case "REGISTRATION_NUM" -> boatDTO.setRegistrationNum(value);
                case "MODEL" -> boatDTO.setModel(value);
                case "PHRF" -> boatDTO.setPhrf(value);
                case "SAIL_NUMBER" -> boatDTO.setSailNumber(value);
                case "LENGTH" -> boatDTO.setLoa(value);
                case "WEIGHT" -> boatDTO.setDisplacement(value);
                case "KEEL" -> boatDTO.setKeel(value);
                case "DRAFT" -> boatDTO.setDraft(value);
                case "BEAM" -> boatDTO.setBeam(value);
                case "LWL" -> boatDTO.setLwl(value);
            }
    }
}
