package com.ecsail.repository.rowmappers;

import com.ecsail.dto.WaitListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WaitListRowMapper implements RowMapper<WaitListDTO> {

    @Override
    public WaitListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WaitListDTO(
                rs.getInt("MS_ID"),
                rs.getBoolean("SLIP_WAIT"),
                rs.getBoolean("KAYAK_RACK_WAIT"),
                rs.getBoolean("SHED_WAIT"),
                rs.getBoolean("WANT_SUBLEASE"),
                rs.getBoolean("WANT_RELEASE"),
                rs.getBoolean("WANT_SLIP_CHANGE"));
    }
}