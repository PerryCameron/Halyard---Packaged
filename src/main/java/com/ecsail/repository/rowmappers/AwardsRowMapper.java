package com.ecsail.repository.rowmappers;




import com.ecsail.dto.AwardDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AwardsRowMapper implements RowMapper<AwardDTO> {
    @Override
    public AwardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AwardDTO(
                rs.getInt("AWARD_ID"),
                rs.getInt("p_id"),
                rs.getString("AWARD_YEAR"),
                rs.getString("AWARD_TYPE"));
    }
}
