package com.ecsail.gui.tabs.fee;

import com.ecsail.EditCell;
import com.ecsail.Launcher;
import com.ecsail.structures.FeeDTO;
import com.ecsail.structures.MembershipListDTO;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.Arrays;
import java.util.function.Function;

public class FeeTableView extends TableView<FeeDTO> {
    FeeEditControls parent;
    ObservableList fees;

    public FeeTableView(FeeEditControls hBoxEditControls) {
        this.parent = hBoxEditControls;
        this.fees = parent.getFees();


        TableColumn<FeeDTO, String> col2 = createColumn("Price", FeeDTO::fieldValueProperty);
        col2.setStyle("-fx-alignment: CENTER-RIGHT;");
        col2.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setFieldValue(t.getNewValue())
        );

        TableColumn<FeeDTO, String> col3 = createColumn("Description", FeeDTO::descriptionProperty);
        col3.setStyle("-fx-alignment: CENTER-LEFT;");
        col3.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setDescription(t.getNewValue())
        );

//        col1.setMaxWidth(1f * Integer.MAX_VALUE * 5);  // Fee Name
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Fee Price
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 85);   // Description

        //////////////// ATTRIBUTES ///////////////////
        HBox.setHgrow(this, Priority.ALWAYS);
//        setEditable(!footer.getInvoice().isCommitted());

        setItems(fees);
        setRowListener();
        setPrefHeight(150);
        setPrefWidth(380);
        setFixedCellSize(30);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(Arrays.asList(col2, col3));
    }

    public void setRowListener() {
        this.setRowFactory(tv -> {
            TableRow<FeeDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    // int rowIndex = row.getIndex();
                    FeeDTO clickedRow = row.getItem();
                    parent.getSelectedHBoxFeeRow().setSelectedFee(clickedRow);
                    parent.getTabFee().getDuesLineChart().refreshChart(clickedRow.getDescription());
                    System.out.println(clickedRow);
                }
            });
            return row;
        });
    }


    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }
}
