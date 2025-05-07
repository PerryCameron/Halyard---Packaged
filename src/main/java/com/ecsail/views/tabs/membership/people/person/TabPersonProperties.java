package com.ecsail.views.tabs.membership.people.person;

import com.ecsail.dto.PersonDTO;
import com.ecsail.views.tabs.membership.people.HBoxPerson;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class TabPersonProperties extends Tab implements Builder {
	private final PersonDTO person;  // this is the person we are focused on.
	private final VBoxPersonMove personMove;
	HBoxPerson parent;
	public TabPersonProperties(PersonDTO p, HBoxPerson parent) {
		super("Properties");
		this.parent = parent;
		this.person = p;
		this.personMove = new VBoxPersonMove(person, parent);
		setContent(build());
	}

	@Override
	public Node build() {
		var hboxMain = new HBox();
		hboxMain.setAlignment(Pos.CENTER);
		hboxMain.setSpacing(5);
		hboxMain.setPadding(new Insets(5, 5, 5, 5));
		hboxMain.setId("custom-tap-pane-frame");
		var hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		hboxGrey.setId("box-background-light");
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		hboxGrey.getChildren().addAll(vBoxLeft(),vBoxRight());
		hboxMain.getChildren().add(hboxGrey);
		return hboxMain;
	}

	private Node vBoxLeft() {
		int age = parent.parent.getModel().getPersonRepository().getPersonAge(person);
		var vBox = new VBox(); // holds all content
		vBox.setId("box-pink");
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(5,5,5,5));
		vBox.getChildren().addAll(
				new Label("Age: " + age),
				new Label("Person ID: " + person.getpId()),
				new Label("MSID: " + person.getMsId()));
		return vBox;
	}

	private Node vBoxRight() {
		var vBox = new VBox();
		vBox.getChildren().add(personMove);
		return vBox;
	}

	public VBoxPersonMove getPersonMove() {
		return personMove;
	}
}
