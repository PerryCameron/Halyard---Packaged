package com.ecsail.repository.rowmappers;

import com.ecsail.dto.Memo2DTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Memo2RowMapper implements RowMapper<Memo2DTO> {

    @Override
    public Memo2DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Memo2DTO(
                rs.getString("MEMBERSHIP_ID"),
                rs.getInt("MEMO_ID"),
                rs.getInt("MS_ID"),
                rs.getString("MEMO_DATE"),
                rs.getString("MEMO"),
                rs.getInt("INVOICE_ID"),
                rs.getString("CATEGORY"));
    }
}