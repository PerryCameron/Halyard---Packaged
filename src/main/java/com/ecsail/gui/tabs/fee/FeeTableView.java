package com.ecsail.gui.tabs.fee;

import com.ecsail.EditCell;
import com.ecsail.StringTools;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.dto.FeeDTO;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

public class FeeTableView extends TableView<FeeDTO> {
    FeeEditControls parent;
    ObservableList<FeeDTO> fees;
    HashMap<Integer,String> hashMap = new HashMap<>();
    public FeeTableView(FeeEditControls hBoxEditControls) {
        this.parent = hBoxEditControls;
        this.fees = parent.getFees();

        // adds old values to hashmap for later use in SQL Query
        fees.addListener((ListChangeListener<FeeDTO>) c -> {
            hashMap.clear();
            if(fees.size() > 0) // old description used for updating fee
                fees.forEach(feeDTO -> hashMap.put(feeDTO.getFeeId(),feeDTO.getDescription()));
        });

        if(fees.size() > 0) // old description used for updating fee
            fees.forEach(feeDTO -> hashMap.put(feeDTO.getFeeId(),feeDTO.getDescription()));

        TableColumn<FeeDTO, String> col2 = createColumn("Price", FeeDTO::fieldValueProperty);
        col2.setStyle("-fx-alignment: CENTER-RIGHT;");
        col2.setOnEditCommit(t -> {
            String checkedValue = StringTools.changeEmptyStringToZero(t.getNewValue());
            BigDecimal dollarValue = new BigDecimal(checkedValue);
            String fixedDollarValue = String.valueOf(dollarValue.setScale(2, RoundingMode.HALF_UP));
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setFieldValue(fixedDollarValue);
            FeeDTO feeDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
            SqlUpdate.updateFeeRecord(feeDTO);
            parent.parent.duesLineChart.refreshChart(feeDTO.getDescription());
        });

        TableColumn<FeeDTO, String> col3 = createColumn("Description", FeeDTO::descriptionProperty);
        col3.setStyle("-fx-alignment: CENTER-LEFT;");
        col3.setOnEditCommit(t -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(t.getNewValue());
            FeeDTO feeDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
            int fee_id = feeDTO.getFeeId();
            SqlUpdate.updateFeeByDescriptionAndFieldName(feeDTO, hashMap.get(fee_id)); // hashMap.get(fee_id) = old
            hashMap.put(fee_id, feeDTO.getDescription()); // = change hash value in case you change it again right away
            parent.parent.duesLineChart.refreshChart(feeDTO.getDescription());
        });

        col2.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Fee Price
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 85);   // Description

        //////////////// ATTRIBUTES ///////////////////
        HBox.setHgrow(this, Priority.ALWAYS);
        setEditable(true);
        setItems(fees);
        setRowListener();
        setPrefHeight(150);
        setPrefWidth(380);
        setFixedCellSize(30);
        getSelectionModel().selectFirst();
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
                    parent.parent.selectedFeeRow.selectedFee = clickedRow;
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
