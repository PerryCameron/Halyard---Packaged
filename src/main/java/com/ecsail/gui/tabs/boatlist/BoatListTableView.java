package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import com.ecsail.dto.BoatListDTO;
import com.ecsail.sql.SqlUpdate;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

public class BoatListTableView extends TableView<BoatListDTO> {
    protected TabBoatList parent;
    public BoatListTableView(TabBoatList pa) {
        this.parent = pa;

        this.setItems(parent.boats);
        this.setFixedCellSize(30);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
        this.setEditable(true);

        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);

        var Col1 = new TableColumn<BoatListDTO, String>("ID");
        var boatId = new TableColumn<BoatListDTO, String>("Boat");
        var Col2 = new TableColumn<BoatListDTO, String>("Last Name");
        var Col3 = new TableColumn<BoatListDTO, String>("First Name");
        var Col4 = new TableColumn<BoatListDTO, String>("Model");
        var Col5 = new TableColumn<BoatListDTO, String>("Registration");
        var Col7 = new TableColumn<BoatListDTO, String>("Name");
        var Col8 = new TableColumn<BoatListDTO, Text>("Images");
        var Col9 = new TableColumn<BoatListDTO, Boolean>("Aux");

        Col1.setCellValueFactory(new PropertyValueFactory<BoatListDTO, String>("membershipId"));
        Col1.setStyle("-fx-alignment: top-center");
        boatId.setCellValueFactory(new PropertyValueFactory<>("boatId"));
        boatId.setStyle("-fx-alignment: top-center");
        Col2.setCellValueFactory(new PropertyValueFactory<>("lName"));
        Col3.setCellValueFactory(new PropertyValueFactory<>("fName"));
        Col4.setCellValueFactory(new PropertyValueFactory<>("model"));
        Col5.setCellValueFactory(new PropertyValueFactory<>("registrationNum"));
        Col7.setCellValueFactory(new PropertyValueFactory<>("boatName"));
        Col8.setCellValueFactory(new PropertyValueFactory<>("numberOfImages"));
        Col8.setStyle("-fx-alignment: top-center");
        Col8.setCellValueFactory(param -> {  // don't need this now but will use for subleases
            BoatListDTO bl = param.getValue();
            String valueDisplayed = String.valueOf(bl.getNumberOfImages());
            Text valueText = new Text(valueDisplayed);
            if(bl.getNumberOfImages() != 0) {
                valueText.setFill(Color.BLUE);
            }
            return new SimpleObjectProperty<>(valueText);
        });

        Col9.setCellValueFactory(new PropertyValueFactory<>("aux"));

        Col9.setCellValueFactory(param -> {
            BoatListDTO boat = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(boat.isAux());
            // Note: singleCol.setOnEditCommit(): Not work for
            // CheckBoxTableCell.

            // When "Listed?" column change.
            booleanProp.addListener((observable, oldValue, newValue) -> {
                boat.setAux(newValue);
                SqlUpdate.updateAux(String.valueOf(boat.getBoatId()), newValue);

            });
            return booleanProp;
        });

        //
        Col9.setCellFactory(p -> {
            CheckBoxTableCell<BoatListDTO, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        /// sets width of columns by percentage
        Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // Membership ID
        boatId.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // boat ID
        Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Last Name
        Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // First Name
        Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Model
        Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Registration
        Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 16 );  // Boat Name
        Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 9 );   // Images
        Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // aux

        this.getColumns()
                .addAll(Arrays.asList(Col1, boatId, Col2, Col3, Col4, Col5, Col7, Col8, Col9));

        this.setRowFactory(tv -> {
            TableRow<BoatListDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                parent.selectedBoat = row.getItem();
                parent.controlBox.refreshCurrentBoatDetails();
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Launcher.openBoatViewTab(parent.selectedBoat);
                }
            });
            return row;
        });
    selectFirstRow(this); // this allows boat details to start off populated.
    }

    private void selectFirstRow(TableView<BoatListDTO> boatListTableView) {
        Platform.runLater(() -> {
            boatListTableView.requestFocus();
            boatListTableView.getSelectionModel().select(0);
            boatListTableView.getFocusModel().focus(0);
            parent.selectedBoat = boatListTableView.getSelectionModel().getSelectedItem();
            parent.controlBox.refreshCurrentBoatDetails();
        });
    }
}
