package com.ecsail.gui.tabs.roster;

import com.ecsail.BaseApplication;
import com.ecsail.StringTools;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.MembershipListDTO;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

public class ControlBox extends VBox {
    protected TabRoster parent;
    private TextField textField = new TextField();
    protected RadioHBox selectedRadioBox;
    private boolean isActiveSearch;
    private Text numberOfRecords;

    public ControlBox(TabRoster p) {
        this.parent = p;
        VBox yearBox = createYearBox();
        HBox recordsBox = setUpRecordCountBox();
        VBox radioBox = createRadioBox();
        HBox searchBox = setUpSearchBox();
        this.setSpacing(10);
        this.setPrefWidth(200);
        this.getChildren().addAll(yearBox,recordsBox,searchBox,radioBox);
    }

    private HBox setUpRecordCountBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5,0,15,0));
        Text label = new Text("Records");
        this.numberOfRecords = new Text(String.valueOf(parent.rosters.size()));
        numberOfRecords.setId("invoice-text-number");
        label.setId("invoice-text-label");
        hBox.getChildren().addAll(label,numberOfRecords);
        return hBox;
    }

    private HBox setUpSearchBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,15,15,0));
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
            parent.searchedRosters.clear();
            parent.searchedRosters.addAll(searchString(searchTerm));
            parent.rosterTableView.setItems(parent.searchedRosters);
            isActiveSearch = true;
        } else { // if search box has been cleared
            parent.rosterTableView.setItems(parent.rosters);
            isActiveSearch = false;
        }
        updateRecordCount();
    }

    private void updateRecordCount() {
        String number = null;
        if(isActiveSearch)
            number = String.valueOf(parent.searchedRosters.size()) + "/" + String.valueOf(parent.rosters.size());
        else
            number = String.valueOf(parent.rosters.size());
        numberOfRecords.setText(number);
    }

    private ObservableList<MembershipListDTO> searchString(String searchTerm) {
        String text = searchTerm.toLowerCase();
        ObservableList<MembershipListDTO> searchedBoats = FXCollections.observableArrayList();
        boolean hasMatch = false;
        for(MembershipListDTO membershipListDTO: parent.rosters) {
            Field[] fields1 = membershipListDTO.getClass().getDeclaredFields();
            Field[] fields2 = membershipListDTO.getClass().getSuperclass().getDeclaredFields();
            Field[] allFields = new Field[fields1.length + fields2.length];
            Arrays.setAll(allFields, i -> (i < fields1.length ? fields1[i] : fields2[i - fields1.length]));
            for(Field field: allFields) {
//                System.out.println(field.getName());
                // TODO filter out fields we don't want to search here
                field.setAccessible(true);
                String value = StringTools.returnFieldValueAsString(field, membershipListDTO).toLowerCase();
                if(value.contains(text)) hasMatch = true;
            }  // add boat DTO here
            if(hasMatch)
                searchedBoats.add(membershipListDTO);
            hasMatch = false;
        }
        return searchedBoats;
    }

    private VBox createRadioBox() {
        ToggleGroup tg = new ToggleGroup();
        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(20,0,0,0));
        for(MembershipListRadioDTO radio: parent.radioChoices) {
            if(!radio.getQuery().equals("query")) {
                RadioHBox radioHBox = new RadioHBox(radio, this);
                vBox.getChildren().add(radioHBox);
                radioHBox.getRadioButton().setToggleGroup(tg);
            }
        }
        return vBox;
    }

    private VBox createYearBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        ComboBox<Integer> comboBox = new ComboBox<>();
        for(int i = Integer.parseInt(BaseApplication.selectedYear) + 1; i > 1969; i--) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(1);
        setComboBoxListener(comboBox);
        vBox.getChildren().add(comboBox);
        return vBox;
    }

    private void setComboBoxListener(ComboBox<Integer> comboBox) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            parent.selectedYear = newValue.toString();
            makeListByRadioButtonChoice();
            parent.records.setText(parent.rosters.size() + " Records");
            parent.rosterTableView.sort();
        });
    }

    protected void makeListByRadioButtonChoice() {
        parent.rosters.clear();
        String query = StringTools.ParseQuery(selectedRadioBox.getQuery(), createParameters());
//        System.out.println(query);
        parent.rosters.addAll(SqlMembershipList.getRoster(query));
        parent.records.setText(parent.rosters.size() + " Records");
        parent.rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
    }

    // The object tells it how many parameters there are, all parameters are the selected year
    // the only difference is the number. Some queries require 0 some 5
    // The number of parameters must match the ? in the query and for this use case it is always
    // the selected year.
    private String[] createParameters() {
        String[] params = new String[selectedRadioBox.getNumberOfParameters()];
        for(int i = 0; i < selectedRadioBox.getNumberOfParameters(); i++) {
            params[i] = parent.selectedYear;
        }
        return params;
    }
}
