package com.ecsail.jotform;

import com.ecsail.jotform.structures.JotFormsDTO;
import com.ecsail.repository.implementations.AppSettingsRepositoryImpl;
import com.ecsail.repository.interfaces.AppSettingsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.json.JSONObject;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TabFormList extends Tab implements Builder {

    private TabPane tabPane;
    private AppSettingsRepository appSettingsRepository;
    private ObservableList<JotFormsDTO> jotFormsDTOS;
    private JotForm client;
    private ObjectProperty<JotFormsDTO> selectedForm = new SimpleObjectProperty<>();
    private TextField idLabel = new TextField("None");
    private Label titleLabel = new Label("None");
    private Label statusLabel = new Label("Status: None");
    private Label createdLabel = new Label("Created: None");
    private Label updatedLabel = new Label("Last Updated: None");
    private Label lastSubmissionLabel = new Label("Last Submission: None");
    private Label newLabel = new Label("New: None");
    private Label submissionsLabel = new Label("Submissions: None");
    private Label typeLabel = new Label("Type: None");
    private Label favoriteLabel = new Label("Favorite: None");
    private Label archivedLabel = new Label("Archived: None");
    private Label urlLabel = new Label("URL: ");
    private Hyperlink hyperlink = new Hyperlink("None");
    private String filter;

    public TabFormList(String text, TabPane tabPane) {
        super(text);
        this.appSettingsRepository = new AppSettingsRepositoryImpl();
        this.client = new JotForm(appSettingsRepository.getApiKeyByName("Jotform API").getKey());
        this.jotFormsDTOS = getForms();
        this.tabPane = tabPane;
        setContent(build());
    }

    private void updateLabels() {
        idLabel.setText(selectedForm.get().getId() + "");
        titleLabel.setText(selectedForm.get().getTitle());
        statusLabel.setText("Status: " + selectedForm.get().getStatus());
        createdLabel.setText("Created: " + selectedForm.get().getCreated_at());
        updatedLabel.setText("Last Updated: " + selectedForm.get().getUpdated_at());
        lastSubmissionLabel.setText("Last Submission: " + selectedForm.get().getLast_submission());
        newLabel.setText("New: " + selectedForm.get().getNewSubmission());
        submissionsLabel.setText("Submissions: " + selectedForm.get().getCount());
        typeLabel.setText("Type: " + selectedForm.get().getType());
        favoriteLabel.setText("Favorite: " + selectedForm.get().isFavorite());
        archivedLabel.setText("Archived: " + selectedForm.get().getArchived());
        hyperlink.setText(selectedForm.get().getUrl());
    }

    @Override
    public Node build() {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
        VBox.setVgrow(hBox, Priority.ALWAYS);
        hBox.getChildren().addAll(formList(), details());
        vBox.getChildren().add(hBox);
        return vBox;
    }

    private Node details() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(0, 0, 0, 10));
        idLabel.setEditable(false); // Makes it non-editable
        idLabel.setBorder(null); // Removes the border
        idLabel.setStyle("-fx-background-color: transparent; -fx-focus-color: transparent; -fx-text-fill: white;"); // Makes the background transparent
        setFormChangeListener();
		vBox.getChildren().addAll(createTitle(), createId() ,statusLabel, createLink() ,createdLabel,updatedLabel,lastSubmissionLabel,
				newLabel, favoriteLabel, archivedLabel,submissionsLabel, typeLabel, getControls());
        vBox.setPrefWidth(340);
        return vBox;
    }

    private Node getControls() {
        HBox hBox = new HBox();
        Label label = new Label("Display Results: ");
        hBox.setAlignment(Pos.CENTER_LEFT);
        String[] items = {"Active", "Archive", "Deleted", "All"};
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setValue("Active");
        comboBox.setOnAction(event -> {
            String selectedItem = comboBox.getValue();
            switch (selectedItem) {
                case "Archive" -> filter="ARCHIVED";
                case "All" -> filter="ALL";
                case "Active" -> filter="ACTIVE";
                case "Deleted" -> filter="DELETED";
            }
            System.out.println("Selected Item: " + selectedItem);
            // Add additional actions you want to perform when an item is selected
        });
        hBox.getChildren().addAll(label, comboBox);
        return hBox;
    }

    private Node createTitle() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setStyle("-fx-text-fill: #ffff13");
        hBox.getChildren().addAll(new Label("Title: "), titleLabel);
        return hBox;
    }

    private Node createId() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        idLabel.setEditable(false); // Makes it non-editable
        idLabel.setBorder(null); // Removes the border
        idLabel.setStyle("-fx-background-color: transparent; -fx-focus-color: transparent; -fx-text-fill: #aaf6aa;"); // Makes the background transparent
        hBox.getChildren().addAll(new Label("ID:"), idLabel);
        return hBox;
    }

    private Node createLink() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(urlLabel, hyperlink);
        hyperlink.setOnAction(event -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(hyperlink.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return hBox;
    }

    private void setFormChangeListener() {
        selectedForm.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateLabels();
            }
        });
    }

    private Node formList() {
        VBox vBox = new VBox();  // this is the vbox for organizing all the widgets
        vBox.setStyle("-fx-background-color: #4d6955;");
        HBox.setHgrow(vBox, Priority.ALWAYS);
        vBox.setPrefHeight(688);
        vBox.getChildren().add(new FormListTableView(this));
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
        if (number.equals("0")) return false;
        return true;
    }

    public ObservableList<JotFormsDTO> getJotFormsDTOS() {
        return jotFormsDTOS;
    }

    public JotFormsDTO getSelectedForm() {
        return selectedForm.get();
    }

    public ObjectProperty<JotFormsDTO> selectedFormProperty() {
        return selectedForm;
    }

    public void setSelectedForm(JotFormsDTO selectedForm) {
        this.selectedForm.set(selectedForm);
    }

    public JotForm getClient() {
        return client;
    }

    public TabPane getMainTabPane() {
        return tabPane;
    }

    public String getFilter() {
        return filter;
    }
}
