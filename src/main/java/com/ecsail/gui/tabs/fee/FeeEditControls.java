package com.ecsail.gui.tabs.fee;

import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.FeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.HashMap;

public class FeeEditControls extends HBox {
    private TabFee parent;
    private ObservableList<FeeDTO> fees = FXCollections.observableArrayList();
    private LabeledSpinner orderedSpinner = new LabeledSpinner("Order");
    private LabeledSpinner maxQtySpinner = new LabeledSpinner("Max Qty");
    private CheckBox autoPopulate = new CheckBox("Auto Populate");
    private CheckBox isCredit = new CheckBox("Credit");
    private CheckBox priceIsEditable = new CheckBox("Price Editable");
    HashMap<String,RadioButton> rbHash = new HashMap<>();
    String[] radioButtonTitles = {"Spinner","TextField","Drop Down","None"};
    private LabeledTextField fieldNameText = new LabeledTextField("Field Name");
    private LabeledTextField groupNameText = new LabeledTextField("Group Name");
    private FeeRow selectedHBoxFreeRow;
    private MockHeader mockHeader = new MockHeader();
    private VBox vBoxMockItems = new VBox();
    ToggleGroup tg = new ToggleGroup();

    public FeeEditControls(TabFee parent) {
        this.parent = parent;
        setPadding(new Insets(15,5,5,7));
        VBox vBoxSpinner = new VBox();
        VBox vBoxCheckBox = new VBox();
        HBox hBoxTableGroup = new HBox();
        VBox vBoxTable = new VBox();
        HBox hBoxTableHeader = new HBox();
        VBox vBoxTableButtons = new VBox();
        VBox vBoxRadio = new VBox();
        VBox vBoxDisplay = new VBox();
        HBox hBoxDisplay = new HBox();
        Button addFeeButton = new Button("Add Fee");
        Button deleteButton = new Button("Delete");
        Arrays.stream(radioButtonTitles).forEach(title -> {
            rbHash.put(title, new RadioButton(title));
            rbHash.get(title).setToggleGroup(tg);
        });


        vBoxCheckBox.setPadding(new Insets(0,0,0,30));
        vBoxTableButtons.setPadding(new Insets(46,0,0,0));
        vBoxDisplay.setPadding(new Insets(0,20,0,0));
        TitledPane titledPane = new TitledPane("Display Area", vBoxMockItems);

        vBoxSpinner.setSpacing(10);
        vBoxCheckBox.setSpacing(10);
        vBoxTable.setSpacing(10);
        hBoxTableHeader.setSpacing(10);
        vBoxRadio.setSpacing(5);
        hBoxTableGroup.setSpacing(5);
        vBoxTableButtons.setSpacing(5);
        vBoxDisplay.setSpacing(10);

        vBoxSpinner.setPrefWidth(160);
        vBoxCheckBox.setPrefWidth(200);
        vBoxRadio.setPrefWidth(150);
        addFeeButton.setPrefWidth(70);
        deleteButton.setPrefWidth(70);
        vBoxTableButtons.setPrefWidth(70);
        hBoxDisplay.setPrefHeight(100);
        HBox.setHgrow(vBoxTable, Priority.ALWAYS);

        vBoxTable.setAlignment(Pos.CENTER_RIGHT);
        vBoxMockItems.setAlignment(Pos.CENTER);
        vBoxMockItems.setId("box-background-light");

        setRadioGroupListener();
        setAutoPopulateCheckBoxListener();
        setIsCreditCheckBoxListener();
        setPriceIsEditableCheckBoxListener();
        vBoxTableButtons.getChildren().addAll(addFeeButton,deleteButton);
        hBoxTableHeader.getChildren().addAll(fieldNameText,groupNameText);
        vBoxRadio.getChildren()
                .addAll(rbHash.get("Spinner"),rbHash.get("TextField"),rbHash.get("Drop Down"),rbHash.get("None"));
        vBoxSpinner.getChildren().addAll(orderedSpinner,maxQtySpinner);
        vBoxCheckBox.getChildren().addAll(autoPopulate,isCredit,priceIsEditable,vBoxRadio);
        vBoxTable.getChildren().addAll(hBoxTableHeader, new FeeTableView(this));
        hBoxTableGroup.getChildren().addAll(vBoxTable,vBoxTableButtons);
        hBoxDisplay.getChildren().addAll(vBoxSpinner,vBoxCheckBox,vBoxRadio);
        vBoxDisplay.getChildren().addAll(hBoxDisplay,titledPane); // add displayed item to bottom
        getChildren().addAll(vBoxDisplay,hBoxTableGroup); // this is hbox
    }

    public void refreshMockBox() {
            vBoxMockItems.getChildren().clear();
            if(fees.size() > 0) // make sure items has associated fee
                vBoxMockItems.getChildren().addAll(mockHeader, new MockInvoiceItemRow(selectedHBoxFreeRow.getDbInvoiceDTO(), fees.get(0)));
            else {
                FeeDTO feeDTO = new FeeDTO(0,
                        getSelectedHBoxFeeRow().getDbInvoiceDTO().getFieldName(),
                        "0.00",0,0,"","");
                vBoxMockItems.getChildren().addAll(mockHeader, new MockInvoiceItemRow(selectedHBoxFreeRow.getDbInvoiceDTO(), feeDTO));
            }
    }

