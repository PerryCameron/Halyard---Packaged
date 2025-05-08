package com.ecsail.views.tabs.membership.people;

import com.ecsail.StringTools;
import com.ecsail.dto.MemoDTO;
import com.ecsail.enums.MemberType;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.dto.PersonDTO;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VBoxAddPerson extends VBox implements Builder<VBox> {

    public static Logger logger = LoggerFactory.getLogger(VBoxAddPerson.class);
    private final TabMembership tabMembership;
    private final Map<String, TextField> textFieldMap;
    private final ObjectProperty<DatePicker> datePickerProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<ComboBox<MemberType>> comboBoxProperty = new SimpleObjectProperty<>();

    public VBoxAddPerson(TabMembership tabMembership) {
        this.tabMembership = tabMembership;
        this.textFieldMap = new HashMap<>();
        setPrefWidth(460);
        setPadding(new Insets(5, 5, 5, 5));
        setId("custom-tap-pane-frame");
        getChildren().add(build());
    }

    @Override
    public VBox build() {
        VBox vboxGrey = new VBox();
        vboxGrey.setId("box-background-light");
        VBox.setVgrow(vboxGrey, Priority.ALWAYS);
        vboxGrey.getChildren().add(hboxTitle());
        vboxGrey.getChildren().add(addTextField("First Name"));
        vboxGrey.getChildren().add(addTextField("Last Name"));
        vboxGrey.getChildren().add(addTextField("Occupation"));
        vboxGrey.getChildren().add(addTextField("Business"));
        vboxGrey.getChildren().add(addDatePicker());
        vboxGrey.getChildren().add(addComboBox());
        vboxGrey.getChildren().add(addButtonBox());
        setPersonRemoveListener();
        return vboxGrey;
    }

    private void setPersonRemoveListener() {
        tabMembership.getModel().getPeople().addListener((ListChangeListener<PersonDTO>) change -> {
            while (change.next()) {
                setComboBoxItems();
            }
        });
    }

//    private Node addButtonBox() {
//        HBox hBox = new HBox();
//        Button addButton = new Button("Add");
//        hBox.setAlignment(Pos.CENTER_RIGHT);
//        hBox.setSpacing(25);
//        hBox.setPadding(new Insets(5, 100, 5, 5));
//        hBox.getChildren().addAll(addButton);
//        addButton.setOnAction(event -> {
//            Task<PersonDTO> task = new Task<>() {
//                @Override
//                protected PersonDTO call() {
//                    return tabMembership.getModel().getPersonRepository().insertPerson(
//                            new PersonDTO(
//                                    tabMembership.getModel().getMembership().getMsId(),
//                                    comboBoxProperty.get().getValue().getCode(),
//                                    textFieldMap.get("First Name").getText(),
//                                    textFieldMap.get("Last Name").getText(),
//                                    StringTools.getBirthday(datePickerProperty.get().getValue()),
//                                    textFieldMap.get("Occupation").getText(),
//                                    textFieldMap.get("Business").getText(),
//                                    true
//                            )
//                    );
//                }
//            };
//            task.setOnSucceeded(e -> {
//                Platform.runLater(() -> {
//                    PersonDTO personDTO = task.getValue();
//                    tabMembership.getModel().getPeople().add(personDTO);
//                    logNewPerson(personDTO);
//                    openNewPersonTab(personDTO);
//                    clearAddMemberBox();
//                    setComboBoxItems(); // Ensure ComboBox is updated
//                });
//            });
//            task.setOnFailed(e -> {
//                Platform.runLater(() -> {
//                    new Dialogue_ErrorSQL((Exception) task.getException(), "Error adding person", "See below for details");
//                });
//            });
//            new Thread(task).start();
//        });
//        return hBox;
//    }

    private Node addButtonBox() {
        HBox hBox = new HBox();
        Button addButton = new Button("Add");
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(25);
        hBox.setPadding(new Insets(5, 100, 5, 5));
        hBox.getChildren().addAll(addButton);
        addButton.setOnAction(event -> {
            // Log DatePicker state
            logger.info("DatePicker state: text={}, value={}",
                    datePickerProperty.get().getEditor().getText(),
                    datePickerProperty.get().getValue());

            // Validate inputs
            String firstName = textFieldMap.get("First Name").getText();
            String lastName = textFieldMap.get("Last Name").getText();
            MemberType memberType = comboBoxProperty.get().getValue();
            LocalDate birthday = datePickerProperty.get().getValue();
            logger.info("Add button clicked: firstName={}, lastName={}, memberType={}, birthday={}",
                    firstName, lastName, memberType, birthday);

            // Validate required fields
            StringBuilder errorMessage = new StringBuilder();
            if (firstName.trim().isEmpty()) {
                errorMessage.append("First Name is required.\n");
            }
            if (lastName.trim().isEmpty()) {
                errorMessage.append("Last Name is required.\n");
            }
            if (memberType == null) {
                errorMessage.append("Member Type is required.\n");
            }
            if (errorMessage.length() > 0) {
                String finalMessage = errorMessage.toString();
                Platform.runLater(() -> {
                    new Dialogue_ErrorSQL(new IllegalArgumentException("Missing required fields"),
                            "Invalid Input",
                            finalMessage);
                });
                return;
            }

            // Validate birthday (optional)
            if (birthday != null && birthday.isAfter(LocalDate.now())) {
                Platform.runLater(() -> {
                    new Dialogue_ErrorSQL(new IllegalArgumentException("Future birthday"),
                            "Invalid Date",
                            "Birthday cannot be in the future.");
                });
                return;
            }

            Task<PersonDTO> task = new Task<>() {
                @Override
                protected PersonDTO call() {
                    String birthdayString = StringTools.getBirthday(birthday);
                    logger.info("Inserting person with birthday string: {}", birthdayString);
                    PersonDTO personDTO = new PersonDTO(
                            tabMembership.getModel().getMembership().getMsId(),
                            memberType.getCode(),
                            firstName,
                            lastName,
                            birthdayString,
                            textFieldMap.get("Occupation").getText(),
                            textFieldMap.get("Business").getText(),
                            true
                    );
                    logger.info("PersonDTO before insert: birthday={}", personDTO.getBirthday());
                    PersonDTO insertedDTO = tabMembership.getModel().getPersonRepository().insertPerson(personDTO);
                    logger.info("PersonDTO after insert: p_id={}, birthday={}",
                            insertedDTO.getpId(), insertedDTO.getBirthday());
                    return insertedDTO;
                }
            };
            task.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    PersonDTO personDTO = task.getValue();
                    tabMembership.getModel().getPeople().add(personDTO);
                    logNewPerson(personDTO);
                    openNewPersonTab(personDTO);
                    clearAddMemberBox();
                    setComboBoxItems();
                });
            });
            task.setOnFailed(e -> {
                Platform.runLater(() -> {
                    new Dialogue_ErrorSQL((Exception) task.getException(), "Error adding person", "See below for details");
                });
            });
            new Thread(task).start();
        });
        return hBox;
    }

    private void logNewPerson(PersonDTO personDTO) {
        String memoToSave = "New person: " + personDTO.getNameWithInfo()
                + " added as " + MemberType.getByCode(personDTO.getMemberType());
        logger.info(memoToSave);
        tabMembership.getModel().getNote().addMemoAndReturnId(new MemoDTO(personDTO.getMsId(), memoToSave, "N"));
    }

    private void openNewPersonTab(PersonDTO personDTO) {
        String newMemberType = String.valueOf(MemberType.getByCode(personDTO.getMemberType()));
        Tab tab = new Tab(newMemberType, new HBoxPerson(personDTO, tabMembership));
        tabMembership.getModel().getPeopleTabPane().getTabs().add(tab);
        tabMembership.getModel().getPeopleTabPane().getSelectionModel().select(tab);
        // Remove this line: tabMembership.getModel().getPeople().add(personDTO);
    }

    private void clearAddMemberBox() {
        textFieldMap.get("First Name").setText("");
        textFieldMap.get("Last Name").setText("");
        textFieldMap.get("Occupation").setText("");
        textFieldMap.get("Business").setText("");
        datePickerProperty.get().setValue(null);
    }

    private Node addComboBox() {
        HBox hBox = new HBox(); // first name
        hBox.setPadding(new Insets(5, 15, 10, 60));  // first Name
        hBox.getChildren().addAll(addLabel("Type"), createComboBox());
        return hBox;
    }

    private Node addTextField(String label) {
        HBox hBox = new HBox(); // first name
        hBox.setPadding(new Insets(5, 15, 10, 60));  // first Name
        VBox vBox = new VBox();
        TextField textField = new TextField();
        textField.setPrefSize(240, 10);
        textFieldMap.put(label, textField);
        vBox.getChildren().add(textField);
        hBox.getChildren().addAll(addLabel(label), vBox);
        return hBox;
    }

