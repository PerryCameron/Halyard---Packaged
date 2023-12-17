package com.ecsail.views.tabs.membership.people.person;

import com.ecsail.BaseApplication;
import com.ecsail.EditCell;
import com.ecsail.enums.PhoneType;
import com.ecsail.repository.implementations.PhoneRepositoryImpl;
import com.ecsail.repository.interfaces.PhoneRepository;
import com.ecsail.dto.PersonDTO;
import com.ecsail.dto.PhoneDTO;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

public class HBoxPhone extends HBox {
    
    private final PersonDTO person;
    private final TableView<PhoneDTO> phoneTableView;
    private final ObservableList<PhoneDTO> phoneDTOS;
    private TableColumn<PhoneDTO, String> Col1;
    private PhoneRepository phoneRepository = new PhoneRepositoryImpl();
    
    public HBoxPhone(PersonDTO p) {
        this.person = p;  // the below callback is to allow commit when focus removed, overrides FX default behavior
        this.phoneDTOS = FXCollections.observableArrayList(param -> new Observable[] { param.isListedProperty() });
        this.phoneDTOS.addAll(phoneRepository.getPhoneByPid(person.getP_id()));
        this.phoneTableView = createTableView();
        VBox vboxButtons = createButtonBox(); // holds phone buttons
        HBox hboxGrey = createOuterBox(vboxButtons); // this is here for the grey background to make nice appearance
        this.setId("custom-tap-pane-frame");
        getChildren().add(hboxGrey);
    }  // end of constructor

    private HBox createOuterBox(VBox vboxButtons) {
        HBox hBox = new HBox();
        VBox vboxPink = new VBox(); // this creates a pink border around the table
        HBox.setHgrow(vboxPink, Priority.ALWAYS);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setSpacing(10);  // spacing in between table and buttons
        hBox.setId("box-background-light");
        hBox.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
        vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
        vboxPink.getChildren().add(phoneTableView);  // adds pink border around table
        hBox.getChildren().addAll(vboxPink,vboxButtons);
        return hBox;
    }

    private TableView<PhoneDTO> createTableView() {
        TableView<PhoneDTO> tableView = new TableView<>();
        VBox.setVgrow(tableView, Priority.ALWAYS);
        HBox.setHgrow(tableView, Priority.ALWAYS);
        tableView.setItems(phoneDTOS);
        tableView.setFixedCellSize(30);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );

        // example for this column found at https://gist.github.com/james-d/be5bbd6255a4640a5357#file-editcell-java-L109
        Col1 = createColumn(PhoneDTO::phoneNumberProperty);
        Col1.setOnEditCommit(
                new EventHandler<>() {
                    @Override
                    public void handle(CellEditEvent<PhoneDTO, String> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setPhoneNumber(t.getNewValue());
                        String processedNumber = processNumber(t.getNewValue());
                        int phone_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPhone_ID();
                        phoneRepository.updatePhone("phone", phone_id, processedNumber);
                        phoneDTOS.stream()
                                .filter(p -> p.getPhone_ID() == phone_id)
                                .forEach(s -> s.setPhoneNumber(processedNumber));
                    }

                    private String processNumber(String newValue) {
                        // adds dashes
                        if (Pattern.matches("\\d{10}", newValue)) {
                            return addDashes(newValue);
                        }
                        // need to add area code
                        else if (Pattern.matches("\\d{7}", newValue)) {
                            return addDashes("317" + newValue);
                        }
                        // perfect no need to change anything
                        else if (Pattern.matches("(?:\\d{3}-){2}\\d{4}", newValue)) {
                            return newValue;
                        }
                        // removes all junk as long as there are 10 numbers
                        else if (keepOnlyNumbers(newValue).length() == 10) {
                            return addDashes(keepOnlyNumbers(newValue));
                        }
                        // removes all junk and adds default area code if there are 7 numbers
                        else if (keepOnlyNumbers(newValue).length() == 7) {
                            return addDashes("317" + keepOnlyNumbers(newValue));
                        } else {
                            return "ill-formatted number";
                        }
                    }

                    private String addDashes(String newValue) {
                        StringBuilder resString = new StringBuilder(newValue);
                        return resString.insert(3, "-").insert(7, "-").toString();
                    }

                    private String keepOnlyNumbers(String newValue) {
                        return newValue.replaceAll("[^0-9]", "");
                    }
                }
        );

        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        ObservableList<PhoneType> phoneTypeList = FXCollections.observableArrayList(PhoneType.values());
        TableColumn<PhoneDTO, PhoneType> Col2 = new TableColumn<>("Type");
        Col2.setCellValueFactory(param -> {
            PhoneDTO thisPhone = param.getValue();
            String phoneCode = thisPhone.getPhoneType();
            PhoneType phoneType = PhoneType.getByCode(phoneCode);
            return new SimpleObjectProperty<>(phoneType);
        });

        Col2.setCellFactory(ComboBoxTableCell.forTableColumn(phoneTypeList));

