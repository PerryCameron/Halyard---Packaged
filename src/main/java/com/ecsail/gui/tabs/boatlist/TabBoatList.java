package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import com.ecsail.dto.DbBoatSettingsDTO;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.SettingsRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.SettingsRepository;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.dto.BoatListDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class TabBoatList extends Tab {
	protected SettingsRepository settingsRepository = new SettingsRepositoryImpl();
	protected BoatRepository boatRepository = new BoatRepositoryImpl();

	protected ObservableList<BoatListDTO> boats = FXCollections.observableArrayList();
	protected ObservableList<BoatListDTO> searchedBoats = FXCollections.observableArrayList();
	protected ArrayList<BoatListRadioDTO> boatListRadioDTOs;
	protected ArrayList<DbBoatSettingsDTO> boatSettings;
	protected TableView<BoatListDTO> boatListTableView = new TableView<>();
	protected BoatListDTO selectedBoat;
	private ControlBox controlBox;
	protected ArrayList<SettingsCheckBox> checkBoxes = new ArrayList<>();
	protected RadioHBox selectedRadioBox;
	protected Text numberOfRecords;
	protected VBox boatDetailsBox;
	protected boolean isActiveSearch;
	protected TextField textField = new TextField();
	
	public TabBoatList(String text) {
		super(text);
		this.boatListRadioDTOs = (ArrayList<BoatListRadioDTO>) settingsRepository.getBoatRadioChoices();
		this.boatSettings = (ArrayList<DbBoatSettingsDTO>) settingsRepository.getBoatSettings();
		this.controlBox = new ControlBox(this);
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox hboxSplitScreen = new HBox();

		boatListTableView.setItems(boats);
		boatListTableView.setFixedCellSize(30);
		boatListTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		boatListTableView.setEditable(true);
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(boatListTableView, Priority.ALWAYS);
		HBox.setHgrow(boatListTableView, Priority.ALWAYS);
		VBox.setVgrow(hboxSplitScreen, Priority.ALWAYS);
		
		var Col1 = new TableColumn<BoatListDTO, String>("ID");
		var boatId = new TableColumn<BoatListDTO, String>("Boat");
		var Col2 = new TableColumn<BoatListDTO, String>("Last Name");
		var Col3 = new TableColumn<BoatListDTO, String>("First Name");
		var Col4 = new TableColumn<BoatListDTO, String>("Model");
		var Col5 = new TableColumn<BoatListDTO, String>("Registration");
		var Col7 = new TableColumn<BoatListDTO, String>("Name");
		var Col8 = new TableColumn<BoatListDTO, Text>("Images");
		var Col9 = new TableColumn<BoatListDTO, Boolean>("Aux");

		Col1.setCellValueFactory(new PropertyValueFactory<BoatListDTO, String>("membershipId"));
		Col1.setStyle("-fx-alignment: top-center");
		boatId.setCellValueFactory(new PropertyValueFactory<>("boatId"));
		boatId.setStyle("-fx-alignment: top-center");
		Col2.setCellValueFactory(new PropertyValueFactory<>("lName"));
		Col3.setCellValueFactory(new PropertyValueFactory<>("fName"));
		Col4.setCellValueFactory(new PropertyValueFactory<>("model"));
		Col5.setCellValueFactory(new PropertyValueFactory<>("registrationNum"));
		Col7.setCellValueFactory(new PropertyValueFactory<>("boatName"));
		Col8.setCellValueFactory(new PropertyValueFactory<>("numberOfImages"));
		Col8.setStyle("-fx-alignment: top-center");
		Col8.setCellValueFactory(param -> {  // don't need this now but will use for subleases
			BoatListDTO bl = param.getValue();
			String valueDisplayed = String.valueOf(bl.getNumberOfImages());
			Text valueText = new Text(valueDisplayed);
			if(bl.getNumberOfImages() != 0) {
				valueText.setFill(Color.BLUE);
			}
			return new SimpleObjectProperty<>(valueText);
		});

		Col9.setCellValueFactory(new PropertyValueFactory<>("aux"));

		Col9.setCellValueFactory(param -> {
			BoatListDTO boat = param.getValue();
			SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(boat.isAux());
			// Note: singleCol.setOnEditCommit(): Not work for
			// CheckBoxTableCell.

			// When "Listed?" column change.
			booleanProp.addListener((observable, oldValue, newValue) -> {
				boat.setAux(newValue);
				SqlUpdate.updateAux(String.valueOf(boat.getBoatId()), newValue);

			});
			return booleanProp;
		});

		//
		Col9.setCellFactory(p -> {
			CheckBoxTableCell<BoatListDTO, Boolean> cell = new CheckBoxTableCell<>();
			cell.setAlignment(Pos.CENTER);
			return cell;
		});
		
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // Membership ID
		boatId.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // boat ID
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Last Name
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // First Name
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Model
		Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Registration
		Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 16 );  // Boat Name
		Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 9 );   // Images
		Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // aux

		/////////////////// LISTENERS  /////////////////////////

		boatListTableView.setRowFactory(tv -> {
			TableRow<BoatListDTO> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				selectedBoat = row.getItem();
				controlBox.refreshCurrentBoatDetails();
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					Launcher.openBoatViewTab(selectedBoat);
				}
			});
			return row;
		});
		////////////////// SET CONTENT ////////////////////////

		boatListTableView.getColumns()
		.addAll(Arrays.asList(Col1, boatId, Col2, Col3, Col4, Col5, Col7, Col8, Col9));
		hboxSplitScreen.getChildren().addAll(boatListTableView, controlBox);
		vboxGrey.getChildren().add(hboxSplitScreen);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		selectFirstRow(boatListTableView);
	}

	private void selectFirstRow(TableView<BoatListDTO> boatListTableView) {
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				boatListTableView.requestFocus();
				boatListTableView.getSelectionModel().select(0);
				boatListTableView.getFocusModel().focus(0);
				selectedBoat = boatListTableView.getSelectionModel().getSelectedItem();
				controlBox.refreshCurrentBoatDetails();
			}
		});
	}

}
