package com.ecsail.gui.tabs.boatview;


import com.ecsail.EditCell;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.BoatMemoDTO;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

public class HBoxBoatNotes extends HBox {
	private ObservableList<BoatMemoDTO> memos;
	
	public HBoxBoatNotes(BoatDTO b) {

		//////////// OBJECTS ///////////////
		var hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		var vboxPink = new VBox(); // this creates a pink border around the table
		var buttonVBox = new VBox();
		var add = new Button("Add");
		var delete = new Button("Delete");
		var memoTableView = new TableView<BoatMemoDTO>();
		
		/////////////  ATTRIBUTES /////////////
		add.setPrefWidth(60);
		delete.setPrefWidth(60);

		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(memoTableView, Priority.ALWAYS);
		VBox.setVgrow(memoTableView, Priority.ALWAYS);
		
		buttonVBox.setSpacing(5);
		buttonVBox.setPrefWidth(80);
		hboxGrey.setSpacing(10);
		this.setPrefHeight(200);

		
		hboxGrey.setPadding(new Insets(5, 5, 5, 5));
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		hboxGrey.setId("box-background-light");
		vboxPink.setId("box-pink");
		this.setId("box-frame-dark");
		
		memoTableView.setEditable(true);
		memoTableView.setItems(memos);
		memoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		memoTableView.setFixedCellSize(30);

		TableColumn<BoatMemoDTO, String> Col1 = createColumn("Date", BoatMemoDTO::memo_dateProperty);
        Col1.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setMemo_date(t.getNewValue());
					int memo_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
					//note.updateMemo(memo_id, "memo_date", t.getNewValue());
				}
		);
		/// editable row that writes to database when enter is hit
        
		//TableColumn<Object_BoatMemo, String> Col2 = new TableColumn<Object_BoatMemo, String>("Type");
		//Col2.setCellValueFactory(new PropertyValueFactory<Object_BoatMemo, String>("category"));
        
		TableColumn<BoatMemoDTO, String> Col3 = createColumn("Note", BoatMemoDTO::memoProperty);
		Col3.setPrefWidth(740);
        Col3.setOnEditCommit(
				t -> {
				   t.getTableView().getItems().get(t.getTablePosition().getRow()).setMemo(t.getNewValue());
				   int boat_memo_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_memo_id();
				   //note.updateMemo(memo_id, "memo", t.getNewValue());
				}
		);
        
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // Date
		//Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );    // Type
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 85 );   // Note

        ////////////////  LISTENERS ///////////////////
        
        add.setOnAction(e -> {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
			int boat_memo_id = SqlSelect.getNextAvailablePrimaryKey("boat_memo", "boat_memo_id"); // gets last memo_id number and add one
			 memos.add(new BoatMemoDTO(boat_memo_id,b.getBoat_id(), date, "new note"));
		});
        
		delete.setOnAction(e -> {
			int selectedIndex = memoTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {  // something is selected
				//memos.removeMemo(selectedIndex);
				memoTableView.getItems().remove(selectedIndex);
			}
		});
        
        ///////////// SET CONTENT ////////////////////
        
		memoTableView.getColumns().addAll(Arrays.asList(Col1,Col3));
		buttonVBox.getChildren().addAll(add,delete);
		vboxPink.getChildren().add(memoTableView);
		hboxGrey.getChildren().addAll(vboxPink,buttonVBox);
		getChildren().add(hboxGrey);
	}
	
	// This allows out of focus commit
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
}