        Col2.setOnEditCommit((CellEditEvent<PhoneDTO, PhoneType> event) -> {
            TablePosition<PhoneDTO, PhoneType> pos = event.getTablePosition();
            PhoneType newPhoneType = event.getNewValue();
            int row = pos.getRow();
            PhoneDTO thisPhone = event.getTableView().getItems().get(row);
            phoneRepository.updatePhone("phone_type", thisPhone.getPhone_ID(), newPhoneType.getCode());
            thisPhone.setPhoneType(newPhoneType.getCode());
        });

        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        TableColumn<PhoneDTO, Boolean> Col3 = new TableColumn<>("Listed");
        Col3.setCellValueFactory(param -> {
            PhoneDTO phone = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(phone.isIsListed());
            // Note: singleCol.setOnEditCommit(): Not work for
            // CheckBoxTableCell.
            // When "isListed?" column change.
            booleanProp.addListener((observable, oldValue, newValue) -> {
                phone.setIsListed(newValue);
                phoneRepository.updatePhone("phone_listed",phone.getPhone_ID(), newValue);
            });
            return booleanProp;
        });

        //
        Col3.setCellFactory(p1 -> {
            CheckBoxTableCell<PhoneDTO, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        /// sets width of columns by percentage
        Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 50);   // Phone
        Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 30 );  // Type
        Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );  // Listed
        tableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3));
        return tableView;
    }

    private VBox createButtonBox() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(80);
        vBox.setSpacing(5); // spacing between buttons
        Button phoneAdd = new Button("Add");
        Button phoneDelete = new Button("Delete");
        Button phoneCopy = new Button("Copy");
        phoneAdd.setPrefWidth(60);
        phoneDelete.setPrefWidth(60);
        phoneCopy.setPrefWidth(60);
        setAddButtonListener(phoneAdd);
        SetDeleteButtonListener(phoneDelete);
        setCopyButtonListener(phoneCopy);
        vBox.getChildren().addAll(phoneAdd, phoneDelete, phoneCopy); // lines buttons up vertically
        return vBox;
    }

    private void setCopyButtonListener(Button phoneCopy) {
        phoneCopy.setOnAction((copy) -> {
            int selectedIndex = phoneTableView.getSelectionModel().getSelectedIndex();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            if (selectedIndex >= 0) {// make sure something is selected
                PhoneDTO phoneDTO = phoneDTOS.get(selectedIndex);
                content.putString(phoneDTO.getPhoneNumber());
                clipboard.setContent(content);
            } else
                alertToSelectRow();
        });
    }

    private void alertToSelectRow() {
        Alert information = new Alert(Alert.AlertType.INFORMATION);
        information.setTitle("Row not selected");
        information.setHeaderText("Opps!");
        information.setContentText("You must select a row first!");
        DialogPane dialogPane = information.getDialogPane();
        dialogPane.getStylesheets().add("css/dark/dialogue.css");
        dialogPane.getStyleClass().add("dialog");
        Optional<ButtonType> result = information.showAndWait();
        result.isPresent();
    }

    private void SetDeleteButtonListener(Button phoneDelete) {
        phoneDelete.setOnAction((event) -> {
            int selectedIndex = phoneTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) { // make sure something is selected
                PhoneDTO ph = phoneDTOS.get(selectedIndex);
                Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
                conformation.setTitle("Delete Phone Entry");
                conformation.setHeaderText(PhoneType.getByCode(ph.getPhoneType()) + " phone");
                conformation.setContentText("Are sure you want to delete the number " + ph.getPhoneNumber());
                DialogPane dialogPane = conformation.getDialogPane();
                dialogPane.getStylesheets().add("css/dark/dialogue.css");
                dialogPane.getStyleClass().add("dialog");
                Optional<ButtonType> result = conformation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (phoneRepository.deletePhone(ph))  // if it is properly deleted in our database
                        phoneTableView.getItems().remove(selectedIndex); // remove it from our GUI
                    BaseApplication.logger.info("Deleted " + PhoneType.getByCode(ph.getPhoneType())
                            + " phone number " + ph.getPhoneNumber()
                            + " from " + person.getNameWithInfo());
                }
            } else
                alertToSelectRow();
        });
    }

    private void setAddButtonListener(Button phoneAdd) {
        phoneAdd.setOnAction((event) -> {
            BaseApplication.logger.info("Added new phone entry for " + person.getNameWithInfo());
            // attempt to add a new record and return if it is successful
            PhoneDTO phoneDTO = phoneRepository.insertPhone(new PhoneDTO(
                    0,
                    person.getP_id(),
                    true,
                    "new phone",
                    ""));
                // add a new row in the tableview
                phoneDTOS.add(phoneDTO);
            // Now we will sort it to the top
            phoneDTOS.sort(Comparator.comparing(PhoneDTO::getPhone_ID).reversed());
            // this line prevents strange buggy behaviour
            phoneTableView.layout();
            phoneTableView.requestFocus();
            phoneTableView.getSelectionModel().select(0);
            phoneTableView.getFocusModel().focus(0);
            // edit the phone number cell after creating
            phoneTableView.edit(0, Col1);
        });
    }

    ////////////////////////  CLASS METHODS //////////////////////////
    private <T> TableColumn<T, String> createColumn(Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>("Phone");
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
} 
