package com.ecsail.gui.tabs.roster;

import com.ecsail.Launcher;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.MembershipListRadioDTO;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.implementations.SettingsRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.repository.interfaces.SettingsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TabRoster extends Tab {
	protected MembershipRepository membershipRepository = new MembershipRepositoryImpl();
	protected SettingsRepository settingsRepository = new SettingsRepositoryImpl();

	protected ObservableList<MembershipListDTO> rosters;
	protected ObservableList<MembershipListDTO> searchedRosters;
	protected TableView<MembershipListDTO> rosterTableView;
	protected ArrayList<MembershipListRadioDTO> radioChoices;
	protected String selectedYear;

	VBox controlsBox;

	public TabRoster(ObservableList<MembershipListDTO> a, String sy) {
		super();
		this.rosters = a;
		this.selectedYear = sy;
		this.radioChoices = (ArrayList<MembershipListRadioDTO>) settingsRepository.getRadioChoices();
		this.searchedRosters = FXCollections.observableArrayList();
		this.setText("Roster");
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

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
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

		vboxBlue.getChildren().add(hboxSplitScreen);
		setContent(vboxBlue);
	}

	private HBox splitScreen() {
		HBox hBox = new HBox();
		hBox.getChildren().addAll(controlsBox,rosterTableView);
		return hBox;
	}
}
