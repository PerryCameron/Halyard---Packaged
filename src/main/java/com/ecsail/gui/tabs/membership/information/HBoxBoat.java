package com.ecsail.gui.tabs.membership.information;

import com.ecsail.EditCell;
import com.ecsail.Launcher;
import com.ecsail.enums.KeelType;
import com.ecsail.gui.tabs.membership.TabMembership;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.BoatDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class HBoxBoat extends HBox {

    private final TableView<BoatDTO> boatTableView;

    TabMembership tm;

    //@SuppressWarnings("unchecked")
    public HBoxBoat(TabMembership tm) {
        this.tm = tm;

        this.boatTableView = new TableView<>();
        ///////////	 OBJECTS ///////////////

        var hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
        var vboxPink = new VBox(); // this creates a pink border around the table
        var buttonVBox = new VBox();
        var boatAdd = new Button("Add");
        var boatDelete = new Button("Delete");
        var boatView = new Button("view");

        /////////////////  ATTRIBUTES  /////////////////////

        buttonVBox.setSpacing(5);
        hboxGrey.setSpacing(10);
        this.setSpacing(10);

        boatAdd.setPrefWidth(60);
        boatDelete.setPrefWidth(60);
        boatView.setPrefWidth(60);
        boatTableView.setPrefWidth(850);

        VBox.setVgrow(boatTableView, Priority.ALWAYS);
        HBox.setHgrow(boatTableView, Priority.ALWAYS);
        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
        HBox.setHgrow(vboxPink, Priority.ALWAYS);

        hboxGrey.setPadding(new Insets(5, 5, 5, 5));
        vboxPink.setPadding(new Insets(2, 2, 2, 2)); // spacing to make pink frame around table
        setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame

        hboxGrey.setId("box-background-light");
        vboxPink.setId("box-pink");
        this.setId("custom-tap-pane-frame");

        ///////////////// TABLE VIEW ///////////////////////

        boatTableView.setItems(tm.getBoats());
        boatTableView.setFixedCellSize(30);
        boatTableView.setEditable(true);
        boatTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        final TableColumn<BoatDTO, String> col1 = createColumn("Boat Name", BoatDTO::boat_nameProperty);
        col1.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setBoat_name(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("boat_name", boat_id, t.getNewValue());
                }
        );

        final TableColumn<BoatDTO, String> col2 = createColumn("Manufacturer", BoatDTO::manufacturerProperty);
        col2.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setManufacturer(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("manufacturer", boat_id, t.getNewValue());
                }
        );

        final TableColumn<BoatDTO, String> col3 = createColumn("Year", BoatDTO::manufacture_yearProperty);
        col3.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setManufacture_year(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("manufacture_year", boat_id, t.getNewValue());
                }
        );

        final TableColumn<BoatDTO, String> col4 = createColumn("Model", BoatDTO::modelProperty);
        col4.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setModel(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("model", boat_id, t.getNewValue());
                }
        );

        final TableColumn<BoatDTO, String> col5 = createColumn("Registration", BoatDTO::registration_numProperty);
        col5.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setRegistration_num(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("registration_num", boat_id, t.getNewValue());
                }
        );

        final TableColumn<BoatDTO, String> col6 = createColumn("Sail #", BoatDTO::sail_numberProperty);
        col6.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setSail_number(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("sail_number", boat_id, t.getNewValue());
                }
        );

        final TableColumn<BoatDTO, String> col7 = createColumn("PHRF", BoatDTO::phrfProperty);
        col7.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setPhrf(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("phrf", boat_id, t.getNewValue());
                }
        );

        final TableColumn<BoatDTO, String> col8 = createColumn("Length", BoatDTO::lengthProperty);
        col8.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setLength(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("length", boat_id, t.getNewValue());
                }
        );

        final TableColumn<BoatDTO, String> col9 = createColumn("Weight", BoatDTO::weightProperty);
        col9.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setWeight(t.getNewValue());
                    var boat_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBoat_id();
                    SqlUpdate.updateBoat("weight", boat_id, t.getNewValue());
                }
        );

        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        final TableColumn<BoatDTO, Boolean> col10 = new TableColumn<>("Trailer");
        col10.setCellValueFactory(param -> {
            BoatDTO boat = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(boat.isHasTrailer());
            // Note: singleCol.setOnEditCommit(): Not work for
            // CheckBoxTableCell.

            // When "has trailer?" column change.
            booleanProp.addListener((observable, oldValue, newValue) -> {
                boat.setHasTrailer(newValue);
                SqlUpdate.updateBoat(boat.getBoat_id(), "HAS_TRAILER", newValue);
            });
            return booleanProp;
        });

        //
        col10.setCellFactory(p -> {
            CheckBoxTableCell<BoatDTO, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        //example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        ObservableList<KeelType> keelList = FXCollections.observableArrayList(KeelType.values());
        final TableColumn<BoatDTO, KeelType> col11 = new TableColumn<>("Keel");
        col11.setCellValueFactory(param -> {
            var boat = param.getValue();
            var keelCode = boat.getKeel();
            var keel = KeelType.getByCode(keelCode);
            return new SimpleObjectProperty<>(keel);
        });

        col11.setCellFactory(ComboBoxTableCell.forTableColumn(keelList));

        col11.setOnEditCommit((CellEditEvent<BoatDTO, KeelType> event) -> {
            var pos = event.getTablePosition();
            var newKeel = event.getNewValue();
            var row = pos.getRow();
            var boat = event.getTableView().getItems().get(row);
            SqlUpdate.updateBoat(boat.getBoat_id(), newKeel.getCode());
            boat.setKeel(newKeel.getCode());
        });

        /// sets width of columns by percentage
        col1.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Boat Name
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 10);  // Manufacturer
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 5);   // Year
        col4.setMaxWidth(1f * Integer.MAX_VALUE * 20);  // Model
        col5.setMaxWidth(1f * Integer.MAX_VALUE * 10);  // Registration
        col6.setMaxWidth(1f * Integer.MAX_VALUE * 5);   // Sail #
        col7.setMaxWidth(1f * Integer.MAX_VALUE * 5);   // PHRF
        col8.setMaxWidth(1f * Integer.MAX_VALUE * 5);   // Length
        col9.setMaxWidth(1f * Integer.MAX_VALUE * 5);   // Weight
        col10.setMaxWidth(1f * Integer.MAX_VALUE * 5);  // Trailer
        col11.setMaxWidth(1f * Integer.MAX_VALUE * 15); // Keel
        /////////////// LISTENERS ////////////////////

        boatAdd.setOnAction((event) -> {
            // get next available primary key for boat table
            int boat_id = SqlSelect.getNextAvailablePrimaryKey("boat", "boat_id");
            // create boat object
            BoatDTO b = new BoatDTO(boat_id, tm.getMembership().getMsid(), "", "", "", "", "", "", true, "", "", "", "", "", "", "", false);
            // insert data from new boat object into SQL table boat, return true if successful
            if (SqlInsert.addBoatRecord(b, tm.getMembership().getMsid()))
                // insert row into tableView to match SQL record
                tm.getBoats().add(b);
            // Now we will sort it to the top
            tm.getBoats().sort(Comparator.comparing(BoatDTO::getBoat_id).reversed());
            // this line prevents strange buggy behaviour
            boatTableView.layout();
            // edit the boat name cell after creating
            boatTableView.edit(0, col1);
        });

        boatDelete.setOnAction((event) -> {
            // get the index that is selected
            int selectedIndex = boatTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                var boatDTO = tm.getBoats().get(selectedIndex);
                Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
                conformation.setTitle("Delete Boat");
                conformation.setHeaderText("Removing Boat " + boatDTO.getBoat_id());
                conformation.setContentText("Are sure you want to delete this boat ?\n\n");
                DialogPane dialogPane = conformation.getDialogPane();
                dialogPane.getStylesheets().add("css/dark/dialogue.css");
                dialogPane.getStyleClass().add("dialog");
                Optional<ButtonType> result = conformation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // delete boat from database
                    SqlDelete.deleteBoatOwner(boatDTO.getBoat_id(), tm.getMembership().getMsid());
                    // remove from GUI
                    boatTableView.getItems().remove(selectedIndex);
                }
            }
        });

        boatView.setOnAction((event) -> {
            int selectedIndex = boatTableView.getSelectionModel().getSelectedIndex();
            Launcher.openBoatViewTab(tm.getBoats().get(selectedIndex));
        });

        boatTableView.setRowFactory(tv -> {
            TableRow<BoatDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                    System.out.println("We want to do something with " + row.getItem().toString());
                }
            });
            return row;
        });

        /////////////////// SET CONTENT ///////////////////

        boatTableView.getColumns().addAll(Arrays.asList(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11));
        buttonVBox.getChildren().addAll(boatAdd, boatDelete, boatView);
        vboxPink.getChildren().add(boatTableView);
        hboxGrey.getChildren().addAll(vboxPink, buttonVBox);
        getChildren().addAll(hboxGrey);
    }

    ///////////////// CLASS METHODS /////////////////

    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }
} // CLASS END
