package com.ecsail.gui.tabs.boatlist;

import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.DbBoatSettingsDTO;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.SettingsRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
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

public class TabBoatList extends Tab {
	protected SettingsRepository settingsRepository = new SettingsRepositoryImpl();
	protected BoatRepository boatRepository = new BoatRepositoryImpl();

	protected ObservableList<BoatListDTO> boats = FXCollections.observableArrayList();
	protected ObservableList<BoatListDTO> searchedBoats = FXCollections.observableArrayList();
	protected ArrayList<BoatListRadioDTO> boatListRadioDTOs;
	protected ArrayList<DbBoatSettingsDTO> boatSettings;
	protected TableView<BoatListDTO> boatListTableView;
	protected BoatListDTO selectedBoat;
	protected ControlBox controlBox;
	protected ArrayList<SettingsCheckBox> checkBoxes = new ArrayList<>();
	protected RadioHBox selectedRadioBox;
	protected Text numberOfRecords;
	protected VBox boatDetailsBox;
	protected boolean isActiveSearch;
	protected TextField textField = new TextField();
	
	public TabBoatList(String text) {
		super(text);
		this.boatListTableView = new BoatListTableView(this);
		this.boatListRadioDTOs = (ArrayList<BoatListRadioDTO>) settingsRepository.getBoatRadioChoices();
		this.boatSettings = (ArrayList<DbBoatSettingsDTO>) settingsRepository.getBoatSettings();
		this.controlBox = new ControlBox(this);
		HBox hboxSplitScreen = createSplitScreenBox();
		VBox contentBox = createContentBox(hboxSplitScreen);
		setContent(contentBox);
	}

	private VBox createContentBox(HBox hBox) {
		VBox vBox = new VBox();
		vBox.setId("box-blue");
		vBox.setPadding(new Insets(10,10,10,10));
		vBox.getChildren().add(hBox);
		return vBox;
	}

	private HBox createSplitScreenBox() {
		HBox hBox = new HBox();
		hBox.getChildren().addAll(boatListTableView, controlBox);
		VBox.setVgrow(hBox, Priority.ALWAYS);
		return hBox;
	}

}
