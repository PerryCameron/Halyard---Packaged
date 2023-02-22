package com.ecsail.repository.rowmappers;

import com.ecsail.dto.MembershipListRadioDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbMembershipListRadioRowMapper implements RowMapper<MembershipListRadioDTO> {

    @Override
    public MembershipListRadioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MembershipListRadioDTO dto = new MembershipListRadioDTO(
        rs.getInt("ID"),
                rs.getString("LABEL"),
                rs.getString("METHOD_NAME"),
                rs.getInt("LIST_ORDER"),
                rs.getInt("LIST"),
                rs.getBoolean("selected"));
        return dto;
    }
}
