package com.ecsail.repository.rowmappers;

import com.ecsail.dto.EmailDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailRowMapper implements RowMapper<EmailDTO> {
    @Override
    public EmailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EmailDTO(
                rs.getInt("EMAIL_ID"),
                rs.getInt("p_id"),
                rs.getBoolean("primary_use"),
                rs.getString("email"),
                rs.getBoolean("EMAIL_LISTED"));
    }
}
