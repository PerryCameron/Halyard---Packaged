package com.ecsail.jotform;

import com.ecsail.jotform.structures.TabForm;
import com.ecsail.jotform.structures.submissions.AnswerBlockPOJO;
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

public class FormTableView extends TableView<AnswerBlockPOJO> implements Builder {
    TabForm parent;

    public FormTableView(TabForm p) {
        this.parent = p;
        build();
    }

    private void setRosterRowFactory() {
        this.setRowFactory(tv -> {
            TableRow<AnswerBlockPOJO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    parent.getvBox().getChildren().clear();
                    parent.fillForm(row.getItem().getFormInfo().getId());
                }
            });
            return row;
        });
    }

    @Override
    public Object build() {
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setItems(parent.getSubmissionNames());
        this.setFixedCellSize(30);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.getColumns().addAll(Arrays.asList(col1()));
        setRosterRowFactory();
        return null;
    }

    private TableColumn<AnswerBlockPOJO, String> col1() {
        TableColumn<AnswerBlockPOJO, String> column = new TableColumn<>("Name");
        column.setMaxWidth(1f * Integer.MAX_VALUE * 80);  // Join Date 15%
        column.setCellValueFactory(new PropertyValueFactory<>("prettyFormat"));
        return column;
    }

//    private TableColumn<AnswersDetailPOJO, String> col2() {
//        TableColumn<AnswersDetailPOJO, String> column = new TableColumn<>("Key");
//        column.setMaxWidth(1f * Integer.MAX_VALUE * 20);  // Join Date 15%
//        column.setCellValueFactory(new PropertyValueFactory<>("info2"));
//        return column;
//    }
}

//        column.setCellValueFactory(new PropertyValueFactory<>("archived"));