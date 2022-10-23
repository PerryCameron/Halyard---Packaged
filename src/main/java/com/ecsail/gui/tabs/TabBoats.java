package com.ecsail.gui.tabs;


import com.ecsail.Launcher;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlBoat;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.BoatListDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Arrays;

public class TabBoats extends Tab {
	ObservableList<BoatListDTO> boats;
	
	public TabBoats(String text) {
		super(text);
		this.boats = SqlBoat.getBoatsWithOwners();
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		TableView<BoatListDTO> boatListTableView = new TableView<>();

		
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
		
		var Col1 = new TableColumn<BoatListDTO, Integer>("MEM");
		var Col2 = new TableColumn<BoatListDTO, String>("Last Name");
		var Col3 = new TableColumn<BoatListDTO, String>("First Name");
		var Col4 = new TableColumn<BoatListDTO, String>("Model");
		var Col5 = new TableColumn<BoatListDTO, String>("Registration");
		var Col6 = new TableColumn<BoatListDTO, String>("Year");
		var Col7 = new TableColumn<BoatListDTO, String>("Name");
		var Col8 = new TableColumn<BoatListDTO, Boolean>("Aux");
		var Col9 = new TableColumn<BoatListDTO, Void>("Select");
		
		Col1.setCellValueFactory(new PropertyValueFactory<>("membership_id"));
		Col2.setCellValueFactory(new PropertyValueFactory<>("lname"));
		Col3.setCellValueFactory(new PropertyValueFactory<>("fname"));
		Col4.setCellValueFactory(new PropertyValueFactory<>("model"));
		Col5.setCellValueFactory(new PropertyValueFactory<>("registration_num"));
		Col6.setCellValueFactory(new PropertyValueFactory<>("manufacture_year"));
		Col7.setCellValueFactory(new PropertyValueFactory<>("boat_name"));
		Col8.setCellValueFactory(new PropertyValueFactory<>("aux"));

		Col8.setCellValueFactory(param -> {
			BoatListDTO boat = param.getValue();
			SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(boat.isAux());
			// Note: singleCol.setOnEditCommit(): Not work for
			// CheckBoxTableCell.

			// When "Listed?" column change.
			booleanProp.addListener((observable, oldValue, newValue) -> {
				boat.setAux(newValue);
				SqlUpdate.updateAux(String.valueOf(boat.getBoat_id()), newValue);

			});
			return booleanProp;
		});

		//
		Col8.setCellFactory(p -> {
			CheckBoxTableCell<BoatListDTO, Boolean> cell = new CheckBoxTableCell<>();
			cell.setAlignment(Pos.CENTER);
			return cell;
		});

		Callback<TableColumn<BoatListDTO, Void>, TableCell<BoatListDTO, Void>> cellFactory = new Callback<>() {
			@Override
			public TableCell<BoatListDTO, Void> call(final TableColumn<BoatListDTO, Void> param) {
				return new TableCell<>() {

					private final Button btn = new Button("View");

					{
						btn.setOnAction((ActionEvent event) -> {
							BoatListDTO BoatListDTO = getTableView().getItems().get(getIndex());
							System.out.println("selectedBoatListDTO: " + BoatListDTO);
							//					BoatListDTO clickedRow = row.getItem();
							BoatDTO selectedBoat = SqlBoat.getBoatbyBoatId(getTableView().getItems().get(getIndex()).getBoat_id());
							Launcher.openBoatViewTab(selectedBoat);
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
			}
		};
		
		Col9.setCellFactory(cellFactory);
		
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Membership ID
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Last Name
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // First Name
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Model
		Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Registration
		Col6.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Year
		Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 13 );  // Boat Name
		Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // aux
		Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 7);	  // view button
		
		/////////////////// LISTENERS  /////////////////////////

//		boatListTableView.setRowFactory(tv -> {
//			TableRow<BoatListDTO> row = new TableRow<>();
//			row.setOnMouseClicked(event -> {
//				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
//					// int rowIndex = row.getIndex();
//					BoatListDTO clickedRow = row.getItem();
//					BoatDTO selectedBoat = SqlBoat.getBoatbyBoatId(clickedRow.getBoat_id());
//					Launcher.openBoatViewTab(selectedBoat);
//				}
//			});
//			return row;
//		});
		////////////////// SET CONTENT ////////////////////////
		
		boatListTableView.getColumns()
		.addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9)); // , Col8, Col9, Col10, Col11
		
		vboxGrey.getChildren().add(boatListTableView);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
	
}
