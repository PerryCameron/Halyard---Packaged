package com.ecsail.views.tabs.roster;

import com.ecsail.Launcher;
import com.ecsail.dto.MembershipListDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

public class RosterTableView extends TableView<MembershipListDTO> {
    TabRoster parent;

    public RosterTableView(TabRoster p) {
        this.parent = p;
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);

        TableColumn<MembershipListDTO, Integer> idCol = new TableColumn<>("ID");
        TableColumn<MembershipListDTO, String> joinDateCol = new TableColumn<>("Join Date");
        TableColumn<MembershipListDTO, Text> typeCol = new TableColumn<>("Type");
        TableColumn<MembershipListDTO, Text> slipCol = new TableColumn<>("Slip");
        TableColumn<MembershipListDTO, String> firstNameCol = new TableColumn<>("First Name");
        TableColumn<MembershipListDTO, String> lastNameCol = new TableColumn<>("Last Name");
        TableColumn<MembershipListDTO, String> stateCol = new TableColumn<>("City");
        TableColumn<MembershipListDTO, String> msIdCol = new TableColumn<>("MSID");

        this.setItems(parent.rosters);
        this.setFixedCellSize(30);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idCol.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("memType"));
        typeCol.setStyle("-fx-alignment: top-center");
        typeCol.setCellValueFactory(param -> {  // don't need this now but will use for subleases
            MembershipListDTO m = param.getValue();
            Text text = new Text();
            String valueDisplayed = "";
            if (m.getMemType() != null) {
                valueDisplayed = m.getMemType();
            }
            switch (valueDisplayed) {
                case "SO" -> text.setFill(Color.GREEN);
                case "FM" -> text.setFill(Color.BLUE);
                case "RM" -> text.setFill(Color.RED);
            }
//			else if(valueDisplayed.equals("LA")) text.setFill(Color.KHAKI);
            text.setText(valueDisplayed);
            return new SimpleObjectProperty<>(text);
        });
        slipCol.setCellValueFactory(new PropertyValueFactory<>("slip"));
        slipCol.setStyle("-fx-alignment: top-center");
        // erasing code below will change nothing
        slipCol.setCellValueFactory(param -> {  // don't need this now but will use for subleases
            MembershipListDTO m = param.getValue();

            String valueDisplayed = "";
            if (m.getSlip() != null) {
                valueDisplayed = m.getSlip();
            }
            Text text = new Text(valueDisplayed);
            text.setFill(Color.CHOCOLATE);
            return new SimpleObjectProperty<>(text);
        });

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("fName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lName"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateCol.setStyle("-fx-alignment: top-center");
        msIdCol.setCellValueFactory(new PropertyValueFactory<>("msId"));

        /// sets width of columns by percentage
        idCol.setMaxWidth(1f * Integer.MAX_VALUE * 5);   // Mem 5%
        joinDateCol.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Join Date 15%
        typeCol.setMaxWidth(1f * Integer.MAX_VALUE * 10);   // Type
        slipCol.setMaxWidth(1f * Integer.MAX_VALUE * 10);   // Slip
        firstNameCol.setMaxWidth(1f * Integer.MAX_VALUE * 15);   // First Name
        lastNameCol.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Last Name
        stateCol.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // State
        msIdCol.setMaxWidth(1f * Integer.MAX_VALUE * 10); // MSID

        this.getColumns()
                .addAll(Arrays.asList(idCol, firstNameCol, lastNameCol, typeCol, joinDateCol, slipCol, stateCol, msIdCol));
        setRosterRowFactory();
    }

    private void setRosterRowFactory() {
        this.setRowFactory(tv -> {
            TableRow<MembershipListDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    // int rowIndex = row.getIndex();
                    MembershipListDTO clickedRow = row.getItem();
                    Launcher.createMembershipTabForRoster(clickedRow.getMembershipId(), clickedRow.getMsId());
                }
//				if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
//				row.setContextMenu(new rosterContextMenu(row.getItem(), selectedYear));
//				}
            });
            return row;
        });
    }
}