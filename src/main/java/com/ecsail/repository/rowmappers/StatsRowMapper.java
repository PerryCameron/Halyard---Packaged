package com.ecsail.repository.rowmappers;


import com.ecsail.dto.StatsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsRowMapper implements RowMapper<StatsDTO> {

    @Override
    public StatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StatsDTO(
                rs.getInt("STAT_ID"),
                rs.getInt("fiscal_year"),
                rs.getInt("ACTIVE_MEMBERSHIPS"),
                rs.getInt("NON_RENEW"),
                rs.getInt("RETURN_MEMBERS"),
                rs.getInt("NEW_MEMBERS"),
                rs.getInt("SECONDARY_MEMBERS"),
                rs.getInt("DEPENDANTS"),
                rs.getInt("NUMBER_OF_BOATS"),
                rs.getInt("FAMILY"),
                rs.getInt("REGULAR"),
                rs.getInt("SOCIAL"),
                rs.getInt("LAKE_ASSOCIATES"),
                rs.getInt("LIFE_MEMBERS"),
                rs.getInt("RACE_FELLOWS"),
                rs.getInt("STUDENT"),
                rs.getDouble("DEPOSITS"),
                rs.getDouble("INITIATION"));
    }
}