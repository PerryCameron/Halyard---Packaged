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
import javafx.util.Builder;

import java.util.Arrays;

public class FormsTableView extends TableView<JotFormsDTO> implements Builder {
    TabJotForm parent;

    public FormsTableView(TabJotForm p) {
        this.parent = p;
        build();
    }

    private void setRosterRowFactory() {
        this.setRowFactory(tv -> {
            TableRow<JotFormsDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    // int rowIndex = row.getIndex();
                    JotFormsDTO clickedRow = row.getItem();
                    System.out.println(clickedRow.getId());
//                    Launcher.createMembershipTabForRoster(clickedRow.getMembershipId(), clickedRow.getMsId());
                }
            });
            return row;
        });
    }

    @Override
    public Object build() {
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setItems(parent.getJotFormsDTOS());
        this.setFixedCellSize(30);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.getColumns().addAll(Arrays.asList(col1(),col2(),col3(),col4(),col5()));
        setRosterRowFactory();
        return null;
    }

    private TableColumn<JotFormsDTO, String> col1() {
        TableColumn<JotFormsDTO, String> column = new TableColumn<>("Form Name");
        column.setMaxWidth(1f * Integer.MAX_VALUE * 60);  // Join Date 15%
        column.setCellValueFactory(new PropertyValueFactory<>("title"));
        return column;
    }

    private TableColumn<JotFormsDTO, String> col2() {
        TableColumn<JotFormsDTO, String> column = new TableColumn<>("Submissions");
        column.setMaxWidth(1f * Integer.MAX_VALUE * 10);  // Join Date 15%
        column.setCellValueFactory(new PropertyValueFactory<>("count"));
        return column;
    }

    private TableColumn<JotFormsDTO, String> col3() {
        TableColumn<JotFormsDTO, String> column = new TableColumn<>("Created");
        column.setMaxWidth(1f * Integer.MAX_VALUE * 20);  // Join Date 15%
        column.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        return column;
    }

    private TableColumn<JotFormsDTO, String> col4() {
        TableColumn<JotFormsDTO, String> column = new TableColumn<>("New");
        column.setMaxWidth(1f * Integer.MAX_VALUE * 5);  // Join Date 15%
        column.setCellValueFactory(new PropertyValueFactory<>("newSubmission"));
        return column;
    }

    private TableColumn<JotFormsDTO, String> col5 () {
        TableColumn<JotFormsDTO, String> column = new TableColumn<>("Archive");
        column.setMaxWidth(1f * Integer.MAX_VALUE * 5);  // Join Date 15%
        column.setCellValueFactory(new PropertyValueFactory<>("archived"));
        return column;
    }
}

//archived