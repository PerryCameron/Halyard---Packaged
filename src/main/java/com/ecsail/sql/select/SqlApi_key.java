package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.ApiKeyDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
