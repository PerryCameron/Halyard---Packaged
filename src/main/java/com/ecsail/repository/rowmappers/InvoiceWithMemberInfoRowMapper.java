package com.ecsail.repository.rowmappers;

import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceWithMemberInfoRowMapper implements RowMapper<InvoiceWithMemberInfoDTO> {

    @Override
    public InvoiceWithMemberInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new InvoiceWithMemberInfoDTO(
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
                rs.getString("MAX_CREDIT"),
                rs.getInt("MEMBERSHIP_ID"),
                rs.getString("F_NAME"),
                rs.getString("L_NAME"));
    }
}