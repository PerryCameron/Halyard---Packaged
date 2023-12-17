package com.ecsail.repository.rowmappers;

import com.ecsail.dto.Email_InformationDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailInformationRowMapper implements RowMapper<Email_InformationDTO> {
    @Override
    public Email_InformationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Email_InformationDTO(
                rs.getInt("membership_id"),
                rs.getString("join_date"),
                rs.getString("l_name"),
                rs.getString("f_name"),
                rs.getString("email"),
                rs.getBoolean("primary_use")
        );
    }
}
