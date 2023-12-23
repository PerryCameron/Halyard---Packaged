package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.BoardDTO;
import com.ecsail.dto.BoardPositionDTO;
import com.ecsail.repository.interfaces.BoardPositionsRepository;
import com.ecsail.repository.rowmappers.BoardPositionsRowMapper;
import com.ecsail.repository.rowmappers.BoardRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BoardPositionsRepositoryImpl implements BoardPositionsRepository {

    public static Logger logger = LoggerFactory.getLogger(BoardPositionsRepository.class);
    private final JdbcTemplate template;
    public BoardPositionsRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<BoardPositionDTO> getPositions() {
        String query = "SELECT * FROM board_positions";
        return template.query(query,new BoardPositionsRowMapper());
    }
    @Override
    public List<BoardDTO> getBoard(String currentYear) {
        String query = """
            SELECT p.p_id, p.ms_id, o.o_id, p.f_name, p.l_name, o.off_year, o.board_year, o.off_type
            FROM person p
            INNER JOIN officer o ON p.p_id = o.p_id
            WHERE o.off_year = ?
            """;
        try {
            return template.query(query, new Object[]{currentYear}, new BoardRowMapper());
        } catch (Exception e) {
            logger.error("Unable to retrieve information", e);
            return List.of(); // Return an empty list in case of failure
        }
    }

    @Override
    public String getByIdentifier(String code) {
        return null;
    }

    @Override
    public String getByName(String name) {
        return null;
    }
}
