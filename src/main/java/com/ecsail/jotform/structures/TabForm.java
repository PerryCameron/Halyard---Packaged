package com.ecsail.jotform.structures;

import com.ecsail.jotform.JotForm;
import com.ecsail.repository.implementations.AppSettingsRepositoryImpl;
import com.ecsail.repository.interfaces.AppSettingsRepository;
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

import java.util.HashMap;

public class TabForm extends Tab implements Builder {

    private AppSettingsRepository appSettingsRepository;
    private JotForm client;
    HashMap<String,String> hashMap = new HashMap<>();
    private JotFormsDTO jotFormsDTO;


    public TabForm(JotFormsDTO jotFormsDTO) {
        setText(jotFormsDTO.getId() + "");
        this.appSettingsRepository = new AppSettingsRepositoryImpl();
        this.client = new JotForm(appSettingsRepository.getApiKeyByName("Jotform API").getKey());
        this.jotFormsDTO = jotFormsDTO;

        getSubmissions();
        setContent(build());
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
//        vBox.setSpacing(10);
//        vBox.setPadding(new Insets(0, 0, 0, 10));
//        idLabel.setEditable(false); // Makes it non-editable
//        idLabel.setBorder(null); // Removes the border
//        idLabel.setStyle("-fx-background-color: transparent; -fx-focus-color: transparent; -fx-text-fill: white;"); // Makes the background transparent
//        setFormChangeListener();
//		vBox.getChildren().addAll(createTitle(), createId() ,statusLabel, createLink() ,createdLabel,updatedLabel,lastSubmissionLabel,
//				newLabel, favoriteLabel, archivedLabel,submissionsLabel, typeLabel);
//        vBox.setPrefWidth(340);
        return vBox;
    }



    private Node formList() {
        VBox vBox = new VBox();  // this is the vbox for organizing all the widgets
//        vBox.setStyle("-fx-background-color: #4d6955;");
//        HBox.setHgrow(vBox, Priority.ALWAYS);
//        vBox.setPrefHeight(688);
//        vBox.getChildren().add(new FormsTableView(this));
        return vBox;
    }


//    public JSONObject getFormSubmissions(long formID, String offset, String limit, HashMap<String, String> filter, String orderBy) {

        private ObservableList<JotFormsDTO> getSubmissions() {
        ObservableList<JotFormsDTO> formsDTOS = FXCollections.observableArrayList();
        hashMap.put("status","ACTIVE");
        JSONObject formSubmissions = client.getFormSubmissions(jotFormsDTO.getId(), "0", "350",hashMap,"created_at");

//        JsonNode neoJsonNode = Json.getJsonNodeFromJsonObject(folders);
//        List<String> keys = new ArrayList<>();
//        Iterator<String> iterator = neoJsonNode.get("content").get("forms").fieldNames();
//        iterator.forEachRemaining(keys::add);
//        keys.forEach(e -> formsDTOS.add(new JotFormsDTO(
//                Long.parseLong(neoJsonNode.get("content").get("forms").get(e).get("id").textValue()),
//                neoJsonNode.get("content").get("forms").get(e).get("username").textValue(),
//                neoJsonNode.get("content").get("forms").get(e).get("title").textValue(),
//                Integer.parseInt(neoJsonNode.get("content").get("forms").get(e).get("height").textValue()),
//                neoJsonNode.get("content").get("forms").get(e).get("status").textValue(),
//                neoJsonNode.get("content").get("forms").get(e).get("created_at").textValue(),
//                neoJsonNode.get("content").get("forms").get(e).get("updated_at").textValue(),
//                neoJsonNode.get("content").get("forms").get(e).get("last_submission").textValue(),
//                Integer.parseInt(neoJsonNode.get("content").get("forms").get(e).get("new").textValue()),
//                Integer.parseInt(neoJsonNode.get("content").get("forms").get(e).get("count").textValue()),
//                neoJsonNode.get("content").get("forms").get(e).get("type").textValue(),
//                intToBoolean(neoJsonNode.get("content").get("forms").get(e).get("favorite").textValue()),
//                Integer.parseInt(neoJsonNode.get("content").get("forms").get(e).get("archived").textValue()),
//                neoJsonNode.get("content").get("forms").get(e).get("url").textValue()
//        )));
        return formsDTOS;
    }

    private boolean intToBoolean(String number) {
        if (number.equals("0")) return false;
        return true;
    }

    public JotForm getClient() {
        return client;
    }
    
}
