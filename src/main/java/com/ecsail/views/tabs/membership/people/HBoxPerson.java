package com.ecsail.views.tabs.membership.people;

import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.Launcher;
import com.ecsail.dto.PersonDTO;
import com.ecsail.repository.implementations.PersonRepositoryImpl;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.views.tabs.membership.people.person.*;
import com.ecsail.views.tabs.people.TabPeople;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

// TODO need to add ability to switch primary and secondary

public class HBoxPerson extends HBox {
    private final PersonDTO personDTO;
    public TabMembership parent;
    private final ObservableList<PersonDTO> people;  // this is only for updating people list when in people list mode
    TabPersonProperties propertiesTab; // this is here for a getter, so I can get to combobox
    private final PersonRepository personRepository = new PersonRepositoryImpl();


    public HBoxPerson(PersonDTO p, TabMembership parent) {
        this.parent = parent;
        this.personDTO = p;


        if (Launcher.tabOpen("People List")) {
            this.people = TabPeople.people;
        } else {
            this.people = null;
        }

        this.propertiesTab = new TabPersonProperties(p, this);
        ImageView photo = getMemberPhoto();
        ///////////// OBJECTS /////////////////

        VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        VBox vboxInfoGrey = new VBox();
        HBox hboxTitle = new HBox();
        VBox vboxMemberInfo = new VBox();
        VBox vboxPicture = new VBox();
        HBox hboxMemberInfoAndPicture = new HBox();
        HBox hboxPictureFrame = new HBox();

        HBox hboxFirstName = new HBox(); // first name
        HBox hboxLastName = new HBox(); // last name
        HBox hboxNickName = new HBox(); // nickname
        HBox hboxOccupation = new HBox(); // Occupation
        HBox hboxBusiness = new HBox(); // Business
        HBox hboxBirthday = new HBox(); // Birthday

        VBox vbLnameLabel = new VBox();
        VBox vbFnameLabel = new VBox();
        VBox vbNnameLabel = new VBox();
        VBox vbOccupationLabel = new VBox();
        VBox vbBusinessLabel = new VBox();
        VBox vbBirthdayLabel = new VBox();
        VBox vbLnameBox = new VBox();
        VBox vbFnameBox = new VBox();
        VBox vbNnameBox = new VBox();
        VBox vbOccupationBox = new VBox();
        VBox vbBusinessBox = new VBox();
        VBox vbBirthdayBox = new VBox();

        HBox hboxPhone = new HBoxPhone(personDTO); // Phone
        HBox hboxEmail = new HBoxEmail(personDTO); // Email
        HBox hboxOfficer = new HBoxOfficer(personDTO); // Officer
        HBox hboxAward = new HBoxAward(personDTO, parent);

        Label fnameLabel = new Label("First Name");
        Label lnameLabel = new Label("Last Name");
        Label nnameLabel = new Label("Nickname");
        Label occupationLabel = new Label("Occupation");
        Label businessLabel = new Label("Business");
        Label birthdayLabel = new Label("Birthday");
        TextField fnameTextField = new TextField();
        TextField lnameTextField = new TextField();
        TextField nnameTextField = new TextField();
        TextField businessTextField = new TextField();
        TextField occupationTextField = new TextField();
        DatePicker birthdayDatePicker = new DatePicker();
        TabPane infoTabPane = new TabPane();

        ///////////////  ATTRIBUTES ////////////////

        infoTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        fnameTextField.setPrefSize(150, 10);
        lnameTextField.setPrefSize(150, 10);
        businessTextField.setPrefSize(150, 10);
        occupationTextField.setPrefSize(150, 10);
        nnameTextField.setPrefSize(150, 10);
        birthdayDatePicker.setPrefSize(150, 10);

        vbLnameLabel.setPrefWidth(75);
        vbFnameLabel.setPrefWidth(75);
        vbNnameLabel.setPrefWidth(75);
        vbOccupationLabel.setPrefWidth(75);
        vbBusinessLabel.setPrefWidth(75);
        vbBirthdayLabel.setPrefWidth(75);
        hboxPictureFrame.setPrefSize(196, 226);

        hboxFirstName.setAlignment(Pos.CENTER_LEFT);
        hboxLastName.setAlignment(Pos.CENTER_LEFT);
        hboxNickName.setAlignment(Pos.CENTER_LEFT);
        hboxOccupation.setAlignment(Pos.CENTER_LEFT);
        hboxBusiness.setAlignment(Pos.CENTER_LEFT);
        hboxTitle.setAlignment(Pos.TOP_RIGHT);
        hboxPhone.setAlignment(Pos.CENTER);
        hboxEmail.setAlignment(Pos.CENTER);
        hboxOfficer.setAlignment(Pos.CENTER);
        hboxAward.setAlignment(Pos.CENTER);
        vbLnameLabel.setAlignment(Pos.CENTER_LEFT);
        vbFnameLabel.setAlignment(Pos.CENTER_LEFT);
        vbNnameLabel.setAlignment(Pos.CENTER_LEFT);
        vbOccupationLabel.setAlignment(Pos.CENTER_LEFT);
        vbBusinessLabel.setAlignment(Pos.CENTER_LEFT);
        vbBirthdayLabel.setAlignment(Pos.CENTER_LEFT);


        hboxTitle.setPadding(new Insets(9, 5, 0, 0));
        hboxFirstName.setPadding(new Insets(7, 5, 5, 15));  // first Name
        hboxLastName.setPadding(new Insets(7, 5, 5, 15));  // last name
        hboxNickName.setPadding(new Insets(7, 5, 5, 15)); // Nickname
        hboxOccupation.setPadding(new Insets(7, 5, 5, 15));  // occupation
        hboxBusiness.setPadding(new Insets(7, 5, 5, 15));  // business
        hboxBirthday.setPadding(new Insets(7, 5, 5, 15));
        hboxPhone.setPadding(new Insets(5, 5, 5, 5));
        hboxEmail.setPadding(new Insets(5, 5, 5, 5));
        hboxOfficer.setPadding(new Insets(5, 5, 5, 5));
        hboxAward.setPadding(new Insets(5, 5, 5, 5));
        hboxPictureFrame.setPadding(new Insets(2, 2, 2, 2));
        vboxPicture.setPadding(new Insets(12, 5, 0, 7));
        vboxInfoGrey.setPadding(new Insets(10, 5, 5, 5)); // creates space for inner tabpane
        this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame

        VBox.setVgrow(vboxInfoGrey, Priority.ALWAYS);
        VBox.setVgrow(infoTabPane, Priority.ALWAYS);
        HBox.setHgrow(vboxGrey, Priority.ALWAYS);

        hboxPhone.setSpacing(5);
        hboxEmail.setSpacing(5);
        hboxOfficer.setSpacing(5);
        hboxAward.setSpacing(5);

        hboxPhone.setId("custom-tap-pane-frame");
        hboxEmail.setId("custom-tap-pane-frame");
        hboxOfficer.setId("custom-tap-pane-frame");
        hboxAward.setId("custom-tap-pane-frame");
        hboxPictureFrame.setId("box-frame-dark");
        this.setId("custom-tap-pane-frame");
        vboxGrey.setId("box-background-light");



        /////////////// LISTENERS //////////////////////////


        fnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (oldValue) {  // we have focused and unfocused
                personDTO.setFirstName(fnameTextField.getText());
                personRepository.updatePerson(personDTO);
            }
        });

        lnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (oldValue) {  // we have focused and unfocused
                personDTO.setLastName(lnameTextField.getText());
                personRepository.updatePerson(personDTO);
            }
        });

        occupationTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (oldValue) {  // we have focused and unfocused
                personDTO.setOccupation(occupationTextField.getText());
                personRepository.updatePerson(personDTO);
            }
        });

        businessTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (oldValue) {  // we have focused and unfocused
                personDTO.setBusiness(businessTextField.getText());
                personRepository.updatePerson(personDTO);
            }
        });

        nnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (oldValue) {  // we have focused and unfocused
                personDTO.setNickName(nnameTextField.getText());
                personRepository.updatePerson(personDTO);
            }
        });
        

        photo.setOnMouseExited(ex -> hboxPictureFrame.setStyle("-fx-background-color: #010e11;"));

        photo.setOnMouseEntered(en -> hboxPictureFrame.setStyle("-fx-background-color: #201ac9;"));


        if (personDTO.getBirthday() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(personDTO.getBirthday(), formatter);
            birthdayDatePicker.setValue(date);
        }
        // This is a hack I got from here
        // https://stackoverflow.com/questions/32346893/javafx-datepicker-not-updating-value
        // Apparently datepicker was broken after java 8 and then fixed in java 18
        // this is a work-around until I upgrade this to java 18+
        birthdayDatePicker.setConverter(new StringConverter<>() {
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
        //This deals with the bug located here where the datepicker value is not updated on focus lost
        //https://bugs.openjdk.java.net/browse/JDK-8092295?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel
        birthdayDatePicker.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (!isFocused){
                try {
                    birthdayDatePicker.setValue(birthdayDatePicker.getConverter().fromString(birthdayDatePicker.getEditor().getText()));
                } catch (DateTimeParseException e) {
                    birthdayDatePicker.getEditor().setText(birthdayDatePicker.getConverter().toString(birthdayDatePicker.getValue()));
                }
                LocalDate localDate = birthdayDatePicker.getValue();
                personDTO.setBirthday(localDate.toString());
                personRepository.updatePerson(personDTO);
            }
        });

        /////////////// SETTING CONTENT /////////////////////

        vboxMemberInfo.getChildren().addAll(hboxTitle, hboxFirstName, hboxLastName, hboxNickName, hboxOccupation, hboxBusiness, hboxBirthday);
        hboxMemberInfoAndPicture.getChildren().addAll(vboxMemberInfo, vboxPicture);
        hboxPictureFrame.getChildren().add(photo);
        vboxPicture.getChildren().add(hboxPictureFrame);
        fnameTextField.setText(personDTO.getFirstName());
        lnameTextField.setText(personDTO.getLastName());
        businessTextField.setText(personDTO.getBusiness());
        occupationTextField.setText(personDTO.getOccupation());
        nnameTextField.setText(personDTO.getNickName());
        infoTabPane.getTabs().add(propertiesTab);
        infoTabPane.getTabs().add(new Tab("Phone", hboxPhone));
        infoTabPane.getTabs().add(new Tab("Email", hboxEmail));
        infoTabPane.getTabs().add(new Tab("Officer", hboxOfficer));
        infoTabPane.getTabs().add(new Tab("Awards", hboxAward));
        vboxInfoGrey.getChildren().add(infoTabPane);
        vbLnameLabel.getChildren().add(lnameLabel);
        vbFnameLabel.getChildren().add(fnameLabel);
        vbNnameLabel.getChildren().add(nnameLabel);
        vbOccupationLabel.getChildren().add(occupationLabel);
        vbBusinessLabel.getChildren().add(businessLabel);
        vbBirthdayLabel.getChildren().add(birthdayLabel);
        vbLnameBox.getChildren().add(lnameTextField);
        vbFnameBox.getChildren().add(fnameTextField);
        vbNnameBox.getChildren().add(nnameTextField);
        vbOccupationBox.getChildren().add(occupationTextField);
        vbBusinessBox.getChildren().add(businessTextField);
        vbBirthdayBox.getChildren().add(birthdayDatePicker);
        hboxFirstName.getChildren().addAll(vbFnameLabel, vbFnameBox);
        hboxLastName.getChildren().addAll(vbLnameLabel, vbLnameBox);
        hboxNickName.getChildren().addAll(vbNnameLabel, vbNnameBox);
        hboxOccupation.getChildren().addAll(vbOccupationLabel, vbOccupationBox);
        hboxBusiness.getChildren().addAll(vbBusinessLabel, vbBusinessBox);
        hboxBirthday.getChildren().addAll(vbBirthdayLabel, vbBirthdayBox);
        vboxGrey.getChildren().addAll(hboxMemberInfoAndPicture, vboxInfoGrey);
        this.getChildren().add(vboxGrey);
    } // constructor end


    private ImageView getMemberPhoto() {
        Image memberPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(HalyardPaths.DEFAULTPHOTO)));
        return new ImageView(memberPhoto);
    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public TabPersonProperties getPropertiesTab() {
        return propertiesTab;
    }
}  // class end
