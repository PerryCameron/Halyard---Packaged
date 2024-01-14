package com.ecsail.jotform.structures;

import com.ecsail.dto.AppSettingsDTO;
import com.ecsail.jotform.FormTableView;
import com.ecsail.jotform.JotForm;
import com.ecsail.jotform.Json;
import com.ecsail.repository.implementations.AppSettingsRepositoryImpl;
import com.ecsail.repository.interfaces.AppSettingsRepository;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Optional;

public class TabForm extends Tab implements Builder {

    public static Logger logger = LoggerFactory.getLogger(TabForm.class);
    private AppSettingsRepository appSettingsRepository;
    private JotForm client;
    private HashMap<String,String> parameters = new HashMap<>();
    private JotFormsDTO jotFormsDTO;
    private ObservableList<FormInfoDTO> submissions;
    private FormInfoDTO mainFormInfo;
    private JsonNode neoJsonNode;
    private HashMap<String, String> formHash = new HashMap();
    private VBox detailsVBox;

    public TabForm(JotFormsDTO jotFormsDTO) {
        this.appSettingsRepository = new AppSettingsRepositoryImpl();
        setText(jotFormsDTO.getId() + "");
        this.client = new JotForm(appSettingsRepository.getApiKeyByName("Jotform API").getKey());
        this.jotFormsDTO = jotFormsDTO;
        this.mainFormInfo = getProfile();
        if(mainFormInfo.hasProfile()) {
            this.submissions = getFormSubmissions();
            this.setText(appSettingsRepository.getSettingFromKey(jotFormsDTO.getId()+":3").getValue());
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
        this.detailsVBox = new VBox();
        detailsVBox.setPadding(new Insets(0,0,0,15));
        detailsVBox.getChildren().add(new Label("Testing"));
        return detailsVBox;
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
        this.neoJsonNode = Json.getJsonNodeFromJsonObject(formSubmissions);
        String infoMap1[] = mainFormInfo.getInfo1().split(" ");
        String infoMap2[] = mainFormInfo.getInfo2().split(" ");
        if (neoJsonNode.has("content") && neoJsonNode.get("content").isArray()) {
            for (JsonNode submission : neoJsonNode.get("content")) {
                // will always be so
                Long formId = submission.has("id") ? submission.get("id").asLong() : null;
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

    public Node fillFormHash(Long id) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        if (neoJsonNode == null || !neoJsonNode.has("content") || !neoJsonNode.get("content").isArray()) {
            vBox.getChildren().add(new Label("There is no content to display"));
            return vBox;
        }
        for (JsonNode form : neoJsonNode.get("content")) {
            // Check if this is the form with the given ID
            if (form.has("id") && form.get("id").asText().equals(Long.toString(id))) {
                JsonNode answers = form.get("answers");
                 // display form information
                if (answers != null) {
                    answers.fields().forEachRemaining(entry -> {
                        JsonNode value = entry.getValue();
                        if(value.has("prettyFormat")) {
                            vBox.getChildren().add(coloredHBox(value.get("name").asText() + ": ", value.get("prettyFormat").asText(),"#d7f8fa"));
                        }
                        else if (value.has("answer")) {
                            if(isImageLink(value.get("answer").asText()))
                                vBox.getChildren().add(imageBox(value.get("name").asText(), value.get("answer").asText()));
                            else
                            vBox.getChildren().add(coloredHBox(value.get("name").asText() + ": ", value.get("answer").asText(),"#d7f8fa"));
                        }
                        else if (value.get("name").asText().equals("heading")) {
                            vBox.getChildren().add(coloredHBox("Title: ", value.get("text").asText(),"#ffff13"));
                            vBox.getChildren().add(coloredHBox("Created: ", form.get("created_at").asText(),"gray"));
                            if(form.get("status").asText().equals("ACTIVE"))
                            vBox.getChildren().add(coloredHBox("Status: ", form.get("status").asText(),"green"));
                        }
                    });
                }
                break; // Break after processing the correct form
            }
        }
        return vBox;
    }

    private Node imageBox(String title, String url) {
        logger.info("Getting image: " + url);
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");
        vBox.setSpacing(10); // Set spacing between elements in the VBox
        vBox.getChildren().add(new Label(title));
        // Create an ImageView
        ImageView imageView = new ImageView();

        // Load image from URL
        try {
            Image image = new Image(url, false); // The second argument is for background loading
            imageView.setImage(image);
            imageView.setPreserveRatio(true); // Preserve the aspect ratio

            vBox.getChildren().add(imageView); // Add ImageView to VBox
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            // Handle error (e.g., image not found or invalid URL)
        }

        return vBox;
    }


    public boolean isImageLink(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        String imagePattern = "^(https?://.*\\.(?:png|jpg|jpeg|gif|bmp))$";
        return url.matches(imagePattern);
    }


    private Node coloredHBox(String key, String value, String color) {
        HBox hBox = new HBox();
        Label keyLabel = new Label(key);
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-text-fill: " + color);
        hBox.getChildren().addAll(keyLabel, valueLabel);
        return hBox;
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

    public VBox getDetailsVBox() {
        return detailsVBox;
    }

    public static Logger getLogger() {
        return logger;
    }
}
