package com.ecsail.gui.tabs.roster;

import com.ecsail.BaseApplication;
import com.ecsail.StringTools;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.MembershipListDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.Comparator;

public class ControlBox extends VBox {
    protected TabRoster parent;

    protected RadioHBox selectedRadioBox;
    public ControlBox(TabRoster p) {
        this.parent = p;
        VBox yearBox = createYearBox();
        VBox radioBox = createRadioBox();
        this.setSpacing(10);
        this.setPrefWidth(200);
        this.getChildren().addAll(yearBox,radioBox);
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
        vBox.getChildren().addAll(comboBox, parent.records);
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
        System.out.println(query);
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
