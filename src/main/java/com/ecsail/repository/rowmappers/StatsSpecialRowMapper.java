package com.ecsail.repository.rowmappers;



import com.ecsail.dto.StatsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsSpecialRowMapper implements RowMapper<StatsDTO> {

    @Override
    public StatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StatsDTO(
                0,
                rs.getInt("YEAR"),
                rs.getInt("ACTIVE_MEMBERSHIPS"), // active membership
                rs.getInt("NON_RENEW"),
                rs.getInt("RETURN_MEMBERS"),
                rs.getInt("NEW_MEMBERS"),
                0,
                0,
                0,
                rs.getInt("FAMILY"),
                rs.getInt("REGULAR"),
                rs.getInt("SOCIAL"),
                rs.getInt("LAKE_ASSOCIATES"),
                rs.getInt("LIFE_MEMBERS"),
                rs.getInt("RACE_FELLOWS"),
                rs.getInt("STUDENT"),
                0,
                0);
    }
}