package com.ecsail.repository.rowmappers;


import com.ecsail.dto.BoatPhotosDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoatPhotosRowMapper implements RowMapper<BoatPhotosDTO> {
    @Override
    public BoatPhotosDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BoatPhotosDTO(
                rs.getInt("ID"),
                rs.getInt("BOAT_ID"),
                rs.getString("upload_date"),
                rs.getString("filename"),
                rs.getInt("file_number"),
                rs.getBoolean("default_image"));
    }


}
