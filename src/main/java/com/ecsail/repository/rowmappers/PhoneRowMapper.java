package com.ecsail.repository.rowmappers;



import com.ecsail.dto.PhoneDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneRowMapper implements RowMapper<PhoneDTO> {

    @Override
    public PhoneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PhoneDTO(
                rs.getInt("PHONE_ID"),
                rs.getInt("p_id"),
                rs.getBoolean("phone_listed"),
                rs.getString("PHONE"),
                rs.getString("phone_type"));
    }
}