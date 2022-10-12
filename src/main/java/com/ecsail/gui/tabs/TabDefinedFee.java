package com.ecsail.gui.tabs;


import com.ecsail.BaseApplication;
import com.ecsail.FixInput;
import com.ecsail.charts.FeesLineChart;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlDefinedFee;
import com.ecsail.structures.DefinedFeeDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TabDefinedFee extends Tab {
	int fieldWidth = 60;
	String selectedYear;
	ObservableList<DefinedFeeDTO> definedFees;
	int selectedIndex;
	TextField duesRegularTextField = new TextField();
	TextField duesFamilyTextField = new TextField();
	TextField duesLakeAssociateTextField = new TextField();
	TextField duesSocialTextField = new TextField();
	TextField initiationTextField = new TextField();
	TextField wetSlipTextField = new TextField();
	TextField beachTextField = new TextField();
	TextField winterStorageTextField = new TextField();
	TextField gateKeyTextField = new TextField();
	TextField sailLoftAccessTextField = new TextField();
	TextField sailLoftKeyTextField = new TextField();
	TextField sailSchoolLoftAccessTextField = new TextField();
	TextField sailSchoolLoftKeyTextField = new TextField();
	TextField kayakRackTextField = new TextField();
	TextField kayakBeachRackTextField = new TextField();
	TextField kayakShedTextField = new TextField();
	TextField kayakShedKeyTextField = new TextField();
	TextField workCreditTextField = new TextField();
	FeesLineChart duesLineChart;

	RadioButton duesRegularRadioButton = new RadioButton();
	RadioButton duesFamilyRadioButton = new RadioButton();
	RadioButton duesLakeAssociateRadioButton = new RadioButton();
	RadioButton duesSocialRadioButton = new RadioButton();
	RadioButton initiationRadioButton = new RadioButton();
	RadioButton wetSlipRadioButton = new RadioButton();
	RadioButton beachRadioButton = new RadioButton();
	RadioButton winterStorageRadioButton = new RadioButton();
	RadioButton gateKeyRadioButton = new RadioButton();
	RadioButton sailLoftAccessRadioButton = new RadioButton();
	RadioButton sailLoftKeyRadioButton = new RadioButton();
	RadioButton sailSchoolLoftAccessRadioButton = new RadioButton();
	RadioButton sailSchoolLoftKeyRadioButton = new RadioButton();
	RadioButton kayakRackRadioButton = new RadioButton();
	RadioButton kayakBeachRackRadioButton = new RadioButton();
	RadioButton kayakShedRadioButton = new RadioButton();
	RadioButton kayakShedKeyRadioButton = new RadioButton();
	RadioButton workCreditRadioButton = new RadioButton();

	public TabDefinedFee(String text) {
		super(text);
		this.selectedYear = BaseApplication.selectedYear;
//		this.definedFees.get(selectedIndex)s = SqlSelect.selectDefinedFees(Integer.parseInt(selectedYear));
		this.definedFees =  SqlDefinedFee.getDefinedFees();
		this.duesLineChart = new FeesLineChart(definedFees);
		this.selectedIndex = getSelectedIndex(selectedYear);
		copyObjectToFields();
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		HBox hboxGrey = new HBox();
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		GridPane gridPane = new GridPane();
		ToggleGroup group = new ToggleGroup();

		ComboBox<Integer> comboBox = new ComboBox<>();
		for(int i = Integer.parseInt(BaseApplication.selectedYear) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().select(1);
		comboBox.getStyleClass().add("bigbox");

		int row = 0;
		row = addRow(gridPane,row,duesRegularRadioButton, duesRegularTextField, "Regular Membership Dues");
		row = addRow(gridPane,row,duesFamilyRadioButton, duesFamilyTextField, "Family Membership Dues");
		row = addRow(gridPane,row,duesLakeAssociateRadioButton, duesLakeAssociateTextField, "Lake Associate Dues");
		row = addRow(gridPane,row,duesSocialRadioButton, duesSocialTextField, "Social Membership Dues");
		row = addRow(gridPane,row,initiationRadioButton, initiationTextField, "Initiation Fee");
		row = addRow(gridPane,row,wetSlipRadioButton, wetSlipTextField, "Wet Slip Fee");
		row = addRow(gridPane,row,beachRadioButton, beachTextField, "Beach Spot Fee");
		row = addRow(gridPane,row,winterStorageRadioButton, winterStorageTextField, "Winter Storage Fee");
		row = addRow(gridPane,row,gateKeyRadioButton, gateKeyTextField, "Gate Key Fee");
		row = addRow(gridPane,row,sailLoftAccessRadioButton, sailLoftAccessTextField, "Sail Loft Access Fee");
		row = addRow(gridPane,row,sailLoftKeyRadioButton, sailLoftKeyTextField, "Sail Loft Key Fee");
		row = addRow(gridPane,row,sailSchoolLoftAccessRadioButton, sailSchoolLoftAccessTextField, "Sail School Loft Access Fee");
		row = addRow(gridPane,row,sailSchoolLoftKeyRadioButton, sailSchoolLoftKeyTextField, "Sail School Loft Key Fee");
		row = addRow(gridPane,row,kayakRackRadioButton, kayakRackTextField, "Kayak Rack Fee");
		row = addRow(gridPane,row,kayakBeachRackRadioButton, kayakBeachRackTextField, "Kayak Beach Rack Fee");
		row = addRow(gridPane,row,kayakShedRadioButton, kayakShedTextField, "Kayak Inside Storage Fee");
		row = addRow(gridPane,row,kayakShedKeyRadioButton, kayakShedKeyTextField, "Kayak Inside Storage Key Fee");
		row = addRow(gridPane,row,workCreditRadioButton, workCreditTextField, "Work Credit Amount");

        /////// ATTRIBUTES //////////
        gridPane.setHgap(10);
        gridPane.setVgap(10);

		duesRegularRadioButton.setToggleGroup(group);
		duesFamilyRadioButton.setToggleGroup(group);
		duesLakeAssociateRadioButton.setToggleGroup(group);
		duesSocialRadioButton.setToggleGroup(group);
		initiationRadioButton.setToggleGroup(group);
		wetSlipRadioButton.setToggleGroup(group);
		beachRadioButton.setToggleGroup(group);
		winterStorageRadioButton.setToggleGroup(group);
		gateKeyRadioButton.setToggleGroup(group);
		sailLoftAccessRadioButton.setToggleGroup(group);
		sailLoftKeyRadioButton.setToggleGroup(group);
		sailSchoolLoftAccessRadioButton.setToggleGroup(group);
		sailSchoolLoftKeyRadioButton.setToggleGroup(group);
		kayakRackRadioButton.setToggleGroup(group);
		kayakBeachRackRadioButton.setToggleGroup(group);
		kayakShedRadioButton.setToggleGroup(group);
		kayakShedKeyRadioButton.setToggleGroup(group);
		workCreditRadioButton.setToggleGroup(group);

		duesRegularTextField.setPrefWidth(fieldWidth);
		duesFamilyTextField.setPrefWidth(fieldWidth);
		duesLakeAssociateTextField.setPrefWidth(fieldWidth);
		duesSocialTextField.setPrefWidth(fieldWidth);
		initiationTextField.setPrefWidth(fieldWidth);
		wetSlipTextField.setPrefWidth(fieldWidth);
		beachTextField.setPrefWidth(fieldWidth);
		winterStorageTextField.setPrefWidth(fieldWidth);
		gateKeyTextField.setPrefWidth(fieldWidth);
		sailLoftAccessTextField.setPrefWidth(fieldWidth);
		sailLoftKeyTextField.setPrefWidth(fieldWidth);
		sailSchoolLoftAccessTextField.setPrefWidth(fieldWidth);
		sailSchoolLoftKeyTextField.setPrefWidth(fieldWidth);
		kayakRackTextField.setPrefWidth(fieldWidth);
		kayakBeachRackTextField.setPrefWidth(fieldWidth);
		kayakShedTextField.setPrefWidth(fieldWidth);
		kayakShedKeyTextField.setPrefWidth(fieldWidth);
		workCreditTextField.setPrefWidth(fieldWidth);
        
        vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxGrey.setPadding(new Insets(10,10,10,10));
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		vboxGrey.setSpacing(15);

		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		
        /////////////////// LISTENERS /////////////////////////

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			selectedYear = newValue.toString();
			if (!SqlExists.definedFeeExists(selectedYear)) {
				DefinedFeeDTO newFee = new DefinedFeeDTO(Integer.parseInt(selectedYear), BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
				definedFees.add(newFee);
				SqlInsert.addDefinedFeeRecord(newFee);
			}
			selectedIndex = getSelectedIndex(selectedYear);
			copyObjectToFields();
		});

		duesRegularTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(duesRegularTextField);
		});
		
		duesFamilyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(duesFamilyTextField);
		});
		
		duesLakeAssociateTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(duesLakeAssociateTextField);
		});
		
		duesSocialTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(duesSocialTextField);
		});
		
		initiationTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(initiationTextField);
		});
		
		wetSlipTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(wetSlipTextField);
		});
		
		beachTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(beachTextField);
		});
		
		winterStorageTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(winterStorageTextField);
		});
		
		gateKeyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(gateKeyTextField);
		});
		
		sailLoftAccessTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue)  updateTextField(sailLoftAccessTextField);
		});
		
		sailLoftKeyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(sailLoftKeyTextField);
		});
		
		sailSchoolLoftAccessTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue)  updateTextField(sailSchoolLoftAccessTextField);
		});
		
		sailSchoolLoftKeyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(sailSchoolLoftKeyTextField);
		});
		
		kayakRackTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(kayakRackTextField);
		});

		kayakBeachRackTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(kayakBeachRackTextField);
		});
		
		kayakShedTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(kayakShedTextField);
		});
		
		kayakShedKeyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(kayakShedKeyTextField);
		});
		
		workCreditTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue) updateTextField(workCreditTextField);
		});

		group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> updateChart());

		//////////////// SET CONTENT /////////////////////////

		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(duesLineChart,Priority.ALWAYS);
		vboxGrey.getChildren().addAll(comboBox,gridPane);
		hboxGrey.getChildren().addAll(vboxGrey, duesLineChart);
		vboxPink.getChildren().add(hboxGrey);
		vboxBlue.getChildren().add(vboxPink);
		setContent(vboxBlue);
	}
	
	////////////////////  CLASS METHODS ////////////////////

	private int addRow(GridPane gridPane, int row, RadioButton rb, TextField textField, String label ) {
		gridPane.add(rb,0,row,1,1);
		gridPane.add(textField, 1, row, 1, 1);
		gridPane.add(new Label(label), 2, row, 1, 1);
		row++;
		return row;
	}

	private void updateTextField(TextField textField) {
		// if not a proper number reset to 0
		if(!FixInput.isBigDecimal(textField.getText())) {
			textField.setText("0.00");
		}
		// put value in variable
		BigDecimal field = new BigDecimal(textField.getText());
		// format the variable
		textField.setText(String.valueOf(field.setScale(2, RoundingMode.HALF_UP)));
		// put all fields into an object definedFees.get(selectedIndex)
		copyFieldsToObject();
		// update fields in sql
		SqlUpdate.updateDefinedFeeRecord(definedFees.get(selectedIndex));
		updateChart();
	}
	
	private void updateChart() {
		String update = "Regular Dues";
		if(duesRegularRadioButton.isSelected()) {
			update = "Regular Dues";
		} else if (duesFamilyRadioButton.isSelected()) {
			update = "Family Dues";
		} else if (duesLakeAssociateRadioButton.isSelected()) {
			update = "Lake Associate Dues";
		} else if (duesSocialRadioButton.isSelected()) {
			update = "Social Membership Dues";
		} else if (initiationRadioButton.isSelected()) {
			update = "Initiation Fee";
		} else if (wetSlipRadioButton.isSelected()) {
			update = "Wetslip";
		} else if (beachRadioButton.isSelected()) {
			update = "Beach Parking";
		} else if (winterStorageRadioButton.isSelected()) {
			update = "Winter Storage";
		} else if (gateKeyRadioButton.isSelected()) {
			update = "Extra Gate Key Fee";
		} else if (sailLoftAccessRadioButton.isSelected()) {
			update = "Sail Loft Access";
		} else if (sailLoftKeyRadioButton.isSelected()) {
			update = "Sail Loft Key";
		} else if (sailSchoolLoftAccessRadioButton.isSelected()) {
			update = "Sail School Loft Access";
		} else if (sailSchoolLoftKeyRadioButton.isSelected()) {
			update = "Sail School Loft Key";
		} else if (kayakRackRadioButton.isSelected()) {
			update = "Kayak Rack Fee";
		} else if (kayakBeachRackRadioButton.isSelected()) {
			update = "Kayak Beach Rack Fee";
		} else if (kayakShedRadioButton.isSelected()) {
			update = "Kayak Inside Storage";
		} else if (kayakShedKeyRadioButton.isSelected()) {
			update = "Kayak Inside Storage Key";
		} else if (workCreditRadioButton.isSelected()) {
			update = "Work Credit Amount";

		}

		duesLineChart.refreshChart(update);
	}

	private void copyFieldsToObject() {
		System.out.println("Copying fields to object");
			definedFees.get(selectedIndex).setDues_regular(new BigDecimal(duesRegularTextField.getText()));
			definedFees.get(selectedIndex).setDues_family(new BigDecimal(duesFamilyTextField.getText()));
			definedFees.get(selectedIndex).setDues_lake_associate(new BigDecimal(duesLakeAssociateTextField.getText()));
			definedFees.get(selectedIndex).setDues_social(new BigDecimal(duesSocialTextField.getText()));
			definedFees.get(selectedIndex).setInitiation(new BigDecimal(initiationTextField.getText()));
			definedFees.get(selectedIndex).setWet_slip(new BigDecimal(wetSlipTextField.getText()));
			definedFees.get(selectedIndex).setBeach(new BigDecimal(beachTextField.getText()));
			definedFees.get(selectedIndex).setWinter_storage(new BigDecimal(winterStorageTextField.getText()));
			definedFees.get(selectedIndex).setMain_gate_key(new BigDecimal(gateKeyTextField.getText()));
			definedFees.get(selectedIndex).setSail_loft(new BigDecimal(sailLoftAccessTextField.getText()));
			definedFees.get(selectedIndex).setSail_loft_key(new BigDecimal(sailLoftKeyTextField.getText()));
			definedFees.get(selectedIndex).setSail_school_laser_loft(new BigDecimal(sailSchoolLoftAccessTextField.getText()));
			definedFees.get(selectedIndex).setSail_school_loft_key(new BigDecimal(sailSchoolLoftKeyTextField.getText()));
			definedFees.get(selectedIndex).setKayak_rack(new BigDecimal(kayakRackTextField.getText()));
			definedFees.get(selectedIndex).setKayak_beach_rack(new BigDecimal(kayakBeachRackTextField.getText()));
			definedFees.get(selectedIndex).setKayak_shed(new BigDecimal(kayakShedTextField.getText()));
			definedFees.get(selectedIndex).setKayak_shed_key(new BigDecimal(kayakShedKeyTextField.getText()));
			definedFees.get(selectedIndex).setWork_credit(new BigDecimal(workCreditTextField.getText()));
	}


	private int getSelectedIndex(String selectedYear) {
		int count = 0;
		int index = 0;
		for(DefinedFeeDTO df: definedFees) {
			if(df.getFiscal_year() == Integer.parseInt(selectedYear)) {
				System.out.println("found match=" + count);
				index = count;
			}
			count++;
		}
		return index;
	}
	
	private void copyObjectToFields() {
		duesRegularTextField.setText(definedFees.get(selectedIndex).getDues_regular() + "");
		duesFamilyTextField.setText(definedFees.get(selectedIndex).getDues_family() + "");
		duesLakeAssociateTextField.setText(definedFees.get(selectedIndex).getDues_lake_associate() + "");
		duesSocialTextField.setText(definedFees.get(selectedIndex).getDues_social() + "");
		initiationTextField.setText(definedFees.get(selectedIndex).getInitiation() + "");
		wetSlipTextField.setText(definedFees.get(selectedIndex).getWet_slip() + "");
		beachTextField.setText(definedFees.get(selectedIndex).getBeach() + "");
		winterStorageTextField.setText(definedFees.get(selectedIndex).getWinter_storage() + "");
		gateKeyTextField.setText(definedFees.get(selectedIndex).getMain_gate_key() + "");
		sailLoftAccessTextField.setText(definedFees.get(selectedIndex).getSail_loft() + "");
		sailLoftKeyTextField.setText(definedFees.get(selectedIndex).getSail_loft_key() + "");
		sailSchoolLoftAccessTextField.setText(definedFees.get(selectedIndex).getSail_school_laser_loft() + "");
		sailSchoolLoftKeyTextField.setText(definedFees.get(selectedIndex).getSail_school_loft_key() + "");
		kayakRackTextField.setText(definedFees.get(selectedIndex).getKayak_rack() + "");
		kayakBeachRackTextField.setText(definedFees.get(selectedIndex).getKayak_beach_rack() + "");
		kayakShedTextField.setText(definedFees.get(selectedIndex).getKayak_shed() + "");
		kayakShedKeyTextField.setText(definedFees.get(selectedIndex).getKayak_shed_key() + "");
		workCreditTextField.setText(definedFees.get(selectedIndex).getWork_credit() + "");
	}
	
}
