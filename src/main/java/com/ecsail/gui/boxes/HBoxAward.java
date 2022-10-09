package com.ecsail.gui.boxes;

import com.ecsail.EditCell;
import com.ecsail.enums.Awards;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlAward;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.AwardDTO;
import com.ecsail.structures.PersonDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Function;

public class HBoxAward extends HBox {

	private final PersonDTO person;
	private final ObservableList<AwardDTO> award;
	private final TableView<AwardDTO> awardTableView;
	private final String currentYear;
	
	@SuppressWarnings("unchecked")
	public HBoxAward(PersonDTO p) {
		this.person = p;
		this.currentYear = new SimpleDateFormat("yyyy").format(new Date());
		this.award =  SqlAward.getAwards(person);
		
		///////////////// OBJECT INSTANCE ///////////////////
		Button awardAdd = new Button("Add");
		Button awardDelete = new Button("Delete");
		VBox vboxButtons = new VBox(); // holds officer buttons
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		awardTableView = new TableView<>();

		/////////////////  ATTRIBUTES  /////////////////////
		awardAdd.setPrefWidth(60);
		awardDelete.setPrefWidth(60);
		vboxButtons.setPrefWidth(80);
		
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(awardTableView, Priority.ALWAYS);
		VBox.setVgrow(awardTableView, Priority.ALWAYS);
		
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vboxButtons.setSpacing(5);
		
		hboxGrey.setId("box-background-light");
		vboxPink.setId("box-pink");
		this.setId("box-frame-dark");
		
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		
		///////////////// TABLE VIEW ///////////////////////

			awardTableView.setItems(award);
			awardTableView.setFixedCellSize(30);
			awardTableView.setEditable(true);
			awardTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
			
			
			TableColumn<AwardDTO, String> Col1 = createColumn("Year", AwardDTO::awardYearProperty);
			Col1.setSortType(TableColumn.SortType.DESCENDING);
	        Col1.setOnEditCommit(
					t -> {
						t.getTableView().getItems().get(
								t.getTablePosition().getRow()).setAwardYear(t.getNewValue());
						SqlUpdate.updateAward("award_year",t.getRowValue().getAwardId(), t.getNewValue());  // have to get by money id and pid eventually
					}
			);
	        
	        
	        ObservableList<Awards> awardsList = FXCollections.observableArrayList(Awards.values());
	    	final TableColumn<AwardDTO, Awards> Col2 = new TableColumn<>("Award Type");
	        Col2.setCellValueFactory(param -> {
				AwardDTO thisAward = param.getValue();
				String awardCode = thisAward.getAwardType();
				Awards type = Awards.getByCode(awardCode);
				return new SimpleObjectProperty<>(type);
			});
	        
	        Col2.setCellFactory(ComboBoxTableCell.forTableColumn(awardsList));
	 
	        Col2.setOnEditCommit((CellEditEvent<AwardDTO, Awards> event) -> {
				// get the position on the table
	            TablePosition<AwardDTO, Awards> pos = event.getTablePosition();
				// use enum to convert DB value
	            Awards newAward = event.getNewValue();
				// give object a name to manipulate
	            AwardDTO thisAward = event.getTableView().getItems().get(pos.getRow());
				// update the SQL
	            SqlUpdate.updateAward("award_type",thisAward.getAwardId(), newAward.getCode());
				// update the GUI
	            thisAward.setAwardType(newAward.getCode());
	        });
	        
			/// sets width of columns by percentage
			Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 20);   // Phone
			Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 50 );  // Type
	        
	        
	        ////////////////////// LISTENERS /////////////////////////
	    	    
	        awardAdd.setOnAction((event) -> {
				// get next primary key for awards table
				int awards_id = SqlSelect.getNextAvailablePrimaryKey("awards","award_id"); // gets last memo_id number
				// Create new award object
				AwardDTO a = new AwardDTO(awards_id,person.getP_id(),currentYear,"New Award");
				// Add info from award object to SQL database
				SqlInsert.addAwardRecord(a);
				// create a new row in tableView to match the SQL insert from above
				award.add(a);
				// sort awards ascending
				award.sort(Comparator.comparing(AwardDTO::getAwardId).reversed());
				// this line prevents strange buggy behaviour
				awardTableView.layout();
				// edit the year cell after creating
				awardTableView.edit(0, Col1);
	        });
	        
	        awardDelete.setOnAction(e -> {
				int selectedIndex = awardTableView.getSelectionModel().getSelectedIndex();
				if(selectedIndex >= 0)
					if(SqlDelete.deleteAward(award.get(selectedIndex)))
						awardTableView.getItems().remove(selectedIndex);
			});
	        
	        /////////////////// SET CONTENT //////////////////
	        
			vboxButtons.getChildren().addAll(awardAdd, awardDelete);
			awardTableView.getColumns().addAll(Col1,Col2);
			awardTableView.getSortOrder().addAll(Col1);
			vboxPink.getChildren().add(awardTableView);
			awardTableView.sort();
			hboxGrey.getChildren().addAll(vboxPink,vboxButtons);
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
