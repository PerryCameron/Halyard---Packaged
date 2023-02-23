package com.ecsail.gui.tabs.roster;

import com.ecsail.dto.DbRosterSettingsDTO;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TabRoster extends Tab {
	protected MembershipRepository membershipRepository = new MembershipRepositoryImpl();
	protected SettingsRepository settingsRepository = new SettingsRepositoryImpl();
	protected ArrayList<DbRosterSettingsDTO> rosterSettings;
	protected ObservableList<MembershipListDTO> rosters;
	protected ObservableList<MembershipListDTO> searchedRosters;
	protected TableView<MembershipListDTO> rosterTableView;
	protected ArrayList<MembershipListRadioDTO> radioChoices;
	protected TextField textField = new TextField();
	protected ArrayList<SettingsCheckBox> checkBoxes = new ArrayList<>();
	protected RadioHBox selectedRadioBox;
	protected boolean isActiveSearch;
	protected Text numberOfRecords;
	protected String selectedYear;

	public TabRoster(ObservableList<MembershipListDTO> a, String sy) {
		super();
		this.rosters = a;
		this.selectedYear = sy;
		this.radioChoices = (ArrayList<MembershipListRadioDTO>) settingsRepository.getRadioChoices();
		this.searchedRosters = FXCollections.observableArrayList();
		this.setText("Roster");
		this.rosterTableView = new RosterTableView(this);
		VBox containerBox = createContainerBox();
		this.setOnClosed(null);
		setContent(containerBox);
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
		hBox.getChildren().addAll(new ControlBox(this),rosterTableView);
		VBox.setVgrow(hBox, Priority.ALWAYS);
		hBox.setPadding(new Insets(3, 3, 5, 3));
		return hBox;
	}
}
