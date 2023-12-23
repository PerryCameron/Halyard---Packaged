package com.ecsail.repository.rowmappers;

import com.ecsail.jotform.structures.ApiKeyDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiKeyRowMapper implements RowMapper<ApiKeyDTO> {
    @Override
    public ApiKeyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ApiKeyDTO(
                rs.getInt("API_ID"),
                rs.getString("NAME"),
                rs.getString("APIKEY"),
                rs.getString("ts"));
    }
}