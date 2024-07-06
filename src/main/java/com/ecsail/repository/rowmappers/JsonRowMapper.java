package com.ecsail.repository.rowmappers;


import com.ecsail.dto.JsonDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonRowMapper implements RowMapper<JsonDTO> {
    @Override
    public JsonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new JsonDTO(rs.getString("membership_info"));
    }
}
