package com.ecsail.views.tabs.membership.fiscal;

import com.ecsail.BaseApplication;
import com.ecsail.EditCell;
import com.ecsail.StringTools;
import com.ecsail.dto.MembershipIdDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.enums.MembershipType;
import com.ecsail.views.dialogues.DialogueError;
import com.ecsail.views.tabs.membership.TabMembership;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class HBoxHistory extends HBox {
    public static Logger logger = LoggerFactory.getLogger(HBoxHistory.class);

    private final TableView<MembershipIdDTO> idTableView;
    private final TabMembership parent;
    LocalDate date;

    public HBoxHistory(TabMembership parent) {
        this.parent = parent;
        this.idTableView = new TableView<>();

        /////// OBJECT INSTANCE //////

        var idAdd = new Button("Add");
        var idDelete = new Button("Delete");
        var hboxControls = new HBox(); // holds phone buttons
        var vboxGrey = new VBox(); // this is here for the grey background to make nice appearance
        var vboxPink = new VBox(); // this creates a pink border around the table
        var hboxJoinDate = new HBox();
        var hboxButtons = new HBox();
        var joinDatePicker = new DatePicker();

        //// OBJECT ATTRIBUTES /////
        hboxJoinDate.setAlignment(Pos.CENTER_LEFT);

        hboxJoinDate.setSpacing(5);
        hboxButtons.setSpacing(5);
        vboxGrey.setSpacing(10); // spacing in between table and buttons
        hboxControls.setSpacing(60); // spacing between buttons and joinDatePicker

        idAdd.setPrefWidth(60);
        idDelete.setPrefWidth(60);

        vboxGrey.setPadding(new Insets(5, 5, 5, 5)); // spacing around table and buttons
        vboxPink.setPadding(new Insets(2, 2, 2, 2)); // spacing to make pink frame around table
        setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame

        vboxGrey.setId("box-background-light");
        this.setId("custom-tap-pane-frame");

        VBox.setVgrow(idTableView, Priority.ALWAYS);
        HBox.setHgrow(idTableView, Priority.ALWAYS);
        VBox.setVgrow(vboxPink, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
        HBox.setHgrow(vboxGrey, Priority.ALWAYS);

        parent.getModel().getMembershipIdDTOS().sort(Comparator.comparing(MembershipIdDTO::getFiscalYear).reversed());

        idTableView.setItems(parent.getModel().getMembershipIdDTOS());
        idTableView.setFixedCellSize(30);
        idTableView.setEditable(true);
        idTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (parent.getModel().getMembership().getJoinDate() != null) {
            date = LocalDate.parse(parent.getModel().getMembership().getJoinDate(), formatter);
        } else {
            date = LocalDate.parse("1900-01-01", formatter);
        }
        joinDatePicker.setValue(date);

        TableColumn<MembershipIdDTO, String> Col1 = createColumn("Year", MembershipIdDTO::fiscalYearProperty);
        Col1.setOnEditCommit(t -> {
            MembershipIdDTO membershipIdDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
            String savedValue = membershipIdDTO.getFiscalYear();
            membershipIdDTO.setFiscalYear(StringTools.changeEmptyStringToZero(t.getNewValue()));
            if (!membershipIdDTO.getFiscalYear().equals(savedValue)) {  // if we changed the value
                if (parent.getModel().getMembershipIdRepository().rowExists(membershipIdDTO) == 0)
                    parent.getModel().getMembershipIdRepository().update(membershipIdDTO);
                else {
                    new DialogueError(true, "An entry for " + membershipIdDTO.getFiscalYear()
                            + " already exists with the membership Id of " + membershipIdDTO.getMembershipId(), 500, 100);
                    membershipIdDTO.setFiscalYear(savedValue);
                }
            }
        });

        TableColumn<MembershipIdDTO, String> Col2 = createColumn("Mem ID",
                MembershipIdDTO::membershipIdProperty);
        Col2.setOnEditCommit(t -> {
            MembershipIdDTO membershipIdDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
            String savedValue = membershipIdDTO.getMembershipId();
            membershipIdDTO.setMembershipId(StringTools.changeEmptyStringToZero(t.getNewValue()));
            if (!membershipIdDTO.getMembershipId().equals(savedValue)) { // if we changed the value
                if (parent.getModel().getMembershipIdRepository().rowExists(membershipIdDTO) == 0)
                    parent.getModel().getMembershipIdRepository().update(membershipIdDTO);
                else { // if it ain't right, but it back right
                    new DialogueError(true, "An entry for " + membershipIdDTO.getFiscalYear()
                            + " already exists with the membership Id of " + membershipIdDTO.getMembershipId(), 500, 100);
                    membershipIdDTO.setMembershipId(savedValue);
                }
            }
        });

        // example for this column found at
        // https://o7planning.org/en/11079/javafx-tableview-tutorial
        ObservableList<MembershipType> MembershipTypeList = FXCollections.observableArrayList(MembershipType.values());
        TableColumn<MembershipIdDTO, MembershipType> Col3 = new TableColumn<>(
                "Mem Type");
        Col3.setCellValueFactory(
                param -> {
                    MembershipIdDTO thisId = param.getValue();
                    String membershipCode = thisId.getMemType();
                    /// careful with capitals
                    MembershipType membershipType = MembershipType.getByCode(membershipCode);
                    return new SimpleObjectProperty<>(membershipType);
                });

        Col3.setCellFactory(ComboBoxTableCell.forTableColumn(MembershipTypeList));

        Col3.setOnEditCommit((CellEditEvent<MembershipIdDTO, MembershipType> t) -> {
            MembershipIdDTO membershipIdDTO = t.getTableView().getItems().get(t.getTablePosition().getRow());
            membershipIdDTO.setMemType(t.getNewValue().getCode());
            parent.getModel().getMembershipIdRepository().update(membershipIdDTO);
        });

        TableColumn<MembershipIdDTO, Boolean> Col4 = new TableColumn<>("Renewed");
        Col4.setCellValueFactory(
                param -> {
                    MembershipIdDTO membershipIdDTO = param.getValue();
                    SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(membershipIdDTO.isRenew());
                    booleanProp.addListener((observable, oldValue, newValue) -> {
                        membershipIdDTO.setIsRenew(newValue);
                        parent.getModel().getMembershipIdRepository().update(membershipIdDTO);
                    });
                    return booleanProp;
                });
        Col4.setCellFactory(p -> {
            CheckBoxTableCell<MembershipIdDTO, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<MembershipIdDTO, Boolean> Col5 = new TableColumn<>("Renew Late");
        Col5.setCellValueFactory(
                param -> {
                    MembershipIdDTO membershipIdDTO = param.getValue();
                    SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(membershipIdDTO.isLateRenew());
                    booleanProp.addListener((observable, oldValue, newValue) -> {
                        membershipIdDTO.setIsLateRenew(newValue);
                        parent.getModel().getMembershipIdRepository().update(membershipIdDTO);
                    });
                    return booleanProp;
                });
        Col5.setCellFactory(p -> {
            CheckBoxTableCell<MembershipIdDTO, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        /// sets width of columns by percentage
        Col1.setMaxWidth(1f * Integer.MAX_VALUE * 25);   // Year
        Col2.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Mem Id
        Col3.setMaxWidth(1f * Integer.MAX_VALUE * 25);   // Mem Type
        Col4.setMaxWidth(1f * Integer.MAX_VALUE * 15);   // Renewed
        Col5.setMaxWidth(1f * Integer.MAX_VALUE * 20);   // Renew Late

        /////////////////// LISTENERS //////////////////////////////

//        joinDatePicker.setOnAction((event -> {
//            LocalDate date = joinDatePicker.getValue();
//            SqlUpdate.updateMembership(membership.getMsid(), "JOIN_DATE", date);
//            membership.setJoinDate(joinDatePicker.getValue().toString());
//            labels.getJoinDate().setText(joinDatePicker.getValue().toString());
//        }));

        // This is a hack I got from here
        // https://stackoverflow.com/questions/32346893/javafx-datepicker-not-updating-value
        // Apparently datepicker was broken after java 8 and then fixed in java 18
        // this is a work-around until I upgrade this to java 18+
        joinDatePicker.setConverter(new StringConverter<>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty())
                    return null;
                try {
                    return LocalDate.parse(dateString, dateTimeFormatter);
                } catch (Exception e) {
                    BaseApplication.logger.error("Bad date value entered");
                    return null;
                }
            }
        });

        joinDatePicker.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (!isFocused) {
                try {
                    joinDatePicker.setValue(joinDatePicker.getConverter().fromString(joinDatePicker.getEditor().getText()));
                } catch (DateTimeParseException e) {
                    joinDatePicker.getEditor().setText(joinDatePicker.getConverter().toString(joinDatePicker.getValue()));
                }
//                LocalDate date = joinDatePicker.getValue();
                MembershipListDTO membershipListDTO = parent.getModel().getMembership();
                membershipListDTO.setJoinDate(joinDatePicker.getValue().toString());
                parent.getModel().getMembershipRepository().updateMembership(membershipListDTO);
                parent.getModel().getLabels().getJoinDate().setText(joinDatePicker.getValue().toString());
            }
        });

        idAdd.setOnAction((event) -> {
            //	if tuple of year=0 and memId=0 exists anywhere in SQL not belonging to this membership then delete it
            if (parent.getModel().getMembershipIdRepository().membershipIdBlankRowExists(String.valueOf(parent.getModel().getMembership().getMsId())))
                parent.getModel().getMembershipIdRepository().deleteBlankMembershipIdRow();
            // see if another year=0 and memId=0 row exists in current tableView, bring it to top and edit
            if (blankTupleExistsInTableView()) {
                parent.getModel().getMembershipIdDTOS().sort(Comparator.comparing(MembershipIdDTO::getFiscalYear));
                idTableView.edit(0, Col1);
                // create an appropriate new object to place in list
            } else {
                BaseApplication.logger.info("Added history record for membership " + parent.getModel().getMembership().getMembershipId());
                // create a blank membershipId object
                MembershipIdDTO membershipIdDTO = new MembershipIdDTO(0, "0",
                        parent.getModel().getMembership().getMsId(), "0", true, parent.getModel().getMembership().getMemType(), false, false);
                // add the information from the new object into SQL
                parent.getModel().getMembershipIdRepository().insert(membershipIdDTO);
                // add the new tuple to the appropriate history tableView
                parent.getModel().getMembershipIdDTOS().add(membershipIdDTO);
                // sort so that new membership id entry is at the top
                parent.getModel().getMembershipIdDTOS().sort(Comparator.comparing(MembershipIdDTO::getFiscalYear));
                // this line prevents strange buggy behaviour I found the solution here:
                // https://stackoverflow.com/questions/49531071/insert-row-in-javafx-tableview-and-start-editing-is-not-working-correctly
                idTableView.layout();
                // edit the year cell after creating
                idTableView.edit(0, Col1);
            }
        });

        idDelete.setOnAction((event) -> {
            int selectedIndex = idTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                MembershipIdDTO membershipIdDTO = parent.getModel().getMembershipIdDTOS().get(selectedIndex);
                Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
                conformation.setTitle("Delete History Entry");
                conformation.setHeaderText(membershipIdDTO.getFiscalYear() + " Membership " + membershipIdDTO.getMembershipId());
                conformation.setContentText("Are sure you want to delete this history entry?\n\n");
                DialogPane dialogPane = conformation.getDialogPane();
                dialogPane.getStylesheets().add("css/dark/dialogue.css");
                dialogPane.getStyleClass().add("dialog");
                Optional<ButtonType> result = conformation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (parent.getModel().getMembershipIdRepository().delete(membershipIdDTO) == 1) // if it is properly deleted in our database
                        idTableView.getItems().remove(selectedIndex); // remove it from our GUI
                }
            }
        });

        /////////////////// SET CONTENT ///////////////////////

        hboxJoinDate.getChildren().addAll(new Label("Join Date"), joinDatePicker);
        hboxButtons.getChildren().addAll(idAdd, idDelete);
        idTableView.getColumns().addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5));
        vboxPink.getChildren().add(idTableView); // adds pink border around table
        hboxControls.getChildren().addAll(hboxJoinDate, hboxButtons); // lines buttons up vertically
        vboxGrey.getChildren().addAll(hboxControls, vboxPink);
        getChildren().add(vboxGrey);

    } // end of constructor


    private boolean blankTupleExistsInTableView() {
        boolean tupleExists = false;
        for (MembershipIdDTO i : parent.getModel().getMembershipIdDTOS()) {
            if (i.getFiscalYear().equals("0") && i.getMembershipId().equals("0")) tupleExists = true;
        }
        return tupleExists;
    }

    //////////////////////// CLASS METHODS //////////////////////////
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }
}
