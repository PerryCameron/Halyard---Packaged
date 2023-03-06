package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.jotform.structures.ApiKeyDTO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlApi_key {
    public static ApiKeyDTO getApiKeyByName(String name) {  // if p_id = 0 then select all
        ApiKeyDTO thisApi = null;
        String query = "select * from api_key  where NAME='" + name + "'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisApi = new ApiKeyDTO(rs.getInt("API_ID"), rs.getString("NAME"), rs.getString("APIKEY"),
                        rs.getString("ts"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisApi;
    }

    public static ArrayList<ApiKeyDTO> getAPIKeys() {  //p_id
        ArrayList<ApiKeyDTO> thisApiKey = new ArrayList<>();
        String query = "SELECT * FROM api_key";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisApiKey.add(new ApiKeyDTO(
                        rs.getInt("API_ID"),
                        rs.getString("NAME"),
                        rs.getString("APIKEY"),
                        rs.getString("TS")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisApiKey;
    }
}
