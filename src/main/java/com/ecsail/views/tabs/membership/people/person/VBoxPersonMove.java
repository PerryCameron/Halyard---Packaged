package com.ecsail.views.tabs.membership.people.person;


import com.ecsail.BaseApplication;
import com.ecsail.dto.PersonDTO;
import com.ecsail.enums.MemberType;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.views.tabs.membership.people.HBoxPerson;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

// This class is for the options in the properties box inside the person tab
public class VBoxPersonMove extends VBox {
    private PersonDTO person;
    final ComboBox<String> combo_box = new ComboBox<>();
    boolean calledFromMembershipTab = false;
    private final PersonRepository personRepository;

    public VBoxPersonMove(PersonDTO person, HBoxPerson parent) {
        this.person = person;
        this.personRepository = parent.parent.getModel().getPersonRepository();
        TabPane personTabPane = parent.parent.getModel().getPeopleTabPane();


        // this is to allow a person to be removed from membership only called from TabMembership.class
        try {
            if (Class.forName(Thread.currentThread().getStackTrace()[4].getClassName()).equals(TabMembership.class)) {
                calledFromMembershipTab = true;
//                BaseApplication.logger.info("Calling Class for HBoxPerson.class is TabMembership.class ");
            }
        } catch (ClassNotFoundException e) {
            BaseApplication.logger.error("Unable to determine calling class: " + e.getMessage());
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
                vBoxFields.getChildren().add(combo_box);
            else
                vBoxFields.getChildren().remove(combo_box);
        });

        submit.setOnAction((event) -> {
            if (rb0.isSelected()) {
                changeMembershipType(personTabPane, combo_box.getValue());
            }
            if (rb1.isSelected()) {
                // makes sure this is not a primary person
                if(person.getMemberType() != 1) {
                    Optional<ButtonType> result = createConformation(
                            "Remove person",
                            "Remove person from membership",
                            "Are you sure you want to remove " + person.getFullName() + " from this membership?\n\n" +
                                    "It will leave this person's record free floating in the database, however "
                                    + person.getNameWithInfo() + " can be looked back up in the People tab and reattached " +
                                    "to this or another membership."
                    );
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // TODO set the secondary to primary if this person is a primary user
                        personRepository.removePersonFromMembership(person);
                        parent.parent.getModel().getPeople().remove(person);
                        removeThisTab(personTabPane);
                    }
                } else // this is a primary person
                    // is there a secondary?
                    replacePrimaryOrSignalError(person, personTabPane, false);
            }
            if (rb2.isSelected()) {
                Optional<ButtonType> result = createConformation(
                        "Delete person from database",
                        "This will permanently delete " + person.getFullName()
                                + " from the database",
                        "Are you sure you want to delete " + person.getFullName() + " from this database?");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    replacePrimaryOrSignalError(person, personTabPane, true);
//                    personRepository.deletePerson(person);
                    // TODO error check to make sure we are in membership view
//                    removeThisTab(personTabPane);
                }
            }
            if (rb3.isSelected()) {
                // TODO move to another msid
                int oldMsid = person.getMsId();
                // set memberType to 3 as default
                person.setMemberType(3);
                person.setOldMsid(oldMsid);
                // TODO make sure it is an integer and that this membership exists
                person.setMsId(Integer.parseInt(msidTextField.getText()));
                personRepository.updatePerson(person);
                // TODO error check to make sure we are in membership view
                removeThisTab(personTabPane);
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
        Tab thisPersonTab = personTabPane.getSelectionModel().getSelectedItem();
        personTabPane.getTabs().remove(thisPersonTab);
    }

    private Optional<ButtonType> createConformation(String title, String header, String content) {
        Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
        conformation.setTitle(title);
        conformation.setHeaderText(header);
        conformation.setContentText(content);
        DialogPane dialogPane = conformation.getDialogPane();
        dialogPane.getStylesheets().add("css/dark/dialogue.css");
        dialogPane.getStyleClass().add("dialog");
        return conformation.showAndWait();
    }

    private void createInformation(String s) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText("Can not remove this person");
        error.setContentText(s);
        DialogPane dialogPane = error.getDialogPane();
        dialogPane.getStylesheets().add("css/dark/dialogue.css");
        dialogPane.getStyleClass().add("dialog");
        error.showAndWait();
    }

    private void changeMembershipType(TabPane personTabPane, String memberTypeToChangeTo) {
        // TODO strange bug in here if you go back and fourth too many times, probably doesn't fit any use case though
        if (memberTypeToChangeTo.equals("Primary")) {
            changeToPrimary(personTabPane);
        } else if (memberTypeToChangeTo.equals("Secondary")) {
            changeToSecondary(personTabPane);
        } else {
            // TODO this is not done
            System.out.println("Moving you to a dependent");
        }
    }

    private void changeToSecondary(TabPane personTabPane) {
        if (personRepository.memberTypeExists(MemberType.SECONDARY.getCode(), person.getMsId())) {
            Tab secondaryTab = getTab(personTabPane, "Secondary");
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
            Tab primaryTab = getTab(personTabPane, "Primary");
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

    private void replacePrimaryOrSignalError(PersonDTO originalPrimary, TabPane personTabPane, boolean deletePrimary) {
        Tab primaryTab = getTab(personTabPane, "Primary");
        assert primaryTab != null;
        PersonDTO secondary = personRepository.getPerson(this.person.getMsId(), 2);
        if(secondary != null) {
            // either delete or remove from membership
            if(deletePrimary) {
                personRepository.deletePerson(originalPrimary);
            } else {
                personRepository.removePersonFromMembership(originalPrimary);
            }
//            parent.parent.getModel().getPeople().remove(person);
            // in case you run this routine again
            this.person = secondary;
            // let's remove the primary tab
            personTabPane.getTabs().remove(primaryTab);
            // set the secondary to primary
            secondary.setMemberType(MemberType.PRIMARY.getCode());
            // update them in the database
            personRepository.updatePerson(secondary);
            Tab secondaryTab = personTabPane.getTabs()
                    .stream()
                    .filter(tab -> "Secondary".equals(tab.getText()))
                    .findFirst()
                    .orElse(null);

            if(secondaryTab != null) {
                secondaryTab.setText("Primary");
            }
        } else
        createInformation(this.person.getFirstName() + " can not be removed because they are the primary member, and there is no secondary to replace them");
    }

    // will select tab by text in tab
    private Tab getTab(TabPane personTabPane, String text) {
        Optional<Tab> matchingTab = personTabPane.getTabs().stream()
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
        combo_box.getItems().clear();
        switch (type) {
            case 1 -> combo_box.getItems().addAll("Secondary", "Dependent");
            case 2 -> combo_box.getItems().addAll("Primary", "Dependent");
            default -> combo_box.getItems().addAll("Primary", "Secondary");
        }
        combo_box.getSelectionModel().selectFirst();
    }

}
