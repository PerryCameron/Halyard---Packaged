package com.ecsail.repository.rowmappers;

import com.ecsail.dto.BoardDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardRowMapper implements RowMapper<BoardDTO> {
    @Override
    public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BoardDTO(
                rs.getInt("p_id"),
                rs.getInt("ms_id"),
                rs.getInt("o_id"),
                rs.getString("f_name"),
                rs.getString("l_name"),
                rs.getString("off_year"),
                rs.getString("board_year"),
                rs.getString("off_type")
        );
    }
}
