package com.ecsail.repository.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ecsail.dto.SlipStructureDTO;
import org.springframework.jdbc.core.RowMapper;


public class SlipStructureRowMapper implements RowMapper<SlipStructureDTO> {

    @Override
    public SlipStructureDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SlipStructureDTO(
                rs.getInt("id"),
                rs.getString("dock"),
                rs.getInt("dock_section"),
                rs.getString("slip1"),
                rs.getString("slip2"),
                rs.getString("slip3"),
                rs.getString("slip4"));
    }
}
