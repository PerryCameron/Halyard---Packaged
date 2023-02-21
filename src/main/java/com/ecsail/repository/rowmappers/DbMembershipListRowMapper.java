package com.ecsail.repository.rowmappers;

import com.ecsail.dto.DbMembershipListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbMembershipListRowMapper implements RowMapper<DbMembershipListDTO> {

    @Override
    public DbMembershipListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DbMembershipListDTO dto = new DbMembershipListDTO(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getString("pojo_name"),
        rs.getString("data_type"),
        rs.getString("field_name"),
        rs.getInt("list_order"),
        rs.getBoolean("searchable"));
        return dto;
    }
}
