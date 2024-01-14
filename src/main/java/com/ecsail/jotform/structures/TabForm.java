package com.ecsail.jotform.structures;

import com.ecsail.dto.AppSettingsDTO;
import com.ecsail.jotform.FormTableView;
import com.ecsail.jotform.JotForm;
import com.ecsail.jotform.Json;
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

import java.util.HashMap;
import java.util.Optional;

public class TabForm extends Tab implements Builder {

    private AppSettingsRepository appSettingsRepository;
    private JotForm client;
    private HashMap<String,String> parameters = new HashMap<>();
    private JotFormsDTO jotFormsDTO;
    private ObservableList<FormInfoDTO> submissions;
    private FormInfoDTO mainFormInfo;
    public TabForm(JotFormsDTO jotFormsDTO) {
        this.appSettingsRepository = new AppSettingsRepositoryImpl();
        setText(jotFormsDTO.getId() + "");
        this.client = new JotForm(appSettingsRepository.getApiKeyByName("Jotform API").getKey());
        this.jotFormsDTO = jotFormsDTO;
        this.mainFormInfo = getProfile();
        if(mainFormInfo.hasProfile()) {
            this.submissions = getFormSubmissions();
            this.setText(appSettingsRepository.getSettingFromKey(jotFormsDTO.getId()+":3").getValue());
            submissions.forEach(System.out::println);
        }
        else System.out.println("This form has no profile");
        setContent(build());
    }

    private FormInfoDTO getProfile() {
        Optional<String> info1Optional = Optional.ofNullable(appSettingsRepository.getSettingFromKey(jotFormsDTO.getId()+":1"))
                .map(AppSettingsDTO::getValue);
        Optional<String> info2Optional = Optional.ofNullable(appSettingsRepository.getSettingFromKey(jotFormsDTO.getId()+":2"))
                .map(AppSettingsDTO::getValue);
        String info1 = info1Optional.orElse(""); // or any other default value
        String info2 = info2Optional.orElse(""); // or any other default value
        return new FormInfoDTO(jotFormsDTO.getId(), info1, info2);
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
        vBox.getChildren().add(new FormTableView(this));
        return vBox;
    }


//    public JSONObject getFormSubmissions(long formID, String offset, String limit, HashMap<String, String> filter, String orderBy) {


    private ObservableList<FormInfoDTO> getFormSubmissions() {
        ObservableList<FormInfoDTO> formsDTOS = FXCollections.observableArrayList();
        parameters.put("status", "ACTIVE");
        JSONObject formSubmissions = client.getFormSubmissions(jotFormsDTO.getId(), "0", "350", parameters, "created_at");
        JsonNode neoJsonNode = Json.getJsonNodeFromJsonObject(formSubmissions);
        String infoMap1[] = mainFormInfo.getInfo1().split(" ");
        String infoMap2[] = mainFormInfo.getInfo2().split(" ");

        if (neoJsonNode.has("content") && neoJsonNode.get("content").isArray()) {
            for (JsonNode submission : neoJsonNode.get("content")) {
                // will always be so
                Long formId = submission.has("form_id") ? submission.get("form_id").asLong() : null;
                // this will change depending on form - database tells what to look for depending on form
                String info1 = submission.has(infoMap1[0]) && submission.get(infoMap1[0]).has(infoMap1[1])
                        && submission.get(infoMap1[0]).get(infoMap1[1]).has(infoMap1[2])
                        ? submission.get(infoMap1[0]).get(infoMap1[1]).get(infoMap1[2]).asText() : "";
                // this will change depending on form - database tells what to look for depending on form
                String info2 = submission.has(infoMap2[0]) && submission.get(infoMap2[0]).has(infoMap2[1])
                        && submission.get(infoMap2[0]).get(infoMap2[1]).has(infoMap2[2])
                        ? submission.get(infoMap2[0]).get(infoMap2[1]).get(infoMap2[2]).asText() : "";
                FormInfoDTO formInfoDTO = new FormInfoDTO(formId, info1, info2);
                formsDTOS.add(formInfoDTO);
            }
        }

        return formsDTOS;
    }

    private boolean intToBoolean(String number) {
        if (number.equals("0")) return false;
        return true;
    }

    public JotForm getClient() {
        return client;
    }

    public ObservableList<FormInfoDTO> getSubmissions() {
        return submissions;
    }
}
