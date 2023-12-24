package com.ecsail.repository.rowmappers;


import com.ecsail.pdf.directory.Object_SlipInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SlipInfoRowMapper implements RowMapper<Object_SlipInfo> {

    @Override
    public Object_SlipInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Object_SlipInfo(
                rs.getString("slip_num"),
                rs.getInt("subleased_to"),
                rs.getString("f_name"),
                rs.getString("l_name")
        );
    }
}
