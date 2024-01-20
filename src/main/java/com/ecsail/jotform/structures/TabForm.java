package com.ecsail.jotform.structures;

import com.ecsail.jotform.FormTableView;
import com.ecsail.jotform.JotForm;
import com.ecsail.jotform.Json;
import com.ecsail.jotform.structures.submissions.AnswerBlockPOJO;
import com.ecsail.jotform.structures.submissions.ContentPOJO;
import com.ecsail.jotform.structures.submissions.FormSubmissionsPOJO;
import com.ecsail.repository.implementations.AppSettingsRepositoryImpl;
import com.ecsail.repository.implementations.SettingsRepositoryImpl;
import com.ecsail.repository.interfaces.AppSettingsRepository;
import com.ecsail.repository.interfaces.SettingsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TabForm extends Tab implements Builder {

    public static Logger logger = LoggerFactory.getLogger(TabForm.class);
    private AppSettingsRepository appSettingsRepository;
    private SettingsRepository settingsRepository;
    private JotForm client;
    private HashMap<String,String> parameters = new HashMap<>();
    private JotFormsDTO jotFormsDTO;
    private ObservableList<AnswerBlockPOJO> submissionNames = FXCollections.observableArrayList();
    private FormInfoDTO mainFormInfo;
    private ScrollPane detailsScrollPane;
    private VBox vBox;
    private ArrayList<JotFormSettingsDTO> jotFormSettingsDTOS;
    private FormSubmissionsPOJO formSubmissionsPOJO;


    public TabForm(JotFormsDTO jotFormsDTO) {
        this.appSettingsRepository = new AppSettingsRepositoryImpl();
        this.settingsRepository = new SettingsRepositoryImpl();
        this.jotFormsDTO = jotFormsDTO;  // this is DTO that hold general info for A form
        this.jotFormSettingsDTOS = (ArrayList<JotFormSettingsDTO>) settingsRepository.getJotFormSettings(jotFormsDTO.getId());  // setting for each choice in form
        this.client = new JotForm(appSettingsRepository.getApiKeyByName("Jotform API").getKey());
        this.formSubmissionsPOJO = getFormSubmissionsPOJO();
        this.setText(getTabText());
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
        this.detailsScrollPane = new ScrollPane();
        this.vBox = new VBox();
        vBox.setSpacing(10);
        detailsScrollPane.setContent(vBox);
        detailsScrollPane.setPadding(new Insets(0,0,0,15));
        return detailsScrollPane;
    }

    private Node formList() {
        VBox vBox = new VBox();  // this is the vbox for organizing all the widgets
        vBox.setPrefWidth(250);
        vBox.setMinWidth(250);
        vBox.getChildren().add(new FormTableView(this));
        return vBox;
    }

    private FormSubmissionsPOJO getFormSubmissionsPOJO() {
        FormSubmissionsPOJO formSubmissionsPOJO = new FormSubmissionsPOJO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        parameters.put("status", "ACTIVE");
        JSONObject formSubmissions = client.getFormSubmissions(jotFormsDTO.getId(), "0", "350", parameters, "created_at");
        JsonNode neoJsonNode = Json.getJsonNodeFromJsonObject(formSubmissions);
        if(neoJsonNode.has("responseCode"))  formSubmissionsPOJO.setResponseCode(neoJsonNode.get("responseCode").asInt());
        if(neoJsonNode.has("message"))  formSubmissionsPOJO.setMessage(neoJsonNode.get("message").asText());
        if (neoJsonNode.has("content") && neoJsonNode.get("content").isArray()) {
            for (JsonNode submission : neoJsonNode.get("content")) {
                if(submission.has("updated_at")) System.out.println(submission.get("updated_at"));
                HashMap<Integer, AnswerBlockPOJO>  hashMap= new HashMap<>();
                ContentPOJO contentPOJO = new ContentPOJO();
                contentPOJO.setAnswers(hashMap);
                formSubmissionsPOJO.getContent().add(contentPOJO);
                if(submission.has("id") && submission.get("id") != null)
                    contentPOJO.setId(submission.get("id").asLong());
                if(submission.has("form_id") && submission.get("form_id") != null)
                    contentPOJO.setFormId(submission.get("form_id").asLong());
                if(submission.has("ip") && submission.get("ip") != null)
                    contentPOJO.setIp(submission.get("ip").asText());
                if(submission.has("created_at") && submission.get("created_at") != null)
                    contentPOJO.setCreatedAt(LocalDateTime.parse(submission.get("created_at").asText(), formatter));
                if(submission.has("status") && submission.get("status") != null)
                    contentPOJO.setStatus(submission.get("status").asText());
                if(submission.has("new") && submission.get("new") != null)
                    contentPOJO.setNewForm(submission.get("new").asBoolean());
                if(submission.has("flag") && submission.get("flag") != null)
                    contentPOJO.setFlag(submission.get("flag").asBoolean());
                if(submission.has("notes") && submission.get("notes") != null)
                    contentPOJO.setNotes(submission.get("notes").asText());
                if(submission.has("updated_at") && submission.get("updated_at").asText() != null)
                    contentPOJO.setUpdatedAt(LocalDateTime.parse("2022-08-26 18:59:41", formatter));
//                LocalDateTime.parse(submission.get("updated_at").asText()
                JsonNode answers = submission.get("answers");
                if (answers != null) {
                    // group of answers
                    answers.fields().forEachRemaining(answerBlock -> {
                        AnswerBlockPOJO answerBlockPOJO = new AnswerBlockPOJO(contentPOJO);
                        answerBlock.getValue().fields().forEachRemaining(answer -> {
                            if(answer.getKey().equals("name"))
                                answerBlockPOJO.setName(answer.getValue().asText());
                            if(answer.getKey().equals("order"))
                                answerBlockPOJO.setOrder(answer.getValue().asInt());
                            if(answer.getKey().equals("text"))
                                answerBlockPOJO.setText(answer.getValue().asText());
                            if(answer.getKey().equals("type"))
                                answerBlockPOJO.setType(answer.getValue().asText());
                            if(answer.getKey().equals("answer"))
                                answerBlockPOJO.setAnswer(answer.getValue().asText());
                            if(answer.getKey().equals("prettyFormat"))
                                answerBlockPOJO.setPrettyFormat(answer.getValue().asText());
                        });
                        hashMap.put(Integer.valueOf(answerBlock.getKey()), answerBlockPOJO);
                        if(answerBlock.getKey().equals("3")) submissionNames.add(answerBlockPOJO);
                    });
                }
            }
        }
        formSubmissions.clear();
        return formSubmissionsPOJO;
    }

    private JotFormSettingsDTO getSetting(int order) {
        return jotFormSettingsDTOS.stream().filter(setting -> setting.getAnswerOrder() == order).findFirst().orElse(null);
    }

    private String getTabText() {
        JotFormSettingsDTO js = jotFormSettingsDTOS.stream().filter(setting ->
                        setting.getAnswerType().equals("tab_text"))
                .findFirst().orElse(null);
        if(js == null) return "";
        else return js.getAnswerText();
    }

    public void fillForm(long id) {
        jotFormSettingsDTOS.sort(Comparator.comparingInt(JotFormSettingsDTO::getAnswerOrder));
        ContentPOJO content = formSubmissionsPOJO.getContent().stream().filter(contentPOJO -> contentPOJO.getId() == id).findFirst().orElse(null);
        jotFormSettingsDTOS.stream().forEach(setting -> {
            int answerKey = 0;
            if(setting.getAnswerOrder() != 0)
            answerKey = setting.getAnswerNumber();
            if(answerKey != 0)
            printValue(content.getAnswers().get(answerKey), setting);
        });
    }

    private void printValue(AnswerBlockPOJO answerBlock, JotFormSettingsDTO setting) {
        switch (setting.getAnswerType()) {
            case "control_head" ->
                    vBox.getChildren().add(coloredHBox("Title: ", answerBlock.getText(),"#ffff13"));
            case "control_fullname" ->
                    vBox.getChildren().add(coloredHBox(answerBlock.getText() + ": ", answerBlock.getPrettyFormat(),"#d7f8fa"));
            case "control_textbox" ->
                vBox.getChildren().add(coloredHBox(answerBlock.getText() + ": ", answerBlock.getAnswer(),"#d7f8fa"));
            case "control_signature" -> vBox.getChildren().add(imageBox(answerBlock.getText(), answerBlock.getAnswer()));
        }
    }

    public String convertStringToBoolean(String input) {
        if ("1".equals(input)) {
            return "true";
        } else if ("0".equals(input)) {
            return "false";
        } else {
            logger.error("Input must be \"1\" or \"0\"");
            return "unknown";
        }
    }

    private Node imageBox(String title, String url) {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");
        vBox.setSpacing(10); // Set spacing between elements in the VBox
        vBox.getChildren().add(new Label(title));
        // Create an ImageView
        ImageView imageView = new ImageView();
        // Load image from URL
        try {
            Image image = new Image(url, true); // The second argument is for background loading
            imageView.setImage(image);
            imageView.setPreserveRatio(true); // Preserve the aspect ratio

            vBox.getChildren().add(imageView); // Add ImageView to VBox
        } catch (Exception e) {
            logger.error("Error loading image: " + e.getMessage());
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

    public ObservableList<AnswerBlockPOJO> getSubmissionNames() {
        return submissionNames;
    }

    public ScrollPane getDetailsScrollPane() {
        return detailsScrollPane;
    }

    public static Logger getLogger() {
        return logger;
    }

    public VBox getvBox() {
        return vBox;
    }
}
