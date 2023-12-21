package com.ecsail.repository.rowmappers;

import com.ecsail.pdf.directory.PDF_Object_Officer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PDFObjectOfficerRowMapper implements RowMapper<PDF_Object_Officer> {

    @Override
    public PDF_Object_Officer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PDF_Object_Officer(
        rs.getString("f_name"),
        rs.getString("L_NAME"),
        rs.getString("off_type"),
        rs.getString("BOARD_YEAR"),
        rs.getString("off_year"));
    }
}