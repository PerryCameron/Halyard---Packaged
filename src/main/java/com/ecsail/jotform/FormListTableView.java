package com.ecsail.jotform;

import com.ecsail.jotform.structures.JotFormsDTO;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Builder;

import java.util.Arrays;

public class FormListTableView extends TableView<JotFormsDTO> implements Builder {
    TabFormList parent;

    public FormListTableView(TabFormList p) {
        this.parent = p;
        build();
    }

    private void setRosterRowFactory() {
        this.setRowFactory(tv -> {
            TableRow<JotFormsDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Tab tab = new TabForm(row.getItem(), parent.getFilter());
                    parent.getMainTabPane().getTabs().add(tab);
                    parent.getMainTabPane().getSelectionModel().select(tab);
                }
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    parent.setSelectedForm(row.getItem());
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
        column.setMaxWidth(1f * Integer.MAX_VALUE * 52);  // Join Date 15%
        column.setCellValueFactory(new PropertyValueFactory<>("title"));
        return column;
    }

    private TableColumn<JotFormsDTO, String> col2() {
        TableColumn<JotFormsDTO, String> column = new TableColumn<>("Submissions");
        column.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Join Date 15%
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

    private TableColumn<JotFormsDTO, Integer> col5() {
        TableColumn<JotFormsDTO, Integer> column = new TableColumn<>("Status");
        column.setMaxWidth(1f * Integer.MAX_VALUE * 8);  // Join Date 15%

        // Use a CellFactory to determine how to display the value
        column.setCellValueFactory(new PropertyValueFactory<>("archived"));
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Text text = new Text(item == 0 ? "Active" : "Archived");
                    text.setFill(item == 0 ? Color.BLUE : Color.RED);
                    setGraphic(text);
                }
            }
        });
        return column;
    }

}

//        column.setCellValueFactory(new PropertyValueFactory<>("archived"));