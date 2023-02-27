package com.ecsail.repository.rowmappers;

import com.ecsail.dto.InvoiceDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceRowMapper implements RowMapper<InvoiceDTO> {

    @Override
    public InvoiceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new InvoiceDTO(
                rs.getInt("ID"),
                rs.getInt("MS_ID"),
                rs.getInt("FISCAL_YEAR"),
                rs.getString("PAID"),
                rs.getString("TOTAL"),
                rs.getString("CREDIT"),
                rs.getString("BALANCE"),
                rs.getInt("BATCH"),
                rs.getBoolean("COMMITTED"),
                rs.getBoolean("CLOSED"),
                rs.getBoolean("SUPPLEMENTAL"),
                rs.getString("MAX_CREDIT"));
    }
}