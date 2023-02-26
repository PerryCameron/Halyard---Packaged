package com.ecsail.repository.rowmappers;

import com.ecsail.dto.MemoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemoRowMapper implements RowMapper<MemoDTO> {

    @Override
    public MemoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MemoDTO(
                rs.getInt("MEMO_ID"),
                rs.getInt("MS_ID"),
                rs.getString("MEMO_DATE"),
                rs.getString("MEMO"),
                rs.getInt("INVOICE_ID"),
                rs.getString("CATEGORY"),
                rs.getInt("boat_id"));
    }
}