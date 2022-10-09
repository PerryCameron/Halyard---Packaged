package com.ecsail.gui.boxes;
///////////BUG///////  adding a person as secondary changes the pid in the membership to the secondary

import com.ecsail.Note;
import com.ecsail.enums.MemberType;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.PersonDTO;
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
	
	int ms_id;
	private Label titleLabel;
	private TabPane peopleTabPane; // a reference so we can open up the newly created pane
	private Note note;
	private MembershipListDTO membership;
	private PersonDTO person;
	private final int PRIMARY = 1;
	private final int SECONDARY = 2;
	private final int DEPENDANT = 3;
	private Boolean hasError = false;
	private TabPane tp;
	
	public VBoxAddPerson(TabPane tp, Note n, MembershipListDTO me) {
		this.note = n;
		this.ms_id = n.getMsid();
		this.peopleTabPane = tp;
		this.membership = me;
		this.tp = tp;
		
		////// OBJECTS //////
		VBox vboxGrey = new VBox();
		Button addButton = new Button("Add");
		Button populateButton = new Button("Populate");
		this.titleLabel = new Label("Add New Member");
		Label fnameLabel = new Label("First Name");
		Label lnameLabel = new Label("Last Name");
		Label occupationLabel = new Label("Occupation");
		Label businessLabel = new Label("Business");
		Label birthdayLabel = new Label("Birthday");
		Label addFromPidLabel = new Label("Populate fields from existing PID");
		Label memberTypeLabel = new Label("Member Type");
		TextField populateTextField = new TextField();
		TextField fnameTextField = new TextField();
		TextField lnameTextField = new TextField();
		TextField businessTextField = new TextField();
		TextField occupationTextField = new TextField();
		DatePicker birthdayDatePicker = new DatePicker();
		final ComboBox<MemberType> memberType = new ComboBox<MemberType>();
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
		fnameTextField.setPrefSize(240, 10);
		lnameTextField.setPrefSize(240, 10);
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
			int pid = SqlSelect.getNextAvailablePrimaryKey("person", "p_id");
			person = new PersonDTO(pid, ms_id, memberType.getValue().getCode(), fnameTextField.getText(),
					lnameTextField.getText(), getBirthday(birthdayDatePicker.getValue()), occupationTextField.getText(),
					businessTextField.getText(), true, null,0);

			// if adding member succeeds, clear the form
			if (!setNewMember(person)) {
				String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
				note.addMemoAndReturnId("New person: " + fnameTextField.getText() + " " + lnameTextField.getText() + " - (PID " + pid
						+ ") added as " + memberType.getValue().toString() + ".", date, 0, "N");
				fnameTextField.setText("");
				lnameTextField.setText("");
				businessTextField.setText("");
				occupationTextField.setText("");
				birthdayDatePicker.setValue(null);
				memberType.setValue(MemberType.getByCode(1)); // sets to primary
			}
		});
		
		
		vboxFnameLabel.getChildren().add(fnameLabel);
		vboxLnameLabel.getChildren().add(lnameLabel);
		vboxOccupLabel.getChildren().add(occupationLabel);
		vboxBuisnLabel.getChildren().add(businessLabel);
		vboxBirthLabel.getChildren().add(birthdayLabel);
		
		vboxFnameBox.getChildren().add(fnameTextField);
		vboxLnameBox.getChildren().add(lnameTextField);
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
		String memberStringType = getMemberType(person,hasError);
		checkName(person.getFname());
		checkName(person.getLname());
		if(!hasError) {
			addPerson(memberStringType);
		}
		return hasError;
	}
	
	private void checkName(String name) {
		if(name.equals("")) {
			hasError = true;
			printErrorMessage("Must have a name");
		}
	}
	
	private String getMemberType(PersonDTO person, Boolean hasError) {
		String memberType = null;
		switch (person.getMemberType()) {
		case PRIMARY:
			memberType = "Primary";
			if (SqlExists.personExists(PRIMARY, ms_id))
				printErrorMessage("A primary member already exists for this account");
			break;
		case SECONDARY:
			memberType = "Secondary";
			if (SqlExists.personExists(SECONDARY, ms_id)) {
				printErrorMessage("A secondary member already exists for this account");
			}
			break;
		case DEPENDANT:
			memberType = "Dependant";
			break;
		default:
			System.out.println("no match");
		}
		return memberType;
	}
	
	private void printErrorMessage(String message) {
		titleLabel.setText(message);
		titleLabel.setTextFill(Color.RED);
		hasError = true;
	}
	
	private void addPerson(String memberType) {
		SqlInsert.addPersonRecord(person);
		peopleTabPane.getTabs().add(new Tab(memberType, new HBoxPerson(person, membership,tp)));  //tp is tappane to remove tab if deleting
    	titleLabel.setText("Add New Member");
		titleLabel.setTextFill(Color.BLACK);
	}
	

}
