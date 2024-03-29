package com.ecsail.views.tabs.membership.information;

import com.ecsail.EditCell;
import com.ecsail.Launcher;
import com.ecsail.enums.KeelType;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.dto.BoatDTO;
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

    protected TabMembership parent;
    private final BoatRepository boatRepository = new BoatRepositoryImpl();

    public HBoxBoat(TabMembership parent) {
        this.parent = parent;

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

        boatTableView.setItems(parent.getModel().getBoats());
        boatTableView.setFixedCellSize(30);
        boatTableView.setEditable(true);
        boatTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        final TableColumn<BoatDTO, String> col1 = createColumn("Boat Name", BoatDTO::boatNameProperty);
        col1.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setBoatName(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        final TableColumn<BoatDTO, String> col2 = createColumn("Manufacturer", BoatDTO::manufacturerProperty);
        col2.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setManufacturer(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        final TableColumn<BoatDTO, String> col3 = createColumn("Year", BoatDTO::manufactureYearProperty);
        col3.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setManufactureYear(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        final TableColumn<BoatDTO, String> col4 = createColumn("Model", BoatDTO::modelProperty);
        col4.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setModel(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        final TableColumn<BoatDTO, String> col5 = createColumn("Registration", BoatDTO::registrationNumProperty);
        col5.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setRegistrationNum(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        final TableColumn<BoatDTO, String> col6 = createColumn("Sail #", BoatDTO::sailNumberProperty);
        col6.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setSailNumber(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        final TableColumn<BoatDTO, String> col7 = createColumn("PHRF", BoatDTO::phrfProperty);
        col7.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setPhrf(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        final TableColumn<BoatDTO, String> col8 = createColumn("Length", BoatDTO::loaProperty);
        col8.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setLoa(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        final TableColumn<BoatDTO, String> col9 = createColumn("Weight", BoatDTO::displacementProperty);
        col9.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDisplacement(t.getNewValue());
                    BoatDTO boatDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    boatRepository.updateBoat(boatDTO );
                }
        );

        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        final TableColumn<BoatDTO, Boolean> col10 = new TableColumn<>("Trailer");
        col10.setCellValueFactory(param -> {
            BoatDTO boatDTO = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(boatDTO.hasTrailer());
            booleanProp.addListener((observable, oldValue, newValue) -> {
                boatDTO.setHasTrailer(newValue);
                boatRepository.updateBoat(boatDTO);
            });
            return booleanProp;
        });


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
            BoatDTO boatDTO = event.getTableView().getItems().get(row);
            boatDTO.setKeel(newKeel.getCode());
            boatRepository.updateBoat(boatDTO);
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
            int msId = parent.getModel().getMembership().getMsId();
            // create boat object
            BoatDTO boatDTO = boatRepository.insertBoat(new BoatDTO(msId));
            // this will return a 1 on success but logic not needed here
            boatRepository.insertBoatOwner(msId, boatDTO.getBoatId());
            parent.getModel().getBoats().add(boatDTO);
            // Now we will sort it to the top
            parent.getModel().getBoats().sort(Comparator.comparing(BoatDTO::getBoatId).reversed());
            // this line prevents strange buggy behaviour
            boatTableView.layout();
            // edit the boat name cell after creating
            boatTableView.edit(0, col1);
        });

        boatDelete.setOnAction((event) -> {
            // get the index that is selected
            int selectedIndex = boatTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                var boatDTO = parent.getModel().getBoats().get(selectedIndex);
                Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
                conformation.setTitle("Delete Boat");
                conformation.setHeaderText("Removing Boat " + boatDTO.getBoatId());
                conformation.setContentText("Are sure you want to delete this boat ?\n\n");
                DialogPane dialogPane = conformation.getDialogPane();
                dialogPane.getStylesheets().add("css/dark/dialogue.css");
                dialogPane.getStyleClass().add("dialog");
                Optional<ButtonType> result = conformation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // delete boat from database
                    boatRepository.deleteBoatOwner(boatDTO.getBoatId(), parent.getModel().getMembership().getMsId());
                    // remove from GUI
                    boatTableView.getItems().remove(selectedIndex);
                }
            }
        });

        boatView.setOnAction((event) -> {
            int selectedIndex = boatTableView.getSelectionModel().getSelectedIndex();
            Launcher.openBoatViewTab(parent.getModel().getBoats().get(selectedIndex));
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
