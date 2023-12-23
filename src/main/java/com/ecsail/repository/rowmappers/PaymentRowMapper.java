package com.ecsail.repository.rowmappers;



import com.ecsail.dto.PaymentDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRowMapper implements RowMapper<PaymentDTO> {
    @Override
    public PaymentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PaymentDTO(
                rs.getInt("pay_id"),
                rs.getInt("INVOICE_ID"),
                rs.getString("CHECK_NUMBER"),
                rs.getString("PAYMENT_TYPE"),
                rs.getString("PAYMENT_DATE"),
                rs.getString("AMOUNT"),
                rs.getInt("DEPOSIT_ID")
        );
    }
}
