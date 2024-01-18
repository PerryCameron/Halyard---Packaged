package com.ecsail.repository.rowmappers;

import com.ecsail.jotform.structures.JotFormSettingsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JotFormsSettingsRowMapper implements RowMapper<JotFormSettingsDTO> {


    @Override
    public JotFormSettingsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new JotFormSettingsDTO(
                rs.getLong("jot_id"),
                rs.getLong("form_number"),
                rs.getInt("answer_number"),
                rs.getString("answer_location"),
                rs.getString("answer_text"),
                rs.getString("answer_type"),
                rs.getString("answer_name"),
                rs.getInt("answer_order"),
                rs.getInt("group_order"),
                rs.getString("sub_labels"),
                rs.getString("data_type"),
                rs.getDate("updated_at"));
    }
}
