package com.ecsail.repository.rowmappers;


import com.ecsail.dto.InvoiceItemDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceItemRowMapper implements RowMapper<InvoiceItemDTO> {

    @Override
    public InvoiceItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new InvoiceItemDTO(
                rs.getInt("ID"),
                rs.getInt("INVOICE_ID"),
                rs.getInt("MS_ID"),
                rs.getInt("FISCAL_YEAR"),
                rs.getString("FIELD_NAME"),
                rs.getBoolean("IS_CREDIT"),
                rs.getString("VALUE"),
                rs.getInt("QTY"));
    }
}