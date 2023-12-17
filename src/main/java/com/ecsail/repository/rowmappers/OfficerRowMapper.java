package com.ecsail.repository.rowmappers;



import com.ecsail.dto.OfficerDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficerRowMapper implements RowMapper<OfficerDTO> {
    @Override
    public OfficerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OfficerDTO(
                rs.getInt("O_ID"),
                rs.getInt("P_ID"),
                rs.getString("BOARD_YEAR"), // beginning of board term
                rs.getString("OFF_TYPE"),
                rs.getString("OFF_YEAR"));
    }
}
