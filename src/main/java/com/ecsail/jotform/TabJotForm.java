package com.ecsail.jotform;

import com.ecsail.sql.select.SqlApi_key;
import com.ecsail.jotform.structures.ApiKeyDTO;
import com.ecsail.jotform.structures.JotFormSubmissionListDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.util.ArrayList;


public class TabJotForm extends Tab {
	public TabJotForm(String text) {
		super(text);
		ApiKeyDTO thisApi = SqlApi_key.getApiKeyByName("Jotform API");
		JotForm client = new JotForm(thisApi.getKey());
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		ObservableList<JotFormSubmissionListDTO> list = FXCollections.observableArrayList();

		JotFormECSC jotFormECSC = new JotFormECSC();
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		vboxGrey.setPrefHeight(688);
		Button listButton = new Button("List");

		CustomViewForms customViewForms = new CustomViewForms();
//		TableViewNewMembership tableViewNewMembership = new TableViewNewMembership(list, client);
//		TableView<JotFormSubmissionListDTO> tableView;
//		Thread t = new Thread(() -> {
//			JSONObject submissions = client.getFormSubmissions(213494856046160L);
//			ArrayList<JSONObject> formSubmissions = jotFormECSC.addFormSubmissionsIntoArray(submissions);
//			for (JSONObject a : formSubmissions) {
//				list.add(jotFormECSC.addSubmissionAnswersIntoDTO(a));
//			}
//		});
//		t.start();
//		tableView = tableViewNewMembership.getContent();
		vboxGrey.getChildren().add(customViewForms);

		vboxGrey.getChildren().add(listButton);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
}
