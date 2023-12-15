package com.ecsail.repository.rowmappers;

import com.ecsail.dto.MembershipDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MembershipRowMapper implements RowMapper<MembershipDTO> {

    @Override
    public MembershipDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MembershipDTO(
        rs.getInt("ms_id"),
        rs.getInt("p_id"),
        rs.getString("join_date"),
        rs.getString("mem_type"),
        rs.getString("address"),
        rs.getString("city"),
        rs.getString("state"),
        rs.getString("zip"));
    }
}