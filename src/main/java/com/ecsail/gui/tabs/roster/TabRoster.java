package com.ecsail.gui.tabs.roster;

import com.ecsail.Launcher;
import com.ecsail.excel.Xls_roster;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.sql.select.SqlMembershipListRadio;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.RosterRadioButtonsDTO;
import com.ecsail.dto.RosterSelectDTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TabRoster extends Tab {
	protected MembershipRepository membershipRepository = new MembershipRepositoryImpl();

	protected ObservableList<MembershipListDTO> rosters;
	protected ObservableList<MembershipListDTO> searchedRosters;
	protected TableView<MembershipListDTO> rosterTableView;
	protected ArrayList<MembershipListRadioDTO> radioChoices;
	private final RosterSelectDTO printChoices;
	private final RosterRadioButtonsDTO rb;
	protected String selectedYear;

	VBox controlsBox;

	public TabRoster(ObservableList<MembershipListDTO> a, String sy) {
		super();
		this.rosters = a;
		this.selectedYear = sy;
		this.radioChoices = SqlMembershipListRadio.getRadioChoices();
		this.searchedRosters = FXCollections.observableArrayList();
		this.setText("Roster");
		this.rb = new RosterRadioButtonsDTO();
		// TODO this needs a rework, to complex for what it does.
		this.printChoices = new RosterSelectDTO(sy, false, false, true, false, false, false, false, true, true, true, false,
				false, false, false, false, false, false, false, false, false);
		this.rosterTableView = new RosterTableView(this);
		/////////////////// OBJECTS //////////////////////////
		this.controlsBox = new ControlBox(this);
		HBox hboxSplitScreen = splitScreen(); // inter vbox

		VBox vboxBlue = new VBox();
		VBox vboxTableBox = new VBox();
		VBox vboxRadioButton1 = new VBox();
		VBox vboxRadioButton2 = new VBox();
		HBox hboxExport = new HBox();
		HBox hboxExportFrame = new HBox();

		TabPane tabPane = new TabPane();

		CheckBox c1 = new CheckBox("Membership Id");
		CheckBox c2 = new CheckBox("Last Name");
		CheckBox c3 = new CheckBox("First Name");
		CheckBox c4 = new CheckBox("Join Date");
		CheckBox c5 = new CheckBox("Address");
		CheckBox c6 = new CheckBox("City");
		CheckBox c7 = new CheckBox("State");
		CheckBox c8 = new CheckBox("Zip");
		CheckBox c9 = new CheckBox("Membership Type");
		CheckBox c10 = new CheckBox("Slip");
		CheckBox c11 = new CheckBox("Phone");
		CheckBox c12 = new CheckBox("Email");
		CheckBox c13 = new CheckBox("Subleased To");
		Button buttonXLS = new Button("Export XLS");

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
//		records.setText(rosters.size() + " Records");
		rb.setSameToggleGroup();
		
		rb.getRadioActive().setSelected(true);
		c1.setSelected(true);
		c2.setSelected(true);
		c3.setSelected(true);
		
		tabPane.setId("custom-mini-tab-pane");
		hboxExportFrame.setId("hboxExport");

		hboxExport.setSpacing(10);

		vboxRadioButton1.setSpacing(3);
		vboxRadioButton2.setSpacing(3);
		
		hboxExportFrame.setPadding(new Insets(2, 2, 2, 2));
		hboxExport.setPadding(new Insets(5, 5, 5, 5));
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		hboxSplitScreen.setPadding(new Insets(3, 3, 5, 3));
		vboxRadioButton1.setPadding(new Insets(5, 5, 5, 5));
		vboxRadioButton2.setPadding(new Insets(5, 5, 5, 5));
		
		tabPane.setSide(Side.LEFT);
		VBox.setVgrow(vboxBlue, Priority.ALWAYS);
		VBox.setVgrow(hboxSplitScreen, Priority.ALWAYS);
		VBox.setVgrow(vboxTableBox, Priority.ALWAYS);




		setOnClosed(null);




		//////////////////// LISTENERS //////////////////////////

		/// this listens for a focus on the slips tab and refreshes data everytime.
//		this.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//			if (newValue) {  // focus Gained
//				makeListByRadioButtonChoice();
//				// sets up printing choices for excel export
//				setListType(rb.getTg1().getSelectedToggle().getUserData().toString());			}
//		});
//
//		// makes change when a radio button is selected
//		rb.getTg1().selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
//			if (rb.getTg1().getSelectedToggle() != null) {
//				makeListByRadioButtonChoice();
//				// sets up printing choices for excel export
//				setListType(rb.getTg1().getSelectedToggle().getUserData().toString());
//			}
//		});


		
		buttonXLS.setOnAction((event) ->
				new Xls_roster(rosters, printChoices)
		);

		c1.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setMembership_id(observable.getValue()));

		c2.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setLastName(observable.getValue()));
		
		c3.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setFirstName(observable.getValue()));
		
		c4.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
				printChoices.setJoinDate(observable.getValue()));

		c5.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setStreetAddress(observable.getValue()));

		c6.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setCity(observable.getValue()));
		
		c7.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setState(observable.getValue()));
		
		c8.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setZip(observable.getValue()));
		
		c9.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setMemtype(observable.getValue()));
		
		c10.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setSlip(observable.getValue()));

		c11.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setPhone(observable.getValue()));

		c12.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setEmail(observable.getValue()));

		c13.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setSubleasedto(observable.getValue()));

		
		rosterTableView.setRowFactory(tv -> {
			TableRow<MembershipListDTO> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					// int rowIndex = row.getIndex();
					MembershipListDTO clickedRow = row.getItem();
					Launcher.createMembershipTabForRoster(clickedRow.getMembershipId(), clickedRow.getMsId());
				}
//				if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
//				row.setContextMenu(new rosterContextMenu(row.getItem(), selectedYear));
//				}
			});
			return row;
		});

		//////////////////// SET CONTENT //////////////////////
		
