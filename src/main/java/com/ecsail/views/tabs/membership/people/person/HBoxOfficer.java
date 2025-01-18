package com.ecsail.views.tabs.membership.people.person;

import com.ecsail.BaseApplication;
import com.ecsail.EditCell;
import com.ecsail.enums.Officer;
import com.ecsail.repository.interfaces.OfficerRepository;
import com.ecsail.dto.OfficerDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.views.tabs.membership.TabMembership;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HBoxOfficer extends HBox {

    private final PersonDTO person;
    private final ObservableList<OfficerDTO> officerDTOS;
    private final TableView<OfficerDTO> officerTableView;
    private final String currentYear;
    private final OfficerRepository officerRepository;


    public HBoxOfficer(PersonDTO personDTO, TabMembership parent) {
        this.person = personDTO;
        this.currentYear = new SimpleDateFormat("yyyy").format(new Date());
        this.officerRepository = parent.getModel().getOfficerRepository();
        this.officerDTOS = FXCollections.observableArrayList(officerRepository.getOfficer(personDTO));
        ///////////////// OBJECT INSTANCE ///////////////////
        Button officerAdd = new Button("Add");
        Button officerDelete = new Button("Delete");
        VBox vboxButton = new VBox(); // holds officer buttons
        HBox hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
        VBox vboxPink = new VBox(); // this creates a pink border around the table
        officerTableView = new TableView<>();

        /////////////////  ATTRIBUTES  /////////////////////
        officerAdd.setPrefWidth(60);
        officerDelete.setPrefWidth(60);
        vboxButton.setPrefWidth(80);

        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
        HBox.setHgrow(vboxPink, Priority.ALWAYS);
        HBox.setHgrow(officerTableView, Priority.ALWAYS);
        VBox.setVgrow(officerTableView, Priority.ALWAYS);

        hboxGrey.setSpacing(10);  // spacing in between table and buttons
        vboxButton.setSpacing(5);
        hboxGrey.setId("box-background-light");
        vboxPink.setId("box-pink");
        hboxGrey.setPadding(new Insets(5, 5, 5, 5));  // spacing around table and buttons
        vboxPink.setPadding(new Insets(2, 2, 2, 2)); // spacing to make pink frame around table

        this.setId("box-frame-dark");

        ///////////////// TABLE VIEW ///////////////////////

        officerTableView.setItems(officerDTOS);
        officerTableView.setFixedCellSize(30);
        officerTableView.setEditable(true);
        officerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<OfficerDTO, String> Col1 = createColumn("Year", OfficerDTO::fiscalYearProperty);
        Col1.setSortType(TableColumn.SortType.DESCENDING);
        Col1.setOnEditCommit(
                t -> {
                    OfficerDTO officerDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    officerDTO.setFiscalYear(t.getNewValue());
                    officerRepository.update(officerDTO);  // have to get by money id and pid eventually
                }
        );

        ObservableList<String> officerList = FXCollections.observableArrayList(BaseApplication.boardPositions.stream().map(e -> e.position()).collect(Collectors.toList()));
        final TableColumn<OfficerDTO, String> Col2 = new TableColumn<>("Officers, Chairs and Board");
        Col2.setCellValueFactory(param -> {
            OfficerDTO thisOfficer = param.getValue();
            String type = Officer.getByCode(thisOfficer.getOfficerType());
            return new SimpleObjectProperty<>(type);
        });

        Col2.setCellFactory(ComboBoxTableCell.forTableColumn(officerList));

        Col2.setOnEditCommit((CellEditEvent<OfficerDTO, String> event) -> {
            TablePosition<OfficerDTO, String> pos = event.getTablePosition();
            String newOfficer = Officer.getByName(event.getNewValue());
            OfficerDTO officerDTO = event.getTableView().getItems().get(pos.getRow());
            officerDTO.setOfficerType(newOfficer);
            officerRepository.update(officerDTO);
        });

        TableColumn<OfficerDTO, String> Col3 = createColumn("Exp", OfficerDTO::boardYearProperty);
        Col3.setOnEditCommit(
                t -> {
                    OfficerDTO officerDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    officerDTO.setBoardYear(t.getNewValue());  // need to change
                    officerRepository.update(officerDTO);  // have to get by money id and pid eventually
                }
        );

        /// sets width of columns by percentage
        Col1.setMaxWidth(1f * Integer.MAX_VALUE * 20);   // Phone
        Col2.setMaxWidth(1f * Integer.MAX_VALUE * 50);  // Type
        Col3.setMaxWidth(1f * Integer.MAX_VALUE * 20);  // Listed
        ////////////////////// LISTENERS /////////////////////////

        officerAdd.setOnAction(e -> {
            BaseApplication.logger.info("Added new officer entry for " + person.getNameWithInfo());
            OfficerDTO officerDTO = officerRepository.insertOfficer(new OfficerDTO(person.getpId(), currentYear));
            // add a new row to the tableView to match new SQL entry
            officerDTOS.add(officerDTO);
            // Now we will sort it to the top
            officerDTOS.sort(Comparator.comparing(OfficerDTO::getOfficerId).reversed());
            // this line prevents strange buggy behaviour
            officerTableView.layout();
            // edit the phone number cell after creating
            officerTableView.edit(0, Col1);
        });

        officerDelete.setOnAction(e -> {
            int selectedIndex = officerTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                OfficerDTO officerDTO = officerDTOS.get(selectedIndex);
                Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
                conformation.setTitle("Delete Officer Entry");
                conformation.setHeaderText(officerDTO.getBoardYear() + " " + Officer.getByCode(officerDTO.getOfficerType()));
                conformation.setContentText("Are sure you want to delete this officer/chairman entry?");
                DialogPane dialogPane = conformation.getDialogPane();
                dialogPane.getStylesheets().add("css/dark/dialogue.css");
                dialogPane.getStyleClass().add("dialog");
                Optional<ButtonType> result = conformation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (officerRepository.deleteOfficer(officerDTO) == 1) {
                        officerTableView.getItems().remove(selectedIndex);
                        BaseApplication.logger.info("Deleted officer entry for " + person.getNameWithInfo());
                    }
                }
            }
        });

        /////////////////// SET CONTENT //////////////////

        vboxButton.getChildren().addAll(officerAdd, officerDelete);
        Collections.addAll(officerTableView.getColumns(), Col1, Col2, Col3);
        officerTableView.getSortOrder().add(Col1);
        vboxPink.getChildren().add(officerTableView);
        officerTableView.sort();
        hboxGrey.getChildren().addAll(vboxPink, vboxButton);
        getChildren().add(hboxGrey);

    } // CONSTRUCTOR END

    ///////////////// CLASS METHODS /////////////////

    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }

}
