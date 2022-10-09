package com.ecsail.gui.tabs;

import com.ecsail.gui.boxes.VBoxPersonMove;
import com.ecsail.sql.SqlPerson;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.PersonDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabPersonProperties extends Tab {
	private PersonDTO person;  // this is the person we are focused on.
	private ObservableList<PersonDTO> people;  // this is only for updating people list when in people list mode
	VBoxPersonMove personMove;

	public TabPersonProperties(PersonDTO p, ObservableList<PersonDTO> pe, TabPane personTabPane) {
		super("Properties");
		this.person = p;
		this.people = pe;
		this.personMove = new VBoxPersonMove(person, personTabPane);
		int age = SqlPerson.getPersonAge(person);

		//////////// OBJECTS /////////////////
		HBox hboxMain = new HBox();
		VBox vBoxLeft = new VBox(); // holds all content
		VBox vBoxRight = new VBox();
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		HBox hboxMemberType = new HBox();
		Button delButton = new Button("Delete");
		CheckBox activeCheckBox = new CheckBox("Is Active");
		
		/////////////////  ATTRIBUTES  /////////////////////
		
        activeCheckBox.setSelected(person.isActive());
		
        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		hboxMain.setId("box-blue");
		hboxGrey.setId("box-grey");

		hboxMain.setPadding(new Insets(5, 5, 5, 5));
		
		hboxMain.setSpacing(5);
		vBoxLeft.setSpacing(5);
		hboxMemberType.setSpacing(5);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		
		hboxMain.setAlignment(Pos.CENTER);
		hboxMemberType.setAlignment(Pos.CENTER_LEFT);

		vBoxLeft.setId("box-pink");
		vBoxLeft.setPadding(new Insets(5,5,5,5));
		//////////  LISTENERS /////
         
        activeCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               //activeCheckBox.setSelected(!newValue);
            	SqlUpdate.updatePersonField("IS_ACTIVE",person.getP_id(),newValue);
            	if(people != null)  // this updates the people list if in people mode
        			people.get(TabPeople.getIndexByPid(person.getP_id())).setActive(newValue);
            }
        });

		//////////////// SET  CONTENT ////////////////
		vBoxLeft.getChildren().addAll(
				new Label("Age: " + age),
				new Label("Person ID: " + person.getP_id()),
				new Label("MSID: " + person.getMs_id()));
		vBoxRight.getChildren().add(personMove);
		hboxGrey.getChildren().addAll(vBoxLeft,vBoxRight);
		hboxMain.getChildren().add(hboxGrey);
		hboxMain.setId("box-blue");
		setContent(hboxMain);
	}
	///////////////// CLASS METHODS /////////////////////

	public VBoxPersonMove getPersonMove() {
		return personMove;
	}
}
