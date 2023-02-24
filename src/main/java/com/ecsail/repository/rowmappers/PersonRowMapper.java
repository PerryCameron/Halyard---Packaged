package com.ecsail.repository.rowmappers;

import com.ecsail.dto.PersonDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<PersonDTO> {

    @Override
    public PersonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PersonDTO personDTO = new PersonDTO(
                rs.getInt("p_id"),
                rs.getInt("MS_ID"),
                rs.getInt("member_type"),
                rs.getString("F_NAME"),
                rs.getString("L_NAME"),
                rs.getString("birthday"),
                rs.getString("OCCUPATION"),
                rs.getString("BUSINESS"),
                rs.getBoolean("IS_ACTIVE"),
                rs.getString("NICK_NAME"),
                rs.getInt("OLD_MSID"));
        return personDTO;
    }
}