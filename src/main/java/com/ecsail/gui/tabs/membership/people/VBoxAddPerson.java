package com.ecsail.gui.tabs.membership.people;
///////////BUG///////  adding a person as secondary changes the pid in the membership to the secondary

import com.ecsail.BaseApplication;
import com.ecsail.enums.MemberType;
import com.ecsail.gui.tabs.membership.TabMembership;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.dto.PersonDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class VBoxAddPerson extends VBox {

	private final Label titleLabel;
	private PersonDTO person;
	private Boolean hasError = false;


	private final TabMembership t;
	public VBoxAddPerson(TabMembership t) {
		this.t = t;

		
		////// OBJECTS //////
		VBox vboxGrey = new VBox();
		Button addButton = new Button("Add");
		Button populateButton = new Button("Populate");
		this.titleLabel = new Label("Add New Member");
		Label firstNameLabel = new Label("First Name");
		Label lastNameLabel = new Label("Last Name");
		Label occupationLabel = new Label("Occupation");
		Label businessLabel = new Label("Business");
		Label birthdayLabel = new Label("Birthday");
		Label addFromPidLabel = new Label("Populate fields from existing PID");
		Label memberTypeLabel = new Label("Member Type");
		TextField populateTextField = new TextField();
		TextField firstNameTextField = new TextField();
		TextField lastNameTextField = new TextField();
		TextField businessTextField = new TextField();
		TextField occupationTextField = new TextField();
		DatePicker birthdayDatePicker = new DatePicker();
		final ComboBox<MemberType> memberType = new ComboBox<>();
		HBox hboxTitle = new HBox(); // Title
		HBox hbox1 = new HBox(); // first name
		HBox hbox2 = new HBox(); // last name
		HBox hbox3 = new HBox(); // Occupation
		HBox hbox4 = new HBox(); // Business
		HBox hbox5 = new HBox(); // Birthday
		HBox hbox6 = new HBox(); // Member Type
		HBox hbox7 = new HBox(); // Add Button
		HBox hbox8 = new HBox(); // Populate Title
		HBox hbox9 = new HBox(); // populate
		
		VBox vboxFnameLabel = new VBox();
		VBox vboxLnameLabel = new VBox();
		VBox vboxOccupLabel = new VBox();
		VBox vboxBuisnLabel = new VBox();
		VBox vboxBirthLabel = new VBox();
		
		VBox vboxFnameBox = new VBox();
		VBox vboxLnameBox = new VBox();
		VBox vboxOccupBox = new VBox();
		VBox vboxBuisnBox = new VBox();
		VBox vboxBirthBox = new VBox();
		
		///////////////  ATTRIBUTES ////////////////
		setPrefWidth(460);
		firstNameTextField.setPrefSize(240, 10);
		lastNameTextField.setPrefSize(240, 10);
		businessTextField.setPrefSize(240, 10);
		occupationTextField.setPrefSize(240, 10);
		populateTextField.setPrefWidth(45);
		populateTextField.setPrefHeight(25);
		memberType.getItems().setAll(MemberType.values());
		memberType.setValue(MemberType.getByCode(1)); // sets to primary
		addButton.setPrefWidth(80);
		populateButton.setPrefWidth(80);
		vboxFnameLabel.setPrefWidth(80);
		vboxLnameLabel.setPrefWidth(80);
		vboxOccupLabel.setPrefWidth(80);
		vboxBuisnLabel.setPrefWidth(80);
		vboxBirthLabel.setPrefWidth(80);
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		
		hboxTitle.setAlignment(Pos.CENTER);
		hbox6.setAlignment(Pos.CENTER_LEFT);
		hbox7.setAlignment(Pos.CENTER_RIGHT);
		hbox8.setAlignment(Pos.CENTER);
		hbox9.setAlignment(Pos.CENTER);
		
		hboxTitle.setSpacing(13);
		hbox6.setSpacing(25);
		hbox7.setSpacing(25);
		hbox9.setSpacing(15);
		
		hboxTitle.setPadding(new Insets(5, 15, 5, 15));  // first Name
		hbox1.setPadding(new Insets(5, 15, 5, 60));  // first Name
		hbox2.setPadding(new Insets(5, 15, 5, 60));  // last name
		hbox3.setPadding(new Insets(5, 15, 5, 60));  // occupation
		hbox4.setPadding(new Insets(5, 15, 5, 60));  // business
		hbox5.setPadding(new Insets(5, 15, 5, 60));  // birthday
		hbox6.setPadding(new Insets(5, 15, 5, 60));  // member type
		hbox7.setPadding(new Insets(5, 100, 5, 5));  // add button		
		hbox8.setPadding(new Insets(60, 5, 5, 5));  // Populate title
		hbox9.setPadding(new Insets(20, 15, 5, 5));  // populate field and button
		setPadding(new Insets(5, 5, 5, 5));
		setId("custom-tap-pane-frame");
		vboxGrey.setId("box-background-light");
		
		vboxFnameLabel.setAlignment(Pos.CENTER_LEFT);
		vboxLnameLabel.setAlignment(Pos.CENTER_LEFT);
		vboxOccupLabel.setAlignment(Pos.CENTER_LEFT);
		vboxBuisnLabel.setAlignment(Pos.CENTER_LEFT);
		vboxBirthLabel.setAlignment(Pos.CENTER_LEFT);
		

		
		/////////////////  LISTENERS  /////////////////////
		addButton.setOnAction((event) -> {
			hasError = false;
			int pid = SqlSelect.getNextAvailablePrimaryKey("person", "p_id");
			person = new PersonDTO(pid, t.getMembership().getMsId(), memberType.getValue().getCode(), firstNameTextField.getText(),
					lastNameTextField.getText(), getBirthday(birthdayDatePicker.getValue()), occupationTextField.getText(),
					businessTextField.getText(), true, "",0);
			BaseApplication.logger.info("New Key=" + pid + " new person=" + person.getNameWithInfo());

			// if adding member succeeds, clear the form
			if (!setNewMember(person)) {
				String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
				t.getNote().addMemoAndReturnId("New person: " + person.getNameWithInfo() + " added as " + memberType.getValue().toString() + ".", date, 0, "N",0);
				firstNameTextField.setText("");
				lastNameTextField.setText("");
				businessTextField.setText("");
				occupationTextField.setText("");
				birthdayDatePicker.setValue(null);
				memberType.setValue(MemberType.getByCode(1)); // sets to primary
			}
		});
		
		
		vboxFnameLabel.getChildren().add(firstNameLabel);
		vboxLnameLabel.getChildren().add(lastNameLabel);
		vboxOccupLabel.getChildren().add(occupationLabel);
		vboxBuisnLabel.getChildren().add(businessLabel);
		vboxBirthLabel.getChildren().add(birthdayLabel);
		
		vboxFnameBox.getChildren().add(firstNameTextField);
		vboxLnameBox.getChildren().add(lastNameTextField);
		vboxOccupBox.getChildren().add(occupationTextField);
		vboxBuisnBox.getChildren().add(businessTextField);
		vboxBirthBox.getChildren().add(birthdayDatePicker);
		
		hboxTitle.getChildren().addAll(titleLabel);
		hbox1.getChildren().addAll(vboxFnameLabel, vboxFnameBox);
		hbox2.getChildren().addAll(vboxLnameLabel, vboxLnameBox);
		hbox3.getChildren().addAll(vboxOccupLabel, vboxOccupBox);
		hbox4.getChildren().addAll(vboxBuisnLabel, vboxBuisnBox);
		hbox5.getChildren().addAll(vboxBirthLabel, vboxBirthBox);
		hbox6.getChildren().addAll(memberTypeLabel,memberType);
		hbox7.getChildren().addAll(addButton);
		hbox8.getChildren().addAll(addFromPidLabel);
		hbox9.getChildren().addAll(populateTextField,populateButton);
		vboxGrey.getChildren().addAll(hboxTitle, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9);
		getChildren().add(vboxGrey);
	}

	private String getBirthday(LocalDate birthday) {
		String date;
		if(birthday == null) {  /// we don't have to have a date
			date = null;
		} else {
			date = birthday.toString();
		}
		return date;
	}
	
	private Boolean setNewMember(PersonDTO person) {  // gives the last memo_id number
		checkIfCoreMembersExist(person);
		String memberStringType = String.valueOf(MemberType.getByCode(person.getMemberType()));
		checkName(person.getFname());
		checkName(person.getLname());
		if(!hasError) {
			addPerson(memberStringType);
			BaseApplication.logger.info("Added " + person.getNameWithInfo() + " to " + t.getMembership().getMembershipInfo());
		}
		return hasError;
	}

	private void checkIfCoreMembersExist(PersonDTO person) {
		if(person.getMemberType() < 3)
   			if (SqlExists.personExists(person.getMemberType(), t.getMembership().getMsId())) {
			printErrorMessage("A " + MemberType.getByCode(person.getMemberType())
					+ " member already exists for this account");
			hasError = true;
		}
	}

	private void checkName(String name) {
		if(name.equals("")) {
			hasError = true;
			printErrorMessage("Must have a name");
		}
	}
	
	private void printErrorMessage(String message) {
		titleLabel.setText(message);
		titleLabel.setTextFill(Color.RED);
		hasError = true;
	}
	
	private void addPerson(String memberType) {
		SqlInsert.addPersonRecord(person);
		t.getPeopleTabPane().getTabs().add(new Tab(memberType,
				new HBoxPerson(person, t.getMembership(),t.getPeopleTabPane())));
    	titleLabel.setText("Add New Member");
		titleLabel.setTextFill(Color.BLACK);
	}
}
