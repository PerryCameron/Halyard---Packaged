package com.ecsail.repository.rowmappers;

import com.ecsail.dto.DepositDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepositRowMapper implements RowMapper<DepositDTO> {

    @Override
    public DepositDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DepositDTO(
                rs.getInt("DEPOSIT_ID"),
                rs.getString("DEPOSIT_DATE"),
                rs.getString("fiscal_year"),
                rs.getInt("batch"));
    }
}