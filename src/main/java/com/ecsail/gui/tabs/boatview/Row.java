package com.ecsail.gui.tabs.boatview;

import com.ecsail.enums.KeelType;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.dto.DbBoatDTO;
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
    DbBoatDTO dbBoatDTO;
    public Row(TabBoatView tabBoatView, DbBoatDTO dbBoatDTO) {
        this.parent = tabBoatView;
        this.dbBoatDTO = dbBoatDTO;
        VBox labelBox = new VBox();
        VBox dataBox = new VBox();
        labelBox.setPrefWidth(90);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.getChildren().add(new Text(dbBoatDTO.getName()));
        dataBox.getChildren().add(getDataBoxContent(dbBoatDTO));
        setPadding(new Insets(0, 5, 5, 15));
        getChildren().addAll(labelBox, dataBox);
    }

    private void setTextFieldListener(TextField textField) {
        textField.focusedProperty()
                .addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    // focus out
                    if (oldValue) { // we have focused and unfocused
                        SqlUpdate.updateBoat(dbBoatDTO.getFieldName(), parent.boatDTO.getBoatId(), textField.getText());
                        if(parent.fromList)
                        setPojo(dbBoatDTO.getFieldName(), textField.getText());
                    }
                });
    }

    private void setCheckBoxListener(CheckBox checkBox) {
        checkBox.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) ->
                SqlUpdate.updateBoat(parent.boatDTO.getBoatId(), dbBoatDTO.getFieldName(), isNowSelected));
//                setPojo(dbBoatDTO.getFieldName(), isNowSelected);
    }

    private void setComboBoxListener(ComboBox<KeelType> comboBox) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            SqlUpdate.updateBoat(parent.boatDTO.getBoatId(), newValue.getCode());
            if(parent.fromList)
            setPojo(dbBoatDTO.getFieldName(), newValue.getCode());
        });
    }

    private Node getDataBoxContent(DbBoatDTO dbBoatDTO) {
        if(dbBoatDTO.getControlType().equals("Text")) {
            Text text = new Text(setStringValue(dbBoatDTO.getFieldName()));
            return text;
        } else if(dbBoatDTO.getControlType().equals("TextField")) {
            TextField textField = new TextField(setStringValue(dbBoatDTO.getFieldName()));
            textField.setPrefSize(150, 10);
            setTextFieldListener(textField);
            return textField;
        } else if (dbBoatDTO.getControlType().equals("CheckBox")) {
            CheckBox checkBox = new CheckBox();
            checkBox.setPrefHeight(10);
            checkBox.setSelected(setBooleanValue(dbBoatDTO.getFieldName()));
            setCheckBoxListener(checkBox);
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
        if(fieldName.equals("BOAT_ID")) return String.valueOf(parent.boatDTO.getBoatId());
        else if(fieldName.equals("BOAT_NAME")) return parent.boatDTO.getBoatName();
        else if(fieldName.equals("MANUFACTURER")) return parent.boatDTO.getManufacturer();
        else if(fieldName.equals("MANUFACTURE_YEAR")) return parent.boatDTO.getManufactureYear();
        else if(fieldName.equals("REGISTRATION_NUM")) return parent.boatDTO.getRegistrationNum();
        else if(fieldName.equals("MODEL")) return parent.boatDTO.getModel();
        else if(fieldName.equals("PHRF")) return parent.boatDTO.getPhrf();
        else if(fieldName.equals("BOAT_NAME")) return parent.boatDTO.getBoatName();
        else if(fieldName.equals("SAIL_NUMBER")) return parent.boatDTO.getSailNumber();
        else if(fieldName.equals("LENGTH")) return parent.boatDTO.getLength();
        else if(fieldName.equals("WEIGHT")) return parent.boatDTO.getWeight();
        else if(fieldName.equals("KEEL")) return parent.boatDTO.getKeel();
        else if(fieldName.equals("DRAFT")) return parent.boatDTO.getDraft();
        else if(fieldName.equals("BEAM")) return parent.boatDTO.getBeam();
        else if(fieldName.equals("LWL")) return parent.boatDTO.getLwl();
        else
            return "";
    }

    private String setPojo(String fieldName, String value) {
        if (parent.fromList) {
            if (fieldName.equals("BOAT_ID")) parent.boatListDTO.setBoatId(Integer.parseInt(value));
            else if (fieldName.equals("BOAT_NAME")) parent.boatListDTO.setBoatName(value);
            else if (fieldName.equals("MANUFACTURER")) parent.boatListDTO.setManufacturer(value);
            else if (fieldName.equals("MANUFACTURE_YEAR")) parent.boatListDTO.setManufactureYear(value);
            else if (fieldName.equals("REGISTRATION_NUM")) parent.boatListDTO.setRegistrationNum(value);
            else if (fieldName.equals("MODEL")) parent.boatListDTO.setModel(value);
            else if (fieldName.equals("PHRF")) parent.boatListDTO.setPhrf(value);
            else if (fieldName.equals("BOAT_NAME")) parent.boatListDTO.setBoatName(value);
            else if (fieldName.equals("SAIL_NUMBER")) parent.boatListDTO.setSailNumber(value);
            else if (fieldName.equals("LENGTH")) parent.boatListDTO.setLength(value);
            else if (fieldName.equals("WEIGHT")) parent.boatListDTO.setWeight(value);
            else if (fieldName.equals("KEEL")) parent.boatListDTO.setKeel(value);
            else if (fieldName.equals("DRAFT")) parent.boatListDTO.setDraft(value);
            else if (fieldName.equals("BEAM")) parent.boatListDTO.setBeam(value);
            else if (fieldName.equals("LWL")) parent.boatListDTO.setLwl(value);
        }
        return "";
    }

}
