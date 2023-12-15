package com.ecsail.repository.rowmappers;

import com.ecsail.dto.AppSettingsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppSettingsRowMapper implements RowMapper<AppSettingsDTO> {
    @Override
    public AppSettingsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AppSettingsDTO(
        rs.getString("setting_key"),
        rs.getString("setting_value"),
        rs.getString("description"),
        rs.getString("data_type"),
        rs.getTimestamp("updated_at"));
    }
}