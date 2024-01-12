package com.ecsail.jotform;

import com.ecsail.jotform.structures.JotFormsDTO;
import com.ecsail.repository.implementations.AppSettingsRepositoryImpl;
import com.ecsail.repository.interfaces.AppSettingsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TabJotForm extends Tab implements Builder {

	AppSettingsRepository appSettingsRepository;
	ObservableList<JotFormsDTO> jotFormsDTOS;
	JotForm client;
	public TabJotForm(String text) {
		super(text);
		this.appSettingsRepository = new AppSettingsRepositoryImpl();
		this.client = new JotForm(appSettingsRepository.getApiKeyByName("Jotform API").getKey());
		this.jotFormsDTOS = getForms();
		setContent(build());
	}

	@Override
	public Node build() {
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		vBox.setPadding(new Insets(10,10,10,10));
		hBox.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		VBox.setVgrow(hBox, Priority.ALWAYS);
		hBox.getChildren().addAll(leftDisplay(), rightDisplay());
		vBox.getChildren().add(hBox);
		return vBox;
	}

	private Node leftDisplay() {
		VBox vBox = new VBox();
		vBox.setPrefWidth(200);
		return vBox;
	}

	private Node rightDisplay() {
		VBox vBox = new VBox();  // this is the vbox for organizing all the widgets
		vBox.setStyle("-fx-background-color: #4d6955;");
		HBox.setHgrow(vBox, Priority.ALWAYS);
		vBox.setPrefHeight(688);
		vBox.getChildren().add(new FormsTableView(this));
		return vBox;
	}

	private ObservableList<JotFormsDTO> getForms() {
		ObservableList<JotFormsDTO> formsDTOS = FXCollections.observableArrayList();
		JSONObject folders = client.getFolders();
		JsonNode neoJsonNode = Json.getJsonNodeFromJsonObject(folders);
		List<String> keys = new ArrayList<>();
		Iterator<String> iterator = neoJsonNode.get("content").get("forms").fieldNames();
		iterator.forEachRemaining(keys::add);
		keys.forEach(e -> formsDTOS.add(new JotFormsDTO(
				Long.parseLong(neoJsonNode.get("content").get("forms").get(e).get("id").textValue()),
				neoJsonNode.get("content").get("forms").get(e).get("username").textValue(),
				neoJsonNode.get("content").get("forms").get(e).get("title").textValue(),
				Integer.parseInt(neoJsonNode.get("content").get("forms").get(e).get("height").textValue()),
				neoJsonNode.get("content").get("forms").get(e).get("status").textValue(),
				neoJsonNode.get("content").get("forms").get(e).get("created_at").textValue(),
				neoJsonNode.get("content").get("forms").get(e).get("updated_at").textValue(),
				neoJsonNode.get("content").get("forms").get(e).get("last_submission").textValue(),
				Integer.parseInt(neoJsonNode.get("content").get("forms").get(e).get("new").textValue()),
				Integer.parseInt(neoJsonNode.get("content").get("forms").get(e).get("count").textValue()),
				neoJsonNode.get("content").get("forms").get(e).get("type").textValue(),
				intToBoolean(neoJsonNode.get("content").get("forms").get(e).get("favorite").textValue()),
				Integer.parseInt(neoJsonNode.get("content").get("forms").get(e).get("archived").textValue()),
				neoJsonNode.get("content").get("forms").get(e).get("url").textValue()
		)));
		return formsDTOS;
	}

	private boolean intToBoolean(String number) {
		if(number.equals("0") ) return false;
		return true;
	}

	public ObservableList<JotFormsDTO> getJotFormsDTOS() {
		return jotFormsDTOS;
	}
}
