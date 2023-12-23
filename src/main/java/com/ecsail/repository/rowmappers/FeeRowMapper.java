package com.ecsail.repository.rowmappers;


import com.ecsail.dto.FeeDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeeRowMapper implements RowMapper<FeeDTO> {

    @Override
    public FeeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FeeDTO(
                rs.getInt("FEE_ID"),
                rs.getString("FIELD_NAME"),
                rs.getString("FIELD_VALUE"),
                rs.getInt("DB_INVOICE_ID"),
                rs.getInt("fee_year"),
                rs.getString("DESCRIPTION"),
                rs.getBoolean("DEFAULT_FEE")
        );
    }
}