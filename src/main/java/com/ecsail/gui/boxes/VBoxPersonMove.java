package com.ecsail.gui.boxes;


import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.PersonDTO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

// This class is for the options in the properties box inside the person tab
public class VBoxPersonMove extends VBox {
    private PersonDTO person;
    final ComboBox<String> combo_box = new ComboBox<>();

    public VBoxPersonMove(PersonDTO person, TabPane personTabPane) {
        this.person = person;
        ToggleGroup tg = new ToggleGroup();
        RadioButton rb0 = new RadioButton("Change "+person.getFname()+"'s member type");
        RadioButton rb1 = new RadioButton("Remove "+person.getFname()+" from this membership");
        RadioButton rb2 = new RadioButton("Delete "+person.getFname()+" from database ");
        RadioButton rb3 = new RadioButton("Move "+person.getFname()+" to membership (MSID)");
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
        this.setPadding(new Insets(5,5,5,5));
        hbox4.setPadding(new Insets(5,0,0,0));
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
            if(rb0.isSelected()) {
                changeMembershipType(personTabPane, combo_box.getValue().toString());
            }
            if(rb1.isSelected()) {
                // TODO need to check to make sure person is attached to a membership
//                Optional<ButtonType> result = createConformationForMovingPerson();
//                // user pushed ok
//                if (result.get() == ButtonType.OK) {
//                    SqlUpdate.removePersonFromMembership(person);
//                    // TODO error check to make sure we are in membership view
//                    removeThisTab(personTabPane);
//                }
            }
            if(rb2.isSelected()) {
//                Optional<ButtonType> result = createConformationForDeletingPerson();
//                if (result.get() == ButtonType.OK) {
//                    SqlDelete.deletePerson(person);
//                    // TODO error check to make sure we are in membership view
//                    removeThisTab(personTabPane);
//                }
            }
            if(rb3.isSelected()) {
                // TODO move to another msid
                int oldMsid = person.getMs_id();
                // set membertype to 3 as default
                person.setMemberType(3);
                person.setOldMsid(oldMsid);
                // TODO make sure it is an integer and that this membership exists
                person.setMs_id(Integer.valueOf(msidTextField.getText()));
                System.out.println("Moving " + person.getFname() + " " + person.getLname() + " to " + person.getMs_id());
                SqlUpdate.updatePerson(person);
                // TODO error check to make sure we are in membership view
                removeThisTab(personTabPane);
            }
        });

        setComboBoxValues(person.getMemberType());
        hBox0.getChildren().add(rb0);
        hBox1.getChildren().add(rb1);
        hBox2.getChildren().add(rb2);
        hBox3.getChildren().add(rb3);
        hbox4.getChildren().addAll(vBoxFields,submit);

        this.getChildren().addAll(hBox0,hBox1,hBox2,hBox3,hbox4);
    }

    private void removeThisTab(TabPane personTabPane) {
        Tab thisPersonTab = personTabPane.getSelectionModel().getSelectedItem();
        personTabPane.getTabs().remove(thisPersonTab);
    }

//    private Optional<ButtonType> createConformationForMovingPerson() {
//        HalyardAlert conformation = new HalyardAlert(HalyardAlert.AlertType.CONFIRMATION);
//        conformation.setTitle("Remove person from membership");
//        conformation.setHeaderText("This will detach " + person.getFname() + " " + person.getLname()
//                + " from this membership, but not from the database");
//        conformation.setContentText("Are you sure you want to remove " + person.getFname() + " " + person.getLname() + " from this membership?");
//        return conformation.showAndWait();
//    }

