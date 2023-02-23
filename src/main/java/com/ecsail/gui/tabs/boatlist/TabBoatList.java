package com.ecsail.gui.tabs.boatlist;

import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.DbBoatSettingsDTO;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.SettingsRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.SettingsRepository;
import javafx.application.Platform;
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

		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox hboxSplitScreen = new HBox();
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(hboxSplitScreen, Priority.ALWAYS);
		hboxSplitScreen.getChildren().addAll(boatListTableView, controlBox);
		vboxGrey.getChildren().add(hboxSplitScreen);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);

	}
}
