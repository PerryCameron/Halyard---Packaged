package com.ecsail.jotform;

import com.ecsail.BaseApplication;
import com.ecsail.structures.jotform.JotFormSubmissionListDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JotFormECSC {

    public JotFormECSC() {
    }

    public boolean getBool(String bool) {
        if(bool.equals("0")) return false;
        return true;
    }

    public JotFormSubmissionListDTO addSubmissionAnswersIntoDTO(JSONObject formSubmission) {
        JotFormSubmissionListDTO jotFormSubmissionDTO = new JotFormSubmissionListDTO(
                getBool(String.valueOf(formSubmission.get("new"))),
                getBool(String.valueOf(formSubmission.get("flag"))),
                String.valueOf(formSubmission.get("notes")),
                String.valueOf(formSubmission.get("created_at")),
                String.valueOf(formSubmission.get("ip")),
                Long.parseLong((String) formSubmission.get("form_id")),
                String.valueOf(formSubmission.get("created_at")),
                Long.parseLong((String) formSubmission.get("id")),
                String.valueOf(formSubmission.get("status")),
                "","","","","","","","");
        JSONObject answerObject = (JSONObject) formSubmission.get("answers");

//        answerObject.keySet().stream()
//                .map(key ->  answerObject.get((String) key))
//                .filter(jsonObj -> jsonObj.keySet).forEach(System.out::println);



        boolean hasAnswer = false;
        for (Object key : answerObject.keySet()) {
            JSONObject x = (JSONObject) answerObject.get((String) key);
            for (Object xkey : x.keySet()) {
                if (xkey.equals("answer"))
                    hasAnswer = true;
            }
            if (hasAnswer) {
                if(x.get("name").equals("membershipType")) {
                    jotFormSubmissionDTO.setMemType(String.valueOf(x.get("answer")).trim());
                } else if(x.get("name").equals("primaryMember")) {
                    JSONObject name = (JSONObject) x.get("answer");
                    jotFormSubmissionDTO.setPrimaryFirstName(String.valueOf(name.get("first")).trim());
                    jotFormSubmissionDTO.setPrimaryLastName(String.valueOf(name.get("last")).trim());
                } else if (x.get("name").equals("address")) {
                    JSONObject address = (JSONObject) x.get("answer");
                    jotFormSubmissionDTO.setAddress(String.valueOf(address.get("addr_line1")).trim());
                    jotFormSubmissionDTO.setCity(String.valueOf(address.get("city")).trim());
                    jotFormSubmissionDTO.setState(String.valueOf(address.get("state")).trim());
                    jotFormSubmissionDTO.setPostal(String.valueOf(address.get("postal")).trim());
                }
                hasAnswer = false;
            }
        }
        return jotFormSubmissionDTO;
    }

    public ArrayList<JSONObject> addFormSubmissionsIntoArray(JSONObject submissions) {
        ArrayList<JSONObject> formSubmissions = new ArrayList<>();
        JSONArray content = (JSONArray) submissions.get("content");
//        BaseApplication.logger.info("JSONArray content= " + submissions.get("content"));
        for(int i =0; i < content.length(); i++) {
            formSubmissions.add((JSONObject) content.get(i));
        }
        return formSubmissions;
    }
}
