package com.ecsail.jotform;

import com.ecsail.jotform.structures.JotFormsDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class FormsTableView extends TableView<JotFormsDTO> {
    TabJotForm parent;

    public FormsTableView(TabJotForm p) {
        this.parent = p;
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);

        this.setItems(parent.getJotFormsDTOS());
        this.setFixedCellSize(30);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);




        /// sets width of columns by percentage
        TableColumn<JotFormsDTO, String> col1 = new TableColumn<>("Form Name");
        col1.setMaxWidth(1f * Integer.MAX_VALUE * 50);  // Join Date 15%
        col1.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<JotFormsDTO, String> col2 = new TableColumn<>("Submissions");
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 50);  // Join Date 15%
        col2.setCellValueFactory(new PropertyValueFactory<>("count"));




        this.getColumns()
                .addAll(Arrays.asList(col1,col2));
        setRosterRowFactory();
    }

    private void setRosterRowFactory() {
        this.setRowFactory(tv -> {
            TableRow<JotFormsDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    // int rowIndex = row.getIndex();
//                    JotFormsDTO clickedRow = row.getItem();
//                    Launcher.createMembershipTabForRoster(clickedRow.getMembershipId(), clickedRow.getMsId());
                }
//				if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
//				row.setContextMenu(new rosterContextMenu(row.getItem(), selectedYear));
//				}
            });
            return row;
        });
    }
}