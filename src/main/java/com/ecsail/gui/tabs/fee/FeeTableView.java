package com.ecsail.gui.tabs.fee;

import com.ecsail.EditCell;
import com.ecsail.structures.FeeDTO;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        
//        TableColumn<FeeDTO, String> col1 = createColumn("Fee Name", FeeDTO::fieldNameProperty);
////        col1.setPrefWidth(60);
//        col1.setStyle("-fx-alignment: CENTER-RIGHT;");
//        col1.setOnEditCommit(
//                t -> {
//                    t.getTableView().getItems().get(
//                            t.getTablePosition().getRow()).setFieldName(t.getNewValue());
//                }
//        );

        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
//        ObservableList<PaymentType> paymentTypeList = FXCollections.observableArrayList(PaymentType.values());

        TableColumn<FeeDTO, String> col2 = createColumn("Price", FeeDTO::fieldValueProperty);
        col2.setStyle("-fx-alignment: CENTER-RIGHT;");
        col2.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setFieldValue(t.getNewValue());
                }
        );

        TableColumn<FeeDTO, String> col3 = createColumn("Description", FeeDTO::descriptionProperty);
        col3.setStyle("-fx-alignment: CENTER-LEFT;");
        col3.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDescription(t.getNewValue());

                }
        );


//        col1.setMaxWidth(1f * Integer.MAX_VALUE * 35);  // Fee Name
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 30);  // Fee Price
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 70);   // Description


        //////////////// ATTRIBUTES ///////////////////
        HBox.setHgrow(this, Priority.ALWAYS);
//        setEditable(!footer.getInvoice().isCommitted());

        setItems(fees);
        setPrefHeight(150);
        setPrefWidth(380);
        setFixedCellSize(30);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(Arrays.asList(col2, col3));
    }

    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }
}
