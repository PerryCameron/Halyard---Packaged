package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import com.ecsail.annotation.ColumnName;
import com.ecsail.sql.select.SqlBoat;
import com.ecsail.sql.select.SqlBoatListRadio;
import com.ecsail.sql.select.SqlDbBoat;
import com.ecsail.structures.BoatListDTO;
import com.ecsail.structures.DbBoatDTO;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

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
                Text valueText = new Text(returnFieldValueAsString(field, parent.selectedBoat));
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

    private HBox setUpSearchBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,0,15,0));
        TextField textField = new TextField();
        Text text = new Text("Search");
        text.setId("invoice-text-number");
        hBox.getChildren().addAll(text, textField);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        // this is awesome, stole from stackoverflow.com
        textField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    pause.setOnFinished(event -> fillTableView(textField.getText()));
                    pause.playFromStart();
                }
        );
        return hBox;
    }

    private void fillTableView(String searchTerm) {
        if(!searchTerm.equals("")) {
            parent.searchedBoats.clear();
            parent.searchedBoats.addAll(searchString(searchTerm));
            parent.boatListTableView.setItems(parent.searchedBoats);
        } else { // if search box has been cleared
            parent.boatListTableView.setItems(parent.boats);
        }
    }

    private ObservableList<BoatListDTO> searchString(String searchTerm) {
        String text = searchTerm.toLowerCase();
        ObservableList<BoatListDTO> searchedBoats = FXCollections.observableArrayList();
        boolean hasMatch = false;
            for(BoatListDTO boatListDTO: parent.boats) {
                Field[] fields1 = boatListDTO.getClass().getDeclaredFields();
                Field[] fields2 = boatListDTO.getClass().getSuperclass().getDeclaredFields();
                Field[] allFields = new Field[fields1.length + fields2.length];
                Arrays.setAll(allFields, i -> (i < fields1.length ? fields1[i] : fields2[i - fields1.length]));
                for(Field field: allFields) {
                    field.setAccessible(true);
                    String value = returnFieldValueAsString(field, boatListDTO).toLowerCase();
                    if(value.contains(text)) hasMatch = true;
                };  // add boat DTO here
                if(hasMatch)
                searchedBoats.add(boatListDTO);
                hasMatch = false;
            };
        return searchedBoats;
    }

    private <T> String returnFieldValueAsString(Field field, T pojo) {
        String result;
        try {
            SimpleObjectProperty value = new SimpleObjectProperty(field.get(pojo));
            int begin = value.getValue().toString().indexOf("value: ") + 7;
            int end = value.getValue().toString().indexOf("]");
            result = value.getValue().toString().substring(begin, end);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
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
