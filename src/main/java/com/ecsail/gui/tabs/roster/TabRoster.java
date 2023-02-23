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
import javafx.scene.control.Tab;
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
	private VBox controlsBox;

	public TabRoster(ObservableList<MembershipListDTO> a, String sy) {
		super();
		this.rosters = a;
		this.selectedYear = sy;
		this.radioChoices = (ArrayList<MembershipListRadioDTO>) settingsRepository.getRadioChoices();
		this.searchedRosters = FXCollections.observableArrayList();
		this.setText("Roster");
		this.rosterTableView = new RosterTableView(this);
		this.controlsBox = new ControlBox(this);
		VBox containerBox = createContainerBox();
		this.setOnClosed(null);
		setRosterRowFactory();
		setContent(containerBox);
	}

	private void setRosterRowFactory() {
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
	}

	private VBox createContainerBox() {
		VBox vBox = new VBox();
		HBox splitScreenBox = splitScreen(); // inter vbox
		vBox.setPadding(new Insets(10, 10, 10, 10));
		VBox.setVgrow(vBox, Priority.ALWAYS);
		vBox.getChildren().add(splitScreenBox);
		return vBox;

	}

	private HBox splitScreen() {
		HBox hBox = new HBox();
		hBox.getChildren().addAll(controlsBox,rosterTableView);
		VBox.setVgrow(hBox, Priority.ALWAYS);
		hBox.setPadding(new Insets(3, 3, 5, 3));
		return hBox;
	}
}
