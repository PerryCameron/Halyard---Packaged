package com.ecsail.views.tabs.membership.people.person;


import com.ecsail.BaseApplication;
import com.ecsail.EditCell;
import com.ecsail.connection.Mail;
import com.ecsail.repository.implementations.EmailRepositoryImpl;
import com.ecsail.repository.interfaces.EmailRepository;
import com.ecsail.dto.EmailDTO;
import com.ecsail.dto.PersonDTO;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class HBoxEmail extends HBox {

    private final PersonDTO person;
    private final ObservableList<EmailDTO> emailDTOS;
    private final TableView<EmailDTO> emailTableView;
    private TableColumn<EmailDTO, String> Col1;
    private EmailRepository emailRepository = new EmailRepositoryImpl();

    public HBoxEmail(PersonDTO p) {
        this.person = p;
        this.emailDTOS = FXCollections.observableArrayList(param -> new Observable[]{param.isPrimaryUseProperty()});
        this.emailDTOS.addAll(emailRepository.getEmail(person.getpId()));
        this.emailTableView = createTableView();
        VBox vboxButtons = makeButtonBox();
        var hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
        var vboxPink = new VBox(); // this creates a pink border around the table

        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
        HBox.setHgrow(vboxPink, Priority.ALWAYS);
        HBox.setHgrow(emailTableView, Priority.ALWAYS);
        VBox.setVgrow(emailTableView, Priority.ALWAYS);

        hboxGrey.setSpacing(10);  // spacing in between table and buttons
        vboxButtons.setSpacing(5);

        hboxGrey.setId("box-background-light");
        vboxPink.setId("box-pink");
        this.setId("box-background-light");

        hboxGrey.setPadding(new Insets(5, 5, 5, 5));  // spacing around table and buttons
        vboxPink.setPadding(new Insets(2, 2, 2, 2)); // spacing to make pink frame around table

        vboxPink.getChildren().add(emailTableView);
        hboxGrey.getChildren().addAll(vboxPink, vboxButtons);
        getChildren().add(hboxGrey);

    } // CONSTRUCTOR END

    private TableView<EmailDTO> createTableView() {
        TableView<EmailDTO> tableView = new TableView<>();
        tableView.setItems(emailDTOS);
        tableView.setFixedCellSize(30);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Col1 = createColumn(EmailDTO::emailProperty);
        Col1.setPrefWidth(137);
        Col1.setOnEditCommit(t -> {
            int email_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getEmail_id();
            if(isValidEmail(t.getNewValue())) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(t.getNewValue());
                emailRepository.updateEmail(email_id, t.getNewValue());
            } else {
                emailDTOS.stream()
                        .filter(q -> q.getEmail_id() == email_id)
                        .forEach(s -> s.setEmail("Bad Email"));
            }
        });

        TableColumn<EmailDTO, Boolean> Col2 = new TableColumn<>("Primary");
        Col2.setStyle( "-fx-alignment: CENTER;");
        Col2.setCellValueFactory(new PropertyValueFactory<>("isPrimaryUse"));
        ObjectProperty<EmailDTO> previousEmailDTO = new SimpleObjectProperty<>();
        previousEmailDTO.set(getOriginalEmailDTO());
        Col2.setCellFactory(c -> new RadioButtonCell(previousEmailDTO));


        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        TableColumn<EmailDTO, Boolean> Col3 = new TableColumn<>("Listed");
        Col3.setCellValueFactory(param -> {
            EmailDTO email = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(email.isIsListed());
            // Note: singleCol.setOnEditCommit(): Not work for
            // CheckBoxTableCell.
            // When "Listed?" column change.
            booleanProp.addListener((observable, oldValue, newValue) -> {
                email.setIsListed(newValue);
                emailRepository.updateEmail("email_listed", email.getEmail_id(), newValue);
            });
            return booleanProp;
        });

        Col3.setCellFactory(p12 -> {
            CheckBoxTableCell<EmailDTO, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        /// sets width of columns by percentage
        Col1.setMaxWidth(1f * Integer.MAX_VALUE * 50);   // Phone
        Col2.setMaxWidth(1f * Integer.MAX_VALUE * 25);  // Type
        Col3.setMaxWidth(1f * Integer.MAX_VALUE * 25);  // Listed
        tableView.getColumns().addAll(Arrays.asList(Col1, Col2, Col3));
        return tableView;
    }

    private VBox makeButtonBox() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(80);
        Button emailAdd = new Button("Add");
        Button emailDelete = new Button("Delete");
        Button emailCopy = new Button("Copy");
        Button emailEmail = new Button("Email");
        emailAdd.setPrefWidth(60);
        emailDelete.setPrefWidth(60);
        emailCopy.setPrefWidth(60);
        emailEmail.setPrefWidth(60);
        setAddButtonListener(emailAdd);
        setDeleteButtonListener(emailDelete);
        setCopyButtonListener(emailCopy);
        setEmailButtonListener(emailEmail);
        vBox.getChildren().addAll(emailAdd, emailDelete, emailCopy, emailEmail);
        return vBox;
    }

    private void setEmailButtonListener(Button emailEmail) {
        emailEmail.setOnAction((event) -> {
            int selectedIndex = emailTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {// make sure something is selected
                EmailDTO emailDTO = emailDTOS.get(selectedIndex);
                Mail.composeEmail(emailDTO.getEmail(), "ECSC", "");
            } else
                alertToSelectRow();
        });
    }

    private void setCopyButtonListener(Button emailCopy) {
        emailCopy.setOnAction((copy) -> {
            int selectedIndex = emailTableView.getSelectionModel().getSelectedIndex();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            if (selectedIndex >= 0) {// make sure something is selected
                EmailDTO emailDTO = emailDTOS.get(selectedIndex);
                content.putString(emailDTO.getEmail());
                clipboard.setContent(content);
            } else
                alertToSelectRow();
        });
    }

    private void setDeleteButtonListener(Button emailDelete) {
        emailDelete.setOnAction((event) -> {
            int selectedIndex = emailTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {// make sure something is selected
                EmailDTO emailDTO = emailDTOS.get(selectedIndex);
                Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
                conformation.setTitle("Delete Email Entry");
                conformation.setHeaderText(emailDTO.getEmail());
                conformation.setContentText("Are sure you want to delete this email entry?");
                DialogPane dialogPane = conformation.getDialogPane();
                dialogPane.getStylesheets().add("css/dark/dialogue.css");
                dialogPane.getStyleClass().add("dialog");
                Optional<ButtonType> result = conformation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (emailRepository.deleteEmail(emailDTO)) {  // if deleted in database
                        emailTableView.getItems().remove(selectedIndex); // remove from GUI
                        BaseApplication.logger.info("Deleted "
                                + emailDTO.getEmail() + " from "
                                + person.getNameWithInfo());
                    }
                }
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

    private void setAddButtonListener(Button emailAdd) {
        emailAdd.setOnAction((event) -> {
            BaseApplication.logger.info("Added new email entry for {}", person.getNameWithInfo());
            boolean existsPrimary = emailDTOS.stream()
                    .anyMatch(emailDTO -> emailDTO.isPrimaryUseProperty().get());
            BaseApplication.logger.info("settting existsPrimary: " + existsPrimary);
            EmailDTO emailDTO = emailRepository.insertEmail(new EmailDTO(
                    0,
                    person.getpId(),
                    !existsPrimary,
                    "new email",
                    true));
            emailDTOS.add(emailDTO);
            // Now we will sort it to the top
            emailDTOS.sort(Comparator.comparing(EmailDTO::getEmail_id).reversed());
            // this line prevents strange buggy behaviour
            emailTableView.layout();
            // edit the phone number cell after creating
            emailTableView.requestFocus();
            emailTableView.getSelectionModel().select(0);
            emailTableView.getFocusModel().focus(0);
            emailTableView.edit(0, Col1);
        });
    }

    private EmailDTO getOriginalEmailDTO() {
        return emailDTOS.stream().filter(EmailDTO::isPrimaryUse).findFirst().orElse(null);
    }

    ///////////////// CLASS METHODS /////////////////

    private <T> TableColumn<T, String> createColumn(Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>("Email");
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }

    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}


