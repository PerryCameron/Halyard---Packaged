package com.ecsail.repository.rowmappers;

import com.ecsail.dto.BoatListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoatListRowMapper implements RowMapper<BoatListDTO> {

    @Override
    public BoatListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BoatListDTO(
        rs.getInt("boat_id"),
                rs.getInt("ms_id"),
                rs.getString("manufacturer"),
                rs.getString("manufacture_year"),
                rs.getString("registration_num"),
                rs.getString("model"),
                rs.getString("boat_name"),
                rs.getString("sail_number"),
                rs.getBoolean("has_trailer"),
                rs.getString("length"),
                rs.getString("weight"),
                rs.getString("keel"),
                rs.getString("phrf"),
                rs.getString("draft"),
                rs.getString("beam"),
                rs.getString("lwl"),
                rs.getBoolean("aux"),
                rs.getInt("membership_id"),
                rs.getString("l_name"),
                rs.getString("f_name"),
                rs.getInt("boat_count"));
    }
}