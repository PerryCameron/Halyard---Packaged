package com.ecsail.jotform;


import com.ecsail.jotform.structures.JotFormsDTO;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomViewForms extends ScrollPane {

    public CustomViewForms(JotForm client) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        JSONObject folders = client.getFolders();
        JsonNode neoJsonNode = Json.getJsonNodeFromJsonObject(folders);
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = neoJsonNode.get("content").get("forms").fieldNames();
        iterator.forEachRemaining(keys::add);
        keys.forEach(e -> vBox.getChildren().add(new FormHbox(new JotFormsDTO(
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
        ))));
        setContent(vBox);
    }

    private boolean intToBoolean(String number) {
        if(number.equals("0") ) return false;
        return true;
    }
}
