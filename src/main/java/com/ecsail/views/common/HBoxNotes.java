package com.ecsail.views.common;


import com.ecsail.EditCell;
import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.MemoDTO;
import com.ecsail.repository.implementations.MemoRepositoryImpl;
import com.ecsail.repository.interfaces.MemoRepository;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
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

public class HBoxNotes extends HBox {
	private MemoRepository memoRepository = new MemoRepositoryImpl();
	private Note note;
	private TableView<MemoDTO> memoTableView;
	private TableColumn<MemoDTO, String> Col2;
	private BoatDTO selectedBoat;
	
	public HBoxNotes(BoatDTO b) {
		this.selectedBoat = b;
		ObservableList<MemoDTO> memos = FXCollections.observableArrayList(memoRepository.getMemosByBoatId(b.getBoatId()));
		this.note = new Note(memos, b.getMsId());
		VBox buttonBox = setUpButtonBox();
		VBox tableViewBox = setUpTableViewBox();
		HBox contentBox = setUpContentBox(tableViewBox,buttonBox);
		getChildren().add(contentBox);
	}

	private HBox setUpContentBox(VBox tableViewBox, VBox buttonBox) {
		HBox hBox = new HBox();  // this is the vbox for organizing all the widgets
		HBox.setHgrow(hBox, Priority.ALWAYS);
		hBox.setSpacing(10);
		hBox.setPadding(new Insets(5, 5, 5, 5));
		hBox.setId("box-background-light");
		hBox.getChildren().addAll(tableViewBox,buttonBox);
		return hBox;
	}
	private VBox setUpTableViewBox() {
		VBox vBox = new VBox();
		HBox.setHgrow(vBox, Priority.ALWAYS);
		vBox.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		setUpTableView();
		vBox.getChildren().add(memoTableView);
//		vboxTableView.setId("box-pink");
		return vBox;
	}

	private void setUpTableView() {
		TableView<MemoDTO> tableView = new TableView<>();
		HBox.setHgrow(tableView, Priority.ALWAYS);
		VBox.setVgrow(tableView, Priority.ALWAYS);
		this.setPrefHeight(200);
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		this.setId("box-frame-dark");

		tableView.setEditable(true);
		tableView.setItems(note.getMemos());
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setFixedCellSize(30);

		TableColumn<MemoDTO, String> Col1 = createColumn("Date", MemoDTO::memo_dateProperty);
		Col1.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setMemo_date(t.getNewValue());
					int memo_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
					note.updateMemo(memo_id, "memo_date", t.getNewValue());
				}
		);

		Col2 = createColumn("Note", MemoDTO::memoProperty);
		Col2.setPrefWidth(740);
		Col2.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(t.getTablePosition().getRow()).setMemo(t.getNewValue());
					int memo_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getMemo_id();
					note.updateMemo(memo_id, "memo", t.getNewValue());
				}
		);
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // Date
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 85 );   // Note
		tableView.getColumns().addAll(Arrays.asList(Col1, Col2));

		memoTableView = tableView;
	}

	private VBox setUpButtonBox() {
		var vBox = new VBox();
		vBox.setSpacing(5);
		vBox.setPrefWidth(80);
		var add = new Button("Add");
		var delete = new Button("Delete");
		add.setPrefWidth(60);
		delete.setPrefWidth(60);
		setAddButtonListener(add);
		setDeleteButtonListener(delete);
		vBox.getChildren().addAll(add,delete);
		return vBox;
	}

	private void setDeleteButtonListener(Button delete) {
		delete.setOnAction(e -> {
			int selectedIndex = memoTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {  // something is selected
				note.removeMemo(selectedIndex);
				memoTableView.getItems().remove(selectedIndex);
			}
		});
	}

	private void setAddButtonListener(Button add) {
		add.setOnAction(e -> {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
			note.addMemoAndReturnId(new MemoDTO(date,"B",selectedBoat.getBoatId()));
			memoTableView.layout();
			// open memo for editing
			memoTableView.requestFocus();
			memoTableView.getSelectionModel().select(0);
			memoTableView.getFocusModel().focus(0);
			memoTableView.edit(0, Col2);
		});
	}

	// This allows out of focus commit
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
}
