package com.ecsail.repository.rowmappers;

import com.ecsail.dto.DepositTotalDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepositTotalRowMapper implements RowMapper<DepositTotalDTO> {

    @Override
    public DepositTotalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DepositTotalDTO(
                rs.getString("TOTAL"),
                rs.getString("CREDIT"),
                rs.getString("PAID"));
    }
}