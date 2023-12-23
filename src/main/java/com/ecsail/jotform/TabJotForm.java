package com.ecsail.jotform;

import com.ecsail.jotform.structures.ApiKeyDTO;
import com.ecsail.jotform.structures.JotFormSubmissionListDTO;
import com.ecsail.repository.implementations.AppSettingsRepositoryImpl;
import com.ecsail.repository.interfaces.AppSettingsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabJotForm extends Tab {

	AppSettingsRepository appSettingsRepository = new AppSettingsRepositoryImpl();
	public TabJotForm(String text) {
		super(text);
		ApiKeyDTO thisApi = appSettingsRepository.getApiKeyByName("Jotform API");
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


		CustomViewForms customViewForms = new CustomViewForms(client);
		vboxGrey.getChildren().add(customViewForms);

		vboxGrey.getChildren().add(new Label("stuff here"));
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}


}
