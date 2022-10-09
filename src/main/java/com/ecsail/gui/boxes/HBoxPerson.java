package com.ecsail.gui.boxes;

import com.ecsail.HalyardPaths;
import com.ecsail.Launcher;

import com.ecsail.gui.tabs.TabPeople;
import com.ecsail.gui.tabs.TabPersonProperties;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.PersonDTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// TODO need to add ability to switch primary and secondary

public class HBoxPerson extends HBox {
	private final PersonDTO person;
	private final MembershipListDTO membership;
	private final ObservableList<PersonDTO> people;  // this is only for updating people list when in people list mode
	TabPersonProperties propertiesTab; // this is here for a getter so I can get to combobox

	public HBoxPerson(PersonDTO p, MembershipListDTO me, TabPane personTabPane) {
		this.person = p;
		this.membership = me;

		if(Launcher.tabOpen("People List")) {
			this.people = TabPeople.people;
		} else {
			this.people = null;
		}

		this.propertiesTab = new TabPersonProperties(p, people, personTabPane);
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
		HBox hboxNickName = new HBox(); // nick name
		HBox hboxOccupation = new HBox(); // Occupation
		HBox hboxBusiness = new HBox(); // Business
		HBox hboxBirthday = new HBox(); // Birthday
		
		VBox vbLnameLabel = new VBox();
		VBox vbFnameLabel = new VBox();
		VBox vbNnameLabel = new VBox();
		VBox vbOccupationLabel = new VBox();
		VBox vbBuisnessLabel = new VBox();
		VBox vbBirthdayLabel = new VBox();
		VBox vbLnameBox = new VBox();
		VBox vbFnameBox = new VBox();
		VBox vbNnameBox = new VBox();
		VBox vbOccupationBox = new VBox();
		VBox vbBuisnessBox = new VBox();
		VBox vbBirthdayBox = new VBox();
		
		HBox hboxPhone = new HBoxPhone(person); // Phone
		HBox hboxEmail = new HBoxEmail(person); // Email
		HBox hboxOfficer = new HBoxOfficer(person); // Officer
		HBox hboxAward = new HBoxAward(person);

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
		vbBuisnessLabel.setPrefWidth(75);
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
		vbBuisnessLabel.setAlignment(Pos.CENTER_LEFT);
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
		
		hboxPhone.setId("box-blue");
		hboxEmail.setId("box-blue");
		hboxOfficer.setId("box-blue");
		hboxAward.setId("box-blue");
		hboxPictureFrame.setId("box-blue");
		this.setId("box-blue");
		vboxGrey.setId("box-grey");

		if(person.getBirthday() != null) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(person.getBirthday(), formatter);
		birthdayDatePicker.setValue(date);
		}
		
		/////////////// LISTENERS //////////////////////////

		
		fnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
						SqlUpdate.updateFirstName(fnameTextField.getText(),person);
						if(person.getMemberType()==1)  // only update table if this is the primary member
							membership.setFname(fnameTextField.getText());
							if(people != null)  // this updates the people list if in people mode
								people.get(TabPeople.getIndexByPid(person.getP_id())).setFname(fnameTextField.getText());
	            }});

		lnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateLastName(lnameTextField.getText(),person);
	            		if(person.getMemberType()==1)  // only update table if this is the primary member
	            			membership.setLname(lnameTextField.getText());
	            			if(people != null)  // this updates the people list if in people mode
	            				people.get(TabPeople.getIndexByPid(person.getP_id())).setLname(lnameTextField.getText());
	            }
	    });

		occupationTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateOccupation(occupationTextField.getText(),person);
	            		if(people != null)  // this updates the people list if in people mode
	            			people.get(TabPeople.getIndexByPid(person.getP_id())).setOccupation(occupationTextField.getText());
	            }
	    });
		
		businessTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateBuisness(businessTextField.getText(), person);
	            		if(people != null)  // this updates the people list if in people mode
	            			people.get(TabPeople.getIndexByPid(person.getP_id())).setBuisness(businessTextField.getText());
	            }
	    });
		
		nnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		SqlUpdate.updateNickName(nnameTextField.getText(), person);
            		if(people != null)  // this updates the people list if in people mode
            			people.get(TabPeople.getIndexByPid(person.getP_id())).setBuisness(businessTextField.getText());
            }
		});
		
//		photo.setOnMouseClicked((e) -> {
//			if (e.getClickCount() == 1) {
//				new Dialogue_ChoosePicture();
//			}
//		});
		
		photo.setOnMouseExited(ex -> hboxPictureFrame.setStyle("-fx-background-color: #9fc0c7;"));
		
		photo.setOnMouseEntered(en -> hboxPictureFrame.setStyle("-fx-background-color: #201ac9;"));

		EventHandler<ActionEvent> event = e -> {
			// get the date picker value
			LocalDate i = birthdayDatePicker.getValue();
				SqlUpdate.updateBirthday(i, person);
		};
		
		birthdayDatePicker.setOnAction(event);
		
		/////////////// SETTING CONTENT /////////////////////
		
		vboxMemberInfo.getChildren().addAll(hboxTitle, hboxFirstName, hboxLastName, hboxNickName, hboxOccupation, hboxBusiness, hboxBirthday);
		hboxMemberInfoAndPicture.getChildren().addAll(vboxMemberInfo,vboxPicture);
		hboxPictureFrame.getChildren().add(photo);
		vboxPicture.getChildren().add(hboxPictureFrame);
		fnameTextField.setText(person.getFname());
		lnameTextField.setText(person.getLname());
		businessTextField.setText(person.getBuisness());
		occupationTextField.setText(person.getOccupation());
		nnameTextField.setText(person.getNname());
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
		vbBuisnessLabel.getChildren().add(businessLabel);
		vbBirthdayLabel.getChildren().add(birthdayLabel);
		vbLnameBox.getChildren().add(lnameTextField);
		vbFnameBox.getChildren().add(fnameTextField);
		vbNnameBox.getChildren().add(nnameTextField);
		vbOccupationBox.getChildren().add(occupationTextField);
		vbBuisnessBox.getChildren().add(businessTextField);
		vbBirthdayBox.getChildren().add(birthdayDatePicker);
		hboxFirstName.getChildren().addAll(vbFnameLabel,vbFnameBox);
		hboxLastName.getChildren().addAll(vbLnameLabel,vbLnameBox);
		hboxNickName.getChildren().addAll(vbNnameLabel, vbNnameBox);
		hboxOccupation.getChildren().addAll(vbOccupationLabel,vbOccupationBox);
		hboxBusiness.getChildren().addAll(vbBuisnessLabel,vbBuisnessBox);
		hboxBirthday.getChildren().addAll(vbBirthdayLabel,vbBirthdayBox);
		vboxGrey.getChildren().addAll(hboxMemberInfoAndPicture, vboxInfoGrey);
		this.getChildren().add(vboxGrey);
	} // constructor end
	
	
	private ImageView getMemberPhoto() {
		Image memberPhoto = new Image(getClass().getResourceAsStream(HalyardPaths.DEFAULTPHOTO));
		return new ImageView(memberPhoto);
	}

	public PersonDTO getPerson() {
		return person;
	}

	public TabPersonProperties getPropertiesTab() {
		return propertiesTab;
	}
}  // class end