//    private Optional<ButtonType> createConformationForDeletingPerson() {
//        HalyardAlert conformation = new HalyardAlert(HalyardAlert.AlertType.CONFIRMATION);
//        conformation.setTitle("Delete person from database");
//        conformation.setHeaderText("This will permanently delete " + person.getFname() + " " + person.getLname()
//                + " from the database");
//        conformation.setContentText("Are you sure you want to delete " + person.getFname() + " " + person.getLname() + " from this database?");
//        return conformation.showAndWait();
//    }

    private void changeMembershipType(TabPane personTabPane, String memberTypeToChangeTo) {
        // TODO strange bug in here if you go back and fourth too many times, probably doesn't fit any use case though
        if(memberTypeToChangeTo.equals("Primary")) {
            changeToPrimary(personTabPane);
        }
        else if(memberTypeToChangeTo.equals("Secondary")) {
            changeToSecondary(personTabPane);
        } else {
            System.out.println("Moving you to a dependent");
        }
    }

    private void changeToSecondary(TabPane personTabPane) {
        if(SqlExists.memberTypeExists(2,person.getMs_id())) {
            Tab secondaryTab = getTab(personTabPane, "Secondary");
            PersonDTO secondary = getPerson(secondaryTab);
            // change the existing secondary to new value, probably primary
            SqlUpdate.updatePersonChangeMemberType(secondary, person.getMemberType());
            // sets combobox values for the other member being switched
            setOtherComboBoxValues(secondaryTab,1);
            secondaryTab.setText("Primary");
        }
        // update person to secondary
        SqlUpdate.updatePersonChangeMemberType(person, 2);
        // create a variable to use
        Tab thisTab = personTabPane.getSelectionModel().getSelectedItem();
        thisTab.setText("Secondary");
        // remove and add the tab in the correct place
        personTabPane.getTabs().remove(thisTab);
        personTabPane.getTabs().add(1,thisTab);
        // select the tab in its new location
        personTabPane.getSelectionModel().select(1);
        // set the combo box to hold correct values now that we have changed member type
        setComboBoxValues(2);
    }

    private void changeToPrimary(TabPane personTabPane) {
        // check if there is already a primary for persons msid
        if(SqlExists.memberTypeExists(1,person.getMs_id())) {
            Tab primaryTab = getTab(personTabPane, "Primary");
            PersonDTO primary = getPerson(primaryTab);
            // change the existing primary to new value, probably secondary
            SqlUpdate.updatePersonChangeMemberType(primary, person.getMemberType());
            // sets combobox values for the other member being switched removing option (a primary shouldn't' have a primary listing)
            setOtherComboBoxValues(primaryTab,2);
            primaryTab.setText("Secondary");
        }
        // update person to primary
        SqlUpdate.updatePersonChangeMemberType(person, 1);
        // create a variable to use
        Tab thisTab = personTabPane.getSelectionModel().getSelectedItem();
        thisTab.setText("Primary");
        // remove and add the tab in the correct place
        personTabPane.getTabs().remove(thisTab);
        personTabPane.getTabs().add(0,thisTab);
        // select the tab in its new location
        personTabPane.getSelectionModel().select(0);
        // set the combo box to hold correct values now that we have changed member type
        setComboBoxValues(1);
    }

    // will select tab by text in tab
    private Tab getTab(TabPane personTabPane, String text) {
        for(Tab t: personTabPane.getTabs()) {
            if(t.getText().equals(text))
                return t;
        }
        return null;
    }

    // will select PersonDTO attached to tab
    private PersonDTO getPerson(Tab tab) {
    HBoxPerson hBoxPerson = (HBoxPerson) tab.getContent();
    return  hBoxPerson.getPerson();
    }

    // sets the combo box values for person on other tab
    private void setOtherComboBoxValues(Tab tab, int value) {
        HBoxPerson hBoxPerson = (HBoxPerson) tab.getContent();
        hBoxPerson.getPropertiesTab().getPersonMove().setComboBoxValues(value);
    }

    private void setComboBoxValues(int type) {
        combo_box.getItems().clear();
        switch(type) {
            case 1:
                combo_box.getItems().addAll("Secondary","Dependent");
            break;
            case 2:
                combo_box.getItems().addAll("Primary","Dependent");
            break;
            default:
                combo_box.getItems().addAll("Primary","Secondary");
        }
        combo_box.getSelectionModel().selectFirst();
    }

    public ComboBox<String> getCombo_box() {
        return combo_box;
    }
}
