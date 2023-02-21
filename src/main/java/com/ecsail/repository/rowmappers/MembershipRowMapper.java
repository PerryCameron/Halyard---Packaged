package com.ecsail.repository.rowmappers;

import com.ecsail.dto.MembershipDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MembershipRowMapper implements RowMapper<MembershipDTO> {

    @Override
    public MembershipDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MembershipDTO membership = new MembershipDTO();
        membership.setMsId(rs.getInt("ms_id"));
        membership.setPid(rs.getInt("p_id"));
        membership.setJoinDate(rs.getString("join_date"));
        membership.setMemType(rs.getString("mem_type"));
        membership.setAddress(rs.getString("address"));
        membership.setCity(rs.getString("city"));
        membership.setState(rs.getString("state"));
        membership.setZip(rs.getString("zip"));
        return membership;
    }


}