package com.ecsail.jotform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class Json {

    public static JsonNode getJsonNodeFromJsonObject(JSONObject jsonObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode neoJsonNode;
        try {
            return neoJsonNode = objectMapper.readTree(jsonObject.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode getJsonNodeFromString(String jsonObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode neoJsonNode;
        try {
            return neoJsonNode = objectMapper.readTree(jsonObject.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
