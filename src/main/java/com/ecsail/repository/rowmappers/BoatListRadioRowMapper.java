package com.ecsail.repository.rowmappers;

import com.ecsail.gui.tabs.boatlist.BoatListRadioDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoatListRadioRowMapper implements RowMapper<BoatListRadioDTO> {

    @Override
    public BoatListRadioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BoatListRadioDTO(
                rs.getInt("ID"),
                rs.getString("LABEL"),
                rs.getString("METHOD_NAME"),
                rs.getInt("LIST_ORDER"),
                rs.getInt("LIST"),
                rs.getBoolean("SELECTED"));
    }
}