package com.ecsail.views.tabs.membership.people.person;


import com.ecsail.BaseApplication;
import com.ecsail.dto.PersonDTO;
import com.ecsail.enums.MemberType;
import com.ecsail.models.MembershipTabModel;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.views.tabs.membership.people.HBoxPerson;
import com.ecsail.widgetfx.AlertFX;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

// This class is for the options in the properties box inside the person tab
public class VBoxPersonMove extends VBox {
    private final MembershipRepositoryImpl membershipRepository;
    private PersonDTO person;
    private final ComboBox<String> comboBox = new ComboBox<>();
    private final PersonRepository personRepository;
    private final MembershipTabModel model;

    public VBoxPersonMove(PersonDTO person, HBoxPerson parent) {
        this.person = person;
        this.membershipRepository = new MembershipRepositoryImpl();
        this.model = parent.getTabMembership().getModel();
        this.personRepository = model.getPersonRepository();
        // this is to allow a person to be removed from membership only called from TabMembership.class
        boolean calledFromMembershipTab = false;
        try {
            if (Class.forName(Thread.currentThread().getStackTrace()[4].getClassName()).equals(TabMembership.class)) {
                calledFromMembershipTab = true;
            }
        } catch (ClassNotFoundException e) {
            BaseApplication.logger.error("Unable to determine calling class: {}", e.getMessage());
        }

        ToggleGroup tg = new ToggleGroup();
        RadioButton rb0 = new RadioButton("Change " + person.getFirstName() + "'s member type");
        RadioButton rb1 = new RadioButton("Remove " + person.getFirstName() + " from this membership");
        RadioButton rb2 = new RadioButton("Delete " + person.getFirstName() + " from database ");
        RadioButton rb3 = new RadioButton("Move " + person.getFirstName() + " to membership (MSID)");
        Button submit = new Button("Submit");
        TextField msidTextField = new TextField();

        HBox hBox0 = new HBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hbox4 = new HBox();
        VBox vBoxFields = new VBox();

        ///// ATTRIBUTES /////
        msidTextField.setPrefWidth(60);
        rb0.setToggleGroup(tg);
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb3.setToggleGroup(tg);
        this.setSpacing(5);
        this.setPadding(new Insets(5, 5, 5, 5));
        hbox4.setPadding(new Insets(5, 0, 0, 0));
        hbox4.setSpacing(40);
        vBoxFields.setPrefWidth(120);
        msidTextField.setPrefWidth(60);

        //// LISTENERS //////

        rb3.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if (isNowSelected)
                vBoxFields.getChildren().add(msidTextField);
            else
                vBoxFields.getChildren().remove(msidTextField);
        });

        rb0.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if (isNowSelected)
                vBoxFields.getChildren().add(comboBox);
            else
                vBoxFields.getChildren().remove(comboBox);
        });

        submit.setOnAction((event) -> {
            if (rb0.isSelected()) {
                changeMembershipType(comboBox.getValue());
            }

            if (rb1.isSelected()) {
                Optional<ButtonType> result = createConformation(
                        "Remove person",
                        "Remove person from membership",
                        "Are you sure you want to remove " + person.getFullName() + " from this membership?\n\n" +
                                "It will leave this person's record free floating in the database, however "
                                + person.getNameWithInfo() + " can be looked back up in the People tab and reattached " +
                                "to this or another membership."
                );
                if (result.isPresent() && result.get() == ButtonType.OK) {
                        replacePrimaryOrSignalError(false);
                }
            }

            if (rb2.isSelected()) {
                Optional<ButtonType> result = createConformation(
                        "Delete person from database",
                        "This will permanently delete " + person.getFullName()
                                + " from the database",
                        "Are you sure you want to delete " + person.getFullName() + " from this database?");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    replacePrimaryOrSignalError(true);
                }
            }

            if (rb3.isSelected()) {
                Optional<ButtonType> result = createConformation(
                        "Move person to another membership",
                        "This will move " + person.getFullName()
                                + " to membership with the MSID of " + msidTextField.getText(),
                        "Are you sure you want to move " + person.getFullName() + " ?");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    movePersonByMSidOrSignalError(msidTextField.getText());
                }
            }
        });

        setComboBoxValues(person.getMemberType());
        hBox0.getChildren().add(rb0);
        hBox1.getChildren().add(rb1);
        hBox2.getChildren().add(rb2);
        hBox3.getChildren().add(rb3);
        hbox4.getChildren().addAll(vBoxFields, submit);

        if (calledFromMembershipTab)
            this.getChildren().addAll(hBox0, hBox1, hBox2, hBox3, hbox4);
        else // this is from people list view
            this.getChildren().addAll(hBox2, hBox3, hbox4);
    }

    private void removeThisTab(TabPane personTabPane) {
        personTabPane.getTabs().remove(personTabPane.getSelectionModel().getSelectedItem());
    }

    private Optional<ButtonType> createConformation(String title, String header, String content) {
        Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
        AlertFX.tieAlertToStage(conformation, 600, 200);
        conformation.setTitle(title);
        conformation.setHeaderText(header);
        conformation.setContentText(content);
        DialogPane dialogPane = conformation.getDialogPane();
        dialogPane.getStylesheets().add("css/dark/dialogue.css");
        dialogPane.getStyleClass().add("dialog");
        return conformation.showAndWait();
    }

    private void createInformation(String header, String content) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        AlertFX.tieAlertToStage(error, 400, 400);
        error.setTitle("Error");
        error.setHeaderText(header);
        error.setContentText(content);
        DialogPane dialogPane = error.getDialogPane();
        dialogPane.getStylesheets().add("css/dark/dialogue.css");
        dialogPane.getStyleClass().add("dialog");
        error.showAndWait();
    }

    private void changeMembershipType(String memberTypeToChangeTo) {
        // TODO strange bug in here if you go back and fourth too many times, probably doesn't fit any use case though
        if (memberTypeToChangeTo.equals("Primary")) {
            changeToPrimary(model.getPeopleTabPane());
        } else if (memberTypeToChangeTo.equals("Secondary")) {
            changeToSecondary(model.getPeopleTabPane());
        } else {
            // TODO this is not done
            System.out.println("Moving you to a dependent");
        }
    }

    private void changeToSecondary(TabPane personTabPane) {
        if (personRepository.memberTypeExists(MemberType.SECONDARY.getCode(), person.getMsId())) {
            Tab secondaryTab = getTab("Secondary");
            assert secondaryTab != null;
            PersonDTO secondary = getPerson(secondaryTab);
            secondary.setMemberType(person.getMemberType());
            // change the existing secondary to new value, probably primary
            personRepository.updatePerson(secondary);
            // sets combobox values for the other member being switched
            setOtherComboBoxValues(secondaryTab, 1);
            secondaryTab.setText("Primary");
        }
        // update person to secondary
        person.setMemberType(MemberType.SECONDARY.getCode());
        personRepository.updatePerson(person);
        // create a variable to use
        Tab thisTab = personTabPane.getSelectionModel().getSelectedItem();
        thisTab.setText("Secondary");
        // remove and add the tab in the correct place
        personTabPane.getTabs().remove(thisTab);
        personTabPane.getTabs().add(1, thisTab);
        // select the tab in its new location
        personTabPane.getSelectionModel().select(1);
        // set the combo box to hold correct values now that we have changed member type
        setComboBoxValues(2);
    }

    private void changeToPrimary(TabPane personTabPane) {
        // check if there is already a primary for persons msid
        if (personRepository.memberTypeExists(MemberType.PRIMARY.getCode(), person.getMsId())) {
            Tab primaryTab = getTab("Primary");
            assert primaryTab != null;
            PersonDTO primary = getPerson(primaryTab);
            // change the existing primary to new value, probably secondary
            primary.setMemberType(person.getMemberType());
            personRepository.updatePerson(primary);
            // sets combobox values for the other member being switched removing option (a primary shouldn't have a primary listing)
            setOtherComboBoxValues(primaryTab, 2);
            primaryTab.setText("Secondary");
        }
        // update person to primary
        person.setMemberType(MemberType.PRIMARY.getCode());
        personRepository.updatePerson(person);
        // create a variable to use
        Tab thisTab = personTabPane.getSelectionModel().getSelectedItem();
        thisTab.setText("Primary");
        // remove and add the tab in the correct place
        personTabPane.getTabs().remove(thisTab);
        personTabPane.getTabs().add(0, thisTab);
        // select the tab in its new location
        personTabPane.getSelectionModel().select(0);
        // set the combo box to hold correct values now that we have changed member type
        setComboBoxValues(1);
    }

    private void replacePrimaryOrSignalError(boolean deletePerson) {
        // we are moving someone not a member
        if (person.getMemberType() != 1) {
            // we are deleting the person from the database
            if (deletePerson) personRepository.deletePerson(person);
            // we are removing the person from the membership
            else personRepository.removePersonFromMembership(person);
            // remove person from our model
            model.getPeople().remove(person);
            // remove the tab of the person
            removeThisTab(model.getPeopleTabPane());
        } else {  // we are removing the primary member
            Tab primaryTab = getTab("Primary");
            assert primaryTab != null;
            // get people from list in memory
            PersonDTO originalPrimary = model.getPersonByType(1);
            PersonDTO secondary = model.getPersonByType(2);
            if (secondary != null) {
                // either delete or remove from membership
                if (deletePerson) personRepository.deletePerson(originalPrimary);
                else personRepository.removePersonFromMembership(originalPrimary);
                // in case you run this routine again
                this.person = secondary;
                // let's remove the primary tab
                model.getPeopleTabPane().getTabs().remove(primaryTab);
                // set the secondary to primary
                secondary.setMemberType(MemberType.PRIMARY.getCode());
                // update them in the database
                personRepository.updatePerson(secondary);
                model.getPeopleTabPane().getTabs()
                        .stream()
                        .filter(tab -> "Secondary".equals(tab.getText()))
                        .findFirst().ifPresent(secondaryTab -> secondaryTab.setText("Primary"));
            } else
                createInformation("Cannot remove this person",
                        this.person.getFirstName() + " cannot be removed because they are the primary member, " +
                                "and there is no secondary to replace them");
        }
    }

    private void movePersonByMSidOrSignalError(String msId) {
        if(!membershipRepository.memberShipExists(Integer.parseInt(msId))) {
            createInformation("Cannot move this person","No membership with an MSID of " + msId + " found");
            return;
        }

        if (person.getMemberType() != 1) {
            int oldMsid = person.getMsId();
            person.setMemberType(3);

            person.setOldMsid(oldMsid);
            // TODO make sure it is an integer and that this membership exists
            person.setMsId(Integer.parseInt(msId));
            personRepository.updatePerson(person);
            // TODO error check to make sure we are in membership view
            removeThisTab(model.getPeopleTabPane());
        } else {
            createInformation("Cannot remove this person",
                    this.person.getFirstName() + " cannot be moved because they are the primary member, you " +
                            "will need to make someone else primary of this membership first");
        }
    }

    // will select tab by text in tab
    private Tab getTab(String text) {
        Optional<Tab> matchingTab = model.getPeopleTabPane().getTabs().stream()
                .filter(t -> t.getText().equals(text)).findFirst();
        return matchingTab.orElse(null);
    }

    // will select PersonDTO attached to tab
    private PersonDTO getPerson(Tab tab) {
        HBoxPerson hBoxPerson = (HBoxPerson) tab.getContent();
        return hBoxPerson.getPersonDTO();
    }

    // sets the combo box values for person on other tab
    private void setOtherComboBoxValues(Tab tab, int value) {
        HBoxPerson hBoxPerson = (HBoxPerson) tab.getContent();
        hBoxPerson.getPropertiesTab().getPersonMove().setComboBoxValues(value);
    }

    private void setComboBoxValues(int type) {
        comboBox.getItems().clear();
        switch (type) {
            case 1 -> comboBox.getItems().addAll("Secondary", "Dependent");
            case 2 -> comboBox.getItems().addAll("Primary", "Dependent");
            default -> comboBox.getItems().addAll("Primary", "Secondary");
        }
        comboBox.getSelectionModel().selectFirst();
    }

}