    public FeeRow getSelectedHBoxFeeRow() {
        return parent.getHboxHashMap().get(parent.getRadioGroup().getSelectedToggle());
    }

    public void refreshData() {
        System.out.println("RefreshData fire!");
        this.selectedHBoxFreeRow = getSelectedHBoxFeeRow();
        autoPopulate.setSelected(selectedHBoxFreeRow.getDbInvoiceDTO().isAutoPopulate());
        isCredit.setSelected(selectedHBoxFreeRow.getDbInvoiceDTO().isCredit());
        priceIsEditable.setSelected(selectedHBoxFreeRow.getDbInvoiceDTO().isPrice_editable());
        fieldNameText.setText(selectedHBoxFreeRow.getDbInvoiceDTO().getFieldName());
        if(selectedHBoxFreeRow.getFees().size() > 0) { // we have at least one fee
            groupNameText.setText(selectedHBoxFreeRow.getFees().get(0).getGroupName());
        }
        setOrderSpinner();
        fees.clear();
        fees.addAll(selectedHBoxFreeRow.getFees());
        refreshMockBox();
        setRadioToMatchDBInvoice();
    }

    public void setOrderSpinner() {
        orderedSpinner.setSpinner(1, parent.getRows().size(), selectedHBoxFreeRow.getOrder());
        maxQtySpinner.setSpinner(1,10000, selectedHBoxFreeRow.getDbInvoiceDTO().getMaxQty());
    }

    public void setOrderSpinnerListener() {
        orderedSpinner.getSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("orderedSpinner listener fire!");
            if (parent.isOrderSpinnerCanChange()) { // don't trigger order spinner if you select another radio button
                FeeRow displacedRow = parent.getRows().stream()
                        .filter(e -> e.getDbInvoiceDTO().getOrder() == newValue).findFirst().orElse(null);
                FeeRow changedRow = getSelectedHBoxFeeRow();
                displacedRow.setOrder(oldValue);
                changedRow.setOrder(newValue);
                SqlUpdate.updateDbInvoice(displacedRow.getDbInvoiceDTO());
                SqlUpdate.updateDbInvoice(changedRow.getDbInvoiceDTO());
                parent.refreshFeeRows();
            }
        });
    }

    private void setRadioToMatchDBInvoice() {  // this sets the radio buttons to the invoice
        if(getSelectedHBoxFeeRow().getDbInvoiceDTO().getWidgetType().equals("text-field"))
            rbHash.get("TextField").setSelected(true);
        else if(getSelectedHBoxFeeRow().getDbInvoiceDTO().getWidgetType().equals("spinner"))
            rbHash.get("Spinner").setSelected(true);
        else if(getSelectedHBoxFeeRow().getDbInvoiceDTO().getWidgetType().equals("combo-box"))
            rbHash.get("Drop-Down").setSelected(true);
        else if(getSelectedHBoxFeeRow().getDbInvoiceDTO().getWidgetType().equals("none"))
            rbHash.get("None").setSelected(true);
    }

    private void setRadioGroupListener() { // this detects which radio is selected then does something
        tg.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) ->
        {
            RadioButton rb = (RadioButton) new_toggle;
            if(rb.getText().equals("TextField")) {
                selectedHBoxFreeRow.getDbInvoiceDTO().setWidgetType("text-field");
                setMultipliedWidgets(false);
            }
            else if(rb.getText().equals("Spinner")) {
                selectedHBoxFreeRow.getDbInvoiceDTO().setWidgetType("spinner");
                setMultipliedWidgets(true);
            }
            else if(rb.getText().equals("Drop Down")) {
                selectedHBoxFreeRow.getDbInvoiceDTO().setWidgetType("combo-box");
                setMultipliedWidgets(true);
            }
            else if(rb.getText().equals("None")) {
                selectedHBoxFreeRow.getDbInvoiceDTO().setWidgetType("none");
                setMultipliedWidgets(false);
            }
            refreshMockBox();
        });
    }

    private void setMultipliedWidgets(boolean value) {
        selectedHBoxFreeRow.getDbInvoiceDTO().setMultiplied(value);
        maxQtySpinner.setVisible(value);
    }

    public void setAutoPopulateCheckBoxListener() {
        autoPopulate.selectedProperty().addListener((observable, oldValue, newValue) -> {
            getSelectedHBoxFeeRow().getDbInvoiceDTO().setAutoPopulate(newValue);
        });
    }

    public void setIsCreditCheckBoxListener() {
        isCredit.selectedProperty().addListener((observable, oldValue, newValue) -> {
            getSelectedHBoxFeeRow().getDbInvoiceDTO().setIsCredit(newValue);
            refreshMockBox();
        });
    }

    public void setPriceIsEditableCheckBoxListener() {
        priceIsEditable.selectedProperty().addListener((observable, oldValue, newValue) -> {
            getSelectedHBoxFeeRow().getDbInvoiceDTO().setPrice_editable(newValue);
            System.out.println(getSelectedHBoxFeeRow().getDbInvoiceDTO());
            refreshMockBox();
        });
    }

    public ObservableList<FeeDTO> getFees() {
        return fees;
    }

    public void setFees(ObservableList<FeeDTO> fees) {
        this.fees = fees;
    }
}
