package com.ecsail.gui.tabs.boatview;

import com.ecsail.enums.KeelType;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.DbBoatDTO;
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

    public Row(TabBoatView tabBoatView, DbBoatDTO dbBoatDTO) {
        this.parent = tabBoatView;
        VBox labelBox = new VBox();
        VBox dataBox = new VBox();
        labelBox.setPrefWidth(90);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.getChildren().add(new Text(dbBoatDTO.getName()));
        dataBox.getChildren().add(getDataBoxContent(dbBoatDTO));
        setPadding(new Insets(0, 5, 5, 15));
        getChildren().addAll(labelBox, dataBox);
    }

    private void setTextFieldListener(TextField textField, DbBoatDTO dbBoatDTO) {
        textField.focusedProperty()
                .addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    // focus out
                    if (oldValue) { // we have focused and unfocused
                        SqlUpdate.updateBoat(dbBoatDTO.getFieldName(), parent.boatDTO.getBoat_id(), textField.getText());
                    }
                });
    }

    private void setCheckBoxListener(CheckBox checkBox, DbBoatDTO dbBoatDTO) {
        checkBox.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) ->
                SqlUpdate.updateBoat(parent.boatDTO.getBoat_id(), dbBoatDTO.getFieldName(), isNowSelected));
    }

    private void setComboBoxListener(ComboBox<KeelType> comboBox) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            SqlUpdate.updateBoat(parent.boatDTO.getBoat_id(), newValue.getCode());
        });
    }

    private Node getDataBoxContent(DbBoatDTO dbBoatDTO) {
        if(dbBoatDTO.getControlType().equals("Text")) {
            Text text = new Text(setStringValue(dbBoatDTO.getFieldName()));
            return text;
        } else if(dbBoatDTO.getControlType().equals("TextField")) {
            TextField textField = new TextField(setStringValue(dbBoatDTO.getFieldName()));
            textField.setPrefSize(150, 10);
            setTextFieldListener(textField, dbBoatDTO);
            return textField;
        } else if (dbBoatDTO.getControlType().equals("CheckBox")) {
            CheckBox checkBox = new CheckBox();
            checkBox.setPrefHeight(10);
            checkBox.setSelected(setBooleanValue(dbBoatDTO.getFieldName()));
            setCheckBoxListener(checkBox, dbBoatDTO);
            return checkBox;
        } else if (dbBoatDTO.getControlType().equals("ComboBox")) {
            ComboBox<KeelType> comboBox = new ComboBox<KeelType>();
            comboBox.getItems().setAll(KeelType.values());
            comboBox.setValue(KeelType.getByCode(parent.boatDTO.getKeel()));
            comboBox.setPrefSize(150,10);
            setComboBoxListener(comboBox);
            return comboBox;
        }
        return new Text("undefined");
    }

    private boolean setBooleanValue(String fieldName) {
        if(fieldName.equals("HAS_TRAILER")) return parent.boatDTO.isHasTrailer();
        else if (fieldName.equals("AUX")) return parent.boatDTO.isAux();
        return false;
    }

    private String setStringValue(String fieldName) {
        if(fieldName.equals("BOAT_ID")) return String.valueOf(parent.boatDTO.getBoat_id());
        else if(fieldName.equals("BOAT_NAME")) return parent.boatDTO.getBoat_name();
        else if(fieldName.equals("MANUFACTURER")) return parent.boatDTO.getManufacturer();
        else if(fieldName.equals("MANUFACTURE_YEAR")) return parent.boatDTO.getManufacture_year();
        else if(fieldName.equals("REGISTRATION_NUM")) return parent.boatDTO.getRegistration_num();
        else if(fieldName.equals("MODEL")) return parent.boatDTO.getModel();
        else if(fieldName.equals("PHRF")) return parent.boatDTO.getPhrf();
        else if(fieldName.equals("BOAT_NAME")) return parent.boatDTO.getBoat_name();
        else if(fieldName.equals("SAIL_NUMBER")) return parent.boatDTO.getSail_number();
        else if(fieldName.equals("LENGTH")) return parent.boatDTO.getLength();
        else if(fieldName.equals("WEIGHT")) return parent.boatDTO.getWeight();
        else if(fieldName.equals("KEEL")) return parent.boatDTO.getKeel();
        else if(fieldName.equals("DRAFT")) return parent.boatDTO.getDraft();
        else if(fieldName.equals("BEAM")) return parent.boatDTO.getBeam();
        else if(fieldName.equals("LWL")) return parent.boatDTO.getLwl();
        else
            return "";
    }
}
