package com.ecsail.views.tabs.membership.people.person;

import com.ecsail.repository.implementations.PersonRepositoryImpl;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.views.tabs.membership.people.HBoxPerson;
import com.ecsail.sql.SqlPerson;
import com.ecsail.dto.PersonDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabPersonProperties extends Tab {
	private final PersonDTO person;  // this is the person we are focused on.
	private final VBoxPersonMove personMove;
	private final PersonRepository personRepository = new PersonRepositoryImpl();
	HBoxPerson parent;
	public TabPersonProperties(PersonDTO p, HBoxPerson parent) {
		super("Properties");
		this.parent = parent;
		this.person = p;
		this.personMove = new VBoxPersonMove(person, parent.parent.getModel().getPeopleTabPane());
		int age = SqlPerson.getPersonAge(person);

		//////////// OBJECTS /////////////////
		var hboxMain = new HBox();
		var vBoxLeft = new VBox(); // holds all content
		var vBoxRight = new VBox();
		var hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
		var hboxMemberType = new HBox();

		/////////////////  ATTRIBUTES  /////////////////////

        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		hboxMain.setId("custom-tap-pane-frame");
		hboxGrey.setId("box-background-light");
		vBoxLeft.setId("box-pink");
		hboxMain.setPadding(new Insets(5, 5, 5, 5));
		
		hboxMain.setSpacing(5);
		vBoxLeft.setSpacing(5);
		hboxMemberType.setSpacing(5);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		
		hboxMain.setAlignment(Pos.CENTER);
		hboxMemberType.setAlignment(Pos.CENTER_LEFT);


		vBoxLeft.setPadding(new Insets(5,5,5,5));
		//////////  LISTENERS /////

		//////////////// SET  CONTENT ////////////////
		vBoxLeft.getChildren().addAll(
				new Label("Age: " + age),
				new Label("Person ID: " + person.getpId()),
				new Label("MSID: " + person.getMsId()));
		vBoxRight.getChildren().add(personMove);
		hboxGrey.getChildren().addAll(vBoxLeft,vBoxRight);
		hboxMain.getChildren().add(hboxGrey);
		setContent(hboxMain);
	}
	///////////////// CLASS METHODS /////////////////////

	public VBoxPersonMove getPersonMove() {
		return personMove;
	}
}
