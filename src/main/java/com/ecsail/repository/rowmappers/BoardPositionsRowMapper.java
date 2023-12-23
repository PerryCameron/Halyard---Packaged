package com.ecsail.repository.rowmappers;

import com.ecsail.dto.BoardPositionDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardPositionsRowMapper implements RowMapper<BoardPositionDTO> {
    @Override
    public BoardPositionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BoardPositionDTO(
                rs.getInt("ID"),
                rs.getString("POSITION"),
                rs.getString("IDENTIFIER"), // beginning of board term
                rs.getInt("LIST_ORDER"),
                rs.getBoolean("IS_OFFICER"),
                rs.getBoolean("IS_CHAIR"),
                rs.getBoolean("IS_ASSISTANT_CHAIR")
        );
    }
}
