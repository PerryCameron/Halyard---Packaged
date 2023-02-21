package com.ecsail.gui.tabs.people;

import com.ecsail.dto.PersonDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HBoxSearch extends HBox {
	public static TableView<PersonDTO> personTableView;
	public HBoxSearch(TableView<PersonDTO> ptv) {
		HBoxSearch.personTableView = ptv;

		var vbox1 = new VBox(); // holds email buttons
		var hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
		var vboxPink = new VBox(); // this creates a pink border around the table
		var searchRecords = new Button("Search");
		var searchBox = new TextField();
		
		hboxGrey.setPrefWidth(463);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vbox1.setSpacing(5);
		hboxGrey.setId("box-background-light");
		vboxPink.setId("box-pink");
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		setId("box-frame-dark");
		setPadding(new Insets(5, 5, 5, 5));
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		
		searchRecords.setOnAction(e -> TabPeople.searchLastName(searchBox.getText().toLowerCase()));
		
		searchBox.setOnKeyPressed(ke -> {
			if (ke.getCode().equals(KeyCode.ENTER))
			{
				TabPeople.searchLastName(searchBox.getText());
			}
		});
	    
	    hboxGrey.getChildren().addAll(searchBox,searchRecords);
		getChildren().addAll(hboxGrey);
	}
}
