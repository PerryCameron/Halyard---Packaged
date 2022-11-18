package com.ecsail.gui.tabs;

import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.Launcher;
import com.ecsail.excel.Xls_roster;
import com.ecsail.gui.tabs.roster.TabKayakLists;
import com.ecsail.gui.tabs.roster.TabSlipOptions;
import com.ecsail.gui.tabs.roster.TabStandard;
import com.ecsail.rosterContextMenu;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.RosterRadioButtonsDTO;
import com.ecsail.structures.RosterSelectDTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Comparator;

public class TabRoster extends Tab {

	private final ObservableList<MembershipListDTO> rosters;
	private final TableView<MembershipListDTO> rosterTableView = new TableView<>();
	private final RosterSelectDTO printChoices;
	private final RosterRadioButtonsDTO rb;
	String selectedYear;
	Label records = new Label();

	public TabRoster(ObservableList<MembershipListDTO> a, String sy) {
		super();
		this.rosters = a;
		this.selectedYear = sy;
		this.setText("Roster");
		this.rb = new RosterRadioButtonsDTO();
		// TODO this needs a rework, to complex for what it does.
		this.printChoices = new RosterSelectDTO(sy, false, false, true, false, false, false, false, true, true, true, false,
				false, false, false, false, false, false, false, false, false);

		/////////////////// OBJECTS //////////////////////////
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // inter vbox
		VBox vboxTableBox = new VBox();
		VBox vboxRadioButton1 = new VBox();
		VBox vboxRadioButton2 = new VBox();
		VBox vboxSpinnerLabel = new VBox();
		VBox vboxCheckBox1 = new VBox();
		VBox vboxCheckBox2 = new VBox();
		VBox vboxCheckBox3 = new VBox();
		VBox vboxCheckBox4 = new VBox();
		VBox vboxCheckBox5 = new VBox();
		HBox hboxExport = new HBox();
		HBox hboxExportFrame = new HBox();
		HBox controlsHbox = new HBox();
		TitledPane titledPane = new TitledPane();
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

		TableColumn<MembershipListDTO, Integer> Col1 = new TableColumn<>("ID");
		TableColumn<MembershipListDTO, String> Col2 = new TableColumn<>("Join Date");
		TableColumn<MembershipListDTO, String> Col3 = new TableColumn<>("Type");
		TableColumn<MembershipListDTO, String> Col4 = new TableColumn<>("Slip");
		TableColumn<MembershipListDTO, String> Col5 = new TableColumn<>("First Name");
		TableColumn<MembershipListDTO, String> Col6 = new TableColumn<>("Last Name");
		TableColumn<MembershipListDTO, String> Col7 = new TableColumn<>("Address");
		TableColumn<MembershipListDTO, String> Col8 = new TableColumn<>("City");
		TableColumn<MembershipListDTO, String> Col9 = new TableColumn<>("State");
		TableColumn<MembershipListDTO, String> Col10 = new TableColumn<>("Zip");
		TableColumn<MembershipListDTO, String> Col11 = new TableColumn<>("MSID");

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		titledPane.setText("Roster " + selectedYear);
//		records.setText(rosters.size() + " Records");
		rb.setSameToggleGroup();
		
		rb.getRadioActive().setSelected(true);
		c1.setSelected(true);
		c2.setSelected(true);
		c3.setSelected(true);
		
		tabPane.setId("custom-mini-tab-pane");
//		vboxBlue.setId("hboxExport"); // outside frame around tableview and TitledPane
//		vboxPink.setId("hboxExport");  // inside frame around tableview and TitledPane
		hboxExportFrame.setId("hboxExport");
//		hboxExport.setId("hboxExport");  // background for export part
		
		hboxExport.setSpacing(10);
		vboxCheckBox4.setSpacing(5);
		controlsHbox.setSpacing(10);
		vboxRadioButton1.setSpacing(3);
		vboxRadioButton2.setSpacing(3);
		vboxSpinnerLabel.setSpacing(10);
		
		hboxExportFrame.setPadding(new Insets(2, 2, 2, 2));
		hboxExport.setPadding(new Insets(5, 5, 5, 5));
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 5, 3));
		vboxRadioButton1.setPadding(new Insets(5, 5, 5, 5));
		vboxRadioButton2.setPadding(new Insets(5, 5, 5, 5));
		
		tabPane.setSide(Side.LEFT);
		VBox.setVgrow(vboxBlue, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(vboxTableBox, Priority.ALWAYS);
		VBox.setVgrow(rosterTableView, Priority.ALWAYS);
		HBox.setHgrow(rosterTableView, Priority.ALWAYS);

		vboxSpinnerLabel.setAlignment(Pos.CENTER);

		setOnClosed(null);
		rosterTableView.setItems(rosters);
		rosterTableView.setFixedCellSize(30);
		rosterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );

		Col1.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
		Col2.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
		Col3.setCellValueFactory(new PropertyValueFactory<>("memType"));
		Col4.setCellValueFactory(new PropertyValueFactory<>("slip"));
		Col5.setCellValueFactory(new PropertyValueFactory<>("fname"));
		Col6.setCellValueFactory(new PropertyValueFactory<>("lname"));
		Col7.setCellValueFactory(new PropertyValueFactory<>("address"));
		Col8.setCellValueFactory(new PropertyValueFactory<>("city"));
		Col9.setCellValueFactory(new PropertyValueFactory<>("state"));
		Col10.setCellValueFactory(new PropertyValueFactory<>("zip"));
		Col11.setCellValueFactory(new PropertyValueFactory<>("msid"));

