package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import com.ecsail.StringTools;
import com.ecsail.annotation.ColumnName;
import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.DbBoatSettingsDTO;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ControlBox extends VBox {
    private final TabBoatList parent;
    public RadioHBox selectedRadioBox;
    private Text numberOfRecords;
    private final VBox boatDetailsBox;
    private boolean isActiveSearch;
    private TextField textField = new TextField();

    public ControlBox(TabBoatList tabBoatList) {
        this.parent = tabBoatList;
        this.boatDetailsBox = setUpBoatDetailsBox();
        HBox recordCountBox = setUpRecordCountBox();
        VBox frameBox = setUpDetailsBoxFrame();
        VBox radioButtonBox = createRadioBox();
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
        viewBoat.setOnAction(event -> Launcher.openBoatViewTab(parent.selectedBoat));
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
                Text valueText = new Text(StringTools.returnFieldValueAsString(field, parent.selectedBoat));
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
        return parent.boatSettings.stream().filter(setting -> setting.getFieldName().equals(value))
                .map(DbBoatSettingsDTO::getName).findFirst().orElse(value);
    }

    private HBox setUpSearchBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,0,15,0));
        ComboBox<String> comboBox = new ComboBox<>();
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
            isActiveSearch = true;
        } else { // if search box has been cleared
            parent.boatListTableView.setItems(parent.boats);
            isActiveSearch = false;
        }
        updateRecordCount();
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
                    System.out.println(field.getName());
                    field.setAccessible(true);
                    String value = StringTools.returnFieldValueAsString(field, boatListDTO).toLowerCase();
                    if(value.contains(text)) hasMatch = true;
                }  // add boat DTO here
                if(hasMatch)
                    searchedBoats.add(boatListDTO);
                hasMatch = false;
            }
        return searchedBoats;
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

    private void updateRecordCount() {
        String number;
        if(isActiveSearch)
            number = parent.searchedBoats.size() + "/" + parent.boats.size();
        else
            number = String.valueOf(parent.boats.size());
        numberOfRecords.setText(number);
    }

    private VBox createRadioBox() {
        ToggleGroup tg = new ToggleGroup();
        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(0,0,15,0));
        for(BoatListRadioDTO radio: parent.boatListRadioDTOs) {
            if(!radio.getMethod().equals("query")) {
                RadioHBox radioHBox = new RadioHBox(radio, this);
                vBox.getChildren().add(radioHBox);
                radioHBox.getRadioButton().setToggleGroup(tg);
            }
        }
        return vBox;
    }

    protected void makeListByRadioButtonChoice()  {
        parent.boats.clear();
        Method method;
        try {
            method = parent.boatRepository.getClass().getMethod(selectedRadioBox.getMethod());
            parent.boats.setAll((List<BoatListDTO>) method.invoke(parent.boatRepository));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if(!textField.getText().equals("")) fillTableView(textField.getText());
        updateRecordCount();
        parent.boats.sort(Comparator.comparing(BoatListDTO::getBoatId));
    }
}
