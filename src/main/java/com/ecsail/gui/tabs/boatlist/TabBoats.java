package com.ecsail.gui.tabs.boatlist;

import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.BoatListDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.Arrays;

public class TabBoats extends Tab {
	protected ObservableList<BoatListDTO> boats = FXCollections.observableArrayList();
	protected BoatListDTO selectedBoat;
	private ControlBox controlBox;
	
	public TabBoats(String text) {
		super(text);
		this.controlBox = new ControlBox(this);
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox hboxSplitScreen = new HBox();
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
//		var Col10 = new TableColumn<BoatListDTO, Void>("Select");

		Col1.setCellValueFactory(new PropertyValueFactory<BoatListDTO, String>("membership_id"));
		Col1.setStyle("-fx-alignment: top-center");

//		Col1.setCellValueFactory(param -> {
//			BoatListDTO boat = param.getValue();
//			String valueDisplayed = "";
//			if(boat.getMembership_id() != 0) {
//				valueDisplayed = String.valueOf(boat.getMembership_id());
//			}
//			return new SimpleObjectProperty<>(valueDisplayed);
//		});

		boatId.setCellValueFactory(new PropertyValueFactory<>("boat_id"));
		boatId.setStyle("-fx-alignment: top-center");

		Col2.setCellValueFactory(new PropertyValueFactory<>("lName"));
		Col3.setCellValueFactory(new PropertyValueFactory<>("fName"));
		Col4.setCellValueFactory(new PropertyValueFactory<>("model"));
		Col5.setCellValueFactory(new PropertyValueFactory<>("registration_num"));
		Col7.setCellValueFactory(new PropertyValueFactory<>("boat_name"));
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
//		Col8.setCellFactory(param -> {
//			TableCell cell = new TableCell() {
//				public void updateItem(Object item, boolean empty) {
//					if(item!=null){
//						setText(item.toString());
//					}
//				}
//			};
//			cell.setAlignment(Pos.TOP_CENTER);
//			return cell;
//		});

		Col9.setCellValueFactory(new PropertyValueFactory<>("aux"));

		Col9.setCellValueFactory(param -> {
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
		Col9.setCellFactory(p -> {
			CheckBoxTableCell<BoatListDTO, Boolean> cell = new CheckBoxTableCell<>();
			cell.setAlignment(Pos.CENTER);
			return cell;
		});

//		Callback<TableColumn<BoatListDTO, Void>, TableCell<BoatListDTO, Void>> cellFactory = new Callback<>() {
//			@Override
//			public TableCell<BoatListDTO, Void> call(final TableColumn<BoatListDTO, Void> param) {
//				return new TableCell<>() {
//
//					private final Button btn = new Button("View");
//
//					{
//						btn.setOnAction((ActionEvent event) -> {
//							BoatListDTO boatListDTO = getTableView().getItems().get(getIndex());
////							BoatDTO selectedBoat = SqlBoat.getBoatByBoatId(BoatListDTO.getBoat_id());
//
//							Launcher.openBoatViewTab(boatListDTO);
//						});
//					}
//
//					@Override
//					public void updateItem(Void item, boolean empty) {
//						super.updateItem(item, empty);
//						if (empty) {
//							setGraphic(null);
//						} else {
//							setGraphic(btn);
//						}
//					}
//				};
//			}
//		};
//
//		Col10.setCellFactory(cellFactory);
		
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
//		Col10.setMaxWidth( 1f * Integer.MAX_VALUE * 11);	  // view button
		
		/////////////////// LISTENERS  /////////////////////////

		boatListTableView.setRowFactory(tv -> {
			TableRow<BoatListDTO> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				selectedBoat = row.getItem();
				controlBox.refreshCurrentBoatDetails();
//				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
//					// int rowIndex = row.getIndex();
//					BoatListDTO clickedRow = row.getItem();
//					BoatDTO selectedBoat = SqlBoat.getBoatbyBoatId(clickedRow.getBoat_id());
//					Launcher.openBoatViewTab(selectedBoat);
//				}
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
	}

	private BoatListDTO getBoatObject(int id) {
		return boats.stream().filter(boatListDTO -> boatListDTO.getBoat_id() == id).findFirst().orElse(null);
	}
	
}
