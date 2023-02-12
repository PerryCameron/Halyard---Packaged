package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import com.ecsail.annotation.ColumnName;
import com.ecsail.sql.select.SqlBoat;
import com.ecsail.sql.select.SqlBoatListRadio;
import com.ecsail.sql.select.SqlDbBoat;
import com.ecsail.structures.BoatListDTO;
import com.ecsail.structures.DbBoatDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ControlBox extends VBox {
    TabBoats parent;
    ArrayList<BoatListRadioDTO> boatListRadioDTOs;
    private ObservableList<DbBoatDTO> dbBoatDTOS;
    Text numberOfRecords;
    VBox boatDetailsBox;

    public ControlBox(TabBoats tabBoats) {
        this.parent = tabBoats;
        this.boatListRadioDTOs = SqlBoatListRadio.getBoatListRadioDTOs();
        this.dbBoatDTOS = SqlDbBoat.getDbBoat();
        this.boatDetailsBox = setUpBoatDetailsBox();
        VBox frameBox = setUpDetailsBoxFrame();
        VBox radioButtonBox = setUpRadioButtonBox();
        HBox recordCountBox = setUpRecordCountBox();
        HBox searchBox = setUpSearchBox();
        HBox viewBoatBox = setUpViewBoatBox();
        setPadding(new Insets(0,5,0,15));
        frameBox.getChildren().add(boatDetailsBox);
        getChildren().addAll(recordCountBox,searchBox,radioButtonBox, frameBox, viewBoatBox);
    }

    private VBox setUpDetailsBoxFrame() {
        VBox vBox = new VBox();
        HBox hBox = new HBox(); // title
        Text text = new Text("Boat Details");
        text.setId("invoice-header");
        vBox.setStyle("-fx-background-color: #0a0a0a;");
        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 2, 2, 2));
        hBox.getChildren().add(text);
        vBox.getChildren().add(hBox);
        return vBox;
    }

    private HBox setUpViewBoatBox() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15,0,0,0));
        Button viewBoat = new Button("View Boat");
        hBox.getChildren().add(viewBoat);
        viewBoat.setOnAction(event -> {
            Launcher.openBoatViewTab(parent.selectedBoat);
        });
        return hBox;
    }

    protected VBox setUpBoatDetailsBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(5,0,5,5));
        vBox.setId("box-background-light");
        return vBox;
    }

    protected void refreshCurrentBoatDetails() {
        boatDetailsBox.getChildren().clear();
        for (Field field: parent.selectedBoat.getClass().getSuperclass().getDeclaredFields()) {
            ColumnName columnName = field.getAnnotation(ColumnName.class);
            if(columnName != null) {
                HBox hBox = new HBox();
                VBox vBox1 = new VBox();
                VBox vBox2 = new VBox();
                vBox1.setPrefWidth(130);
                field.setAccessible(true);
                Text valueText = new Text(getObjectValue(field));
                Text labelText = new Text(getLabel(columnName.value()));
                labelText.setId("invoice-text-light");
                vBox1.getChildren().add(labelText);
                vBox2.getChildren().add(valueText);
                hBox.getChildren().addAll(vBox1,vBox2);
                boatDetailsBox.getChildren().add(hBox);
            }
        }
    }

    private String getLabel(String value) {
        return dbBoatDTOS.stream().filter(dbBoatDTO -> dbBoatDTO.getFieldName().equals(value))
                .map(dbBoatDTO -> dbBoatDTO.getName()).findFirst().orElse(value);
    }

    @NotNull
    private String getObjectValue(Field field) {
        Object value;
        try {
            value = field.get(parent.selectedBoat);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }  // seems like a cheap hack but can't seem to get value out of a ObjectProperty .get() no work
        return removeLastCharOptional(value.toString().substring(23).trim());
    }

    public static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }

    private HBox setUpSearchBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,0,15,0));
        TextField textField = new TextField();
        Button button = new Button("Search");
        hBox.getChildren().addAll(textField,button);
        button.setOnAction(event -> {
            searchString(textField.getText());
        });
        return hBox;
    }

    private void searchString(String text) {
        ObservableList<BoatListDTO> searchedBoats = FXCollections.observableArrayList();
        if(!text.equals("")) {
            parent.boats.forEach(boatListDTO -> {
                Field[] fields = boatListDTO.getClass().getSuperclass().getDeclaredFields();
                Arrays.stream(fields).forEach(field -> {
                    field.setAccessible(true);
                    getBoatDTOObjectValue(field, boatListDTO);
//                    System.out.println(field.getName() + " " + getBoatDTOObjectValue(field, boatListDTO));
                });
                System.out.println();
            });
        }
    }

    private String getBoatDTOObjectValue(Field field, BoatListDTO boatListDTO) {
        SimpleObjectProperty value;
        try {
            value = new SimpleObjectProperty(field.get(boatListDTO));
            System.out.println("value is" + value.getValue());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }  // seems like a cheap hack but can't seem to get value out of a ObjectProperty .get() no work
        return removeLastCharOptional(value.toString().substring(23).trim());
    }

    private HBox setUpRecordCountBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5,0,15,0));
        Text label = new Text("Records");
        this.numberOfRecords = new Text(String.valueOf(parent.boats.size()));
        numberOfRecords.setId("invoice-text-number");
        label.setId("invoice-text-label");
        hBox.getChildren().addAll(label,numberOfRecords);
        return hBox;
    }

    private void updateRecordCount(int size) {
        numberOfRecords.setText(String.valueOf(size));
    }

    private VBox setUpRadioButtonBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(0,0,15,0));
        vBox.setPrefWidth(400);
        ToggleGroup tg = new ToggleGroup();
        boatListRadioDTOs.forEach(r -> {
            RadioButton rb = new RadioButton(r.getLabel());
            r.setRadioButton(rb);
            rb.setToggleGroup(tg);
            vBox.getChildren().add(rb);
            setRadioButtonListener(r);
            if(r.getOrder() == 1) rb.setSelected(true);
        });
        return vBox;
    }

    private void setRadioButtonListener(BoatListRadioDTO r) {
        r.getRadioButton().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            parent.boats.clear();
            parent.boats.addAll(SqlBoat.getBoatList(r.getQuery()));
            if(numberOfRecords != null) // have we created the object yet?
            updateRecordCount(parent.boats.size());
        });
    }
}
