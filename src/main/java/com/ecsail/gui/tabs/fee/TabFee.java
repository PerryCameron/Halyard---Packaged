package com.ecsail.gui.tabs.fee;

import com.ecsail.BaseApplication;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlFee;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.FeeDTO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;


/// This is the new experimental version
public class TabFee extends Tab {
    private String selectedYear;
    private ArrayList<FeeDTO> feeDTOS;
    // current index selected from feeDTOS
    private RadioButton selectedRadio;
    FeesLineChartEx duesLineChart;
    private final ToggleGroup radioGroup;
    private final HashMap<RadioButton, HBoxFeeRow> hboxHashMap;
    private final VBox vboxFeeRow;
    private final HBox hboxControls;
    private final ComboBox<Integer> comboBox;



    public TabFee(String text) {
        super(text);
        this.selectedYear = BaseApplication.selectedYear;
        this.feeDTOS = SqlFee.getFeesFromYear(Integer.parseInt(selectedYear));
        this.radioGroup = new ToggleGroup();
        this.hboxHashMap = new HashMap<>();
        this.comboBox = addComboBox();
        this.vboxFeeRow = createControlsVBox();
        this.hboxControls = new HBox();
        this.duesLineChart = new FeesLineChartEx();
        addHBoxRows();


        // this is the vbox for organizing all the widgets
        VBox vboxGrey = new VBox();
        HBox hboxGrey = new HBox();
        VBox vboxBlue = new VBox();
        // this creates a pink border around the table
        VBox vboxPink = new VBox();
        // this holds controls


        ////////////////////// ADD PROPERTIES TO OBJECTS //////////////
        hboxControls.setSpacing(10);
        vboxBlue.setId("box-blue");
        vboxBlue.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
        vboxGrey.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setId("box-pink");
        vboxGrey.setPrefHeight(688);
        vboxGrey.setSpacing(15);

        //////////////// LISTENERS ///////////////////
        // gives primary key to selected radio button
        radioGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) ->
        {
            System.out.println(selectedRadio = (RadioButton) new_toggle);
            duesLineChart.refreshChart(hboxHashMap.get(new_toggle).getSelectedFee().getDescription());
        });
        // add listener to each text field

        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> setNewYear(newValue));

        //////////// SETTING CONTENT /////////////

        // adds buttons and year combobox
        addControlBox();
        VBox.setVgrow(vboxPink, Priority.ALWAYS);
        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
        HBox.setHgrow(duesLineChart, Priority.ALWAYS);
        vboxGrey.getChildren().addAll(hboxControls, vboxFeeRow);
        hboxGrey.getChildren().addAll(vboxGrey, duesLineChart);
        vboxPink.getChildren().add(hboxGrey);
        vboxBlue.getChildren().add(vboxPink);
        setContent(vboxBlue);
    }

    private void addControlBox() {
        hboxControls.getChildren().clear();
        if (feeDTOS.size() > 0)
            hboxControls.getChildren().addAll(comboBox, createAddButton(), createEditButton(), createDeleteButton());
            // if we donn't have entries set buttons add, copy fees
        else
            hboxControls.getChildren().addAll(comboBox, createAddButton(), createCopyFeeButton());
    }

    private Button createAddButton() {
        Button addButton = new Button("New");
        addButton.setOnAction((event) -> addNewRow());
        return addButton;
    }

    private Button createDeleteButton() {
        Button delButton = new Button("Delete");
        delButton.setOnAction((event) -> deleteRowIn());
        return delButton;
    }

    private Button createEditButton() {
        Button editButton = new Button(("Edit"));
        editButton.setOnAction((event) -> openEditRow());
        return editButton;
    }

    private Button createCopyFeeButton() {
        Button copyFeesBtn = new Button("Copy Fees");
        copyFeesBtn.setOnAction((event) -> copyPreviousYearsFees());
        return copyFeesBtn;
    }

    private void copyPreviousYearsFees() {
        // clear the current list
        feeDTOS.clear();
        // get next available primary key
        int key = SqlSelect.getNextAvailablePrimaryKey("fee", "FEE_ID");
        // choose year to copy
        int copyYear = Integer.parseInt(selectedYear) + 1;
        // populate that list with objects from copy year
        feeDTOS = SqlFee.getFeesFromYear(copyYear);
        // change the year from old objets to current year and save it to SQL
        for (FeeDTO feeDTO : feeDTOS) {
            feeDTO.setFeeYear(Integer.parseInt(selectedYear));
            feeDTO.setFeeId(key);
            SqlInsert.addNewFee(feeDTO);
            key++;
        }
        // update buttons on gui
        addControlBox();
        // update fees on gui
        addHBoxRows();
    }

    private void setNewYear(Object newValue) {
        // reset selected year
        this.selectedYear = newValue.toString();
        // clear our list
        this.feeDTOS.clear();
        // repopulate our list with new year objects
        this.feeDTOS = SqlFee.getFeesFromYear(Integer.parseInt(selectedYear));
        // clear fx objects
        vboxFeeRow.getChildren().clear();
        // add button objects
        addControlBox();
        // add fee objects
        addHBoxRows();
    }

    private void openEditRow() {
        if (selectedRadio == null) System.out.println("You need to select an index first");
        else {
            createEditHBox(hboxHashMap.get(selectedRadio));
        }
    }

    // java fx controls for editing, no business logic
    private void createEditHBox(HBoxFeeRow hbox) {
        hbox.getChildren().clear();
        Button saveButton = new Button("Save");
        Label description = new Label("Description:");
        TextField descriptionText = new TextField(hbox.getSelectedFee().getDescription());
        CheckBox checkMultiply = new CheckBox("Multiplied by QTY");
        Label maxQty = new Label("Max Qty");
        TextField qtyText = new TextField("0");
        CheckBox price_editable = new CheckBox("Price Editable");
        CheckBox checkCredit = new CheckBox("Is Credit");
        CheckBox autoPopulate = new CheckBox("Auto-populate");
        VBox vboxEditBox = new VBox();
        HBox hboxRow1 = new HBox();
        HBox hboxRow2 = new HBox();
        HBox hboxRow3 = new HBox();
        HBox hboxRow4 = new HBox();
        HBox hboxRow5 = new HBox();
        HBox hboxRow6 = new HBox();
        vboxEditBox.setSpacing(5);
        hboxRow1.setSpacing(5);
        hboxRow3.setSpacing(5);
        hboxRow1.getChildren().addAll(descriptionText, description);
        hboxRow2.getChildren().addAll(checkMultiply);
        hboxRow3.getChildren().addAll(maxQty, qtyText);
        hboxRow4.getChildren().addAll(price_editable);
        hboxRow5.getChildren().addAll(checkCredit);
        hboxRow6.getChildren().addAll(autoPopulate);
        vboxEditBox.getChildren().addAll(hboxRow1, hboxRow2, hboxRow3, hboxRow4, hboxRow5, hboxRow6, saveButton);
        hbox.getChildren().add(vboxEditBox);
        addButtonListener(saveButton, descriptionText);
    }

    private void addButtonListener(Button saveButton, TextField fieldNameText) {
        saveButton.setOnAction((event) -> {
            // update selected object
            hboxHashMap.get(selectedRadio).getSelectedFee().setFieldName(fieldNameText.getText());
            // write object to sql
            SqlUpdate.updateFeeRecord(hboxHashMap.get(selectedRadio).getSelectedFee());
            // update contents of hbox

        });
    }

    private void deleteRowIn() {
        if (selectedRadio == null) System.out.println("You need to select an index first");
        else {
            // remove from database
            SqlDelete.deleteFee(hboxHashMap.get(selectedRadio).getSelectedFee());
            // remove from list
            feeDTOS.remove(hboxHashMap.get(selectedRadio).getSelectedFee());
            // clear HBoxes from column
            vboxFeeRow.getChildren().remove(hboxHashMap.get(selectedRadio));
        }
    }

    private void addNewRow() {
        // get next key
        int key = SqlSelect.getNextAvailablePrimaryKey("fee", "FEE_ID");
        // make DTO object
        FeeDTO feeDTO = new FeeDTO(key, "", new BigDecimal(0), 0, Integer.parseInt(selectedYear), "Enter Description","NONE");
        // add object to database
        SqlInsert.addNewFee(feeDTO);
        // add new object to our list
        feeDTOS.add(feeDTO);
        // add hbox
        vboxFeeRow.getChildren().add(new HBoxFeeRow(feeDTO, this));
    }

    // year combo box at top
    private ComboBox addComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        // creates a combo box with a list of years
        for (int i = Integer.parseInt(BaseApplication.selectedYear) + 1; i > 1969; i--) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(1);
        return comboBox;
    }

    // create our controls vbox and add hbox rows to it
    private VBox createControlsVBox() {
        VBox feeBox = new VBox();
        feeBox.setSpacing(5);
        return feeBox;
    }

    // used to initially place hbox rows into vbox for a given year
    private void addHBoxRows() {
        HashMap<String, HBoxFeeRow> groupMap = new HashMap<>();
        for (FeeDTO fee : feeDTOS)
            if(!fee.getGroupName().equals("NONE")) // check if fee is part of a group
                if(groupMap.containsKey(fee.getGroupName())) // group is already created
                    groupMap.get(fee.getGroupName()).getFees().add(fee); // add to group
                else {  // we need to create new group HBOX
                    HBoxFeeRow newHbox = new HBoxFeeRow(fee, this); // create new group
                    groupMap.put(fee.getGroupName(),newHbox); // map it for next check
                    vboxFeeRow.getChildren().add(newHbox); // add group
                }
            else  // not part of a group
                vboxFeeRow.getChildren().add(new HBoxFeeRow(fee, this));
    }

    public HashMap<RadioButton, HBoxFeeRow> getHboxHashMap() {
        return hboxHashMap;
    }

    public ToggleGroup getRadioGroup() {
        return radioGroup;
    }

}
