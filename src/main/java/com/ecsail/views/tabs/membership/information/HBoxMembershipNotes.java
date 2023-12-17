package com.ecsail.views.tabs.membership.information;


import com.ecsail.EditCell;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.dto.MemoDTO;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
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
	TabMembership parent;
	
	public HBoxMembershipNotes(TabMembership parent) {
		this.parent = parent;
		
		//////////// OBJECTS ///////////////
		var hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		var vboxPink = new VBox(); // this creates a pink border around the table
		var buttonVBox = new VBox();
		var add = new Button("Add");
		var delete = new Button("Delete");
		var memoTableView = new TableView<MemoDTO>();
		
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
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		hboxGrey.setId("box-background-light");
		this.setId("custom-tap-pane-frame");
		memoTableView.setEditable(true);
		memoTableView.setItems(parent.getModel().getNote().getMemos());
		memoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		memoTableView.setFixedCellSize(30);

		TableColumn<MemoDTO, String> Col1 = createColumn("Date", MemoDTO::memo_dateProperty);
        Col1.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setMemo_date(t.getNewValue());
					int memo_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getMemo_id();
					parent.getModel().getNote().updateMemo(memo_id, "memo_date", t.getNewValue());
				}
		);
		/// editable row that writes to database when enter is hit
        
		var Col2 = new TableColumn<MemoDTO, String>("Type");
		Col2.setCellValueFactory(new PropertyValueFactory<>("category"));
        
		TableColumn<MemoDTO, String> Col3 = createColumn("Note", MemoDTO::memoProperty);
		Col3.setPrefWidth(740);
        Col3.setOnEditCommit(
				t -> {
				   t.getTableView().getItems().get(t.getTablePosition().getRow()).setMemo(t.getNewValue());
				   int memo_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getMemo_id();
					parent.getModel().getNote().updateMemo(memo_id, "memo", t.getNewValue());
				}
		);
        
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // Date
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );    // Type
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 85 );   // Note

        ////////////////  LISTENERS ///////////////////
        
        add.setOnAction(e -> {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
			int msId = parent.getModel().getMembership().getMsId();
			parent.getModel().getNote().addMemoAndReturnId(new MemoDTO(msId,"new memo", date,"N"));
			memoTableView.layout();
			memoTableView.edit(0,Col3);
		});
        
		delete.setOnAction(e -> {
			int selectedIndex = memoTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {  // something is selected
				parent.getModel().getNote().removeMemo(selectedIndex);
				memoTableView.getItems().remove(selectedIndex);
			}
		});
        
        ///////////// SET CONTENT ////////////////////
        
		memoTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3));
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
