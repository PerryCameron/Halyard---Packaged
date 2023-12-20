package com.ecsail.views.tabs.membership.people;
///////////BUG///////  adding a person as secondary changes the pid in the membership to the secondary

import com.ecsail.dto.MemoDTO;
import com.ecsail.enums.MemberType;
import com.ecsail.repository.implementations.PersonRepositoryImpl;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.dto.PersonDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VBoxAddPerson extends VBox implements Builder<VBox> {

	public static Logger logger = LoggerFactory.getLogger(VBoxAddPerson.class);
	private Boolean hasError = false;
	private final PersonRepository personRepository = new PersonRepositoryImpl();
	private final TabMembership parent;
	private final Map<String, TextField> textFieldMap;
	private final ObjectProperty<DatePicker> datePickerProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<ComboBox<MemberType>> comboBoxProperty = new SimpleObjectProperty<>();

	public VBoxAddPerson(TabMembership parent) {
		this.parent = parent;
		this.textFieldMap = new HashMap<>();
		setPrefWidth(460);
		setPadding(new Insets(5, 5, 5, 5));
		setId("custom-tap-pane-frame");
		getChildren().add(build());
	}

	@Override
	public VBox build() {
		VBox vboxGrey = new VBox();
		vboxGrey.setId("box-background-light");
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		vboxGrey.getChildren().add(hboxTitle());
		vboxGrey.getChildren().add(addTextField("First Name"));
		vboxGrey.getChildren().add(addTextField("Last Name"));
		vboxGrey.getChildren().add(addTextField("Occupation"));
		vboxGrey.getChildren().add(addTextField("Business"));
		vboxGrey.getChildren().add(addDatePicker());
		vboxGrey.getChildren().add(addComboBox());
		vboxGrey.getChildren().add(addButtonBox());
		return vboxGrey;
	}

	private Node addButtonBox() {
		HBox hBox = new HBox();
		Button addButton = new Button("Add");
		hBox.setAlignment(Pos.CENTER_RIGHT);
		hBox.setSpacing(25);
		hBox.setPadding(new Insets(5, 100, 5, 5));  // add button
		hBox.getChildren().addAll(addButton);
		addButton.setOnAction((event) -> {
			hasError = false;
			PersonDTO personDTO = personRepository.insertPerson(
					new PersonDTO(parent.getModel().getMembership().getMsId(),
					comboBoxProperty.get().getValue().getCode(),
					textFieldMap.get("First Name").getText(),
					textFieldMap.get("Last Name").getText(),
					getBirthday(datePickerProperty.get().getValue()),
					textFieldMap.get("Occupation").getText(),
					textFieldMap.get("Business").getText(),
					true));
			logNewPerson(personDTO);
			openNewPersonTab(personDTO);
			clearAddMemberBox();
		});
		return hBox;
	}

	private void logNewPerson(PersonDTO personDTO) {
		String memoToSave = "New person: " + personDTO.getNameWithInfo()
				+ " added as " + MemberType.getByCode(personDTO.getMemberType());
		logger.info(memoToSave);
		parent.getModel().getNote().addMemoAndReturnId(new MemoDTO(personDTO.getMsId(),memoToSave,"N"));
	}

	private void openNewPersonTab(PersonDTO personDTO) {
		String newMemberType = String.valueOf(MemberType.getByCode(personDTO.getMemberType()));
		Tab tab = new Tab(newMemberType, new HBoxPerson(personDTO, parent));
		parent.getModel().getPeopleTabPane().getTabs().add(tab);
		parent.getModel().getPeopleTabPane().getSelectionModel().select(tab);
		parent.getModel().getPeople().add(personDTO);
	}

	private void clearAddMemberBox() {
		textFieldMap.get("First Name").setText("");
		textFieldMap.get("Last Name").setText("");
		textFieldMap.get("Occupation").setText("");
		textFieldMap.get("Business").setText("");
		datePickerProperty.get().setValue(null);
		comboBoxProperty.get().setValue(MemberType.getByCode(3)); // sets to primary
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

	private Node addComboBox() {
		HBox hBox = new HBox(); // first name
		hBox.setPadding(new Insets(5, 15, 5, 60));  // first Name
		hBox.getChildren().addAll(addLabel("Type"), createComboBox());
		return hBox;
	}

	private Node addTextField(String label) {
		HBox hBox = new HBox(); // first name
		hBox.setPadding(new Insets(5, 15, 5, 60));  // first Name
		VBox vBox = new VBox();
		TextField textField = new TextField();
		textField.setPrefSize(240, 10);
		textFieldMap.put(label,textField);
		vBox.getChildren().add(textField);
		hBox.getChildren().addAll(addLabel(label), vBox);
		return hBox;
	}

	private Node addDatePicker() {
		HBox hBox = new HBox(); // first name
		VBox vBox = new VBox();
		hBox.setPadding(new Insets(5, 15, 5, 60));  // first Name
		DatePicker datePicker = new DatePicker();
		datePicker.setPrefSize(240, 10);
		vBox.getChildren().add(datePicker);
		datePickerProperty.set(datePicker);
		hBox.getChildren().addAll(addLabel("BirthDay"), vBox);
		return hBox;
	}

	private Node createComboBox() {
		VBox vBox = new VBox();
		ComboBox<MemberType> comboBox = new ComboBox<>();
		comboBox.getItems().setAll(MemberType.values());
		comboBox.setValue(MemberType.getByCode(1)); // sets to primary
		comboBoxProperty.set(comboBox);
		comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// This code will be executed whenever the selected item changes
			if (newValue != null) {
				System.out.println("Selected MemberType: " + newValue.getCode());
				// You can also use other properties or methods of the MemberType object
			}
		});
		vBox.getChildren().add(comboBox);
		return vBox;
	}



	private Node addLabel(String text) {
		VBox vBox = new VBox();
		Label label = new Label(text);
		vBox.setPrefWidth(80);
		vBox.setAlignment(Pos.CENTER_LEFT);
		vBox.getChildren().add(label);
		return vBox;
	}

	private Node hboxTitle() {
		HBox hBox = new HBox(); // Title
		Label titleLabel = new Label("Add New Member");
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(13);
		hBox.setPadding(new Insets(20, 15, 20, 15));  // first Name
		hBox.getChildren().addAll(titleLabel);
		return hBox;
	}
	
//	private void printErrorMessage(String message) {
//		titleLabel.setText(message);
//		titleLabel.setTextFill(Color.RED);
//		hasError = true;
//	}


}