//    private Node addDatePicker() {
//        HBox hBox = new HBox(); // first name
//        VBox vBox = new VBox();
//        hBox.setPadding(new Insets(5, 15, 10, 60));  // first Name
//        DatePicker datePicker = new DatePicker();
//        datePicker.setPrefSize(240, 10);
//        datePicker.setPromptText("MM/DD/YYYY");
//        vBox.getChildren().add(datePicker);
//        datePickerProperty.set(datePicker);
//        hBox.getChildren().addAll(addLabel("Birthday"), vBox);
//        return hBox;
//    }
//private Node addDatePicker() {
//    HBox hBox = new HBox();
//    VBox vBox = new VBox();
//    hBox.setPadding(new Insets(5, 15, 10, 60));
//    DatePicker datePicker = new DatePicker();
//    datePicker.setPrefSize(240, 10);
//    datePicker.setPromptText("MM/DD/YYYY");
//
//    // Configure DateTimeFormatter for flexible parsing
//    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
//            .appendPattern("[M/d/yyyy][MM/dd/yyyy]") // Accept 1/1/2009 or 01/01/2009
//            .parseLenient() // Allow lenient parsing
//            .toFormatter(Locale.US); // Use US locale for consistency
//    datePicker.setConverter(new StringConverter<LocalDate>() {
//        @Override
//        public String toString(LocalDate date) {
//            return date != null ? formatter.format(date) : "";
//        }
//
//        @Override
//        public LocalDate fromString(String string) {
//            if (string == null || string.trim().isEmpty()) {
//                return null;
//            }
//            try {
//                return LocalDate.parse(string, formatter);
//            } catch (DateTimeParseException e) {
//                logger.warn("Invalid date format: {}", string);
//                return null; // Return null for invalid input
//            }
//        }
//    });
//
//    vBox.getChildren().add(datePicker);
//    datePickerProperty.set(datePicker);
//    hBox.getChildren().addAll(addLabel("Birthday"), vBox);
//    return hBox;
//}

    private Node addDatePicker() {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        hBox.setPadding(new Insets(5, 15, 10, 60));
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(240, 10);
        datePicker.setPromptText("MM/DD/YYYY");
        datePicker.setTooltip(new Tooltip("Enter date as MM/DD/YYYY, e.g., 01/01/2005"));

        // Configure formatters
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
        DateTimeFormatter parsingFormatter = new DateTimeFormatterBuilder()
                .appendPattern("[M/d/yyyy][MM/dd/yyyy][M-d-yyyy][MM-dd-yyyy]")
                .parseLenient()
                .toFormatter(Locale.US);
        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                String formatted = date != null ? displayFormatter.format(date) : "";
                logger.info("DatePicker toString: LocalDate={} -> String={}", date, formatted);
                return formatted;
            }

            @Override
            public LocalDate fromString(String string) {
                logger.info("DatePicker fromString: Input={}", string);
                if (string == null || string.trim().isEmpty()) {
                    logger.info("DatePicker fromString: Returning null (empty input)");
                    return null;
                }
                try {
                    LocalDate date = LocalDate.parse(string, parsingFormatter);
                    logger.info("DatePicker fromString: Parsed LocalDate={}", date);
                    return date;
                } catch (DateTimeParseException e) {
                    logger.warn("DatePicker fromString: Invalid date format: {} - {}", string, e.getMessage());
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);

        // Real-time validation for styling
        datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            logger.info("DatePicker editor text changed: oldValue={} -> newValue={}", oldValue, newValue);
            if (!newValue.isEmpty()) {
                try {
                    LocalDate.parse(newValue, parsingFormatter);
                    datePicker.getEditor().setStyle("");
                } catch (DateTimeParseException e) {
                    datePicker.getEditor().setStyle("-fx-border-color: red;");
                }
            } else {
                datePicker.getEditor().setStyle("");
            }
        });

        // Workaround for JavaFX DatePicker bug (JDK-8092295)
        datePicker.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (!isFocused) {
                logger.info("DatePicker lost focus: text={}", datePicker.getEditor().getText());
                try {
                    LocalDate date = converter.fromString(datePicker.getEditor().getText());
                    datePicker.setValue(date);
                    logger.info("DatePicker focus lost: Set value={}", date);
                } catch (DateTimeParseException e) {
                    logger.warn("DatePicker focus lost: Invalid date format: {} - {}",
                            datePicker.getEditor().getText(), e.getMessage());
                    datePicker.getEditor().setText(converter.toString(datePicker.getValue()));
                }
            }
        });

        // Commit value on Enter
        datePicker.setOnAction(event -> {
            String text = datePicker.getEditor().getText();
            logger.info("DatePicker onAction: Text={}", text);
            if (!text.isEmpty()) {
                try {
                    LocalDate date = converter.fromString(text);
                    datePicker.setValue(date);
                    logger.info("DatePicker onAction: Set value={}", date);
                } catch (DateTimeParseException e) {
                    logger.warn("DatePicker onAction: Invalid date format: {} - {}", text, e.getMessage());
                }
            }
        });

        // Log value changes
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            logger.info("DatePicker value changed: oldValue={} -> newValue={}", oldValue, newValue);
        });

        vBox.getChildren().add(datePicker);
        datePickerProperty.set(datePicker);
        hBox.getChildren().addAll(addLabel("Birthday"), vBox);
        return hBox;
    }

    private Node createComboBox() {
        VBox vBox = new VBox();
        ComboBox<MemberType> comboBox = new ComboBox<>();
        comboBoxProperty.set(comboBox);
        setComboBoxItems();
        vBox.getChildren().add(comboBox);
        return vBox;
    }

    private void setComboBoxItems() {
        if (comboBoxProperty.get() == null) {
            logger.warn("ComboBox is null");
            return;
        }
        comboBoxProperty.get().getItems().clear();
        List<MemberType> filteredItems = Arrays.stream(MemberType.values())
                .filter(memberType -> {
                    // Always include DEPENDENT; exclude PRIMARY and SECONDARY if they exist
                    return memberType == MemberType.DEPENDANT || !memberTypeExists(memberType.getCode());
                })
                .collect(Collectors.toList());
        comboBoxProperty.get().getItems().setAll(filteredItems);
        // Set default to DEPENDENT if available, otherwise first item
        MemberType defaultValue = MemberType.getByCode(3); // DEPENDENT
        if (filteredItems.contains(defaultValue)) {
            comboBoxProperty.get().setValue(defaultValue);
        } else if (!filteredItems.isEmpty()) {
            comboBoxProperty.get().setValue(filteredItems.get(0));
        }
    }


    private boolean memberTypeExists(int type) {
        return tabMembership.getModel().getPeople().stream()
                .anyMatch(p -> p.getMemberType() == type);
    }

    private Node addLabel(String text) {
        VBox vBox = new VBox();
        Label label = new Label(text);
        vBox.setPrefWidth(80);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(label);
        return vBox;
    }

    private Node hboxTitle() {
        HBox hBox = new HBox(); // Title
        Label titleLabel = new Label("Add New Member");
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(13);
        hBox.setPadding(new Insets(20, 15, 20, 15));  // first Name
        hBox.getChildren().addAll(titleLabel);
        return hBox;
    }
}
