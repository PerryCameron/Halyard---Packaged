package com.ecsail.gui.tabs.roster;

import com.ecsail.BaseApplication;
import com.ecsail.StringTools;
import com.ecsail.dto.DbRosterSettingsDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.MembershipListRadioDTO;
import com.ecsail.excel.Xls_roster;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ControlBox extends VBox {
    protected TabRoster parent;
    private TextField textField = new TextField();
    protected RadioHBox selectedRadioBox;
    private boolean isActiveSearch;
    private Text numberOfRecords;
    ArrayList<DbRosterSettingsDTO> rosterSettings;
    ArrayList<SettingsCheckBox> checkBoxes = new ArrayList<>();

    public ControlBox(TabRoster p) {
        this.parent = p;
        this.rosterSettings = (ArrayList<DbRosterSettingsDTO>) parent.settingsRepository.getSearchableListItems();
        HBox recordsBox = setUpRecordCountBox();
        VBox radioBox = createRadioBox();
        HBox searchBox = setUpSearchBox();
        VBox fieldsSelectToSearchBox = setUpFieldSelectedToSearchBox();
        VBox fieldsSelectToExportBox= setUpFieldSelectedToExport();
        this.setSpacing(10);
        this.setPrefWidth(220);
        this.getChildren().addAll(recordsBox,searchBox,fieldsSelectToSearchBox,radioBox,fieldsSelectToExportBox);
    }

    private VBox setUpFieldSelectedToExport() {
        VBox vBox = new VBox();
        VBox checkVBox = new VBox();
        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15,15,0,0));
        buttonVBox.setPadding(new Insets(10,0,0,0));
        Button button = new Button("Export to XLS");
        TitledPane titledPane = new TitledPane();
        titledPane.setText("Export to XLS");
        titledPane.setExpanded(false);
        checkVBox.setSpacing(5);
        for(DbRosterSettingsDTO dto: rosterSettings) {
            SettingsCheckBox checkBox = new SettingsCheckBox(this, dto, "exportable");
            checkBoxes.add(checkBox);
            checkVBox.getChildren().add(checkBox);
        }
        button.setOnAction((event) ->
				new Xls_roster(parent.rosters, rosterSettings, parent.selectedYear,"")
		);
        buttonVBox.getChildren().add(button);
        checkVBox.getChildren().add(buttonVBox);
        titledPane.setContent(checkVBox);
        vBox.getChildren().add(titledPane);
        return vBox;
    }

    private VBox setUpFieldSelectedToSearchBox() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0,15,0,57));
        TitledPane titledPane = new TitledPane();
        titledPane.setText("Searchable Fields");
        titledPane.setExpanded(false);
        VBox checkVBox = new VBox();
        checkVBox.setSpacing(5);
        for(DbRosterSettingsDTO dto: rosterSettings) {
            SettingsCheckBox checkBox = new SettingsCheckBox(this, dto, "searchable");
            checkBoxes.add(checkBox);
            checkVBox.getChildren().add(checkBox);
        }
        titledPane.setContent(checkVBox);
        vBox.getChildren().add(titledPane);
        return vBox;
    }

    private VBox createYearBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(0,10,0,0));
        ComboBox<Integer> comboBox = new ComboBox<>();
        for(int i = Integer.parseInt(BaseApplication.selectedYear) + 1; i > 1969; i--) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(1);
        setComboBoxListener(comboBox);
        vBox.getChildren().add(comboBox);
        return vBox;
    }

    private HBox setUpRecordCountBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        VBox vBox = createYearBox();
        hBox.setPadding(new Insets(5,0,15,0));
        Text label = new Text("Records");
        this.numberOfRecords = new Text(String.valueOf(parent.rosters.size()));
        numberOfRecords.setId("invoice-text-number");
        label.setId("invoice-text-label");
        hBox.getChildren().addAll(vBox,label,numberOfRecords);
        return hBox;
    }

    private HBox setUpSearchBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,15,0,0));
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
            number = parent.searchedRosters.size() + "/" + parent.rosters.size();
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
                System.out.println(field.getName());
                if(fieldIsSearchable(field.getName())) {
                    field.setAccessible(true);
                    String value = StringTools.returnFieldValueAsString(field, membershipListDTO).toLowerCase();
                    if (value.contains(text)) hasMatch = true;
                }
            }  // add boat DTO here
            if(hasMatch)
                searchedBoats.add(membershipListDTO);
            hasMatch = false;
        }
        return searchedBoats;
    }

    private boolean fieldIsSearchable(String fieldName) {
       return checkBoxes.stream()
                .filter(dto -> dto.getDTOFieldName().equals(fieldName))
                .findFirst()
                .map(SettingsCheckBox::isSearchable)
                .orElse(false);
    }

    private VBox createRadioBox() {
        ToggleGroup tg = new ToggleGroup();
        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(20,0,0,20));
        for(MembershipListRadioDTO radio: parent.radioChoices) {
            if(!radio.getMethodName().equals("query")) {
                RadioHBox radioHBox = new RadioHBox(radio, this);
                vBox.getChildren().add(radioHBox);
                radioHBox.getRadioButton().setToggleGroup(tg);
            }
        }
        return vBox;
    }

    private void setComboBoxListener(ComboBox<Integer> comboBox) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            parent.selectedYear = newValue.toString();
                makeListByRadioButtonChoice();
            updateRecordCount();
            parent.rosterTableView.sort();
        });
    }

    protected void makeListByRadioButtonChoice()  {
        parent.rosters.clear();
        Method method = null;
        try {
            method = parent.membershipRepository.getClass().getMethod(selectedRadioBox.getMethod(),String.class);
            parent.rosters.setAll((List<MembershipListDTO>) method.invoke(parent.membershipRepository, parent.selectedYear));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        updateRecordCount();
        parent.rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
    }

}
