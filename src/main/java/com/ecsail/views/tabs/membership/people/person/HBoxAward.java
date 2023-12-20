package com.ecsail.views.tabs.membership.people.person;

import com.ecsail.BaseApplication;
import com.ecsail.EditCell;
import com.ecsail.enums.Awards;
import com.ecsail.repository.interfaces.AwardRepository;
import com.ecsail.dto.AwardDTO;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

public class HBoxAward extends HBox {

    private final PersonDTO person;
    private final ObservableList<AwardDTO> award;
    private final TableView<AwardDTO> awardTableView;
    private final String currentYear;
    private final AwardRepository awardRepository;


    public HBoxAward(PersonDTO p, TabMembership parent) {
        this.person = p;
        this.awardRepository = parent.getModel().getAwardRepository();
        this.currentYear = new SimpleDateFormat("yyyy").format(new Date());
        this.award = FXCollections.observableArrayList(awardRepository.getAwards(person));

        ///////////////// OBJECT INSTANCE ///////////////////
        var awardAdd = new Button("Add");
        var awardDelete = new Button("Delete");
        var vboxButtons = new VBox(); // holds officer buttons
        var hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
        var vboxPink = new VBox(); // this creates a pink border around the table
        awardTableView = new TableView<>();

        /////////////////  ATTRIBUTES  /////////////////////
        awardAdd.setPrefWidth(60);
        awardDelete.setPrefWidth(60);
        vboxButtons.setPrefWidth(80);

        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
        HBox.setHgrow(vboxPink, Priority.ALWAYS);
        HBox.setHgrow(awardTableView, Priority.ALWAYS);
        VBox.setVgrow(awardTableView, Priority.ALWAYS);

        hboxGrey.setSpacing(10);  // spacing in between table and buttons
        vboxButtons.setSpacing(5);

        hboxGrey.setId("box-background-light");
        vboxPink.setId("box-pink");
        this.setId("box-frame-dark");

        hboxGrey.setPadding(new Insets(5, 5, 5, 5));  // spacing around table and buttons
        vboxPink.setPadding(new Insets(2, 2, 2, 2)); // spacing to make pink frame around table

        ///////////////// TABLE VIEW ///////////////////////

        awardTableView.setItems(award);
        awardTableView.setFixedCellSize(30);
        awardTableView.setEditable(true);
        awardTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<AwardDTO, String> Col1 = createColumn(AwardDTO::awardYearProperty);
        Col1.setSortType(TableColumn.SortType.DESCENDING);
        Col1.setOnEditCommit(
                t -> {
                    AwardDTO awardDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    awardRepository.updateAward(awardDTO);  // have to get by money id and pid eventually
                }
        );

        ObservableList<Awards> awardsList = FXCollections.observableArrayList(Awards.values());
        final TableColumn<AwardDTO, Awards> col2 = new TableColumn<>("Award Type");
        col2.setCellValueFactory(param -> {
            AwardDTO thisAward = param.getValue();
            String awardCode = thisAward.getAwardType();
            Awards type = Awards.getByCode(awardCode);
            return new SimpleObjectProperty<>(type);
        });

        col2.setCellFactory(ComboBoxTableCell.forTableColumn(awardsList));

        col2.setOnEditCommit((CellEditEvent<AwardDTO, Awards> event) -> {
            // get the position on the table
            TablePosition<AwardDTO, Awards> pos = event.getTablePosition();
            // use enum to convert DB value
            Awards newAward = event.getNewValue();
            // give object a name to manipulate
            AwardDTO awardDTO = event.getTableView().getItems().get(pos.getRow());
            // update the DTO
            awardDTO.setAwardType(newAward.getCode());
            // update the SQL
            awardRepository.updateAward(awardDTO);  // have to get by money id and pid eventually
        });

        /// sets width of columns by percentage
        Col1.setMaxWidth(1f * Integer.MAX_VALUE * 20);   // Phone
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 50);  // Type


        ////////////////////// LISTENERS /////////////////////////

        awardAdd.setOnAction((event) -> {
            BaseApplication.logger.info("Added award entry to " + person.getNameWithInfo());
            // Create new award object and insert into database
            AwardDTO awardDTO = awardRepository.insertAward(new AwardDTO(person.getpId(), currentYear));
            // create awardDTO new row in tableView to match the SQL insert from above
            award.add(awardDTO);
            // sort awards ascending
            award.sort(Comparator.comparing(AwardDTO::getAwardId).reversed());
            // this line prevents strange buggy behaviour
            awardTableView.layout();
            // edit the year cell after creating
            awardTableView.edit(0, Col1);
        });

        awardDelete.setOnAction(e -> {
            int selectedIndex = awardTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                AwardDTO awardDTO = award.get(selectedIndex);
                Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
                conformation.setTitle("Delete Award Entry");
                String thisAward = awardDTO.getAwardYear() + " " + Awards.getNameByCode(awardDTO.getAwardType());
                conformation.setHeaderText(thisAward);
                conformation.setContentText("Are sure you want to delete this award for " + person.getFullName() + "?");
                DialogPane dialogPane = conformation.getDialogPane();
                dialogPane.getStylesheets().add("css/dark/dialogue.css");
                dialogPane.getStyleClass().add("dialog");
                Optional<ButtonType> result = conformation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (awardRepository.delete(awardDTO) == 1) {
						BaseApplication.logger.info("Removed award entry "
								+ thisAward
								+ " for " + person.getNameWithInfo());
						awardTableView.getItems().remove(selectedIndex);
					}
                }
            }
        });

        /////////////////// SET CONTENT //////////////////

        vboxButtons.getChildren().addAll(awardAdd, awardDelete);
        awardTableView.getColumns().addAll(Arrays.asList(Col1, col2));
        awardTableView.getSortOrder().add(Col1);
        vboxPink.getChildren().add(awardTableView);
        awardTableView.sort();
        hboxGrey.getChildren().addAll(vboxPink, vboxButtons);
        getChildren().add(hboxGrey);

    } // CONSTRUCTOR END

    ///////////////// CLASS METHODS /////////////////

    private <T> TableColumn<T, String> createColumn(Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>("Year");
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }
}
