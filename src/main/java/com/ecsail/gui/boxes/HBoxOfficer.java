package com.ecsail.gui.boxes;

import com.ecsail.BaseApplication;
import com.ecsail.EditCell;
import com.ecsail.enums.Officer;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlOfficer;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.OfficerDTO;
import com.ecsail.structures.PersonDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

public class HBoxOfficer extends HBox {

	private final PersonDTO person;
	private final ObservableList<OfficerDTO> officer;
	private final TableView<OfficerDTO> officerTableView;
	private final String currentYear;
	
	@SuppressWarnings("unchecked")
	public HBoxOfficer(PersonDTO p) {
		this.person = p;
		this.currentYear = new SimpleDateFormat("yyyy").format(new Date());
		this.officer =  SqlOfficer.getOfficer("p_id",person.getP_id());
		
		///////////////// OBJECT INSTANCE ///////////////////
		Button officerAdd = new Button("Add");
		Button officerDelete = new Button("Delete");
		VBox vboxButton = new VBox(); // holds officer buttons
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		officerTableView = new TableView<>();

		/////////////////  ATTRIBUTES  /////////////////////
		officerAdd.setPrefWidth(60);
		officerDelete.setPrefWidth(60);
		vboxButton.setPrefWidth(80);
		
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(officerTableView, Priority.ALWAYS);
		VBox.setVgrow(officerTableView, Priority.ALWAYS);
		
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vboxButton.setSpacing(5);
		hboxGrey.setId("box-background-light");
		vboxPink.setId("box-pink");
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		
		this.setId("box-frame-dark");
		
		///////////////// TABLE VIEW ///////////////////////
			
			officerTableView.setItems(officer);
			officerTableView.setFixedCellSize(30);
			officerTableView.setEditable(true);
			officerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
			
			TableColumn<OfficerDTO, String> Col1 = createColumn("Year", OfficerDTO::fiscal_yearProperty);
			Col1.setSortType(TableColumn.SortType.DESCENDING);
	        Col1.setOnEditCommit(
					t -> {
						t.getTableView().getItems().get(
								t.getTablePosition().getRow()).setFiscal_year(t.getNewValue());
						SqlUpdate.updateOfficer("off_year",t.getRowValue().getOfficer_id(), t.getNewValue());  // have to get by money id and pid eventually
					}
			);

	        ObservableList<Officer> officerList = FXCollections.observableArrayList(Officer.values());
	    	final TableColumn<OfficerDTO, Officer> Col2 = new TableColumn<>("Officers, Chairs and Board");
	        Col2.setCellValueFactory(param -> {
				OfficerDTO thisOfficer = param.getValue();
				String officerCode = thisOfficer.getOfficer_type();
				Officer type = Officer.getByCode(officerCode);
				return new SimpleObjectProperty<>(type);
			});
	        
	        Col2.setCellFactory(ComboBoxTableCell.forTableColumn(officerList));
	 
	        Col2.setOnEditCommit((CellEditEvent<OfficerDTO, Officer> event) -> {
	            TablePosition<OfficerDTO, Officer> pos = event.getTablePosition();
	            Officer newOfficer = event.getNewValue();
	            int row = pos.getRow();
	            OfficerDTO thisofficer = event.getTableView().getItems().get(row);
	            SqlUpdate.updateOfficer("off_type",thisofficer.getOfficer_id(), newOfficer.getCode());
	            thisofficer.setOfficer_type(newOfficer.getCode());
	        });
	        
			TableColumn<OfficerDTO, String> Col3 = createColumn("Exp", OfficerDTO::board_yearProperty);
	        Col3.setOnEditCommit(
					t -> {
						t.getTableView().getItems().get(
								t.getTablePosition().getRow()).setBoard_year(t.getNewValue());  // need to change
							SqlUpdate.updateOfficer("board_year",t.getRowValue().getOfficer_id(), t.getNewValue());  // have to get by money id and pid eventually
					}
			);
	        
			/// sets width of columns by percentage
			Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 20);   // Phone
			Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 50 );  // Type
			Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );  // Listed
	        ////////////////////// LISTENERS /////////////////////////

		officerAdd.setOnAction(e -> {
			BaseApplication.logger.info("Added new officer entry for " + person.getNameWithInfo());
			// get next available primary key for officer table
			int officer_id = SqlSelect.getNextAvailablePrimaryKey("officer", "o_id"); // gets last memo_id number
			// insert a new officer row into SQL and return true on success
			if (SqlInsert.addOfficerRecord(officer_id, person.getP_id(), "0", "new officer", Integer.parseInt(currentYear)))
				// add a new row to the tableView to match new SQL entry
				officer.add(new OfficerDTO(officer_id, person.getP_id(), "0", "new officer", currentYear));
			// Now we will sort it to the top
			officer.sort(Comparator.comparing(OfficerDTO::getOfficer_id).reversed());
			// this line prevents strange buggy behaviour
			officerTableView.layout();
			// edit the phone number cell after creating
			officerTableView.edit(0, Col1);
		});

		officerDelete.setOnAction(e -> {
			int selectedIndex = officerTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				OfficerDTO officerDTO = officer.get(selectedIndex);
				Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
				conformation.setTitle("Delete Officer Entry");
				conformation.setHeaderText(officerDTO.getBoard_year() + " " + Officer.getNameByCode(officerDTO.getOfficer_type()));
				conformation.setContentText("Are sure you want to delete this officer/chairman entry?");
				DialogPane dialogPane = conformation.getDialogPane();
				dialogPane.getStylesheets().add("css/dark/dialogue.css");
				dialogPane.getStyleClass().add("dialog");
				Optional<ButtonType> result = conformation.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					if (SqlDelete.deleteOfficer(officerDTO)) {
						officerTableView.getItems().remove(selectedIndex);
						BaseApplication.logger.info("Deleted officer entry for " + person.getNameWithInfo());
					}
				}
			}
		});
	        
	        /////////////////// SET CONTENT //////////////////
	        
			vboxButton.getChildren().addAll(officerAdd, officerDelete);
			officerTableView.getColumns().addAll(Col1,Col2,Col3);
			officerTableView.getSortOrder().addAll(Col1);
			vboxPink.getChildren().add(officerTableView);
			officerTableView.sort();
			hboxGrey.getChildren().addAll(vboxPink,vboxButton);
			getChildren().add(hboxGrey);
		
	} // CONSTRUCTOR END
	
	///////////////// CLASS METHODS /////////////////
	
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }

}
