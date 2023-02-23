package com.ecsail.repository.rowmappers;

import com.ecsail.dto.DbBoatSettingsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbBoatSettingsRowMapper implements RowMapper<DbBoatSettingsDTO> {

    @Override
    public DbBoatSettingsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DbBoatSettingsDTO boatSettingsDTO = new DbBoatSettingsDTO(
                rs.getInt("ID"),
                rs.getString("name"),
                rs.getString("control_type"),
                rs.getString("data_type"),
                rs.getString("field_name"),
                rs.getString("getter"),
                rs.getBoolean("searchable"),
                rs.getBoolean("exportable")
        );
        return boatSettingsDTO;
    }
}