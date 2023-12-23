package com.ecsail.repository.rowmappers;



import com.ecsail.dto.DbInvoiceDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbInvoiceRowMapper implements RowMapper<DbInvoiceDTO> {
    @Override
    public DbInvoiceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DbInvoiceDTO(
                rs.getInt("ID"),
                rs.getString("FISCAL_YEAR"),
                rs.getString("FIELD_NAME"),
                rs.getString("widget_type"),
                rs.getDouble("width"),
                rs.getInt("invoice_order"),
                rs.getBoolean("multiplied"),
                rs.getBoolean("price_editable"),
                rs.getBoolean("is_credit"),
                rs.getInt("max_qty"),
                rs.getBoolean("auto_populate"),
                rs.getBoolean("is_itemized")
        );
    }
}