//		vboxCheckBox1.getChildren().addAll(c1, c2, c3, c4);
//		vboxCheckBox2.getChildren().addAll(c5, c6, c7, c8);
//		vboxCheckBox3.getChildren().addAll(c9, c10, c11, c12);
//		vboxCheckBox5.getChildren().addAll(buttonXLS);
//		hboxExportFrame.getChildren().add(hboxExport);
//		hboxExport.getChildren().addAll(vboxCheckBox1, vboxCheckBox2, vboxCheckBox3, vboxCheckBox4, vboxCheckBox5);
		tabPane.getTabs().addAll(new TabStandard(rb), new TabSlipOptions(rb), new TabKayakLists(rb));
		vboxBlue.getChildren().add(hboxSplitScreen);
		setContent(vboxBlue);
	}

	private HBox splitScreen() {
		HBox hBox = new HBox();
		hBox.getChildren().addAll(controlsBox,rosterTableView);
		return hBox;
	}




	//// Class Methods ////
	private void setListType(String type) {
		setChoicesFalse();
		switch (type) {
		case "all":
			printChoices.setAll(true);
		case "active":
			printChoices.setActive(true);
			break;
		case "non-renew":
			printChoices.setNonRenew(true);
			break;
		case "new-members":
			printChoices.setNewMembers(true);
			break;
		case "return":
			printChoices.setNewAndReturnd(true);
			break;
		case "slip-waitlist":
			printChoices.setSlipwait(true);
			break;
		}
	}

	private void setChoicesFalse() {
		printChoices.setAll(false);
		printChoices.setActive(false);
		printChoices.setNonRenew(false);
		printChoices.setNewMembers(false);
		printChoices.setNewAndReturnd(false);
		printChoices.setSlipwait(false);
	}
}