//		Col4.setCellFactory(col -> {
//			TableCell<MembershipListDTO, String> cell = new TableCell<>();
//			cell.itemProperty().addListener((obs, old, newVal) -> {
//				if (newVal != null) {
//					System.out.println(newVal);
//					Node centreBox = createSlipText(newVal);
//					cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
//				}
//			});
//			return cell;
//		});
		
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Mem 5%
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Join Date 15%
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Type
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Slip
		Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // First Name
		Col6.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Last Name
		Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Address
		Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // City
		Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // State
		Col10.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // Zip
		Col11.setMaxWidth( 1f * Integer.MAX_VALUE * 5 ); // MSID

		rosterTableView.getColumns()
				.addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11));

		ComboBox<Integer> comboBox = new ComboBox<>();
		for(int i = Integer.parseInt(BaseApplication.selectedYear) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().select(1);

		//////////////////// LISTENERS //////////////////////////

		/// this listens for a focus on the slips tab and refreshes data everytime.
		this.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {  // focus Gained
				makeListByRadioButtonChoice();
				// sets up printing choices for excel export
				setListType(rb.getTg1().getSelectedToggle().getUserData().toString());			}
		});

		// makes change when a radio button is selected
		rb.getTg1().selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
			if (rb.getTg1().getSelectedToggle() != null) {
				makeListByRadioButtonChoice();
				// sets up printing choices for excel export
				setListType(rb.getTg1().getSelectedToggle().getUserData().toString());
			}
		});

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
				selectedYear = newValue.toString();
				printChoices.setYear(selectedYear);
			    makeListByRadioButtonChoice();
				titledPane.setText("Roster " + selectedYear);
				records.setText(rosters.size() + " Records");
				rosterTableView.sort();
		});
		
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
					Launcher.createMembershipTabForRoster(clickedRow.getMembershipId(), clickedRow.getMsid());
				}
//				if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
//				row.setContextMenu(new rosterContextMenu(row.getItem(), selectedYear));
//				}
			});
			return row;
		});

		//////////////////// SET CONTENT //////////////////////
		
		vboxCheckBox1.getChildren().addAll(c1, c2, c3, c4);
		vboxCheckBox2.getChildren().addAll(c5, c6, c7, c8);
		vboxCheckBox3.getChildren().addAll(c9, c10, c11, c12);
		vboxCheckBox5.getChildren().addAll(buttonXLS);
		hboxExportFrame.getChildren().add(hboxExport);
		hboxExport.getChildren().addAll(vboxCheckBox1, vboxCheckBox2, vboxCheckBox3, vboxCheckBox4, vboxCheckBox5);
		tabPane.getTabs().addAll(new TabStandard(rb), new TabSlipOptions(rb), new TabKayakLists(rb));
		vboxSpinnerLabel.getChildren().addAll(comboBox, records);
		vboxTableBox.getChildren().add(rosterTableView);
		controlsHbox.getChildren().addAll(vboxSpinnerLabel, tabPane, hboxExportFrame);
		titledPane.setContent(controlsHbox);
		vboxPink.getChildren().addAll(titledPane,vboxTableBox);
		vboxBlue.getChildren().add(vboxPink);
		setContent(vboxBlue);
		records.setText(rosters.size() + " Records");
	}

	private void makeListByRadioButtonChoice() {
		rosters.clear();
		if(rb.getRadioActive().isSelected())
			rosters.addAll(SqlMembershipList.getRoster(selectedYear, true));
		if(rb.getRadioAll().isSelected())
			rosters.addAll(SqlMembershipList.getRosterOfAll(selectedYear));
		if(rb.getRadioNonRenew().isSelected())
			rosters.addAll(SqlMembershipList.getRoster(selectedYear, false));
		if(rb.getRadioNewMembers().isSelected())
			rosters.addAll(SqlMembershipList.getNewMemberRoster(selectedYear));
		if(rb.getRadioReturnMembers().isSelected())
			rosters.addAll(SqlMembershipList.getReturnMembers(Integer.parseInt(selectedYear)));
		if(rb.getRadioSlipWaitList().isSelected())
			rosters.addAll(SqlMembershipList.getWaitListRoster("slipwait"));
		if(rb.getRadioOpenSlips().isSelected())
			rosters.addAll(SqlMembershipList.getWaitListRoster("wantrelease"));
		if(rb.getRadioSlip().isSelected())
			rosters.addAll(SqlMembershipList.getRosterOfSlipOwners(HalyardPaths.getYear()));
		if(rb.getRadioWantsToSublease().isSelected())
			rosters.addAll(SqlMembershipList.getWaitListRoster("wantsublease"));
		if(rb.getRadioSlipChange().isSelected())
			rosters.addAll(SqlMembershipList.getWaitListRoster("wantslipchange"));
		if(rb.getRadioSubLeasedSlips().isSelected())
			rosters.addAll(SqlMembershipList.getRosterOfSubleasedSlips());
		if(rb.getRadioShedOwner().isSelected())
			rosters.addAll(SqlMembershipList.getRosterOfKayakShedOwners(HalyardPaths.getYear()));
		if(rb.getRadioKayakRackOwners().isSelected())
			rosters.addAll(SqlMembershipList.getRosterOfKayakRackOwners(HalyardPaths.getYear()));
		if(rb.getRadioLatePaymentMembers().isSelected())
			rosters.addAll(SqlMembershipList.getRosterOfMembershipsThatPaidLate(selectedYear));
		records.setText(rosters.size() + " Records");
		rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
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

	private Node createSlipText(String text) {
		HBox textContainer = new HBox();
		Text slip = new Text(text);
		slip.setFill(Color.BLUE);
		textContainer.getChildren().add(slip);
		textContainer.setAlignment(Pos.CENTER);
		textContainer.setSpacing(3);
		return textContainer;
	}
}
