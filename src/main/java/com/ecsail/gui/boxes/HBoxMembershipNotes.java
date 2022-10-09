package com.ecsail.gui.boxes;


import com.ecsail.EditCell;
import com.ecsail.Note;
import com.ecsail.structures.MemoDTO;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

public class HBoxMembershipNotes extends HBox {
	private Note note;
	
	public HBoxMembershipNotes(Note n) {
		this.note = n;
		
		//////////// OBJECTS ///////////////
		HBox hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox buttonVBox = new VBox();
		Button add = new Button("Add");
		Button delete = new Button("Delete");
		TableView<MemoDTO> memoTableView = new TableView<MemoDTO>();
		
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
		
		
		hboxGrey.setPadding(new Insets(5, 5, 5, 5));
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		hboxGrey.setId("box-background-light");
		this.setId("custom-tap-pane-frame");
		memoTableView.setEditable(true);
		memoTableView.setItems(note.getMemos());
		memoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		memoTableView.setFixedCellSize(30);

		TableColumn<MemoDTO, String> Col1 = createColumn("Date", MemoDTO::memo_dateProperty);
        Col1.setOnEditCommit(
                new EventHandler<CellEditEvent<MemoDTO, String>>() {
                    @Override
                    public void handle(CellEditEvent<MemoDTO, String> t) {
                        ((MemoDTO) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setMemo_date(t.getNewValue());
                        int memo_id = ((MemoDTO) t.getTableView().getItems().get(t.getTablePosition().getRow())).getMemo_id();
                        note.updateMemo(memo_id, "memo_date", t.getNewValue());
                    }
                }
            );
		/// editable row that writes to database when enter is hit
        
		TableColumn<MemoDTO, String> Col2 = new TableColumn<MemoDTO, String>("Type");
		Col2.setCellValueFactory(new PropertyValueFactory<MemoDTO, String>("category"));
        
		TableColumn<MemoDTO, String> Col3 = createColumn("Note", MemoDTO::memoProperty);
		Col3.setPrefWidth(740);
        Col3.setOnEditCommit(
                new EventHandler<CellEditEvent<MemoDTO, String>>() {
                    @Override
                    public void handle(CellEditEvent<MemoDTO, String> t) {
                       ((MemoDTO) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMemo(t.getNewValue());
                       int memo_id = ((MemoDTO) t.getTableView().getItems().get(t.getTablePosition().getRow())).getMemo_id();
                       note.updateMemo(memo_id, "memo", t.getNewValue());
                    }
                }
            );
        
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // Date
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );    // Type
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 85 );   // Note

        ////////////////  LISTENERS ///////////////////
        
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
				// add a memo and return its id
             	int memoId = note.addMemoAndReturnId("new memo", date,0,"N");
				// this line prevents strange behaviour I found the solution here:
				// https://stackoverflow.com/questions/49531071/insert-row-in-javafx-tableview-and-start-editing-is-not-working-correctly
				memoTableView.layout();
				// open memo for editing
				memoTableView.edit(0,Col3);
            }
        });
        
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			    int selectedIndex = memoTableView.getSelectionModel().getSelectedIndex();
			    if (selectedIndex >= 0) {  // something is selected
			    	note.removeMemo(selectedIndex);
			    	memoTableView.getItems().remove(selectedIndex);
			    }
			}
		});
        
        ///////////// SET CONTENT ////////////////////
        
		memoTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3));
		buttonVBox.getChildren().addAll(add,delete);
		vboxPink.getChildren().add(memoTableView);
		hboxGrey.getChildren().addAll(vboxPink,buttonVBox);
		getChildren().add(hboxGrey);
	}




	// This allows out of focus committ
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
}
